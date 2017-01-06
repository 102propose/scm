package com.pnt.ac.controller;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

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
import com.pnt.ac.AcInfoVO;
import com.pnt.ac.AcVO;
import com.pnt.ac.service.AcService;
import com.pnt.user.controller.UserController;

@Controller
@RequestMapping(value = "/ac")
public class AcController {
    private Logger logger = Logger.getLogger(this.getClass());

    @Resource(name = "pntMessageSource")
    private PntMessageSource messageSource;
    
    @Resource(name = "acService")
    private AcService acService;
    
    @Resource(name = "printWriterView")
    private View printWriterView;
    
    @Resource(name = "userController")
    private UserController userController;

    @RequestMapping(value = "/page.do")
    public String main(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return "ac/ac";
    }
    
    @RequestMapping(value = "/select/getList.do")
    public View selectAcList(HttpServletRequest request, Model model, HttpSession session) throws Exception {
        
        String dtFr = SecureUtils.getParameter(request, "dtFr", null);
        String dtTo = SecureUtils.getParameter(request, "dtTo", null);
        String acPartner = SecureUtils.getParameter(request, "acPartner", null);
        
        User user = (User)session.getAttribute(Configurator.Constants.SESSION_SIGNED_USER);
        
        List<AcVO> data = this.acService.selectAcListByKey(user.getId(), dtFr, dtTo, acPartner);
        
        DecimalFormat df = new DecimalFormat("#,##0");
        //System.out.println("data ============ "+data.toString());
        for(int i=0; i < data.size(); ){
        	        	
        	int am_cr = 0; //입고
        	int am_dr = 0; //지급        	
        	int am_jan = 0; //잔액
        	
        	String new_am_jan = "";        	
        	        	
        	if(i > 0){
        		am_jan = Integer.parseInt(data.get(--i).getAm_jan().replaceAll(",", ""));
        		//System.out.println("i ========= "+ i +"    이전잔액 ========================= "+am_jan);
        		
        		i++;
        		am_cr = Integer.parseInt(data.get(i).getAm_cr().replaceAll(",", ""));
        		am_dr = Integer.parseInt(data.get(i).getAm_dr().replaceAll(",", ""));
        		//System.out.println("i ========= "+ i +"    입고 ========================= "+am_cr);
        		//System.out.println("i ========= "+ i +"    출고 ========================= "+am_dr);
        		am_jan = am_jan + am_cr - am_dr;
        		//System.out.println("i ========= "+ i +"    차액잔액 ========================= "+am_jan);        		
        		new_am_jan = ""+df.format(am_jan);
        		data.get(i).setAm_jan(new_am_jan);
        		        		
        	}
        	
        	i++;
        	        	
        }
        
        DataTransformer dtf = new DHTMLXGridDataTransformer();
        dtf.transform(data);

        String json = dtf.getTransformData();

        model.addAttribute("OUT_DATA", json);

        return this.printWriterView;
    }
    
    @RequestMapping(value = "/select/partners.do")
    public View selectAcPartners(HttpServletRequest request, Model model, HttpSession session) throws Exception {
        
        String dtFr = SecureUtils.getParameter(request, "dtFr", null);
        String dtTo = SecureUtils.getParameter(request, "dtTo", null);
        
        List<Map<String,String>> data = this.acService.selectAcPartners(dtFr, dtTo);

        model.addAttribute("OUT_DATA", JSONArray.fromObject(data));

        return this.printWriterView;
    }
     
}