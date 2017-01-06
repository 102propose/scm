package com.pnt.common.message;

import org.springframework.context.MessageSource;

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

public interface PntMessageSource {
    
    public void setLocale(String locale);
    
    public void setMessageSource(MessageSource messageSource);

    public abstract String getMessage(String code);

    public abstract String getMessage(String code, String arg1);

    public abstract String getMessage(String code, String arg1, String arg2);

    public abstract String getMessage(String code, String arg1, String arg2, String arg3);

    public abstract String getMessage(String code, Object[] args);

}