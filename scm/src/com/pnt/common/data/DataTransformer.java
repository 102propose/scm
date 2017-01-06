package com.pnt.common.data;

import com.pnt.common.message.PntMessageSource;

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

abstract public class DataTransformer {
    protected String text;
    protected PntMessageSource messageSource;

    public String getTransformData() {
        return this.text;
    }

    public void setMessageSource(PntMessageSource messageSource) {
        this.messageSource = messageSource;
    }

    abstract public void transform(Object data) throws Exception;
}
