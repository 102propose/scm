package com.pnt.sign.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.pnt.common.message.PntMessageSource;
import com.pnt.context.Menu;
import com.pnt.context.User;
import com.pnt.context.config.Configurator;
import com.pnt.sign.UnRegisteredUserException;
import com.pnt.sign.service.SignService;
import com.pnt.user.UserVO;
import com.pnt.user.service.UserService;

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

@Service("signService")
public class SignServiceImpl implements SignService {
    private Logger logger = Logger.getLogger(this.getClass());

    @Resource(name = "pntMessageSource")
    private PntMessageSource messageSource;
    
    @Resource(name = "userService")
    private UserService userService;

    public void signin(HttpServletRequest request, String uid, String pwd, boolean bwUser) throws Exception {
        List<UserVO> users = this.userService.getUserCheck(uid, pwd);
        
        if (users.size() > 0) {
            UserVO vo = users.get(0);
            User signedUser = new User(vo);
            
            request.getSession().setAttribute(Configurator.Constants.SESSION_SIGNED_USER, signedUser);

        } else {
            throw new UnRegisteredUserException(messageSource.getMessage("signin.user.noexist", uid));
        }
    }

//    private Object lock = new Object();
    public List<Menu> getUserMenu(HttpServletRequest request) throws Exception {
//        User signedUser = (User) request.getSession().getAttribute(Configurator.Constants.SESSION_SIGNED_USER);
//
//        this.logger.debug("signed user - " + signedUser);
//        
//        List<Menu> userDocumentList = (List<Menu>) request.getSession(true).getAttribute(Configurator.Constants.SAP_SIGNED_USER_MENU_BASKET);
//
//        List<Menu> userMenu = null;
//        synchronized (this.lock) {
//        	BootStrap bootup = new BootStrap();
//    		bootup.start(new String[] {"usersync"});
//            
//    		userMenu = MenuConfigurator.getInstance().getUserMenus(userDocumentList);
//		}
//        
//        return userMenu;
        return null;
    }

    public String getUserMenuJson(HttpServletRequest request) throws Exception {
        String json = "{\"id\": \"ROOT_MENU\", \"item\": [";

        String contextPath = request.getSession().getServletContext().getContextPath();

        User user = (User)request.getSession().getAttribute(Configurator.Constants.SESSION_SIGNED_USER);
        String authInfo = user.getAuthInfo();
        
        this.logger.debug("====================================================================");
        this.logger.debug(" contextPath : " + contextPath);
        this.logger.debug(" getId : " + user.getId());
        this.logger.debug(" getName : " + user.getName());
        this.logger.debug(" getAuthInfo : " + user.getAuthInfo());
        this.logger.debug("====================================================================");
//        List<Menu> userMenus = this.getUserMenu(request);
        
        List<Menu> userMenus = new ArrayList<Menu>();

        if("A".equals(authInfo)){
        	/* 납입지시*/
            userMenus.add(new Menu(100, "납입지시", "", 1));
            userMenus.add(new Menu(101, "발주관리", "/po/page.do", 2, 100));
            userMenus.add(new Menu(102, "납품예정(D/O)관리", "/do/page.do", 2, 100));
            userMenus.add(new Menu(103, "납품등록관리", "/pu/page.do", 2, 100));
            
            /* 물류정보*/
        	userMenus.add(new Menu(200, "물류정보", "", 1));
        	userMenus.add(new Menu(201, "입고현황", "/rc/page.do", 2, 200));
        	
        	/* 생산정보*/
        	userMenus.add(new Menu(300, "생산정보", "", 1));
        	userMenus.add(new Menu(301, "생산현황", "/pp/page.do", 2, 300));
        	
        	/* 회계정보*/
        	userMenus.add(new Menu(400, "회계정보", "", 1));
        	userMenus.add(new Menu(401, "대금지불현황", "/ac/page.do", 2, 400));
        	
        	/* 품질정보*/
        	userMenus.add(new Menu(500, "품질정보", "", 1));
        	
        	/* 기본정보*/
        	userMenus.add(new Menu(600, "기본정보", "", 1));
        } else if("B".equals(authInfo) || "".equals(authInfo)){
        	/* 납입지시*/
            userMenus.add(new Menu(100, "납입지시", "", 1));
            userMenus.add(new Menu(101, "발주관리", "/po/page.do", 2, 100));
            userMenus.add(new Menu(102, "납품예정(D/O)관리", "/do/page.do", 2, 100));
            userMenus.add(new Menu(103, "납품등록관리", "/pu/page.do", 2, 100));
            
            /* 물류정보*/
        	userMenus.add(new Menu(200, "물류정보", "", 1));
        	userMenus.add(new Menu(201, "입고현황", "/rc/page.do", 2, 200));
        	
        	/* 생산정보*/
        	userMenus.add(new Menu(300, "", "", 1));
        	//userMenus.add(new Menu(301, "생산현황", "/pp/page.do", 2, 300));
        	
        	/* 회계정보*/
        	userMenus.add(new Menu(400, "회계정보", "", 1));
        	userMenus.add(new Menu(401, "대금지불현황", "/ac/page.do", 2, 400));
        	
        	/* 품질정보*/
        	userMenus.add(new Menu(500, "품질정보", "", 1));
        	
        	/* 기본정보*/
        	userMenus.add(new Menu(600, "기본정보", "", 1));        	
        }
                
        //userMenus.add(new Menu(103, "출하등록", "", 2, 100));

        for (int i = 0; i < userMenus.size(); i++) {
            json += ((Menu) userMenus.get(i)).asJson() + ",";
        }

        if (userMenus.size() > 0) {
            json = json.substring(0, json.length() - 1);
        }

        json += "]";
        json +="}";

        return json;
    }

    public String getUserMenuXml(HttpServletRequest request) throws Exception {
        String xml = "<?xml version=\"1.0\" encoding=\"" + Configurator.getInstance().getConfig("encoding") + "\"?><tree id=\"0\">";

        List<Menu> userMenus = this.getUserMenu(request);

        for (int i = 0; i < userMenus.size(); i++) {
            xml += ((Menu) userMenus.get(i)).asTreeXml();
        }

        xml += "</tree>";

        return xml;
    }

	private String getCookieVal(HttpServletRequest request, String name) {
		Cookie cookies[] = request.getCookies();
		String value = null;
		if(cookies != null) {
			for(int i = 0;  i < cookies.length; i++) {
				Cookie cook = cookies[i];
				if(cook.getName().trim().equals(name)){
					value = cook.getValue();
					break;
				}
			}
		}
		return value;
	}
}
