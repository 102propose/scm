package com.pnt.common.interceptor;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.pnt.common.exception.SessionTimeoutException;
import com.pnt.common.message.PntMessageSource;
import com.pnt.common.util.CoreUtils;
import com.pnt.context.User;
import com.pnt.context.config.Configurator;

/**
 * @author ERP 시스템즈 이주용
 * @since 2015.04.18
 * @version 1.0
 * @see
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 * 
 *     수정일         수정자               수정내용
 *  --------------    ------------    ---------------------------
 *  2015.04.18        이주용              최초 생성
 * </pre>
 */

public class SessionValidationInterceptor implements HandlerInterceptor {
    private Logger logger = Logger.getLogger(getClass());
    
    @Resource(name = "pntMessageSource")
    private PntMessageSource messageSource;

    private List<String> bypassUris;
    private List<String> bypassControllers;

    public void setBypassUris(List<String> bypassUris) {
        this.bypassUris = bypassUris;
    }

    public void setBypassControllers(List<String> bypassControllers) {
        this.bypassControllers = bypassControllers;
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object controller) throws Exception {
    	String invokedController = controller.getClass().getName();
        this.logger.debug("invoked controller : " + invokedController);

        boolean proceed = false;

        String t;
        String requestURI = request.getRequestURI();
        this.logger.debug("invoked uri : " + requestURI);

        for (int i = 0; i < this.bypassUris.size(); i++) {
            t = this.bypassUris.get(i);

            if (requestURI.indexOf(t) > -1) {
                proceed = true;
                this.logger.debug(requestURI + " is not session validation target uri, so pass!!");
                break;
            }
        }

        if (!proceed) {
            proceed = true;

            if (this.bypassControllers.contains(invokedController)) {
                this.logger.debug(invokedController + " is not session validation target controller, so pass!!");
            } else {
                User signedUser = (User) request.getSession(true).getAttribute(Configurator.Constants.SESSION_SIGNED_USER);

                if (signedUser == null) {
                    proceed = false;
                    String msg = this.messageSource.getMessage("signin.user.session.expired");
                    this.logger.debug(msg);
                    
                    if (CoreUtils.isAjaxRequest(request)) {
                        response.setStatus(8501);
                    } else {
                        throw new SessionTimeoutException(msg);
                    }
                }
            }
        }

        return proceed;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object controller, ModelAndView modelNView) throws Exception {
        // do nothing...
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object controller, Exception paramException) throws Exception {
        // do nothing...
    }

}
