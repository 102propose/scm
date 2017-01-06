package com.pnt.rc.controller;

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
import com.pnt.rc.RcInfoVO;
import com.pnt.rc.RcVO;
import com.pnt.rc.service.RcService;
import com.pnt.user.controller.UserController;

@Controller
@RequestMapping(value = "/rc")
public class RcController {
    private Logger logger = Logger.getLogger(this.getClass());

    @Resource(name = "pntMessageSource")
    private PntMessageSource messageSource;
    
    @Resource(name = "rcService")
    private RcService rcService;
    
    @Resource(name = "printWriterView")
    private View printWriterView;
    
    @Resource(name = "userController")
    private UserController userController;

    @RequestMapping(value = "/page.do")
    public String main(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return "rc/rc";
    }
    
    @RequestMapping(value = "/select/getList.do")
    public View selectRcList(HttpServletRequest request, Model model, HttpSession session) throws Exception {
        
        String dtFr = SecureUtils.getParameter(request, "dtFr", null);
        String dtTo = SecureUtils.getParameter(request, "dtTo", null);
        String rcPartner = SecureUtils.getParameter(request, "rcPartner", null);
        
        User user = (User)session.getAttribute(Configurator.Constants.SESSION_SIGNED_USER);
        
        List<RcVO> data = this.rcService.selectRcListByKey(user.getId(), dtFr, dtTo, rcPartner);

        DataTransformer dtf = new DHTMLXGridDataTransformer();
        dtf.transform(data);

        String json = dtf.getTransformData();

        model.addAttribute("OUT_DATA", json);

        return this.printWriterView;
    }
    
    @RequestMapping(value = "/select/partners.do")
    public View selectRcPartners(HttpServletRequest request, Model model, HttpSession session) throws Exception {
        
        String dtFr = SecureUtils.getParameter(request, "dtFr", null);
        String dtTo = SecureUtils.getParameter(request, "dtTo", null);
        
        List<Map<String,String>> data = this.rcService.selectRcPartners(dtFr, dtTo);

        model.addAttribute("OUT_DATA", JSONArray.fromObject(data));

        return this.printWriterView;
    }
     
}