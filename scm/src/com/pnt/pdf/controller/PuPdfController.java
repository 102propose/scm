package com.pnt.pdf.controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.StringReader;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.DefaultEditorKit.InsertContentAction;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfPTable;
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
import com.pnt.po.PoInfoVO;
import com.pnt.po.service.PoService;
import com.pnt.pu.PuInfoVO;
import com.pnt.pu.service.PuService;

@Controller
@Service("puPdf")
@RequestMapping(value = "/puPdf")
public class PuPdfController {
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	@Resource(name = "pntMessageSource")
    private PntMessageSource messageSource;
    
    @Resource(name = "puService")
    private PuService puService;
	
    public String puPdfSave(HttpServletRequest request, HttpServletResponse response, String jsonArrayData) throws Exception{
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
    	Document document = new Document(PageSize.A4, 10, 10, 10, 10); // 용지 및 여백 설정
    	
    	// PdfWriter 생성
    	String pdfdir = "/tempPdf/pu/puPdf.pdf";
    	PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dir+pdfdir));        
    	
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
     	fontProvider.register(contextPath+"/font/nanum.ttf", "MalgunGothic"); // 나눔명조.	
     	fontProvider.register(contextPath+"/font/ciacode39_h.ttf", "Cia"); // 나눔명조.	
     	CssAppliers cssAppliers = new CssAppliersImpl(fontProvider);    	 

     	HtmlPipelineContext htmlContext = new HtmlPipelineContext(cssAppliers);
     	htmlContext.setTagFactory(Tags.getHtmlTagProcessorFactory());    	 

     	// Pipelines
     	PdfWriterPipeline pdf = new PdfWriterPipeline(document, writer);
     	HtmlPipeline html = new HtmlPipeline(htmlContext, pdf);
     	CssResolverPipeline css = new CssResolverPipeline(cssResolver, html);

     	XMLWorker worker = new XMLWorker(css, true);
     	XMLParser xmlParser = new XMLParser(worker, Charset.forName("UTF-8"));  
    	
     	//거래명세서 파트너사 정보 가져오기.
     	JSONObject jsonData;
        JSONArray jsonArray = JSONArray.fromObject(jsonArrayData);
        
        String po_no = jsonArray.getJSONObject(0).getString("NO_PO");
        String po_no2 = jsonArray.getJSONObject(0).getString("NO_PO").replaceAll("POZ20","");
 
        String dt_rcv = jsonArray.getJSONObject(0).getString("DT_RCV");
        String dtFr = jsonArray.getJSONObject(0).getString("DTFR").replaceAll("-","");
        String dtTo = jsonArray.getJSONObject(0).getString("DTTO").replaceAll("-","");
        
     	PuInfoVO puInfoVo = puService.selectPuHeadInfo(po_no, dtFr, dtTo);
     	String no_company = "";
     	if(puInfoVo.getNo_company().length() == 10){
     		no_company = puInfoVo.getNo_company().substring(0,3)+" - "+puInfoVo.getNo_company().substring(3, 5)+ " - "+puInfoVo.getNo_company().substring(5);
     	}else{
     		no_company = puInfoVo.getNo_company();
     	}
     	
     	DecimalFormat df = new DecimalFormat("#,##0"); //1000단위 콤마 클래스호출
     	
     	int bk=0;
		String htmlStr = "<html><head></head><body>";
		for(bk=1; bk < 5; bk++){
			String txt="";
			switch(bk){
				case 1: txt="(공급자보관용)"; break;
				case 2: txt="(구매부)"; break;
				case 3: txt="(재무회계팀)"; break;
				case 4: txt="(물자관리/품질관리부)"; break;			
			}
		htmlStr += "<table border=0 style='width: 765px;'> "		
		+"		<tr>                                                                                                                              "
    	+"		  		<td rowspan='2' style='width: 5px; height: 0px;' text-align:left;'>                                                                                                                                        "
    	+"	    			<p style='font-family: MalgunGothic;'></p> "
    	+"		  		</td>	                                                                                                                                      "    	
    	+"		  		<td rowspan='2' style='width: 5px; height: 0px; text-align:left;'>                                                                                                                                        "
    	+"	    			<p style='font-family: MalgunGothic;'></p> "
    	+"		  		</td>	                                                                                                                                      "    	
    	+"		  		<td rowspan='2' style='width: 5px; height: 27px; text-align: center;'>                                                                                                                                        "
    	+"	    			<p style='font-family: MalgunGothic; font-size:27px;'><u>거래명세표</u></p> "
    	+"		  		</td>	                                                                                                                                      "
    	+"		  		<td rowspan='4' style='width: 208px; height: 66px; text-align:right;'>                                                                                                                                        "
    	+"						<img src='"+contextPath+"/images/logo/preview_logo_55.bmp' />   "
    	+"		  		</td>	                                                                                                                                      "
    	+"		</tr>                                                                                                                                           "
    	
    	+"		<tr> "     
    	+"		  		<td style='width: 70px; height: 20px;' text-align:left;'>                                                                                                                                        "
    	+"	    			<p style='font-family: MalgunGothic; font-size:16px;'>구매/외주</p> "    	
    	+"		  		</td>	                                                                                                                                      "    	                                                                                                                            	
    	+"		  		<td style='width: 100px; height: 20px;' text-align:left;'>                                                                                                                                        "
    	+"	    			<p style='font-family: MalgunGothic;'></p> "    	
    	+"		  		</td>	                                                                                                                                      "    	
    	+"		  		<td style='width: 300px; height: 20px; text-align: center;'>                                                                                                                                        "    	
    	+"	    			<p style='font-family: MalgunGothic; font-size:14px;'>"+ txt+"</p> "    	
    	+"		  		</td>	                                                                                                                                      "
    	+"		</tr>                                                                                                                                           "
    	+"		<tr>                                                                                                                                            "
    	+"		  		<td style='width: 70px; height: 5px; text-align:left;'>                                                                                                                                        "
    	+"		  	  		<p style='font-family: MalgunGothic; font-size:12px;'>주문번호:</p>                                                       "
    	+"		  		</td>	                                                                                                                                      "
    	+"		  		<td style='width: 100px; height: 5px; text-align:left;'>                                                                                                                                        "
    	+"		  	  		<p style='font-family: MalgunGothic; font-size:12px;'>"+ puInfoVo.getNo_po() +"</p>"
    	+"		  		</td>	                                                                                                                                      "
    	+"		  		<td style='width: 300px; height: 5px; text-align:left;'>                                                                                                                                        "
    	+"		  	  		<p style='font-family: MalgunGothic;'></p>                                                       "
    	+"		  		</td>	                                                                                                                                      "
    	+"		</tr>                                                                                                                                           "
    	+"		<tr>                                                                                                                              "
    	+"		  		<td colspan='4' style='height: 5px; text-align:left;'>                                                                                                                                        "
    	+"	    			<p style='font-family: MalgunGothic;'></p> "
    	+"		  		</td>	                                                                                                                                      "    	
    	+"		</tr>    "
    	+"</table>"
		+"<table border=1 style='width: 765px; '>"
		+"		<tr>"
    	+"		  		<td colspan='2' style='width: 76px; height: 27px; text-align: center;'>"
    	+"	    			<p style='font-family: MalgunGothic; font-size:14px;'>납품일자</p>"
    	+"		  		</td>"    	
    	+"		  		<td colspan='3' style='width: 114px; height: 27px; text-align: center;'>"
    	+"	    			<p style='font-family: MalgunGothic; font-size:14px;'>"+ dt_rcv +"</p>"
    	+"		  		</td>"    	
    	+"		  		<td colspan='2' style='width: 76px; height: 27px; text-align: center;'>"
    	+"	    			<p style='font-family: MalgunGothic; font-size:14px;'>납품시간</p>"
    	+"		  		</td>"    	
    	+"		  		<td colspan='3' style='width: 114px; height: 27px; text-align: left;'>"
    	+"	    			<p style='font-family: MalgunGothic; font-size:14px;'></p>"
    	+"		  		</td>"    	  	
    	+"		  		<td colspan='2' style='width: 76px; height: 27px; text-align: center;'>"
    	+"	    			<p style='font-family: MalgunGothic;  font-size:14px;'>검사일자</p>"
    	+"		  		</td>"    	
    	+"		  		<td colspan='4' style='width: 152px; height: 27px; text-align: left;'>"
    	+"	    			<p style='font-family: MalgunGothic;'></p>"
    	+"		  		</td>"    	
    	+"		  		<td colspan='2' style='width: 76px; height: 27px; text-align: center;'>"
    	+"	    			<p style='font-family: MalgunGothic; font-size:14px;'>입력필</p>"
    	+"		  		</td>"    
    	+"		  		<td colspan='3' style='width: 114px; height: 27px; text-align: left;'>"
    	+"	    			<p style='font-family: MalgunGothic; font-size:14px;'></p>"
    	+"		  		</td>"    	  		
    	+"		</tr>"
    	+"		<tr>"
    	+"		  		<td rowspan='4' style='width: 38px; height: 27px; text-align: center;'>"
    	+"	    			<p style='font-family: MalgunGothic; font-size:14px;'>공</p>"
    	+"	    			<p style='font-family: MalgunGothic; font-size:14px;'>&nbsp;</p>"
    	+"	    			<p style='font-family: MalgunGothic; font-size:14px;'>급</p>"
    	+"	    			<p style='font-family: MalgunGothic; font-size:14px;'>&nbsp;</p>"
    	+"	    			<p style='font-family: MalgunGothic; font-size:14px;'>자</p>"
    	+"		  		</td>"
    	+"		  		<td colspan='2' style='width: 78px; height: 27px; text-align: center;'>"
    	+"	    			<p style='font-family: MalgunGothic; font-size:14px;'>사업자번호</p>"
    	+"		  		</td>"  
    	+"		  		<td colspan='8' style='width: 304px; height: 27px; text-align: center;'>"
    	+"	    			<p style='font-family: MalgunGothic; font-size:14px;'>"+ no_company +"</p>"
    	+"		  		</td>"    	
    	+"		  		<td rowspan='4' style='width: 38px; height: 27px; text-align: center;'>"
    	+"	    			<p style='font-family: MalgunGothic; font-size:14px;'>공</p>"
    	+"	    			<p style='font-family: MalgunGothic; font-size:14px;'>급</p>"
    	+"	    			<p style='font-family: MalgunGothic; font-size:14px;'>받</p>"
    	+"	    			<p style='font-family: MalgunGothic; font-size:14px;'>는</p>"
    	+"	    			<p style='font-family: MalgunGothic; font-size:14px;'>자</p>"
    	+"		  		</td>"
    	+"		  		<td colspan='2' style='width: 76px; height: 27px; text-align: center;'>"
    	+"	    			<p style='font-family: MalgunGothic; font-size:14px;'>사업자번호</p>"
    	+"		  		</td>" 
    	+"		  		<td colspan='7' style='width: 266px; height: 27px; text-align: center;'>"
    	+"	    			<p style='font-family: MalgunGothic; font-size:14px;'>139 - 81 - 00869</p>"
    	+"		  		</td>"  
    	+"		</tr>"
    	+"		<tr>"
    	+"		  		<td colspan='2' style='width: 76px; height: 27px; text-align: center;'>"
    	+"	    			<p style='font-family: MalgunGothic; font-size:14px;'>상&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;호</p>"
    	+"		  		</td>"  
    	+"		  		<td colspan='4' style='width: 152px; height: 27px; text-align: left;'>"
    	+"	    			<p style='font-family: MalgunGothic; font-size:14px;'>&nbsp;"+ puInfoVo.getLn_partner() +"</p>"
    	+"		  		</td>"    	
    	+"		  		<td style='width: 38px; height: 27px; text-align: center;'>"
    	+"	    			<p style='font-family: MalgunGothic; font-size:14px;'>성명</p>"
    	+"		  		</td>"
    	+"		  		<td colspan='3' style='width: 114px; height: 27px; text-align: right;'>"
    	+"	    			<p style='font-family: MalgunGothic; font-size:14px;'>"+ puInfoVo.getNm_ceo() +"&nbsp;&nbsp;(인)&nbsp;</p>"
    	+"		  		</td>"    	
    	+"		  		<td colspan='2' style='width: 76px; height: 27px; text-align: center;'>"
    	+"	    			<p style='font-family: MalgunGothic; font-size:14px;'>상&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;호</p>"
    	+"		  		</td>" 
    	+"		  		<td colspan='4' style='width: 152px; height: 27px; text-align: left;'>"
    	+"	    			<p style='font-family: MalgunGothic; font-size:14px;'>&nbsp;레이캅코리아(주)</p>"
    	+"		  		</td>"
    	+"		  		<td style='width: 38px; height: 27px; text-align: center;'>"
    	+"	    			<p style='font-family: MalgunGothic; font-size:14px;'>성명</p>"
    	+"		  		</td>"
    	+"		  		<td colspan='2' style='width: 76px; height: 27px; text-align: center;'>"
    	+"	    			<p style='font-family: MalgunGothic; font-size:14px;'>이성진</p>"
    	+"		  		</td>"    	
    	+"		</tr>"
    	+"		<tr>"
    	+"		  		<td colspan='2' style='width: 76px; height: 27px; text-align: center;'>"
    	+"	    			<p style='font-family: MalgunGothic; font-size:14px;'>주&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;소</p>"
    	+"		  		</td>"  
    	+"		  		<td colspan='8' style='width: 304px; height: 27px; text-align: left;'>"
    	+"	    			<p style='font-family: MalgunGothic; font-size:10px;'>&nbsp;"+ puInfoVo.getDc_ads1_h() +"</p>"
    	+"		  		</td>"
    	+"		  		<td colspan='2' style='width: 76px; height: 27px; text-align: center;'>"
    	+"	    			<p style='font-family: MalgunGothic; font-size:14px;'>주&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;소</p>"
    	+"		  		</td>" 
    	+"		  		<td colspan='7' style='width: 266px; height: 27px; text-align: left;'>"
    	+"	    			<p style='font-family: MalgunGothic; font-size:14px;'>&nbsp;인천시 남동구 청능대로 450</p>"
    	+"		  		</td>"  
    	+"		</tr>"
    	+"		<tr>"
    	+"		  		<td colspan='2' style='width: 76px; height: 27px; text-align: center;'>"
    	+"	    			<p style='font-family: MalgunGothic; font-size:14px;'>업&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;태</p>"
    	+"		  		</td>"  
    	+"		  		<td colspan='4' style='width: 152px; height: 27px; text-align: left;'>"
    	+"	    			<p style='font-family: MalgunGothic; font-size:14px;'>&nbsp;"+ puInfoVo.getTp_job() +"</p>"
    	+"		  		</td>"    	
    	+"		  		<td style='width: 38px; height: 27px; text-align: center;'>"
    	+"	    			<p style='font-family: MalgunGothic; font-size:14px;'>종목</p>"
    	+"		  		</td>"
    	+"		  		<td colspan='3' style='width: 114px; height: 27px; text-align: left;'>"
    	+"	    			<p style='font-family: MalgunGothic; font-size:10px;'>&nbsp;"+ puInfoVo.getCls_job() +"</p>"
    	+"		  		</td>"
    	+"		  		<td colspan='2' style='width: 76px; height: 27px; text-align: center;'>"
    	+"	    			<p style='font-family: MalgunGothic; font-size:14px;'>업&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;태</p>"
    	+"		  		</td>" 
    	+"		  		<td colspan='4' style='width: 152px; height: 27px; text-align: left;'>"
    	+"	    			<p style='font-family: MalgunGothic; font-size:14px;'>&nbsp;제조</p>"
    	+"		  		</td>"    	
    	+"		  		<td style='width: 38px; height: 27px; text-align: center;'>"
    	+"	    			<p style='font-family: MalgunGothic; font-size:14px;'>종목</p>"
    	+"		  		</td>"
    	+"		  		<td colspan='2' style='width: 76px; height: 27px; text-align: center;'>"
    	+"	    			<p style='font-family: MalgunGothic; font-size:14px;'>전자제품</p>"
    	+"		  		</td>"    	
    	+"		</tr>"
    	+"		<tr>"
    	+"		  		<td style='width: 38px; height: 27px; text-align: center;'>"
    	+"	    			<p style='font-family: MalgunGothic; font-size:14px;'></p>"
    	+"		  		</td>"  
    	+"		  		<td colspan='3' style='width: 114px; height: 27px; text-align: center;'>"
    	+"	    			<p style='font-family: MalgunGothic; font-size:14px;'>품목코드</p>"
    	+"		  		</td>"    	
    	+"		  		<td colspan='4' style='width: 152px; height: 27px; text-align: center;'>"
    	+"	    			<p style='font-family: MalgunGothic; font-size:14px;'>품명</p>"
    	+"		  		</td>"
    	+"		  		<td colspan='4' style='width: 152px; height: 27px; text-align: center;'>"
    	+"	    			<p style='font-family: MalgunGothic; font-size:14px;'>규격</p>"
    	+"		  		</td>"
    	+"		  		<td style='width: 38px; height: 27px; text-align: center;'>"
    	+"	    			<p style='font-family: MalgunGothic; font-size:14px;'>단위</p>"
    	+"		  		</td>"
    	+"		  		<td colspan='2' style='width: 76px; height: 27px; text-align: center;'>"
    	+"	    			<p style='font-family: MalgunGothic; font-size:14px;'>수량</p>"
    	+"		  		</td>" 
    	+"		  		<td colspan='4' style='width: 152px; height: 27px; text-align: center;'>"
    	+"	    			<p style='font-family: MalgunGothic; font-size:14px;'>비고</p>"
    	+"		  		</td>"    	
    	+"		  		<td style='width: 38px; height: 27px; text-align: center;'>"
    	+"	    			<p style='font-family: MalgunGothic; font-size:14px;'>합</p>"
    	+"		  		</td>"
    	+"		  		<td style='width: 38px; height: 27px; text-align: center;'>"
    	+"	    			<p style='font-family: MalgunGothic; font-size:14px;'>불</p>"
    	+"		  		</td>"    	    	
    	+"		</tr>";
    	for(int i=0; i < jsonArray.size(); i++){
    		//납품등록 표에서 입력된 값을 배열로 가져와 담고 있음.
        //for (int x0 = 0; x0 < jsonArray.size(); x0++) {
        	jsonData = jsonArray.getJSONObject(i);
        	
    	htmlStr += "<tr>"
    	+"		  		<td style='width: 38px; height: 27px; text-align: center;'>"
    	+"	    			<p style='font-family: MalgunGothic; font-size:14px;'>"+ (i+1) + "</p>"
    	+"		  		</td>"  
    	+"		  		<td colspan='3' style='width: 114px; height: 27px; text-align: center;'>"
    	+"	    			<p style='font-family: MalgunGothic; font-size:14px;'>"+ jsonData.getString("CD_ITEM") + "</p>"
    	+"		  		</td>"    	
    	+"		  		<td colspan='4' style='width: 152px; height: 27px; text-align: left;'>"
    	+"	    			<p style='font-family: MalgunGothic; font-size:10px;'>&nbsp;"+ jsonData.getString("NM_ITEM") + "</p>"
    	+"		  		</td>"
    	+"		  		<td colspan='4' style='width: 152px; height: 27px; text-align: left;'>"
    	+"	    			<p style='font-family: MalgunGothic; font-size:10px;'>&nbsp;"+ jsonData.getString("STND_ITEM") + "</p>"
    	+"		  		</td>"
    	+"		  		<td style='width: 38px; height: 27px; text-align: center;'>"
    	+"	    			<p style='font-family: MalgunGothic; font-size:14px;'>"+ jsonData.getString("CD_UNIT_MM") + "</p>"
    	+"		  		</td>"
    	+"		  		<td colspan='2' style='width: 76px; height: 27px; text-align: right;'>"
    	+"	    			<p style='font-family: MalgunGothic; font-size:14px;'>"+ df.format(Integer.parseInt(jsonData.getString("QT_RCV"))) + "&nbsp;</p>"
    	+"		  		</td>" 
    	+"		  		<td colspan='4' style='width: 152px; height: 27px; text-align: left;'>"
    	+"	    			<p style='font-family: MalgunGothic; font-size:10px;'>&nbsp;"+ jsonData.getString("DESC_RCV") + "</p>"
    	+"		  		</td>"    	
    	+"		  		<td style='width: 38px; height: 27px; text-align: center;'>"
    	+"	    			<p style='font-family: MalgunGothic; font-size:14px;'></p>"
    	+"		  		</td>"
    	+"		  		<td style='width: 38px; height: 27px; text-align: center;'>"
    	+"	    			<p style='font-family: MalgunGothic; font-size:14px;'></p>"
    	+"		  		</td>"    	    	
    	+"		</tr>";
    	}
    	for(int i=1; i < 8-jsonArray.size(); i++){
		htmlStr += "<tr>"
    	+"		  		<td style='width: 38px; height: 27px; text-align: center;'>"
    	+"	    			<p style='font-family: MalgunGothic; font-size:14px;'>"+ (jsonArray.size()+i) + "</p>"
    	+"		  		</td>"  
    	+"		  		<td colspan='3' style='width: 114px; height: 27px; text-align: left;'>"
    	+"	    			<p style='font-family: MalgunGothic; font-size:14px;'></p>"
    	+"		  		</td>"    	
    	+"		  		<td colspan='4' style='width: 152px; height: 27px; text-align: left;'>"
    	+"	    			<p style='font-family: MalgunGothic; font-size:12px;'></p>"
    	+"		  		</td>"
    	+"		  		<td colspan='4' style='width: 152px; height: 27px; text-align: left;'>"
    	+"	    			<p style='font-family: MalgunGothic; font-size:10px;'></p>"
    	+"		  		</td>"
    	+"		  		<td style='width: 38px; height: 27px; text-align: center;'>"
    	+"	    			<p style='font-family: MalgunGothic; font-size:14px;'></p>"
    	+"		  		</td>"
    	+"		  		<td colspan='2' style='width: 76px; height: 27px; text-align: right;'>"
    	+"	    			<p style='font-family: MalgunGothic; font-size:14px;'></p>"
    	+"		  		</td>" 
    	+"		  		<td colspan='4' style='width: 152px; height: 27px; text-align: left;'>"
    	+"	    			<p style='font-family: MalgunGothic; font-size:12px;'></p>"
    	+"		  		</td>"    	
    	+"		  		<td style='width: 38px; height: 27px; text-align: center;'>"
    	+"	    			<p style='font-family: MalgunGothic; font-size:14px;'></p>"
    	+"		  		</td>"
    	+"		  		<td style='width: 38px; height: 27px; text-align: center;'>"
    	+"	    			<p style='font-family: MalgunGothic; font-size:14px;'></p>"
    	+"		  		</td>"    	    	
    	+"		</tr>";	
    	}
    	htmlStr+="<tr>"
		+"		  		<td rowspan='3' colspan='9' style='width: 342px; height: 27px; text-align: left;'>"
		+"	    			<p style='font-family: MalgunGothic; font-size: 10px;'>&nbsp;1. 품목코드 오기, 누락, 사용인감 미날인 전표는 인정되지 않습니다.</p>"
		+"	    			<p style='font-family: MalgunGothic; font-size: 10px;'>&nbsp;2. 수리 후 재 납품시에도 거래명세표를 제출 하십시요.</p>"
		+"	    			<p style='font-family: MalgunGothic; font-size: 10px;'>&nbsp;3. 인수자 확인이 없는 것은 인정하지 않습니다.</p>"
		+"	    			<p style='font-family: MalgunGothic; font-size: 10px;'>&nbsp;4. 납품 후 검사결과를 1일 이내 확인하고,반품은 2일 이내 조치하십시오</p>"		
		+"		  		</td>"
		+"		  		<td colspan='4' style='width: 152px; height: 27px; text-align: center;'>"
    	+"	    			<p style='font-family: MalgunGothic; font-size:14px;'>물자관리부</p>"
    	+"		  		</td>"    	
    	+"		  		<td colspan='4' style='width: 152px; height: 27px; text-align: center;'>"
    	+"	    			<p style='font-family: MalgunGothic; font-size:14px;'>수입검사</p>"
    	+"		  		</td>"  
    	+"		  		<td  rowspan='3' style='width: 38px; height: 27px; text-align: center;'>"
    	+"	    			<p style='font-family: MalgunGothic; font-size:14px;'>경</p>"
    	+"	    			<p style='font-family: MalgunGothic; font-size:14px;'>비</p>"
    	+"	    			<p style='font-family: MalgunGothic; font-size:14px;'>실</p>"
    	+"		  		</td>"  
    	+"		  		<td rowspan='3' colspan='3' style='width: 114px; height: 27px; text-align: center;'>"
    	+"	    			<p style='font-family: MalgunGothic; font-size:10px;'>물자인수/수입검사</p>"
    	+"	    			<p style='font-family: MalgunGothic; font-size:10px;'>확인 분만</p>"
    	+"	    			<p style='font-family: MalgunGothic; font-size:10px;'>날인 할 것</p>"
    	+"		  		</td>"  
    	+"		</tr>"		
    	+"		<tr>"
		+"		  		<td colspan='2' style='width: 76px; height: 27px; text-align: center;'>"
    	+"	    			<p style='font-family: MalgunGothic; font-size:14px;'>인수자</p>"
    	+"		  		</td>" 
    	+"		  		<td colspan='2' style='width: 76px; height: 27px; text-align: center;'>"
    	+"	    			<p style='font-family: MalgunGothic; font-size:14px;'>정입고</p>"
    	+"		  		</td>" 
    	+"		  		<td rowspan='2' colspan='4' style='width: 152px; height: 27px; text-align: center;'>"
    	+"	    			<p style='font-family: MalgunGothic; font-size:14px;'></p>"
    	+"		  		</td>"    	
    	+"		</tr>"
    	+"		<tr>"
		+"		  		<td colspan='2' style='width: 76px; height: 27px; text-align: center;'>"
    	+"	    			<p style='font-family: MalgunGothic; font-size:14px;'></p>"
    	+"		  		</td>" 
    	+"		  		<td colspan='2' style='width: 76px; height: 27px; text-align: center;'>"
    	+"	    			<p style='font-family: MalgunGothic; font-size:14px;'></p>"
    	+"		  		</td>"
    	+"		</tr>"    	
    	+"</table>"
    	+"<table border=0 style='width: 765px; '>"
    	+"		<tr>"
		+"		  		<td style='width: 651px; height: 18px; text-align: left;'>"
    	+"	    			<p style='font-family: MalgunGothic; font-size:12px;'>&nbsp;EF740-03(2014.04)</p>"
    	+"		  		</td>" 
    	+"		  		<td style='width: 114px; height: 18px; text-align: right;'>"
    	+"	    			<p style='font-family: MalgunGothic; font-size:12px;'>(210mm * 297mm)&nbsp;</p>"
    	+"		  		</td>"
    	+"		</tr>"
    	+"		<tr>                                                                                                                              "
    	+"		  		<td colspan='2' style='height: 15px; text-align: left;'>                                                                                                                                        "
    	+"	    			<p style='font-family: MalgunGothic;'></p> "
    	+"		  		</td>	                                                                                                                                      "    	
    	+"		</tr> "
    	+"</table>";    	
	    	if(bk == 1 || bk== 3){
	    		htmlStr +="<hr size='1' noshade style='border-style: dotted;'></hr>";
	    	}	   
		}
		int bacode;
		
		htmlStr += "<table border=1 style='width: 765px;'>";
		for(int i=0; i < jsonArray.size(); i++){ //for(bacode = 1; bacode < 8; bacode++){
			jsonData = jsonArray.getJSONObject(i);			
		 htmlStr +="		<tr>                                                                                                                              "
		        	+"		  		<td style='width: 365px; height: 155px; text-align: center;'>                                                                                                                                        "
		        	+"	    			<p style='font-family: MalgunGothic;'>발주번호/품번/수량</p> "
		        	+"	    			<p style='font-family: MalgunGothic;'>*"+po_no+"/"+ jsonData.getString("CD_ITEM") + "/"+jsonData.getString("QT_RCV")+"*</p> "
		        	+"	    			<p style='font-family: MalgunGothic;'></p> "
		        	+"	    			<p style='font-family: MalgunGothic;'></p> "
		        	+"	    			<p style='font-family: MalgunGothic;'>&nbsp;</p> "
		        	+"	    			<p style='font-family: Cia; font-size:12px;'>*"+po_no2+ jsonData.getString("CD_ITEM") + "/"+ jsonData.getString("QT_RCV") +"*</p> "
		        	+"		  		</td>"    	
		        	+"		  		<td style='width: 35px; height: 155px; text-align:left;'>                                                                                                                                        "
		        	+"	    			<p style='font-family: MalgunGothic;'></p> "
		        	+"		  		</td>	                                                                                                                                      "    	
		        	+"		  		<td style='width: 365px; height: 155px; text-align: center;'>                                                                                                                                        "
		        	+"	    			<p style='font-family: MalgunGothic;'>발주번호/품번/수량</p> "
		        	+"	    			<p style='font-family: MalgunGothic;'>*"+po_no+"/"+ jsonData.getString("CD_ITEM") + "/"+jsonData.getString("QT_RCV")+"*</p> "
		        	+"	    			<p style='font-family: MalgunGothic;'></p> "
		        	+"	    			<p style='font-family: MalgunGothic;'></p> "
		        	+"	    			<p style='font-family: MalgunGothic;'>&nbsp;</p> "
		        	+"	    			<p style='font-family: Cia; font-size:12px;'>*"+po_no2+ jsonData.getString("CD_ITEM") +"/"+ jsonData.getString("QT_RCV") +"*</p> "
		        	+"		  		</td>"
		        	+"		</tr>";		        	
		}
		for(int i=1; i < 8-jsonArray.size(); i++){
		htmlStr +="		<tr>                                                                                                                              "
	    	+"		  		<td style='width: 365px; height: 155px; text-align: center;'>                                                                                                                                        "
	    	+"	    			<p style='font-family: MalgunGothic;'>&nbsp;</p> "
	    	+"	    			<p style='font-family: MalgunGothic;'>&nbsp;</p> "
	    	+"	    			<p style='font-family: MalgunGothic;'>&nbsp;</p> "
	    	+"	    			<p style='font-family: MalgunGothic;'>&nbsp;</p> "
	    	+"	    			<p style='font-family: MalgunGothic;'>&nbsp;</p> "
	    	+"	    			<p style='font-family: MalgunGothic;'>&nbsp;</p> "
	    	+"		  		</td>"    	
	    	+"		  		<td style='width: 35px; height: 155px; text-align:left;'>                                                                                                                                        "
	    	+"	    			<p style='font-family: MalgunGothic;'></p> "
	    	+"		  		</td>	                                                                                                                                      "    	
	    	+"		  		<td style='width: 365px; height: 155px; text-align: center;'>                                                                                                                                        "
	    	+"	    			<p style='font-family: MalgunGothic;'>&nbsp;</p> "
	    	+"	    			<p style='font-family: MalgunGothic;'>&nbsp;</p> "
	    	+"	    			<p style='font-family: MalgunGothic;'>&nbsp;</p> "
	    	+"	    			<p style='font-family: MalgunGothic;'>&nbsp;</p> "
	    	+"	    			<p style='font-family: MalgunGothic;'>&nbsp;</p> "
	    	+"	    			<p style='font-family: MalgunGothic;'>&nbsp;</p> "
	    	+"		  		</td>"
	    	+"		</tr>";	
		}
		htmlStr +="</table>";
		htmlStr +="</body></html>";
        
    	StringReader strReader = new StringReader(htmlStr);
    	xmlParser.parse(strReader);
		
    	document.close();
    	writer.close();
    	
        return pdfdir;
    }
       
}
