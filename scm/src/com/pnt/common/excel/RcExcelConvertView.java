package com.pnt.common.excel;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.pnt.common.message.PntMessageSource;
import com.pnt.rc.RcVO;
import com.pnt.rc.service.RcService;

import jxl.CellView;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;


public class RcExcelConvertView extends AbstractExcelView {
	
	@Resource(name = "pntMessageSource")
    private PntMessageSource messageSource;
    
	@Resource(name = "rcService")
    private RcService rcService;
    
    
    @Override
	protected void buildExcelDocument(Map model, HSSFWorkbook workbook,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
    	
    	@SuppressWarnings("unchecked")
    	Map<String,List<RcVO>> mapRcData = (Map<String,List<RcVO>>) model.get("mapRcData");
    	Map<String,String> fileNameMap = (Map<String,String>) model.get("mapRcData");
    	String fileName = (String) fileNameMap.get("fileName");
    	
    	HttpSession session  = request.getSession();
    	String root = session.getServletContext().getRealPath("/");
    	
    	File desti = new File(root + "\\download\\");
    	
    	if(!desti.exists()){
    		desti.mkdirs(); 
		}
    	
    	File file = new File(desti + "\\" + fileName);
    	
    	WritableWorkbook wb = Workbook.createWorkbook(file);
    	
    	WritableSheet sh = wb.createSheet("RcData", 0);

        for(int i=0;i<15;i++){
//        	CellView cellView = sh.getColumnView(i);
        	CellView cellView = new CellView();
        	cellView.setAutosize(true);
        	sh.setColumnView(i,  cellView);
        }
        
    	
    	WritableCellFormat cellFormat = new WritableCellFormat();
    	cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
    	cellFormat.setAlignment(Alignment.CENTRE);

    	sh.addCell(new Label(0, 0, "순번", cellFormat));
    	sh.addCell(new Label(1, 0, "거래처", cellFormat));
    	sh.addCell(new Label(2, 0, "입고번호", cellFormat));
    	sh.addCell(new Label(3, 0, "입고일자", cellFormat));
    	sh.addCell(new Label(4, 0, "품목코드", cellFormat));
    	sh.addCell(new Label(5, 0, "품목명", cellFormat));
    	sh.addCell(new Label(6, 0, "규격", cellFormat)); 
    	sh.addCell(new Label(7, 0, "입고수량", cellFormat));
    	sh.addCell(new Label(8, 0, "합격수량", cellFormat));
    	sh.addCell(new Label(9, 0, "환종", cellFormat));
    	sh.addCell(new Label(10, 0, "단가", cellFormat));
    	sh.addCell(new Label(11, 0, "금액", cellFormat));
    	sh.addCell(new Label(12, 0, "유무한", cellFormat));
    	sh.addCell(new Label(13, 0, "가입고번호", cellFormat));
    	sh.addCell(new Label(14, 0, "발주번호", cellFormat));
    	
    	List<RcVO> rcList = mapRcData.get("mapRcData");
    	
    	int rowCount = 1;
        
        for (RcVO rc : rcList) {
        	sh.addCell(new Label(0, rowCount, rc.getNum(), cellFormat));
        	sh.addCell(new Label(1, rowCount, rc.getCd_partner(), cellFormat));
        	sh.addCell(new Label(2, rowCount, rc.getNo_io(), cellFormat));
        	sh.addCell(new Label(3, rowCount, rc.getDt_io(), cellFormat));
        	sh.addCell(new Label(4, rowCount, rc.getCd_item(), cellFormat));
        	sh.addCell(new Label(5, rowCount, rc.getNm_item(), cellFormat));
        	sh.addCell(new Label(6, rowCount, rc.getStnd_item(), cellFormat));
        	sh.addCell(new Label(7, rowCount, rc.getQt_io(), cellFormat));
        	sh.addCell(new Label(8, rowCount, rc.getQt_good_inv(), cellFormat));
        	sh.addCell(new Label(9, rowCount, rc.getNm_cd_exch(), cellFormat));
        	sh.addCell(new Label(10, rowCount, rc.getUm_ex(), cellFormat));
        	sh.addCell(new Label(11, rowCount, rc.getAm_ex(), cellFormat));
        	sh.addCell(new Label(12, rowCount, rc.getYn_am(), cellFormat));
        	sh.addCell(new Label(13, rowCount, rc.getNo_rev(), cellFormat));
        	sh.addCell(new Label(14, rowCount, rc.getNo_pso_mgmt(), cellFormat));
             
            rowCount++;
        }
        
        wb.write();
        wb.close();
    	
    }
}
