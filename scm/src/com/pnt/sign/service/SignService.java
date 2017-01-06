package com.pnt.sign.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.pnt.context.Menu;

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

public interface SignService {
    
    public void signin(HttpServletRequest request, String uid, String pwd, boolean bwUser) throws Exception;
    
	public List<Menu> getUserMenu(HttpServletRequest request) throws Exception;
	
	public String getUserMenuJson(HttpServletRequest request) throws Exception;
	
	public String getUserMenuXml(HttpServletRequest request) throws Exception;
	
}