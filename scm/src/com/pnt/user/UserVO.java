package com.pnt.user;

import com.pnt.common.data.i.GridDataTransformBean;
import com.pnt.common.util.CoreUtils;

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

public class UserVO implements GridDataTransformBean {
    private String id;
    private String name;
    private String password;
    private String gradeName;
    private String orgName;
    private String roleID;
    private String useYN;
    private String regUserID;
    private String regDate;
    private String updUserID;
    private String updDate;
    
    private int num;

    /* paging attribute */
    private int currPage;
    private int maxPage;
    private int rowCountPerPage;
    private int startRow;
    private int endRow;
    private int totalRowCount;
    
    private String condOrgDiv; // 화면 조회 조건에서 넘어오는 조직 구분값

    private String authInfo; //권한별 메뉴별 명시 차이를 위한 값
    
    public UserVO() {
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
        if (this.name != null && this.name.indexOf("/") > -1) {
            this.name = this.name.replaceAll("/", ".");
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getRoleID() {
        return roleID;
    }

    public void setRoleID(String roleID) {
        this.roleID = roleID;
    }

    public String getUseYN() {
        return this.useYN;
    }

    public void setUseYN(String useYN) {
        this.useYN = CoreUtils.ifNull(useYN, "N");
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

    public String getCondOrgDiv() {
        return condOrgDiv;
    }

    public void setCondOrgDiv(String condOrgDiv) {
        this.condOrgDiv = condOrgDiv;
    }

    public int getCurrPage() {
        return currPage;
    }
    
    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getAuthInfo() {
		return authInfo;
	}

	public void setAuthInfo(String authInfo) {
		this.authInfo = authInfo;
	}

	public void setCurrPage(int currPage, int rowCountPerPage, int totalRowCount) {
        this.rowCountPerPage = rowCountPerPage;
        this.totalRowCount = totalRowCount;
        
        this.maxPage = (this.totalRowCount / this.rowCountPerPage) + (this.totalRowCount % this.rowCountPerPage > 0 ? 1 : 0);
        
        currPage = currPage <= 1 ? 1 : currPage;
        this.currPage = currPage > this.maxPage ? this.maxPage : currPage;
        
        this.endRow = this.currPage * this.rowCountPerPage;
        this.endRow = this.endRow >= this.totalRowCount ? this.totalRowCount : this.endRow;
        this.startRow = this.currPage == 1 ? 1 : (this.currPage - 1) * this.rowCountPerPage + 1;
    }
    
    public int getMaxPage() {
        return maxPage;
    }

    @Override
    public String toString() {
        return "UserVO [id=" + id + ", name=" + name + ", password=" + password + ", gradeName=" + gradeName + ", orgName=" + orgName + ", roleID="
                + roleID + ", useYN=" + useYN + ", regUserID=" + regUserID + ", regDate=" + regDate + ", updUserID=" + updUserID + ", updDate="
                + updDate + ", num=" + num + ", currPage=" + currPage + ", maxPage=" + maxPage + ", rowCountPerPage=" + rowCountPerPage
                + ", startRow=" + startRow + ", endRow=" + endRow + ", totalRowCount=" + totalRowCount + ", condOrgDiv=" + condOrgDiv + "]";
    }

    public String asGridJson() {
        String json = "{";
        json += "\"id\" : \"" + this.getId() + "\",";
//        json += "\"data\" : [0, ";
        json += "\"data\" : [" + this.getNum() + ", ";
        json += "\"" + this.getName() + "\",";
        json += "\"" + this.getGradeName() + "\",";
        json += "\"" + this.getOrgName() + "\",";
        json += "\"" + this.getRoleID() + "\",";
        json += "\"" + this.getUseYN() + "\"";
        json += "]";
        json += "}";
        return json;
    }
    
    public static void main(String[] s) {
        UserVO vo = new UserVO();
        
        vo.setCurrPage(2, 10, 22);
        
        System.out.println(vo);
    }

}
