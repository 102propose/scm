package com.pnt.context;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

public class UserGroup implements Serializable {
    private static final long serialVersionUID = -2666097158881132581L;

    private int id;
    private String name;
    private String desc;
    private List<UserGroup> parents = new ArrayList<UserGroup>();

    public UserGroup() {
    }

    public UserGroup(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean compare(UserGroup role) {
        if (this.name != null && this.name.equalsIgnoreCase(role.getName())) {
            return true;
        } else {
            return false;
        }
    }
    
    public void addParent(UserGroup parent) {
        this.parents.add(parent);
    }

    public List<UserGroup> getParents() {
        return parents;
    }

    @Override
    public String toString() {
        return "UserGroup [id=" + id + ", name=" + name + ", desc=" + desc + ", parents=" + parents + "]";
    }

}
