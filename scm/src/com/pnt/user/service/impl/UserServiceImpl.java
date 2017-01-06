package com.pnt.user.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pnt.common.exception.DuplicateKeyException;
import com.pnt.common.message.PntMessageSource;
import com.pnt.context.User;
import com.pnt.context.config.Configurator;
import com.pnt.user.UserVO;
import com.pnt.user.dao.UserDAO;
import com.pnt.user.service.UserService;

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

@Service("userService")
public class UserServiceImpl implements UserService {
    private Logger logger = Logger.getLogger(this.getClass());

    @Resource(name = "pntMessageSource")
    private PntMessageSource messageSource;

    @Resource(name = "userDAO")
    private UserDAO userDAO;

    public List<UserVO> getUsers(String id, String name, String gradeName, String orgName, String useYN) throws Exception {
        UserVO user = new UserVO();
        user.setId(id);
        user.setName(name);
        user.setGradeName(gradeName);
        user.setOrgName(orgName);
        
        if (useYN != null) {
            user.setUseYN(Boolean.valueOf(useYN));
        }
        
        List<UserVO> result = this.userDAO.getUsers(user);

        return result;
    }
    
    public List<UserVO> getUserCheck(String id, String pw) throws Exception {
        UserVO user = new UserVO();
        user.setId(id);
        user.setPassword(pw);        
        
        List<UserVO> result = this.userDAO.getUserCheck(user);

        return result;
    }
    
    public Map<String,Object> getUsersPaging(String id, String name, String gradeName, String orgName, String useYN, String condRoleID, int currPage, int rowCountPerPage) throws Exception {
        UserVO user = new UserVO();
        user.setId(id);
        user.setName(name);
        user.setGradeName(gradeName);
        user.setOrgName(orgName);
        user.setRoleID(condRoleID);
        
        if (useYN != null) {
            user.setUseYN(Boolean.valueOf(useYN));
        }
        
        int userTotalCount = this.userDAO.getUsersTotalCount(user);
        
        /* for paging */
        user.setCurrPage(currPage, rowCountPerPage, userTotalCount);
        
        List<UserVO> result = this.userDAO.getUsersPaging(user);
        
        Map<String,Object> json = new HashMap<String,Object>();
        json.put("currPage", user.getCurrPage());
        json.put("maxPage", user.getMaxPage());
        json.put("paginatorPageCount", Configurator.getInstance().getConfig("paginator_count"));
        json.put("DATA", result);

        return json;
    }
    
    @Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
    private void saveUser(User signedUser, List<?> list, boolean isAdd) throws Exception {
        String regUserID = signedUser.getId();

        String id;
        String name;
        String gradeName;
        String orgName;
        String roleID;
        Object useYN;

        UserVO vo = new UserVO();
        
        List<UserVO> users = null;
        
        if (isAdd) {
            users = this.userDAO.getUsers(new UserVO());    
        }

        for (int index = 0; index < list.size(); index++) {
            id = ((JSONObject) list.get(index)).getString("id");
            name = ((JSONObject) list.get(index)).getString("nm");
            gradeName = ((JSONObject) list.get(index)).getString("grade");
            orgName = ((JSONObject) list.get(index)).getString("org");
            roleID = ((JSONObject) list.get(index)).getString("role");
            useYN = ((JSONObject) list.get(index)).getString("use");

            vo.setId(id);
            vo.setName(name);
            vo.setGradeName(gradeName);
            vo.setOrgName(orgName);
            vo.setRoleID(roleID);
            vo.setPassword(id);
//            vo.setPassword(SecureUtils.sha256(id));
            vo.setRegUserID(regUserID);
            vo.setUseYN(useYN);

            if (isAdd) {
                if (users != null) {
                    for (int z = 0; z < users.size(); z++) {
                        if (users.get(z).getId().equals(vo.getId())) {
                            throw new DuplicateKeyException(this.messageSource.getMessage("common.msg.dup.emp", new String[]{vo.getId() + " - " + vo.getName()}));
                        }
                    }
                }
                this.userDAO.insertUser(vo);
            } else {
                vo.setUpdUserID(regUserID);
                this.userDAO.updateUser(vo);
            }
            
        }
    }

    public void insertUser(User signedUser, List<?> list) throws Exception {
        this.saveUser(signedUser, list, true);
    }

    public void updateUser(User signedUser, List<?> list) throws Exception {
        this.saveUser(signedUser, list, false);
    }

    @Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
    public void deleteUser(List<?> list) throws Exception {
        String userID;

        for (int index = 0; index < list.size(); index++) {
            userID = ((JSONObject) list.get(index)).getString("id");
            this.userDAO.deleteUser(userID);
        }

    }

    public int changePassword(String id, String cipherPwd, String updUserID) throws Exception {
        return this.userDAO.changePassword(id, cipherPwd, updUserID);
    }
    
}
