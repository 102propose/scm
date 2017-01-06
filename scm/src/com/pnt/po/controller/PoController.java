package com.pnt.po.controller;

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
import com.pnt.po.PoInfoVO;
import com.pnt.po.PoVO;
import com.pnt.po.service.PoService;
import com.pnt.user.controller.UserController;

@Controller
@RequestMapping(value = "/po")
public class PoController {
    private Logger logger = Logger.getLogger(this.getClass());

    @Resource(name = "pntMessageSource")
    private PntMessageSource messageSource;
    
    @Resource(name = "poService")
    private PoService poService;
    
    @Resource(name = "printWriterView")
    private View printWriterView;
    
    @Resource(name = "userController")
    private UserController userController;

    @RequestMapping(value = "/page.do")
    public String main(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return "po/po";
    }
    
    @RequestMapping(value = "/select/getList.do")
    public View selectPoList(HttpServletRequest request, Model model, HttpSession session) throws Exception {
        
        String dtFr = SecureUtils.getParameter(request, "dtFr", null);
        String dtTo = SecureUtils.getParameter(request, "dtTo", null);
        String poPartner = SecureUtils.getParameter(request, "poPartner", null);
        
        User user = (User)session.getAttribute(Configurator.Constants.SESSION_SIGNED_USER);
        
        List<PoVO> data = this.poService.selectPoListByKey(user.getId(), dtFr, dtTo, poPartner);

        DataTransformer dtf = new DHTMLXGridDataTransformer();
        dtf.transform(data);

        String json = dtf.getTransformData();

        model.addAttribute("OUT_DATA", json);

        return this.printWriterView;
    }
    
    @RequestMapping(value = "/select/getInfo.do")
    public View selectPoInfo(HttpServletRequest request, Model model) throws Exception {
        
        String po_no = SecureUtils.getParameter(request, "po_no", null);
        String dtFr = SecureUtils.getParameter(request, "dtFr", null);
        String dtTo = SecureUtils.getParameter(request, "dtTo", null);
        
        List<PoInfoVO> data = this.poService.selectPoInfo(po_no, dtFr, dtTo);

//        DataTransformer dtf = new DHTMLXGridDataTransformer();
//        dtf.transform(data);
//
//        String json = dtf.getTransformData();

        model.addAttribute("OUT_DATA", JSONArray.fromObject(data));

        return this.printWriterView;
    }
    
    @RequestMapping(value = "/select/partners.do")
    public View selectPoPartners(HttpServletRequest request, Model model, HttpSession session) throws Exception {
        
        String dtFr = SecureUtils.getParameter(request, "dtFr", null);
        String dtTo = SecureUtils.getParameter(request, "dtTo", null);
        
        List<Map<String,String>> data = this.poService.selectPoPartners(dtFr, dtTo);

        model.addAttribute("OUT_DATA", JSONArray.fromObject(data));

        return this.printWriterView;
    }
    
    @RequestMapping(value = "/insert.do")
    public View insertPo(HttpServletRequest request, Model model) throws Exception {
        JSONArray secured = SecureUtils.getSecuredJSONArray(request, "json");
//        User signedUser = (User) request.getSession(true).getAttribute(Configurator.Constants.SESSION_SIGNED_USERID);

        this.poService.savePo(secured);

        model.addAttribute("OUT_DATA", this.messageSource.getMessage("common.msg.saved"));

        return this.printWriterView;
    }

    @RequestMapping(value = "/update.do")
    public View updatePo(HttpServletRequest request, Model model) throws Exception {
        JSONArray secured = SecureUtils.getSecuredJSONArray(request, "json");
//        User signedUser = (User) request.getSession(true).getAttribute(Configurator.Constants.SESSION_SIGNED_USERID);

        this.poService.savePo(secured);

        model.addAttribute("OUT_DATA", this.messageSource.getMessage("common.msg.saved"));

        return this.printWriterView;
    }

    @RequestMapping(value = "/delete.do")
    public View deletePo(HttpServletRequest request, Model model) throws Exception {
        JSONArray secured = SecureUtils.getSecuredJSONArray(request, "json");

        this.poService.deletePo(secured);

        model.addAttribute("OUT_DATA", this.messageSource.getMessage("common.msg.deleted"));

        return this.printWriterView;
    }
}