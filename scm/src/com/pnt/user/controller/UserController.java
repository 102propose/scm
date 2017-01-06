package com.pnt.user.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

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
@Service("userController")
@RequestMapping(value = "/user")
public class UserController {
    private Logger logger = Logger.getLogger(this.getClass());

    @Resource(name = "printWriterView")
    private View printWriterView;

    @RequestMapping(value = "/pwd/notify.do")
    public String changePwdNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return "user/pwd/notify";
    }
    
    @RequestMapping(value = "/pwd/register/page.do")
    public String registerpwdPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return "user/pwd/register";
    }
    
    @RequestMapping(value = "/pwd/chgConfirm.do")
    public String chgConfirm(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	return "user/pwd/chgConfirm";
    }
    
    @RequestMapping(value = "/pwd/register.do")
    public String registerpwd(HttpServletRequest request, HttpServletResponse response) throws Exception {
//    	String redirector;
//        User dummyUser = (User) request.getSession(true).getAttribute(Configurator.Constants.SESSION_DUMMY_SIGNED_USER);
//        
//        if (dummyUser == null) {
//            return "redirect:" + request.getContextPath() + "/html/sessionExpired.html";
//        }
//        
//        Authenticator authn = null;
//        
//        String pwd = request.getParameter("pwd");
//        String cPwd = request.getParameter("cpwd");
//
//        try {
//            PasswordValidator pv = new PasswordValidator(dummyUser.getId(), pwd, cPwd);
//            pv.validate();
//            
//            String sapCmsPort = Configurator.getInstance().getConfig("sap_cmc_port");
//        	sapCmsPort = ("".equals(sapCmsPort) ? "" : ":" + sapCmsPort);
//            String cmc = Configurator.getInstance().getConfig("sap_host") + sapCmsPort;
//            String sharedSecret = Configurator.getInstance().getConfig(Configurator.Constants.SAP_TA_SHARED_SECRET);
//            
//            String adminid = Configurator.getInstance().getConfig("sap_admin_id");
//        	
//        	authn = new Authenticator();
//            authn.logOnWithTA(adminid, cmc, sharedSecret);
//        	authn.updateUserPassword(dummyUser.getId(), pwd);
//
//        	String g = SecureUtils.getParameter(request, "g");
//        	if ("self".equals(g)) {
//        		redirector = "user/pwd/chgConfirm";
//                request.getSession(true).invalidate(); // 재로그인을 위해 session invalidate
//        	}
//        	else if ("main".equals(g)) {
//        		redirector = "user/pwd/chgConfirm_m";
//        		request.getSession().removeAttribute(Configurator.Constants.SESSION_DUMMY_SIGNED_USER);
//        	}
//        	else { // via EP (SSO)
//        		redirector = "user/pwd/chgConfirm_o";
//        		request.getSession(true).invalidate(); // sso.do redirect를 위해 sessio invalidate
//        	}
//
//        } catch (PasswordValidationException e) {
//            redirector = "user/pwd/register";
//
//            String message = e.getMessage();
//            request.setAttribute("message", message);
//
//            if ("EMPTY_PWD".equals(message) || "EMPTY_CPWD".equals(message)) {
//                request.setAttribute("pwd", pwd);
//                request.setAttribute("cpwd", cPwd);
//            } else {
//                request.setAttribute("pwd", "");
//                request.setAttribute("cpwd", "");
//            }
//        } finally {
//        	if (authn != null) {
//        		authn.logOff();
//        	}
//        }
//
//        return redirector;
        return "";
    }
}