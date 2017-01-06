package com.pnt.context;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.pnt.common.data.i.TreeDataTransformBean;
import com.pnt.common.util.CoreUtils;
import com.pnt.menu.MenuVO;

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

public class Menu implements TreeDataTransformBean, Cloneable, Serializable {
    private static final long serialVersionUID = 5091249139741467976L;
    
    private String id;
    private String name;
    private String type;
    private String link;
    private String pid;
    private int order;
    private int level;
    private String desc;
    private boolean use = false;

    private int checked = 0; // 0 for false, 1 for true

    private List<Menu> item = new ArrayList<Menu>();
    private List<UserGroup> roles = new ArrayList<UserGroup>();

//    private MenuSorter menuSorter = new MenuSorter();

    public Menu() {
    }

    public Menu(MenuVO vo) {
        this.serialize(vo);
    }
    public Menu(int id, String name, String link, int level) {
        this.id = Integer.toString(id);
        this.name = name;
        this.link = link;
        this.level = level;
        this.use = true;
    }
    public Menu(int id, String name, String link, int level, int pid) {
    	this(id, name, link, level);
    	this.pid = Integer.toString(pid);
    }

    public String getId() {
        return this.id;
//        return this.id == null ? Long.toString(System.nanoTime()) : this.id;
    }

    public void setId(String id) {
        this.id = id;
//        this.id = id == null ? Long.toString(System.nanoTime()) : id;
    }

