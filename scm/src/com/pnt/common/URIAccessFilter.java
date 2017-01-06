package com.pnt.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.pnt.common.exception.InvalidAccessException;

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

public class URIAccessFilter implements Filter {
    private boolean isProtected = true;
    private List<String> targets = new ArrayList<String>();

    public void init(FilterConfig config) throws ServletException {
        String mode = config.getInitParameter("mode");

        if (mode == null || "PROTECT".equalsIgnoreCase(mode)) {
            this.isProtected = true;
        } else {
            this.isProtected = false;
        }

        String targets = config.getInitParameter("targets");

        if (targets != null) {
            String target = null;
            String[] targetList = targets.split(",");

            for (int i = 0; i < targetList.length; i++) {
                target = targetList[i];

                if (target == null || "".equals(target.trim())) {
                    continue;
                }

                this.targets.add(target);
            }
        }

    }

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        boolean checker = true;
        HttpServletRequest request = (HttpServletRequest) req;
        String requestURI = request.getRequestURI();

        if (this.isProtected) {
            checker = this.logic(req, res, requestURI, false);
        } else {
            checker = this.logic(req, res, requestURI, true);
        }

        if (checker) {
            chain.doFilter(req, res);
        } else {
            throw new InvalidAccessException("INVALID REQUEST URI - [" + requestURI + "]");
        }
    }
    
    private boolean logic(ServletRequest req, ServletResponse res, String requestURI, boolean checker) throws IOException, ServletException {
        String target = null;
        String ext = null;
        int lastIndex;

        for (int index = 0; index < this.targets.size(); index++) {
            target = this.targets.get(index);
            lastIndex = requestURI.lastIndexOf(target);
            
            if (lastIndex > -1) {
                ext = requestURI.substring(lastIndex);
                
                if (target.equals(ext)) {
                    checker = !checker;
                    break;
                }
            }
            
        }

        return checker;
    } 

    public void destroy() {
    }

}
