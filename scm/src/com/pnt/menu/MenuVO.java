package com.pnt.menu;

import java.util.Map;

import net.sf.json.JSONObject;

/**
 * @author ERP 시스템즈 경영지원 개발팀 이주용
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

public class MenuVO {
    private String id;
    private String name;
    private int level;
    private int order;
    private String parentID;
    private String link;
    private String type;
    private String selfYN;
    private String useYN;
    private String roles;
    private int checked;
    private String regUserID;
    private String regDate;
    private String updUserID;
    private String updDate;

    public MenuVO() {
    }

    public MenuVO(Map<String, String> map) {
        this.serialize((JSONObject) map);
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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setLevel(String level) {
        int l = Integer.valueOf(level).intValue();
        this.setLevel(l);
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public void setOrder(String order) {
        int s = Integer.valueOf(order).intValue();
        this.setOrder(s);
    }

    public String getParentID() {
        return parentID;
    }

    public void setParentID(String parentID) {
        this.parentID = parentID;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSelfYN() {
        return selfYN;
    }

    public void setSelfYN(String selfYN) {
        this.selfYN = selfYN;
    }
    
    public void setSelfYN(Object selfYN) {
        String value;

        if (selfYN instanceof Boolean) {
            value = (Boolean) selfYN ? "Y" : "N";
        } else {
            value = (String) selfYN;
        }

        this.setSelfYN(value);
    }

    public String getUseYN() {
        return useYN;
    }

    public void setUseYN(Object useYN) {
        String value;

        if (useYN instanceof Boolean) {
            value = (Boolean) useYN ? "Y" : "N";
        } else {
            value = (String) useYN;
        }

        this.setUseYN(value);
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public void setUseYN(String useYN) {
        this.useYN = useYN;
    }

    public int getChecked() {
        return checked;
    }

    public void setChecked(int checked) {
        this.checked = checked;
    }

    public String getRegUserID() {
        return regUserID;
    }

    public void setRegUserID(String regUserID) {
        this.regUserID = regUserID;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public String getUpdUserID() {
        return updUserID;
    }

    public void setUpdUserID(String updUserID) {
        this.updUserID = updUserID;
    }

    public String getUpdDate() {
        return updDate;
    }

    public void setUpdDate(String updDate) {
        this.updDate = updDate;
    }

    public void serialize(JSONObject json) {
        String id = json.containsKey("id") ? json.getString("id") : "";
        String name = json.containsKey("nm") ? json.getString("nm") : "";
        String level = json.containsKey("level") ? json.getString("level") : "";
        String order = json.containsKey("order") ? json.getString("order") : "";
        String parentID = json.containsKey("pid") ? json.getString("pid") : "";
        String link = json.containsKey("link") ? json.getString("link") : "";
        String type = json.containsKey("type") ? json.getString("type") : "";
        Object useYN = json.containsKey("use") ? json.get("use") : new Boolean(true);
        Object selfYN = json.containsKey("self") ? json.get("self") : new Boolean(true);

        this.setId(id);
        this.setName(name);
        this.setLevel(level);
        this.setOrder(order);
        this.setParentID(parentID);
        this.setLink(link);
        this.setType(type);
        this.setUseYN(useYN);
        this.setSelfYN(selfYN);
    }

    @Override
    public String toString() {
        return "MenuVO [id=" + id + ", name=" + name + ", level=" + level + ", order=" + order + ", parentID=" + parentID + ", link=" + link
                + ", type=" + type + ", selfYN=" + selfYN + ", useYN=" + useYN + ", roles=" + roles + ", checked=" + checked + ", regUserID="
                + regUserID + ", regDate=" + regDate + ", updUserID=" + updUserID + ", updDate=" + updDate + "]";
    }

}
