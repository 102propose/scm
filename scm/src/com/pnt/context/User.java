package com.pnt.context;

import com.pnt.user.UserVO;

/**
 * @author NH농협생명 신보험시스템 정보계 EDW Portal 개발팀 이주용
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

public class User {
    private String id;
    private String name;
    private String authInfo;
    
    public User() {
    }

    public User(UserVO vo) {
        this.serialize(vo);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getAuthInfo() {
		return authInfo;
	}

	public void setAuthInfo(String authInfo) {
		this.authInfo = authInfo;
	}

	public boolean isAdminUser() {
        return "admin".endsWith(id) ? true : false;
    }
    public boolean getAdminUser() {
        return this.isAdminUser();
    }
    
    public void serialize(UserVO vo) {
        this.setId(vo.getId());
        this.setName(vo.getName());
        this.setAuthInfo(vo.getAuthInfo());
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", authInfo=" + authInfo + "]";
    }

}
