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
import com.pnt.po.PoVO;
import com.pnt.po.service.PoService;

import jxl.CellView;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;


public class PoExcelConvertView extends AbstractExcelView {
	
	@Resource(name = "pntMessageSource")
    private PntMessageSource messageSource;
    
	@Resource(name = "poService")
    private PoService poService;
    
    
    @Override
	protected void buildExcelDocument(Map model, HSSFWorkbook workbook,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
    	
    	@SuppressWarnings("unchecked")
    	Map<String,List<PoVO>> mapPoData = (Map<String,List<PoVO>>) model.get("mapPoData");
    	Map<String,String> fileNameMap = (Map<String,String>) model.get("mapPoData");
    	String fileName = (String) fileNameMap.get("fileName");
    	
    	HttpSession session  = request.getSession();
    	String root = session.getServletContext().getRealPath("/");
    	
    	File desti = new File(root + "\\download\\");
    	
    	if(!desti.exists()){
    		desti.mkdirs(); 
		}
    	
    	File file = new File(desti + "\\" + fileName);
    	
    	WritableWorkbook wb = Workbook.createWorkbook(file);
    	
    	WritableSheet sh = wb.createSheet("PoData", 0);

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
    	sh.addCell(new Label(1, 0, "발주번호", cellFormat));
    	sh.addCell(new Label(2, 0, "거래처명", cellFormat));
    	sh.addCell(new Label(3, 0, "발주일자", cellFormat));
    	sh.addCell(new Label(4, 0, "품목코드", cellFormat));
    	sh.addCell(new Label(5, 0, "품목명", cellFormat));
    	sh.addCell(new Label(6, 0, "규격", cellFormat)); 
    	sh.addCell(new Label(7, 0, "발주수량", cellFormat));
    	sh.addCell(new Label(8, 0, "환종", cellFormat));
    	sh.addCell(new Label(9, 0, "단가", cellFormat));
    	sh.addCell(new Label(10, 0, "금액", cellFormat));
    	sh.addCell(new Label(11, 0, "입고수량", cellFormat));
    	sh.addCell(new Label(12, 0, "발주잔량", cellFormat));
    	sh.addCell(new Label(13, 0, "발주상태", cellFormat));
    	sh.addCell(new Label(14, 0, "납기일자", cellFormat));
    	
    	List<PoVO> poList = mapPoData.get("mapPoData");
    	
    	int rowCount = 1;
        
        for (PoVO po : poList) {
        	sh.addCell(new Label(0, rowCount, "" + rowCount, cellFormat));
        	sh.addCell(new Label(1, rowCount, po.getNo_po(), cellFormat));
        	sh.addCell(new Label(2, rowCount, po.getLn_partner(), cellFormat));
        	sh.addCell(new Label(3, rowCount, po.getDt_po(), cellFormat));
        	sh.addCell(new Label(4, rowCount, po.getCd_item(), cellFormat));
        	sh.addCell(new Label(5, rowCount, po.getNm_item(), cellFormat));
        	sh.addCell(new Label(6, rowCount, po.getStnd_item(), cellFormat));
        	sh.addCell(new Label(7, rowCount, po.getQt_po_mm(), cellFormat));
        	sh.addCell(new Label(8, rowCount, po.getCd_unit_mm(), cellFormat));
        	sh.addCell(new Label(9, rowCount, po.getUm(), cellFormat));
        	sh.addCell(new Label(10, rowCount, po.getAm(), cellFormat));
        	sh.addCell(new Label(11, rowCount, po.getQt_rcv_mm(), cellFormat));
        	sh.addCell(new Label(12, rowCount, po.getQt_diff(), cellFormat));
        	sh.addCell(new Label(13, rowCount, po.getFg_post(), cellFormat));
        	sh.addCell(new Label(14, rowCount, po.getDt_limit(), cellFormat));
             
            rowCount++;
        }
        
        wb.write();
        wb.close();
    	
    }
}
