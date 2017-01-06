package com.pnt.preview.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.pnt.common.message.PntMessageSource;
import com.pnt.common.secure.SecureUtils;
import com.pnt.po.PoInfoVO;
import com.pnt.po.service.PoService;
import com.pnt.preview.service.PreviewService;

@Controller
@RequestMapping(value = "/preview")
public class PreviewController {
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	@Resource(name = "pntMessageSource")
    private PntMessageSource messageSource;
    
    @Resource(name = "previewService")
    private PreviewService previewService;
    
    @Resource(name = "poService")
    private PoService poService;

    @Resource(name = "printWriterView")
    private View printWriterView;

    @RequestMapping(value = "/purchaseOrder.do")
    public String page(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return "preview/purchaseOrder";
    }
    
    @RequestMapping(value = "/select/getPreviewInfo.do")
    public View selectPreviewInfo(HttpServletRequest request, Model model) throws Exception {
    	
    	String info = SecureUtils.getParameter(request, "info", null);
        
    	if("po".equals(info)){
    		String po_no = SecureUtils.getParameter(request, "po_no", null);
            String dtFr = SecureUtils.getParameter(request, "dtFr", null);
            String dtTo = SecureUtils.getParameter(request, "dtTo", null);
            
            List<PoInfoVO> data = this.poService.selectPoInfo(po_no, dtFr, dtTo);
            
            model.addAttribute("OUT_DATA", JSONArray.fromObject(data));
    	}
    	
        return this.printWriterView;
    }
    
    

}