    public String getName() {
        return CoreUtils.ifNull(this.name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return CoreUtils.ifNull(this.type);
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLink() {
        return CoreUtils.ifNull(this.link);
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
    
    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isUse() {
        return use;
    }

    public void setUse(boolean use) {
        this.use = use;
    }

    public int getChecked() {
        return checked;
    }

    public void setChecked(int checked) {
        this.checked = checked;
    }

    public List<Menu> getItem() {
        return item;
    }

    public List<UserGroup> getRoles() {
        return roles;
    }

    public void setItem(List<Menu> menus) {
        this.item = menus;
    }

    public void addMenu(Menu menu) {
        this.item.add(menu);
//        Collections.sort(this.item, this.menuSorter);
    }

    public boolean hasMenu() {
        return this.item.size() == 0 ? false : true;
    }

    public void addRole(UserGroup role) {
//        String rolenm = role.getName();
//
//        if (rolenm != null) {
//            if (rolenm.indexOf(",") > -1) {
//                String[] split = rolenm.split(",");
//                for (int i = 0; i < split.length; i++) {
//                    String nm = CoreUtils.ifNull(split[i]);
//                    if (!"".equals(nm)) {
//                        this.roles.add(new UserGroup(nm));
//                    }
//                }
//            } else {
                this.roles.add(role);
//            }
//        }
    }

    public boolean hasRole(List<UserGroup> roles) {
        boolean checker = false;

        UserGroup ur, tr;
        m: for (int x = 0; x < roles.size(); x++) {
            ur = roles.get(x);

            for (int y = 0; y < this.roles.size(); y++) {
                tr = this.roles.get(y);

                if (tr.compare(ur)) {
                    checker = true;
                    break m;
                }
            }
        }

        return checker;
    }

    public void serialize(MenuVO vo) {
        this.setId(vo.getId());
        this.setName(vo.getName());
        this.setType("".equals(CoreUtils.ifNull(vo.getParentID()).trim()) || "null".equalsIgnoreCase(vo.getParentID().trim()) ? "folder" : vo.getType());
        this.setLink(vo.getLink());
        this.setOrder(vo.getOrder());
        this.setPid(vo.getParentID());
//        this.setDesc(vo.getDesc());
        this.setLevel(vo.getLevel());
        this.setChecked(vo.getChecked());
        this.addRole(new UserGroup(Integer.valueOf(vo.getId()).intValue(), vo.getRoles()));
        this.setUse("Y".equalsIgnoreCase(vo.getUseYN()) ? true : false);
    }

    @Override
    public String toString() {
        return "Menu [id=" + id + ", name=" + name + ", type=" + type + ", link=" + link + ", pid=" + pid + ", order=" + order + ", level=" + level
                + ", desc=" + desc + ", use=" + use + ", checked=" + checked + ", item=" + item + ", roles=" + roles + "]";
    }

    public String asJson() {
        String json;

        json = "{";
        json += "\"id\" : \"" + this.getId() + "\",";
        json += "\"name\" : \"" + this.getName() + "\",";
        json += "\"order\" : \"" + this.getOrder() + "\",";
        json += "\"type\" : \"" + this.getType() + "\",";
        json += "\"link\" : \"" + this.getLink() + "\",";
        json += "\"level\" : \"" + this.getLevel() + "\",";
        json += "\"use\" : \"" + this.isUse() + "\",";
        json += "\"pid\" : \"" + this.getPid() + "\",";
        json += "\"desc\" : \"" + this.getDesc() + "\",";
        json += "\"checked\" : \"" + this.getChecked() + "\",";

        /* roles */
        String rolesStr = "";
        json += "\"roles\" : \"";

        for (int i = 0; i < this.roles.size(); i++) {
            rolesStr += ((UserGroup) this.roles.get(i)).getName() + ",";
        }

        if (this.roles.size() > 0) {
            rolesStr = rolesStr.substring(0, rolesStr.length() - 1);
        }

        json += rolesStr;
        json += "\"";

        /* sub item */
        if (this.item.size() > 0) {
            json += ", \"item\" : [";
            for (int i = 0; i < this.item.size(); i++) {
                json += ((Menu) this.item.get(i)).asJson() + ",";
            }
            json = json.substring(0, json.length() - 1);
            json += "]";
        }

        json += "}";

        return json;
    }

    public String asTreeXml() {
        String xml;

        String checkedAttr = this.checked == 1 ? " checked=\"1\"" : "";
        String nodeImg = "";
//        String nodeImg = this.isUse() ? "" : " im0=\"unused.gif\"" ;
//        
        if ("".equals(nodeImg)) {
            if ("folder".equalsIgnoreCase(this.getType())) {
                nodeImg = " im0=\"folderClosed_yellow.gif\" im2=\"folderClosed_yellow.gif\" im1=\"folderOpen_yellow.gif\"";
//                nodeImg = " im0=\"folderClosed.gif\" im2=\"folderClosed.gif\" im1=\"folderOpen.gif\"";
            } else if ("bo".equalsIgnoreCase(this.getType()) || "Webi".equals(this.getType())) {
                nodeImg = " im0=\"iconWrite1\"";
            } else if ("ex".equalsIgnoreCase(this.getType()) || "XL.XcelsiusEnterprise".equals(this.getType())) {
                nodeImg = " im0=\"iconGraph.gif\"";
            } else if ("jsp".equalsIgnoreCase(this.getType()) || "html".equalsIgnoreCase(this.getType())) {
                nodeImg = " im0=\"iconTexts.gif\"";
            } else {
                nodeImg = "";
            }
        }

        xml = "<item id=\"" + this.getId() + "\" text=\"" + this.getName() + "\" " + checkedAttr + " " + nodeImg + ">";
        xml += "    <userdata name=\"type\">" + this.getType() + "</userdata>";
        xml += "    <userdata name=\"link\">" + this.getLink() + "</userdata>";
//        xml += "    <userdata name=\"use\">" + this.isUse() + "</userdata>";
//        xml += "    <userdata name=\"order\">" + this.getOrder() + "</userdata>";
        xml += "    <userdata name=\"pid\">" + this.getPid() + "</userdata>";
//        xml += "    <userdata name=\"desc\">" + this.getDesc() + "</userdata>";

        /* roles */
        String rolesStr = "";
        xml += "    <userdata name=\"roles\">";

        for (int i = 0; i < this.roles.size(); i++) {
            rolesStr += ((UserGroup) this.roles.get(i)).getName() + ",";
        }

        if (rolesStr.length() > 0) {
            rolesStr = rolesStr.substring(0, rolesStr.length() - 1);
        }

        xml += rolesStr;
        xml += "</userdata>";

        /* sub item */
        if (this.item.size() > 0) {
            for (int i = 0; i < this.item.size(); i++) {
                xml += ((Menu) this.item.get(i)).asTreeXml();
            }
        }

        xml += "</item>";

        return xml;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
