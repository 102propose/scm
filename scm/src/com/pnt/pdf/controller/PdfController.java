package com.pnt.pdf.controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.StringReader;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.css.CssFile;
import com.itextpdf.tool.xml.css.StyleAttrCSSResolver;
import com.itextpdf.tool.xml.html.CssAppliers;
import com.itextpdf.tool.xml.html.CssAppliersImpl;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.parser.XMLParser;
import com.itextpdf.tool.xml.pipeline.css.CSSResolver;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;
import com.pnt.common.message.PntMessageSource;
import com.pnt.common.secure.SecureUtils;
import com.pnt.context.User;
import com.pnt.context.config.Configurator;
import com.pnt.po.PoInfoVO;
import com.pnt.po.service.PoService;

@Controller
@RequestMapping(value = "/pdf")
public class PdfController {
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	@Resource(name = "pntMessageSource")
    private PntMessageSource messageSource;
    
    //@Resource(name = "pdfService")
    //private PreviewService pdfService;
	
	@Resource(name = "poService")
    private PoService poService;
    
    @Resource(name = "printWriterView")
    private View printWriterView;

    @RequestMapping(value = "/savePdf.do")
    public View page(HttpServletRequest request, HttpServletResponse response, Model model, HttpSession session) throws Exception {
        
    	String url = this.getClass().getResource("").getPath();
    	
    	String dir = "";
    	if(StringUtils.contains(url, "metadata")){
    		//이클립스 경로
    		dir = url.substring(1, url.indexOf(".metadata"))+"scm/WebContent";    	
    	} else {
    		//서버에서 경로
    		dir = url.substring(1, url.indexOf("WEB-INF"));    
    	}
    	
    	String contextPath = request.getServletContext().getRealPath("/");
    	
    	// Document 생성
    	Document document = new Document(PageSize.A4, 20, 20, 20, 20); // 용지 및 여백 설정
    	
    	// PdfWriter 생성
    	String pdfdir = "/tempPdf/po/poPdf.pdf";
    	PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dir+pdfdir)); // 바로 다운로드.    	
    	writer.setInitialLeading(12.5f); 

    	// 파일 다운로드 설정
    	response.setContentType("application/pdf");
    	String fileName = URLEncoder.encode("한글파일명", "UTF-8"); // 파일명이 한글일 땐 인코딩 필요
    	response.setHeader("Content-Transper-Encoding", "binary");
    	response.setHeader("Content-Disposition", "inline; filename=" + fileName + ".pdf");    	 

        document.open();
        
        XMLWorkerHelper helper = XMLWorkerHelper.getInstance();    	     

    	// CSS
    	CSSResolver cssResolver = new StyleAttrCSSResolver();
    	CssFile cssFile = helper.getCSS(new FileInputStream(contextPath+"/css/pdf.css"));
    	cssResolver.addCss(cssFile);

    	// HTML, 폰트 설정
    	XMLWorkerFontProvider fontProvider = new XMLWorkerFontProvider(XMLWorkerFontProvider.DONTLOOKFORFONTS);
    	fontProvider.register(contextPath+"/font/arialbd.ttf", "ArialBd"); 
    	fontProvider.register(contextPath+"/font/malgun.ttf", "MalgunGothic"); // MalgunGothic은 alias,
    	CssAppliers cssAppliers = new CssAppliersImpl(fontProvider);    	 

    	HtmlPipelineContext htmlContext = new HtmlPipelineContext(cssAppliers);
    	htmlContext.setTagFactory(Tags.getHtmlTagProcessorFactory());    	 

    	// Pipelines
    	PdfWriterPipeline pdf = new PdfWriterPipeline(document, writer);
    	HtmlPipeline html = new HtmlPipeline(htmlContext, pdf);
    	CssResolverPipeline css = new CssResolverPipeline(cssResolver, html);

    	XMLWorker worker = new XMLWorker(css, true);
    	XMLParser xmlParser = new XMLParser(worker, Charset.forName("UTF-8"));    	 
    	
    	//구매주문서 데이터 loading..
    	String po_no = SecureUtils.getParameter(request, "po_no", null);
        String dtFr = SecureUtils.getParameter(request, "df", null);
        String dtTo = SecureUtils.getParameter(request, "dt", null);
        
        dtFr = dtFr.replaceAll("-", "");
        dtTo = dtTo.replaceAll("-", "");
        
        //pdf를 열었을때, 최초열람일자 저장,
        User user = (User)session.getAttribute(Configurator.Constants.SESSION_SIGNED_USER);
        //if(!"admin".equals(user)){
        if(user.getId() != "admin"){
        	this.poService.firstOpenPdf(po_no);
        }
       
    	
        
    	
    	//pdf 데이터 가져오는 부분.
        PoInfoVO headData = this.poService.selectPoHeadInfo(po_no, dtFr, dtTo);
        List<PoInfoVO> data = this.poService.selectPoInfo(po_no, dtFr, dtTo);        
        int pdfRepeat = (int)Math.ceil(data.size()/10)+1; //한페이지 테이블 row 10개로 함. 42 row일경우 5페이지가 나옴.
        if((data.size() != 0 && pdfRepeat == 0) || data.size() <= 10){
        	pdfRepeat = 1; //데이터가 없을땐 1페이지만 출력한다.
        }        
        
        int a, b = 0;    	
    	int vat = 0;
    	int total = 0;    	
    	
    	for(int num=0; num < data.size(); num++){
    		total +=  Integer.parseInt(data.get(num).getAm_ex().replaceAll(",",""));  //전체합계    	
    		vat += Integer.parseInt(data.get(num).getVat().replaceAll(",",""));  //전체vat
    	}
    	
    	
    	DecimalFormat df = new DecimalFormat("#,##0"); //1000단위 콤마 
    	
        // 폰트 설정에서 별칭으로 줬던 "MalgunGothic"을 html 안에 폰트로 지정한다.
        String htmlStr = "<html><head></head><body>";
        for( a=0; a < pdfRepeat; ) {
        
        htmlStr += "<table border=0 style='width: 740px; height: 63px'> "
    	+"		<tr>                                                                                                                              "
    	+"		  		<td style='height: 0px; text-align:left;'>                                                                                                                                        "
    	+"	    			<p style='font-family: MalgunGothic;'></p> "
    	+"		  		</td>	                                                                                                                                      "    	
    	+"		  		<td style='height: 0px; text-align:left;'>                                                                                                                                        "
    	+"	    			<p style='font-family: MalgunGothic;'></p> "
    	+"		  		</td>	                                                                                                                                      "    	
    	+"		</tr>                                                                                                                                           "
    	+"		<tr> "     
    	+"	  			<td style='height: 60px;'>                                                                                       "    	
    	+"						<img src='"+contextPath+"/images/logo/preview_logo_55.bmp' />   "
    	+"				</td>                                                                                                                         "    	
    	+"				<td style='height: 60px; text-align:right;'>                                                                                                              "
    	+"	 	 			<b style='font-family: ArialBd; font-size:40px; color: rgb(139, 150, 169);'>PURCHASE</b>                                                   "
    	+"			 		<b style='font-family: ArialBd; font-size:40px; color: rgb(139, 150, 169);'>ORDER</b>                                                      "
    	+"				</td>			                                                                                                                                  "
    	+"		</tr>                                                                                                                                           "
    	+"		<tr>                                                                                                                                            "
    	+"		  		<td style='height: 40px; text-align:left;'>                                                                                                                                        "
    	+"		  	  		<p style='font-family: ArialBd; font-size:30px; color: rgb(91, 103, 129);'>RaycopKorea Inc.</p>                                                       "
    	+"		  		</td>	                                                                                                                                      "
    	+"		  		<td style='height: 40px; text-align:left;'>                                                                                                                                        "
    	+"		  	  		<p style='font-family: ArialBd; font-size:30px; color: rgb(91, 103, 129);'></p>                                                       "
    	+"		  		</td>	                                                                                                                                      "
    	+"		</tr>                                                                                                                                           "
    	+"		<tr>                                                                                                                                            "
    	+"		  		<td style='width: 400px; height: 20px;'>                                                                                                                                        "
    	+"		  			<p style='font-family: MalgunGothic;'>인천광역시 남동구 청능대로 450 (고잔동 104B-1L)</p>                                                  "
    	+"		  			<p style='font-family: MalgunGothic;'>&nbsp;&nbsp;Tel: +82-32-814-1760. &nbsp; Fax: +82-32-814-8768</p>                            "
    	+"		  		</td>	                                                                                                                                      "
    	+"		  		<td style='height: 20px; text-align:left;'>                                                                                                                                        "
    	+"		  	  		<p style='font-family: ArialBd; font-size:30px; color: rgb(91, 103, 129);'></p>                                                       "
    	+"		  		</td>	                                                                                                                                      "
    	+"		</tr>                                                                                                                                            "
    	+"</table>"    	
    	+"<table border=0 style='height: 1px;'>  "    			
    	+"	    <tr><td style='height: 1px;'><p style='font-family: MalgunGothic; font-size:8px;'>&nbsp;</p></td></tr>                                                                                              "    	
    	+"</table>          "
    	+"<table border=0 style='height: 1px;'>  "    			
    	+"	    <tr><td style='height: 1px;'><p style='font-family: MalgunGothic; font-size:8px;'>&nbsp;</p></td></tr>                                                                                              "    	
    	+"</table>          "    	
    	+"<table border=0>          "
    	+"	    <tr>                                                                                             "
    	+"	    	<td>                                                                                           "
    	+"	      		<table border=0 style='width: 498px;'>                                                    "
    	+"	      			<tr>                                                                                       "
    	+"	      				<td style='width: 180px; height: 21px; text-align: right;'><p style='font-family: MalgunGothic;'>Purchase Order No.&nbsp;&nbsp;</p></td>"
    	+ "                    <td style='width: 318px; height: 21px; text-align: left;'><p style='font-family: MalgunGothic;'>"+ headData.getNo_po() +"</p></td>"
    	+"	      			</tr>                                                                                      "
    	+"	      			<tr>                                                                                       "
    	+"	      				<td style='width: 180px; height: 21px; text-align: right;'><p style='font-family: MalgunGothic;'>발주일자 :&nbsp;&nbsp;</p></td> "
    	+ "                    <td style='width: 318px; height: 21px; text-align: left;'><p style='font-family: MalgunGothic;'>"+ headData.getDt_po() +"</p></td> "
    	+"	      			</tr>                                                                                      "
    	+"	      			<tr>                                                                                       "
    	+"	      				<td style='width: 180px; height: 21px; text-align: right;'><p style='font-family: MalgunGothic;'>담 당 자 :&nbsp;&nbsp;</p></td>"
    	+ "                    <td style='width: 318px; height: 21px; text-align: left;'><p style='font-family: MalgunGothic;'></p></td>"
    	+"	      			</tr>                                                                                      "
    	+"	      		</table>                                                                                     "
    	+"		    </td>                                                                                              "
    	+"		    <td>                                                                                               "
    	+"	      		<table border=1 style='width: 240px;'>                                                    "
    	+"	      			<tr>                                                                                       "
    	+"	      				<td rowspan='2' style='width: 0px; height: 19px; text-align: center;'><b style='font-family: MalgunGothic;'>결 제</b></td>"
    	+ "						<td style='width: 78px; height: 19px; text-align: center;'><b style='font-family: MalgunGothic;'>작 성</b></td>"    	
    	+ "						<td style='width: 78px; height: 19px; text-align: center;'><b style='font-family: MalgunGothic;'>검 토</b></td>"
    	+ "						<td style='width: 78px; height: 19px; text-align: center;'><b style='font-family: MalgunGothic;'>승 인</b></td>"
    	+"	      			</tr>                                                                                      "
    	+"	      			<tr>                                                                                       "
    	+"	      				<td style='width: 78px; height: 50px;'></td>"    	
    	+ "                    <td style='width: 78px; height: 50px;'></td>                                "
    	+ "                    <td style='width: 78px; height: 50px;'></td>                                "
    	+"	      			</tr>                                                                                      "    	
    	+"	      		</table>                                                                                     "
    	+"		    </td>                                                                                              "
    	+"		</tr>                                                                                                "
    	+"</table>"
    	+"<table border=0 style='height: 1px;'>  "    			
    	+"	    <tr><td style='height: 1px;'><p style='font-family: MalgunGothic; font-size:8px;'>&nbsp;</p></td></tr>                                                                                              "    	
    	+"</table>          "    	   	
    	+"<table border=0>          "
    	+"	    <tr style='background-color: rgb(195, 195, 195);'>                                                                                             "
    	+"		    <td colspan='2' style='width: 185px; height: 19px;'><p style='font-family: MalgunGothic; font-size:16px;'>To.</p></td>                                                                                     "    	
    	+"		    <td colspan='2' style='width: 185px; height: 19px;'><p style='font-family: MalgunGothic; font-size:16px;'>Ship To.</p></td>"    	
    	+"	    </tr>                                                                                             "
    	+"	    <tr>                                                                                             "
    	+"		    <td style='width: 185px; height: 19px;'><p style='font-family: MalgunGothic; font-size: 14px;'>"+ headData.getLn_partner() +"</p></td>"  	
    	+"		    <td style='width: 185px; height: 19px;'></td>"
    	+"		    <td style='width: 185px; height: 19px;'><p style='font-family: MalgunGothic; font-size: 14px;'>RAYCOP KOREA INC.</p></td>"
    	+"		    <td style='width: 185px; height: 19px;'></td>"
    	+"	    </tr>                                                                                             "
    	+"	    <tr>                                                                                             "
    	+"		    <td colspan='2' style='width: 185px; height: 19px;'><p style='font-family: MalgunGothic; font-size: 12px;'>"+ headData.getDc_ads1_h() +"</p></td>"    	
    	+"		    <td colspan='2' style='width: 185px; height: 19px;'><p style='font-family: MalgunGothic; font-size: 14px;'>450, Cheongneungdae-ro Namdong-Gu, Incheon, Korea</p></td>"    	
    	+"	    </tr>                                                                                             "
    	+"	    <tr>                                                                                             "
    	+"		    <td style='width: 185px; height: 19px;'><p style='font-family: MalgunGothic; font-size: 14px;'>Tel: "+headData.getNo_tel() +"</p></td>"
    	+"		    <td style='width: 185px; height: 19px;'><p style='font-family: MalgunGothic; font-size: 14px;'>Fax: "+headData.getNo_fax() +"</p></td>"
    	+"		    <td style='width: 185px; height: 19px;'><p style='font-family: MalgunGothic; font-size: 14px;'>Tel: +82-32-814-1760,</p></td>"
    	+"		    <td style='width: 185px; height: 19px;'><p style='font-family: MalgunGothic; font-size: 14px;'>Fax: +82-32-814-8768</p></td>"
    	+"	    </tr>                                                                                             "
    	+"	    <tr>                                                                                             "
    	+"		    <td style='width: 185px; height: 19px;'><p style='font-family: MalgunGothic; font-size:14px;'>"+ headData.getTp_job() +"</p></td>"
    	+"		    <td colspan='3' style='width: 185px; height: 19px;'><p style='font-family: MalgunGothic; font-size:14px;'>"+ headData.getCls_job() +"</p></td>"
    	+"	    </tr>                                                                                             "
		+"</table>          "
		+"<table border=0 style='height: 1px;'>  "    			
    	+"	    <tr><td style='height: 1px;'><p style='font-family: MalgunGothic; font-size:8px;'>&nbsp;</p></td></tr>                                                                                              "    	
    	+"</table>          "
		+"<table border=0>          "
    	+"	    <tr style='background-color: rgb(195, 195, 195);'>                                                                                             "
    	+"		    <td style='width: 123px; height: 19px; border-color: white; text-align: center;'><p style='font-family: MalgunGothic; font-size:16px;'>지급조건</p></td>                                                                                     "    	
    	+"		    <td style='width: 123px; height: 19px; border-color: white; text-align: center;'><p style='font-family: MalgunGothic; font-size:16px;'>부가세</p></td>                                                                                     "
    	+"		    <td style='width: 123px; height: 19px; border-color: white; text-align: center;'><p style='font-family: MalgunGothic; font-size:16px;'>환종</p></td>                                                                                     "
    	+"		    <td style='width: 185px; height: 19px; border-color: white; text-align: center;'><p style='font-family: MalgunGothic; font-size:16px;'>입고창고</p></td>                                                                                     "
    	+"		    <td style='width: 186px; height: 19px; border-color: white; text-align: center;'><p style='font-family: MalgunGothic; font-size:16px;'>적요</p></td>                                                                                     "
    	+"	    </tr>                                                                                             "
    	+"	    <tr>                                                                                             "
    	+"		    <td style='width: 123px; height: 26px; text-align: center;'><p style='font-family: MalgunGothic;'></p></td>                                                                                     "    	
    	+"		    <td style='width: 123px; height: 26px; text-align: center;'><p style='font-family: MalgunGothic;'>"+ df.format(vat) +"</p></td> "
    	+"		    <td style='width: 123px; height: 26px; text-align: center;'><p style='font-family: MalgunGothic;'></p></td>                                                                                     "
    	+"		    <td style='width: 185px; height: 26px; text-align: center;'><p style='font-family: MalgunGothic;'></p></td>                                                                                     "
    	+"		    <td style='width: 186px; height: 26px; text-align: center;'><p style='font-family: MalgunGothic;'></p></td>                                                                                     "
    	+"	    </tr>                                                                                             "    	
		+"</table>          "		
		+"<table border=1 >          "    	
    	+"	    <tr style='background-color: rgb(195, 195, 195);'>                                                                                             "
    	+"						<td style='width: 120px; height: 19px; text-align: center;'><p style='font-family: MalgunGothic; font-size: 14px;'>품목코드</p></td>"
    	+"						<td style='width: 300px; height: 19px; text-align: center;'><p style='font-family: MalgunGothic; font-size: 14px;'>품 목 명</p></td>"
    	+"						<td style='width: 100px; height: 19px; text-align: center;'><p style='font-family: MalgunGothic; font-size: 14px;'>수&nbsp;&nbsp;&nbsp;&nbsp;량</p></td>"
    	+"						<td style='width: 80px; height: 19px; text-align: center;'><p style='font-family: MalgunGothic; font-size: 14px;'>단&nbsp;&nbsp;&nbsp;&nbsp;가</p></td>"
    	+"						<td style='width: 130px; height: 19px; text-align: center;'><p style='font-family: MalgunGothic; font-size: 14px;'>금&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;액</p></td>"
    	+"	    </tr>                                                                                             "
    	+"	    <tr style='background-color: rgb(195, 195, 195);'>                                                                                             "
    	+"						<td style='width: 120px; height: 19px; text-align: center;'><p style='font-family: MalgunGothic; font-size: 14px;'>단&nbsp;&nbsp;&nbsp;&nbsp;위</p></td>"
    	+"						<td style='width: 300px; height: 19px; text-align: center;'><p style='font-family: MalgunGothic; font-size: 14px;'>규&nbsp;&nbsp;&nbsp;&nbsp;격</p></td>"
    	+"						<td style='width: 100px; height: 19px; text-align: center;'><p style='font-family: MalgunGothic; font-size: 14px;'>납기일자</p></td>"
    	+"						<td style='width: 80px; height: 19px; text-align: center;'><p style='font-family: MalgunGothic; font-size: 14px;'>비&nbsp;&nbsp;&nbsp;고1</p></td>"
    	+"						<td style='width: 130px; height: 19px; text-align: center;'><p style='font-family: MalgunGothic; font-size: 14px;'>비&nbsp;&nbsp;&nbsp;&nbsp;고2</p></td>"
    	+"	    </tr>                                                                                             ";    	
        a++; 
        int subtotal = 0;
        if( data.size() < 11 ){        	
        	for( b=(a-1)*10; b < data.size(); b++){
        		htmlStr += "<tr>";
        		htmlStr += "<td style='width: 120px; height: 19px; text-align: left;'><p style='font-family: MalgunGothic; font-size:14px;'>"+data.get(b).getCd_item()+"</p></td>";
        		htmlStr += "<td style='width: 300px; height: 19px; text-align: left;'><p style='font-family: MalgunGothic; font-size:14px;'>"+data.get(b).getNm_item()+"</p></td>";
        		htmlStr += "<td style='width: 100px; height: 19px; text-align: right;'><p style='font-family: MalgunGothic; font-size:14px;'>"+data.get(b).getQt_po_nm()+"</p></td>";
        		htmlStr += "<td style='width: 80px; height: 19px; text-align: right;'><p style='font-family: MalgunGothic; font-size:14px;'>"+data.get(b).getUm_ex()+"</p></td>";
        		htmlStr += "<td style='width: 130px; height: 19px; text-align: right;'><p style='font-family: MalgunGothic; font-size:14px;'>"+data.get(b).getAm_ex()+"</p></td>";    		
        		htmlStr += "</tr>";
        		htmlStr += "<tr>";
        		htmlStr += "<td style='width: 120px; height: 19px; text-align: left;'><p style='font-family: MalgunGothic; font-size:14px;'>"+data.get(b).getCd_unit_mm()+"</p></td>";
        		if((data.get(b).getStnd_item()).length() < 35){ //pdf 규격정보가 30자이상일때, 폰트사이즈 12 ---> 10 변경.
        			htmlStr += "<td style='width: 300px; height: 19px; text-align: left;'><p style='font-family: MalgunGothic; font-size:12px;'>"+data.get(b).getStnd_item()+"</p></td>";
        		}else{
        			htmlStr += "<td style='width: 300px; height: 19px; text-align: left;'><p style='font-family: MalgunGothic; font-size:9px;'>"+data.get(b).getStnd_item()+"</p></td>";
        		}
        		htmlStr += "<td style='width: 100px; height: 19px; text-align: right;'><p style='font-family: MalgunGothic; font-size:14px;'>"+data.get(b).getDt_limit()+"</p></td>";
        		htmlStr += "<td style='width: 80px; height: 19px; text-align: right;'><p style='font-family: MalgunGothic; font-size:14px;'>"+data.get(b).getNm_cls_item()+"</p></td>";
        		htmlStr += "<td style='width: 130px; height: 19px; text-align: right;'><p style='font-family: MalgunGothic; font-size:14px;'>"+data.get(b).getDc_rmk21()+"</p></td>";    		
        		htmlStr += "</tr>";
        		
        		subtotal += Integer.parseInt(data.get(b).getAm_ex().replaceAll(",",""));        		
        	}
        	
        	for(int i=0; i < 10-data.size(); i++){
	    		htmlStr += "<tr>";
	    		htmlStr += "<td style='width: 120px; height: 19px; text-align: left;'><p style='font-family: MalgunGothic; font-size:14px;'></p></td>";
	    		htmlStr += "<td style='width: 300px; height: 19px; text-align: left;'><p style='font-family: MalgunGothic; font-size:14px;'></p></td>";
	    		htmlStr += "<td style='width: 100px; height: 19px; text-align: right;'><p style='font-family: MalgunGothic; font-size:14px;'></p></td>";
	    		htmlStr += "<td style='width: 80px; height: 19px; text-align: right;'><p style='font-family: MalgunGothic; font-size:14px;'></p></td>";
	    		htmlStr += "<td style='width: 130px; height: 19px; text-align: right;'><p style='font-family: MalgunGothic; font-size:14px;'></p></td>";    		
	    		htmlStr += "</tr>";
	    		htmlStr += "<tr>";
	    		htmlStr += "<td style='width: 120px; height: 19px; text-align: left;'><p style='font-family: MalgunGothic; font-size:14px;'></p></td>";
	    		htmlStr += "<td style='width: 300px; height: 19px; text-align: left;'><p style='font-family: MalgunGothic; font-size:14px;'></p></td>";
	    		htmlStr += "<td style='width: 100px; height: 19px; text-align: right;'><p style='font-family: MalgunGothic; font-size:14px;'></p></td>";
	    		htmlStr += "<td style='width: 80px; height: 19px; text-align: right;'><p style='font-family: MalgunGothic; font-size:14px;'></p></td>";
	    		htmlStr += "<td style='width: 130px; height: 19px; text-align: right;'><p style='font-family: MalgunGothic; font-size:14px;'></p></td>";    		
	    		htmlStr += "</tr>";
	    	}
	    	
        } else {
        	int end =0;
        	if(data.size() < a*10){
        		end = data.size();
        	} else {
        		end = a*10;
        	}        	
        	for( b=(a-1)*10; b < end; b++){        		
        		htmlStr += "<tr>";
        		htmlStr += "<td style='width: 120px; height: 19px; text-align: left;'><p style='font-family: MalgunGothic; font-size:14px;'>"+data.get(b).getCd_item()+"</p></td>";
        		htmlStr += "<td style='width: 300px; height: 19px; text-align: left;'><p style='font-family: MalgunGothic; font-size:14px;'>"+data.get(b).getNm_item()+"</p></td>";
        		htmlStr += "<td style='width: 100px; height: 19px; text-align: right;'><p style='font-family: MalgunGothic; font-size:14px;'>"+data.get(b).getQt_po_nm()+"</p></td>";
        		htmlStr += "<td style='width: 80px; height: 19px; text-align: right;'><p style='font-family: MalgunGothic; font-size:14px;'>"+data.get(b).getUm_ex()+"</p></td>";
        		htmlStr += "<td style='width: 130px; height: 19px; text-align: right;'><p style='font-family: MalgunGothic; font-size:14px;'>"+data.get(b).getAm_ex()+"</p></td>";    		
        		htmlStr += "</tr>";
        		htmlStr += "<tr>";
        		htmlStr += "<td style='width: 120px; height: 19px; text-align: left;'><p style='font-family: MalgunGothic; font-size:14px;'>"+data.get(b).getCd_unit_mm()+"</p></td>";
        		if((data.get(b).getStnd_item()).length() < 35){ //pdf 규격정보가 30자이상일때, 폰트사이즈 12 ---> 10 변경.
        			htmlStr += "<td style='width: 300px; height: 19px; text-align: left;'><p style='font-family: MalgunGothic; font-size:12px;'>"+data.get(b).getStnd_item()+"</p></td>";
        		}else{
        			htmlStr += "<td style='width: 300px; height: 19px; text-align: left;'><p style='font-family: MalgunGothic; font-size:9px;'>"+data.get(b).getStnd_item()+"</p></td>";
        		}        		
        		htmlStr += "<td style='width: 100px; height: 19px; text-align: right;'><p style='font-family: MalgunGothic; font-size:14px;'>"+data.get(b).getDt_limit()+"</p></td>";
        		htmlStr += "<td style='width: 80px; height: 19px; text-align: right;'><p style='font-family: MalgunGothic; font-size:14px;'>"+data.get(b).getNm_cls_item()+"</p></td>";
        		htmlStr += "<td style='width: 130px; height: 19px; text-align: right;'><p style='font-family: MalgunGothic; font-size:14px;'>"+data.get(b).getDc_rmk21()+"</p></td>";    		
        		htmlStr += "</tr>";      
        		
        		subtotal += Integer.parseInt(data.get(b).getAm_ex().replaceAll(",",""));
        	}
        	
        	if( data.size() < a*10 && (end-b) < 10){
        		for(int i=0; i < (a*10-end); i++){
    	    		htmlStr += "<tr>";
    	    		htmlStr += "<td style='width: 120px; height: 19px; text-align: left;'><p style='font-family: MalgunGothic; font-size:14px;'></p></td>";
    	    		htmlStr += "<td style='width: 300px; height: 19px; text-align: left;'><p style='font-family: MalgunGothic; font-size:14px;'></p></td>";
    	    		htmlStr += "<td style='width: 100px; height: 19px; text-align: right;'><p style='font-family: MalgunGothic; font-size:14px;'></p></td>";
    	    		htmlStr += "<td style='width: 80px; height: 19px; text-align: right;'><p style='font-family: MalgunGothic; font-size:14px;'></p></td>";
    	    		htmlStr += "<td style='width: 130px; height: 19px; text-align: right;'><p style='font-family: MalgunGothic; font-size:14px;'></p></td>";    		
    	    		htmlStr += "</tr>";
    	    		htmlStr += "<tr>";
    	    		htmlStr += "<td style='width: 120px; height: 19px; text-align: left;'><p style='font-family: MalgunGothic; font-size:14px;'></p></td>";
    	    		htmlStr += "<td style='width: 300px; height: 19px; text-align: left;'><p style='font-family: MalgunGothic; font-size:14px;'></p></td>";
    	    		htmlStr += "<td style='width: 100px; height: 19px; text-align: right;'><p style='font-family: MalgunGothic; font-size:14px;'></p></td>";
    	    		htmlStr += "<td style='width: 80px; height: 19px; text-align: right;'><p style='font-family: MalgunGothic; font-size:14px;'></p></td>";
    	    		htmlStr += "<td style='width: 130px; height: 19px; text-align: right;'><p style='font-family: MalgunGothic; font-size:14px;'></p></td>";    		
    	    		htmlStr += "</tr>";
    	    	}
        	}
        }       
       
    	 	
    	
    	htmlStr += "</table>"
    	+"<table border=0 style='height: 2px;'>  "    			
    	+"	    <tr><td style='height: 1px;'><p style='font-family: MalgunGothic; font-size:8px;'>&nbsp;</p></td></tr>                                                                                              "    	
    	+"</table>          "
    	+"<table border=0 >          "  
		+"	    <tr>                                                                                             "
		+"		    <td style='width: 30px; height: 19px; text-align: right;'><p style='font-family: MalgunGothic; font-size:12px;'>* 주1.</p></td>  "
		+"		    <td style='width: 390px; height: 19px; text-align: left;'><p style='font-family: MalgunGothic; font-size:12px;'>&nbsp;수입검사는 Sample 검사로 진행되며 부적합 발생 시 즉시 귀사에서</p></td>  "		
		+"		    <td style='width: 230px; height: 19px; text-align: right;'><p style='font-family: MalgunGothic; font-size:14px;'>Subtotal.&nbsp;&nbsp;&nbsp;&nbsp;</p></td>  "		
		+"		    <td style='width: 90px; height: 19px; text-align: right;'><p style='font-family: MalgunGothic; font-size:14px;'>" + df.format(subtotal) +"</p></td>  "		
		+"	    </tr> "
		+"	    <tr>                                                                                             "
		+"		    <td style='width: 30px; height: 19px; text-align: right;'><p style='font-family: MalgunGothic; font-size:12px;'></p></td>  "
		+"		    <td style='width: 390px; height: 19px; text-align: left;'><p style='font-family: MalgunGothic; font-size:12px;'>&nbsp;선별하여야 하며 불가시 전체를 반품할 예정입니다.</p></td>  "		
		+"		    <td style='width: 230px; height: 19px; text-align: right;'><p style='font-family: MalgunGothic; font-size:14px;'>VAT.&nbsp;&nbsp;&nbsp;&nbsp;</p></td>  "
		+"		    <td style='width: 90px; height: 19px; text-align: right;'><p style='font-family: MalgunGothic; font-size:14px;'>"+ df.format(vat) +"</p></td>  "
		+"	    </tr>                                                                                             "		                                                                                       
		+"	    <tr>                                                                                             "
		+"		    <td style='width: 30px; height: 19px; text-align: right;'><p style='font-family: MalgunGothic; font-size:12px;'>* 주2.</p></td>  "
		+"		    <td style='width: 390px; height: 19px; text-align: left;'><p style='font-family: MalgunGothic; font-size:12px;'>&nbsp;부득이한 사정으로 납기가 지연되는 경우 본 조직의</p></td>  "		
		+"		    <td style='width: 230px; height: 19px; text-align: right;'><p style='font-family: MalgunGothic; font-size:14px;'>Total.&nbsp;&nbsp;&nbsp;&nbsp;</p></td>  "
		+"		    <td style='width: 90px; height: 19px; text-align: right;'><p style='font-family: MalgunGothic; font-size:14px;'>"+ df.format(total) +"</p></td>  "
		+"	    </tr>                                                                                             "
		+"	    <tr>                                                                                             "
		+"		    <td style='width: 30px; height: 19px; text-align: right;'><p style='font-family: MalgunGothic; font-size:12px;'></p></td>  "
		+"		    <td style='width: 390px; height: 19px; text-align: left;'><p style='font-family: MalgunGothic; font-size:12px;'>&nbsp;구매담당자와 반드시 협의하여야 합니다.</p></td>  "		
		+"		    <td style='width: 230px; height: 19px; text-align: right;'><p style='font-family: MalgunGothic; font-size:12px;'></p></td>  "
		+"		    <td style='width: 90px; height: 19px; text-align: right;'><p style='font-family: MalgunGothic; font-size:12px;'></p></td>  "
		+"	    </tr>                                                                                             "
		+"	    <tr>                                                                                             "
		+"		    <td style='width: 30px; height: 19px; text-align: right;'><p style='font-family: MalgunGothic; font-size:12px;'></p></td>  "
		+"		    <td style='width: 390px; height: 19px; text-align: left;'><p style='font-family: MalgunGothic; font-size:12px;'></p></td>  "		
		+"		    <td style='width: 230px; height: 19px; text-align: right;'><p style='font-family: MalgunGothic; font-size:14px;'></p></td>  "
		+"		    <td style='width: 90px; height: 19px; text-align: right;'><p style='font-family: MalgunGothic; font-size:14px;'></p></td>  "
		+"	    </tr> "
		+"	    <tr>                                                                                             "
		+"		    <td style='width: 30px; height: 19px; text-align: right;'><p style='font-family: MalgunGothic; font-size:12px;'></p></td>  "
		+"		    <td style='width: 390px; height: 19px; text-align: left;'><p style='font-family: MalgunGothic; font-size:12px;'></p></td>  "		
		+"		    <td style='width: 230px; height: 19px; text-align: right;'><p style='font-family: MalgunGothic; font-size:12px;'></p></td>  "
		+"		    <td style='width: 90px; height: 19px; text-align: right;'><p style='font-family: MalgunGothic; font-size:12px;'></p></td>  "
		+"	    </tr>                                                                                             "
		+"	    <tr>                                                                                             "
		+"		    <td style='width: 30px; height: 19px; text-align: right;'><p style='font-family: MalgunGothic; font-size:12px;'></p></td>  "
		+"		    <td style='width: 390px; height: 19px; text-align: left;'><p style='font-family: MalgunGothic; font-size:12px;'></p></td>  "		
		+"		    <td style='width: 230px; height: 19px; text-align: right;'><p style='font-family: MalgunGothic; font-size:12px;'></p></td>  "
		+"		    <td style='width: 90px; height: 19px; text-align: right;'><p style='font-family: MalgunGothic; font-size:12px;'></p></td>  "
		+"	    </tr>                                                                                             "
		+"	    <tr>                                                                                             "
		+"		    <td colspan='4' style='height: 5px; text-align: right;'><p style='font-family: MalgunGothic; font-size:12px;'></p></td>  "
		+"	    </tr>                                                                                             "		
		+"	    <tr>                                                                                             "
		+"		    <td style='width: 30px; height: 10px; text-align: right;'><p style='font-family: MalgunGothic; font-size:12px;'></p></td>  "
		+"		    <td style='width: 390px; height: 10px; text-align: left;'><p style='font-family: MalgunGothic; font-size:12px;'></p></td>  "		
		+"		    <td style='width: 230px; height: 10px; text-align: right;'><p style='font-family: MalgunGothic; font-size:12px;'></p></td>  "
		+"		    <td style='width: 90px; height: 10px; text-align: left;'><p style='font-family: MalgunGothic; font-size:12px;'></p></td>  "
		+"	    </tr>                                                                                             "
		+"	    <tr>                                                                                             "
		+"		    <td style='width: 30px; height: 14px; text-align: right;'><p style='font-family: MalgunGothic; font-size:12px;'></p></td>  "
		+"		    <td style='width: 390px; height: 14px; text-align: left;'><p style='font-family: MalgunGothic; font-size:12px;'></p></td>  "		
		+"		    <td style='width: 230px; height: 14px; text-align: right;'><p style='font-family: MalgunGothic; font-size:14px;'>Authorized Signature</p></td>  "
		+"		    <td style='width: 90px; height: 14px; text-align: left;'><p style='font-family: MalgunGothic; font-size:14px;'>　Of</p></td>  "
		+"	    </tr>                                                                                             "		
		+"	    <tr>                                                                                             "
		+"		    <td style='width: 30px; height: 15px; text-align: right;'><p style='font-family: MalgunGothic; font-size:12px;'></p></td>  "
		+"		    <td style='width: 390px; height: 15px; text-align: center;'><p style='font-family: MalgunGothic; font-size:14px;'>Confirmed sign</p></td>  "		
		+"		    <td style='width: 230px; height: 15px; text-align: right;'><p style='font-family: MalgunGothic; font-size:14px;'>RaycopKorea Inc.</p></td>  "
		+"		    <td style='width: 90px; height: 15px; text-align: right;'><p style='font-family: MalgunGothic; font-size:12px;'></p></td>  "
		+"	    </tr>                                                                                             "
		+"	    <tr>                                                                                             "
		+"			<td colspan='4' style='height: 1px;'><hr style='width: 800px;'></hr></td>          "
		+"	    </tr>                                                                                             "	
		+"</table>          ";		                                                                   
        }
		htmlStr +="</body></html>";
        
    	StringReader strReader = new StringReader(htmlStr);
    	xmlParser.parse(strReader);    	 

    	document.close();
    	writer.close();  
    	    	
    	model.addAttribute("OUT_DATA", pdfdir);        
        
        return this.printWriterView;
    }
        
}
