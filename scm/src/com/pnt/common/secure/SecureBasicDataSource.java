package com.pnt.common.secure;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;

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

public class SecureBasicDataSource extends BasicDataSource {
    private Logger logger = Logger.getLogger(getClass());
            
    final private String key = Configurator.Constants.SEED_CBC_ENCRIPTION_KEY;
    
    @Override
    public synchronized void setUrl(String url) {
        try {
        	String dec = SecureUtils.decSeed(this.key, url);
            super.setUrl(dec);
        } catch (Exception e) {
           this.logger.warn("", e);
        }
    }

    @Override
    public void setUsername(String username){
        try {
        	String dec = SecureUtils.decSeed(this.key, username);
            super.setUsername(dec);
        } catch (Exception e) {
            this.logger.warn("", e);
        } 
   }
  
    @Override
	public void setPassword(String password) {
		try {
			String dec = SecureUtils.decSeed(this.key, password);
			super.setPassword(dec);
		} catch (Exception e) {
			this.logger.warn("", e);
		}
	}
}
