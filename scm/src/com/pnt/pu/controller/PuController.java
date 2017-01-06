package com.pnt.pu.controller;

import java.util.List; 
import java.util.Map;
import java.util.StringTokenizer;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.pnt.common.data.DHTMLXGridDataTransformer;
import com.pnt.common.data.DataTransformer;
import com.pnt.common.message.PntMessageSource;
import com.pnt.common.secure.SecureUtils;
import com.pnt.context.User;
import com.pnt.context.config.Configurator;
import com.pnt.pu.PuVO;
import com.pnt.pu.service.PuService;
import com.pnt.pdf.controller.PuPdfController;


import com.pnt.user.controller.UserController;

@Controller
@RequestMapping(value = "/pu")
public class PuController {
    private Logger logger = Logger.getLogger(this.getClass());

    @Resource(name = "pntMessageSource")
    private PntMessageSource messageSource;
    
    @Resource(name = "puService")
    private PuService puService;
    
    @Resource(name = "puPdf")
    private PuPdfController puPdf;
    
    @Resource(name = "printWriterView")
    private View printWriterView;
    
    @Resource(name = "userController")
    private UserController userController;

    @RequestMapping(value = "/page.do")
    public String main(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return "pu/pu";
    }
    
    @RequestMapping(value = "/select/partners.do")
    public View selectPartners(HttpServletRequest request, Model model, HttpSession session) throws Exception {
        
        String dtFr = SecureUtils.getParameter(request, "dtFr", null);
        String dtTo = SecureUtils.getParameter(request, "dtTo", null);
        
        List<Map<String,String>> data = this.puService.selectPartners(dtFr, dtTo);

        model.addAttribute("OUT_DATA", JSONArray.fromObject(data));

        return this.printWriterView;
    }
    
    @RequestMapping(value = "/select/list.do")
    public View selectList(HttpServletRequest request, Model model, HttpSession session) throws Exception {
        
        String dtFr = SecureUtils.getParameter(request, "dtFr", null);
        String dtTo = SecureUtils.getParameter(request, "dtTo", null);
        String dt = SecureUtils.getParameter(request, "dt", null);
        String partner = SecureUtils.getParameter(request, "partner", null);
        
        User user = (User)session.getAttribute(Configurator.Constants.SESSION_SIGNED_USER);
        
        List<PuVO> data = this.puService.selectList(user.getId(), dtFr, dtTo, dt, partner);
        
        //for()
        //data.get(i).
        
        DataTransformer dtf = new DHTMLXGridDataTransformer();
        dtf.transform(data);

        String json = dtf.getTransformData();

        model.addAttribute("OUT_DATA", json);

        return this.printWriterView;
    }
    
    @RequestMapping(value = "/insert.do")
    public View insertDoQty(HttpServletRequest request, HttpServletResponse response, Model model, HttpSession session) throws Exception {
        
        String jsonArrayData = SecureUtils.getParameter(request, "iter");
        jsonArrayData = jsonArrayData.replaceAll("\\&quot\\;", "\"");
        System.out.println("jsonArrayData :: "+jsonArrayData);
        
        int result = this.puService.insertPu(jsonArrayData);
        
        String puPdfDir = puPdf.puPdfSave(request, response, jsonArrayData);
        
        //model.addAttribute("OUT_DATA", result + "건 저장 되었습니다.");
        model.addAttribute("OUT_DATA", puPdfDir);

        return this.printWriterView;
    }
    
    @RequestMapping(value = "/select/puHistory.do")
    public View selectPuHistory(HttpServletRequest request, Model model, HttpSession session) throws Exception {
        
        String dt = SecureUtils.getParameter(request, "dt", null);
        dt = dt.replaceAll("-","");
        User user = (User)session.getAttribute(Configurator.Constants.SESSION_SIGNED_USER);        
        String partner = SecureUtils.getParameter(request, "partner", null);
        //System.out.println("partner =================== ======================== "+partner);
        PuVO vo = new PuVO();
        vo.setDt(dt);
        vo.setPartner(user.getId());
        
        List<Map<String,String>> data = this.puService.selectPuHistory(vo);

        model.addAttribute("OUT_DATA", JSONArray.fromObject(data));

        return this.printWriterView;
    }
    
    @RequestMapping(value = "/popup.do")
    public String popup(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
        
    	String noRcv = SecureUtils.getParameter(request, "noRcv", null);
    	model.addAttribute("noRcv", noRcv);
    	
    	return "pu/puPopup";
    }
    
    @RequestMapping(value = "/select/histroyList.do")
    public View selectHistoryList(HttpServletRequest request, Model model, HttpSession session) throws Exception {
        
    	String noRcv = SecureUtils.getParameter(request, "noRcv", null);
    	
    	String cd_partner="";
    	String dt_rcv="";
    	String seq_rcv="";
    	if(!"".equals(noRcv)){
    		
    		cd_partner = noRcv.split("-")[0];
    		dt_rcv = noRcv.split("-")[1];
    		noRcv = noRcv.replaceAll("-", "");
    		
    		
    		System.out.println("cd_partner =================== ======================== "+cd_partner);
        	System.out.println("dt_rcv =================== ======================== "+dt_rcv);
        	System.out.println("noRcv =================== ======================== "+noRcv);
    	}
    	
    	PuVO vo = new PuVO();
    	vo.setCd_partner(cd_partner);
    	vo.setDt_rcv(dt_rcv);    	
    	vo.setNoRcv(noRcv);
    	
    	List<PuVO> data = this.puService.selectHistoryList(vo);

    	DataTransformer dtf = new DHTMLXGridDataTransformer();
        dtf.transform(data);

        String json = dtf.getTransformData();

        model.addAttribute("OUT_DATA", json);

        return this.printWriterView;
    }
    
}