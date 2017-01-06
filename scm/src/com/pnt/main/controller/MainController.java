package com.pnt.main.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.pnt.sign.service.SignService;
import com.pnt.user.controller.UserController;

@Controller
@RequestMapping(value = "/main")
public class MainController {
    private Logger logger = Logger.getLogger(this.getClass());

    @Resource(name = "signService")
    private SignService signService;
    
    @Resource(name = "printWriterView")
    private View printWriterView;
    
    @Resource(name = "userController")
    private UserController userController;
    
    @RequestMapping(value = "/page.do")
    public String main(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return "main/main";
    }
    
    @RequestMapping(value = "/menu/json.do")
    public View getUserMenuJson(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {

    	try {
    		String jsonMenu = this.signService.getUserMenuJson(request);

            this.logger.debug("====================================================================");
            this.logger.debug(jsonMenu);
            this.logger.debug("====================================================================");

            model.addAttribute("OUT_DATA", jsonMenu);
    	} catch (Exception e){
    		this.logger.error("", e);
    		response.sendError(500);
    	}
    	
        return this.printWriterView;
    }
}