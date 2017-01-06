package com.pnt.user.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.pnt.common.jdbc.SqlSessionDaoSupport;
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

@Repository("userDAO")
public class UserDAO extends SqlSessionDaoSupport {
    
    public List<UserVO> getUsers(UserVO user) throws Exception {
        List<UserVO> result = super.getSqlSession().selectList("User.getUsers", user);
        return result;
    }
    
    public List<UserVO> getUserCheck(UserVO user) throws Exception {
        List<UserVO> result = super.getSqlSession().selectList("User.getUserCheck", user);
        return result;
    }
    
    public int getUsersTotalCount(UserVO user) throws Exception {
        Integer totalCount = super.getSqlSession().selectOne("User.getUsersTotalCount", user);
        return totalCount.intValue();
    }
    
    public List<UserVO> getUsersPaging(UserVO user) throws Exception {
        List<UserVO> result = super.getSqlSession().selectList("User.getUsersPaging", user);
        return result;
    }

    public int insertUser(UserVO user) throws Exception {
        int count = super.getSqlSession().insert("User.insertUser", user);
        return count;
    }

    public int updateUser(UserVO user) throws Exception {
        int count = super.getSqlSession().update("User.updateUser", user);
        return count;
    }

    public int deleteUser(String id) throws Exception {
        int count = super.getSqlSession().delete("User.deleteUser", id);
        return count;
    }
    
    public int changePassword(String userID, String cipherPwd, String updUserID) throws Exception {
        UserVO vo = new UserVO();
        vo.setId(userID);
        vo.setPassword(cipherPwd);
        vo.setUpdUserID(updUserID);
        
        int count = super.getSqlSession().delete("User.changePassword", vo);
        return count;
    }
    
}
