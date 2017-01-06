package com.pnt.pdf.controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.StringReader;
import java.nio.charset.Charset;

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

public class Test {
		
	public static void main(String[] args) throws Exception {
		
		String imgDir = "C:\\preview_logo_55.bmp";
		
		String contextPath = "C:\\workspace\\scm\\WebContent";
		
		// Document 생성
    	Document document = new Document(PageSize.A4, 10, 10, 10, 10); // 용지 및 여백 설정
    	
		// PdfWriter 생성
    	String pdfdir = "C:\\puPdf.pdf";
    	PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(pdfdir));        
    	document.open();
    	
    	 XMLWorkerHelper helper = XMLWorkerHelper.getInstance();    	     

     	// CSS
     	CSSResolver cssResolver = new StyleAttrCSSResolver();
     	CssFile cssFile = helper.getCSS(new FileInputStream(contextPath+"/css/pdf.css"));
     	cssResolver.addCss(cssFile);

     	// HTML, 폰트 설정
     	XMLWorkerFontProvider fontProvider = new XMLWorkerFontProvider(XMLWorkerFontProvider.DONTLOOKFORFONTS);
     	fontProvider.register(contextPath+"/font/arialbd.ttf", "ArialBd"); 
     	fontProvider.register(contextPath+"/font/nanum.ttf", "MalgunGothic"); // MalgunGothic은 alias,     	
     	CssAppliers cssAppliers = new CssAppliersImpl(fontProvider);    	 

     	HtmlPipelineContext htmlContext = new HtmlPipelineContext(cssAppliers);
     	htmlContext.setTagFactory(Tags.getHtmlTagProcessorFactory());    	 

     	// Pipelines
     	PdfWriterPipeline pdf = new PdfWriterPipeline(document, writer);
     	HtmlPipeline html = new HtmlPipeline(htmlContext, pdf);
     	CssResolverPipeline css = new CssResolverPipeline(cssResolver, html);

     	XMLWorker worker = new XMLWorker(css, true);
     	XMLParser xmlParser = new XMLParser(worker, Charset.forName("UTF-8"));  
    	
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
    	+"		  	  		<p style='font-family: MalgunGothic; font-size:14px;'>주문번호:</p>                                                       "
    	+"		  		</td>	                                                                                                                                      "
    	+"		  		<td style='width: 100px; height: 5px; text-align:left;'>                                                                                                                                        "
    	+"		  	  		<p style='font-family: MalgunGothic; font-size:14px;'>_NO_PO_</p>                                                       "
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
    	+"		  		<td colspan='3' style='width: 114px; height: 27px; text-align: left;'>"
    	+"	    			<p style='font-family: MalgunGothic; font-size:14px;'>-DT_RCV-</p>"
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
    	+"	    			<p style='font-family: MalgunGothic; font-size:14px;'></p>"
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
    	+"		  		<td colspan='4' style='width: 152px; height: 27px; text-align: center;'>"
    	+"	    			<p style='font-family: MalgunGothic; font-size:14px;'>-NM_PARTER-</p>"
    	+"		  		</td>"    	
    	+"		  		<td style='width: 38px; height: 27px; text-align: center;'>"
    	+"	    			<p style='font-family: MalgunGothic; font-size:14px;'>성명</p>"
    	+"		  		</td>"
    	+"		  		<td colspan='3' style='width: 114px; height: 27px; text-align: right;'>"
    	+"	    			<p style='font-family: MalgunGothic; font-size:14px;'> (인)</p>"
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
    	+"		  		<td colspan='8' style='width: 304px; height: 27px; text-align: center;'>"
    	+"	    			<p style='font-family: MalgunGothic; font-size:14px;'></p>"
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
    	+"		  		<td colspan='4' style='width: 152px; height: 27px; text-align: center;'>"
    	+"	    			<p style='font-family: MalgunGothic; font-size:14px;'>-NM_PARTER-</p>"
    	+"		  		</td>"    	
    	+"		  		<td style='width: 38px; height: 27px; text-align: center;'>"
    	+"	    			<p style='font-family: MalgunGothic; font-size:14px;'>종목</p>"
    	+"		  		</td>"
    	+"		  		<td colspan='3' style='width: 114px; height: 27px; text-align: center;'>"
    	+"	    			<p style='font-family: MalgunGothic; font-size:14px;'>-NM_CEO-</p>"
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
    	for(int i=1; i < 8; i++){
		htmlStr += "<tr>"
    	+"		  		<td style='width: 38px; height: 27px; text-align: center;'>"
    	+"	    			<p style='font-family: MalgunGothic; font-size:14px;'>"+ i + "</p>"
    	+"		  		</td>"  
    	+"		  		<td colspan='3' style='width: 114px; height: 27px; text-align: center;'>"
    	+"	    			<p style='font-family: MalgunGothic; font-size:14px;'></p>"
    	+"		  		</td>"    	
    	+"		  		<td colspan='4' style='width: 152px; height: 27px; text-align: center;'>"
    	+"	    			<p style='font-family: MalgunGothic; font-size:14px;'></p>"
    	+"		  		</td>"
    	+"		  		<td colspan='4' style='width: 152px; height: 27px; text-align: center;'>"
    	+"	    			<p style='font-family: MalgunGothic; font-size:14px;'></p>"
    	+"		  		</td>"
    	+"		  		<td style='width: 38px; height: 27px; text-align: center;'>"
    	+"	    			<p style='font-family: MalgunGothic; font-size:14px;'></p>"
    	+"		  		</td>"
    	+"		  		<td colspan='2' style='width: 76px; height: 27px; text-align: center;'>"
    	+"	    			<p style='font-family: MalgunGothic; font-size:14px;'></p>"
    	+"		  		</td>" 
    	+"		  		<td colspan='4' style='width: 152px; height: 27px; text-align: center;'>"
    	+"	    			<p style='font-family: MalgunGothic; font-size:14px;'></p>"
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
		for(bacode = 1; bacode < 8; bacode++){
		 htmlStr +="		<tr>                                                                                                                              "
		        	+"		  		<td style='width: 365px; height: 155px; text-align: center;'>                                                                                                                                        "
		        	+"	    			<p style='font-family: MalgunGothic;'>발주번호(POZ20생략)/품번/수량</p> "
		        	+"	    			<p style='font-family: MalgunGothic;'>*POZ20150600021/1065-4064-00/999999*</p> "
		        	+"	    			<p style='font-family: MalgunGothic;'>*0600021/1065-4064-00/999999*</p> "
		        	+"		  		</td>"    	
		        	+"		  		<td style='width: 35px; height: 155px; text-align:left;'>                                                                                                                                        "
		        	+"	    			<p style='font-family: MalgunGothic;'></p> "
		        	+"		  		</td>	                                                                                                                                      "    	
		        	+"		  		<td style='width: 365px; height: 155px; text-align: center;'>                                                                                                                                        "
		        	+"	    			<p style='font-family: MalgunGothic;'>발주번호(POZ20생략)/품번/수량</p> "
		        	+"	    			<p style='font-family: MalgunGothic;'>*POZ20150600021/1065-4064-00/999999*</p> "
		        	+"	    			<p style='font-family: MalgunGothic;'>*0600021/1065-4064-00/999999*</p> "
		        	+"		  		</td>"
		        	+"		</tr>";		        	
		}                           	
		htmlStr +="</table>";
		htmlStr +="</body></html>";
        
    	StringReader strReader = new StringReader(htmlStr);
    	xmlParser.parse(strReader);    	 
		System.out.println("생성완료");
		
    	document.close();
    	
	}
}
