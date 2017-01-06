package com.pnt.context.config;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

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

public class ApplicationContextListener implements ServletContextListener {
    private Logger logger = Logger.getLogger(this.getClass());

	public void contextInitialized(ServletContextEvent event) {
	    
		try {
			Configurator.getInstance().setServletContext(event.getServletContext());
			Configurator.getInstance().load();
			this.logger.debug("Configurations : " + Configurator.getInstance());
		} catch (Exception e) {
			this.logger.error("exception occur while loading context listener", e);
		} 
	}
	
	public void contextDestroyed(ServletContextEvent event) {
	}

}
