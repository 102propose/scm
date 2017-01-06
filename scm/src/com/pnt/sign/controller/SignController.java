package com.pnt.sign.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.pnt.common.message.PntMessageSource;
import com.pnt.sign.service.SignService;

/**
 * @author ERP 시스템즈 이주용
 * @since 2015.04.18
 * @version 1.0
 * @see
 * 
 *      <pre>
 * << 개정이력(Modification Information) >>
 * 
 *     수정일         수정자               수정내용
 *  --------------    ------------    ---------------------------
 *  2015.04.18        이주용              최초 생성
 * </pre>
 */

@Controller
@RequestMapping(value = "/sign")
public class SignController {
    private Logger logger = Logger.getLogger(this.getClass());
    
    @Resource(name = "pntMessageSource")
    private PntMessageSource messageSource;

    @Resource(name = "signService")
    private SignService signService;

    @RequestMapping(value = "/page.do")
    public ModelAndView page(HttpServletRequest request) throws Exception {
        request.getSession().invalidate();
        return new ModelAndView("sign/login");
    }
    
    @RequestMapping(value = "/in.do")
    public String signIn(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String uid = request.getParameter("userid");
        String pwd = request.getParameter("userpwd");
        
        uid = uid.trim();
        
        try {
            this.signService.signin(request, uid, pwd, false);
        } 
        catch(Exception e) {
            this.logger.error(e);
//            response.setStatus(500);
            throw e;
        }
        
        return "main/main";
    }
    
    @RequestMapping(value = "/out.do")
    public ModelAndView signOut(HttpServletRequest request) throws Exception {
        request.getSession().invalidate();
        return new ModelAndView("sign/logout");
    }

}