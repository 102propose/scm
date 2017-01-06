package com.pnt.pp.controller;

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
import com.pnt.pp.PpInfoVO;
import com.pnt.pp.PpVO;
import com.pnt.pp.service.PpService;
import com.pnt.user.controller.UserController;

@Controller
@RequestMapping(value = "/pp")
public class PpController {
    private Logger logger = Logger.getLogger(this.getClass());

    @Resource(name = "pntMessageSource")
    private PntMessageSource messageSource;
    
    @Resource(name = "ppService")
    private PpService ppService;
    
    @Resource(name = "printWriterView")
    private View printWriterView;
    
    @Resource(name = "userController")
    private UserController userController;

    @RequestMapping(value = "/page.do")
    public String main(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return "pp/pp";
    }
    
    @RequestMapping(value = "/select/getList.do")
    public View selectPpList(HttpServletRequest request, Model model, HttpSession session) throws Exception {
        
        String dtFr = SecureUtils.getParameter(request, "dtFr", null);
        String dtTo = SecureUtils.getParameter(request, "dtTo", null);
        
        User user = (User)session.getAttribute(Configurator.Constants.SESSION_SIGNED_USER);
        
        List<PpVO> data = this.ppService.selectPpListByKey(user.getId(), dtFr, dtTo);

        DataTransformer dtf = new DHTMLXGridDataTransformer();
        dtf.transform(data);

        String json = dtf.getTransformData();

        model.addAttribute("OUT_DATA", json);

        return this.printWriterView;
    }
}