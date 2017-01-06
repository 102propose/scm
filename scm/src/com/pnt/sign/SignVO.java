package com.pnt.sign;

import java.io.Serializable;

import com.pnt.common.secure.SecureUtils;

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

public class SignVO implements Serializable {
    private static final long serialVersionUID = 7821470971870849679L;

    private String userid = "";

    private String userpwd = "";

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public void setUserpwd(String userpwd) throws Exception {
        this.userpwd = SecureUtils.sha256(userpwd);
    }

    public String getUserid() {
        return userid;
    }

    public String getUserpwd() {
        return userpwd;
    }

}
