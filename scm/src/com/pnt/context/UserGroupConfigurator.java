package com.pnt.context;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.digester3.Digester;

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

public class UserGroupConfigurator {
	private static UserGroupConfigurator instance;
	
	private List<UserGroup> userGroups = new LinkedList<UserGroup>();

    private UserGroupConfigurator() {
    }

	public static UserGroupConfigurator getInstance() {
		if (UserGroupConfigurator.instance == null) {
		    UserGroupConfigurator.instance = new UserGroupConfigurator();
		}
		return UserGroupConfigurator.instance;
	}

	/* xml 파일로 부터 메뉴를 읽어 this.menus에 저장한다. */
	public synchronized void load() throws Exception {
		try {
			Digester digester = new Digester();
			digester.push(this);
			digester.setValidating(false);
			
			digester.addObjectCreate("usergroups/usergroup", UserGroup.class);
			digester.addSetProperties("usergroups/usergroup", "id", "id");
			digester.addBeanPropertySetter("usergroups/usergroup/name", "name");
			digester.addBeanPropertySetter("usergroups/usergroup/desc", "desc");
			
			digester.addSetNext("usergroups/usergroup", "addUserGroup");
	        
	        /* parent group setting */
	        digester.addObjectCreate("usergroups/usergroup/parents/usergroup", UserGroup.class);
            digester.addSetProperties("usergroups/usergroup/parents/usergroup", "id", "id");
            digester.addBeanPropertySetter("usergroups/usergroup/parents/usergroup/name", "name");
            digester.addBeanPropertySetter("usergroups/usergroup/parents/usergroup/desc", "desc");
            
            digester.addSetNext("usergroups/usergroup/parents/usergroup", "addParent", "com.pnt.context.UserGroup");
			
			File configure = new File(Configurator.getInstance().getServletContext().getRealPath(UserGroupConfigurator.Constants.USERGROUP_XML));
			
			if (!configure.exists()) {
                throw new ConfigFileNotExistException(UserGroupConfigurator.Constants.USERGROUP_XML);
            }
			
            digester.parse(configure);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void clear() throws Exception {
	    this.userGroups.clear();
    }
	
	public void setUserGroups(List<UserGroup> userGroup) {
        this.userGroups = userGroup;
    }
	
	public UserGroup getUserGroup(int ugid) {
        UserGroup userGroup = null;

        for (int index = 0; index < this.userGroups.size(); index++) {
            userGroup = this.userGroups.get(index);
            
            if (userGroup.getId() == ugid) {
                break;
            }
        }
        
        return userGroup;
    }
	
	public List<UserGroup> getUserGroups() {
        return this.userGroups;
    }

    public void addUserGroup(UserGroup userGroup) {
        this.userGroups.add(userGroup);
    }
    
	final static class Constants {
	    final static String USERGROUP_XML = Configurator.Constants.APPLICATION_CONFIG_PATH + "hwg_eis_full_usergroup.xml";
	}

}
