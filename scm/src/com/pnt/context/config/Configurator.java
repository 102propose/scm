package com.pnt.context.config;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.digester3.Digester;
import org.apache.log4j.Logger;

import com.pnt.common.util.CoreUtils;

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

public class Configurator {
    private Logger logger = Logger.getLogger(this.getClass());

    private static Configurator instance;

    private ServletContext servletContext;
    
    private Map<String, Object> configs = new HashMap<String, Object>();

    private Configurator() {
    }

    public static Configurator getInstance() {
        if (Configurator.instance == null) {
            Configurator.instance = new Configurator();
        }

        return Configurator.instance;
    }

    public void load() throws Exception {
        /* loading web application configurations */
        Digester digester = new Digester();
        digester.push(this);
        digester.setValidating(false);

        /* String config */
        digester.addObjectCreate("Configurations/config", Configuration.class);
        digester.addSetProperties("Configurations/config", "name", "name");
        digester.addSetProperties("Configurations/config", "value", "value");

        digester.addSetNext("Configurations/config", "addConfig");

        /* List config */
        digester.addObjectCreate("Configurations/list", ListConfiguration.class);
        digester.addSetProperties("Configurations/list", "name", "name");
        digester.addCallMethod("Configurations/list/value", "add", 1);
        digester.addCallParam("Configurations/list/value", 0);

        digester.addSetNext("Configurations/list", "addListConfig");

        File configure = new File(Configurator.getInstance().getServletContext().getRealPath(Configurator.Constants.APPLICATION_CONFIG_XML));
        digester.parse(configure);
        // digester.parse(this.getClass().getClassLoader().getResourceAsStream(configXml));
    }

    public void clear() {
        this.configs.clear();
    }

    public void addConfig(Configuration configuration) {
        String value = configuration.getValue();
        
        value = ConfigurationReplacer.replace(value, this.configs);
        
        this.configs.put(configuration.getName(), value);
    }

    public void addListConfig(ListConfiguration listConfig) {
        List<String> values = new ArrayList<String>();
        
        for (String value : listConfig.getList()) {
            value = ConfigurationReplacer.replace(value, this.configs);
            values.add(value);
        }
        
        this.configs.put(listConfig.getName(), values);
    }
    
    public Object getconfigObjectValue(String key) {
    	return this.configs.get(key);
    }

    public String getConfig(String key) {
        String value;
        
        if (this.configs.get(key) == null) {
            this.logger.debug("can't find config = " + key);
            value = "";
        } else {
            value = ((String) this.configs.get(key)).trim();
        }
        
        return value;
    }
    
    public int getConfigIntValue(String key) {
        int intValue;

        if (this.configs.get(key) == null || "".equals(this.configs.get(key))) {
            this.logger.debug("can't find config = " + key);
            intValue = 0;
        } else {
            String value = ((String) this.configs.get(key)).trim();
            try {
                intValue = Integer.valueOf(value).intValue();
            } catch (Exception e) {
                intValue = -99999;
            }
        }

        return intValue;
    }
    
    public boolean getConfigBooleanValue(String key) {
        Boolean boolValue = false;
        
        String val = this.getConfig(key);
        
        boolValue = new Boolean(val).booleanValue();
        
        return boolValue;
    }

    public List<String> getListConfig(String key) {
        List<String> value;
        
        if (this.configs.get(key) == null) {
            this.logger.debug("can't find config = " + key);
            value = new ArrayList<String>();
        } else {
            value = (List<String>) this.configs.get(key);
        }
        
        return value;
    }

    public String getFullDomain(HttpServletRequest request) {
        String protocol = request.isSecure() ? "https://" : "http://";
        String domain = request.getServerName();
        int port = request.getServerPort();

        String portStr = port == 80 ? "" : ":" + port;

        return protocol + domain + portStr;
    }

    public String getSapDomain() {
        String secure = CoreUtils.ifNull(Configurator.getInstance().getConfig("sap_secure"));

        String protocol = Boolean.valueOf(secure) ? "https://" : "http://";
        String domain = CoreUtils.ifNull(Configurator.getInstance().getConfig("sap_domain"));
        String port = Boolean.valueOf(secure) ? Configurator.getInstance().getConfig("sap_bi_secure_port") : Configurator.getInstance().getConfig("sap_bi_port");
        port = ("".equals(port) || "80".equals(port)) ? "" : ":" + CoreUtils.ifNull(port);

        String url = protocol + domain + port;

        return url;
    }

    public ServletContext getServletContext() {
        return servletContext;
    }

    final void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public String toString() {
        return this.configs.toString();
    }

    final public static class Constants {
        final public static String APPLICATION_CONFIG_PATH = "/WEB-INF/configs/application";
        final private static String APPLICATION_CONFIG_XML = Configurator.Constants.APPLICATION_CONFIG_PATH + "/configurations.xml";

        final public static String SESSION_SIGNED_USER = "SESSION_SIGNED_USER";

        final public static String SEED_CBC_ENCRIPTION_KEY = "VmxaV29UMVF4Um5OU2JrcHFVMFUxWVZZd1ZrZE9WbFpYVjJzNVVrMVlRbmRXVm1RMFYxZFdjazVWU2xWaGEzQm9XWHBCTVZKV1JsVldWREE5";
        final public static int SEED_CBC_ENCRIPTION_KEY_ROUND = 4;

        final public static String SAP_SIGNED_USER_MENU_BASKET = "SAP_SIGNED_USER_MENU";
        
        final public static String EXCEPTION_MUST_CHANGE_USER_PASSWORD = "EXCEPTION_MUST_CHANGE_USER_PASSWORD";
    }

}
