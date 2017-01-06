package com.pnt.user.service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.pnt.context.User;
import com.pnt.user.UserVO;

/**
 * @author NH농협생명 신보험시스템 정보계 EDW Portal 개발팀 이주용
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

public interface UserService {

    public List<UserVO> getUsers(String id, String name, String gradeName, String orgName, String useYN) throws Exception;
    
    public List<UserVO> getUserCheck(String id, String pw) throws Exception;
    
    public Map<String,Object> getUsersPaging(String id, String name, String gradeName, String orgName, String useYN, String condRoleID, int currPage, int rowCountPerPage) throws Exception;

    public void insertUser(User signedUser, List<?> users) throws Exception;

    public void updateUser(User signedUser, List<?> users) throws Exception;

    public void deleteUser(List<?> userList) throws Exception;
    
    public int changePassword(String userID, String password, String updUserID) throws Exception;

}