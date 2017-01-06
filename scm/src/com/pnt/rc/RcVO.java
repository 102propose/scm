package com.pnt.rc;

import net.sf.json.JSONObject;

import com.pnt.common.data.i.GridDataTransformBean;
import com.pnt.common.data.i.JsonBean;

public class RcVO implements GridDataTransformBean, JsonBean {
	private String cd_partner			; //거래처
	private String no_io					; //전표번호
	private String dt_io					; //입고일자
	private String cd_item				; //품목코드
	private String nm_item				; //품목명
	private String stnd_item			; //규격

	private String qt_io					; //입고수량
	private String qt_good_inv			; //합격수량
	private String nm_cd_exch			; //환종
	private String um_ex					; //단가
	private String am_ex					; //금액
	private String yn_am					; //유무한구분
	private String no_rev				; //가입고번호
	private String no_pso_mgmt		; //발주번호
		
	private String id					; //사용자id
	private String num				; //순번
	
	// 조회조건
    private String userId;
    private String dtFr;
    private String dtTo;
    private String rc_partner; 

    public RcVO() {
    }
    
    public RcVO(JSONObject json) throws Exception {
        this.serialize(json);
    }    
    
  
    @Override
	public void serialize(JSONObject json) throws Exception {
    	/*
    	if(json.containsKey("no_po"      	)) this.setNo_po 		(json.getString("no_po"      	));
    	if(json.containsKey("ln_partner"	)) this.setLn_partner	(json.getString("ln_partner" 	));
    	if(json.containsKey("dt_po"      	)) this.setDt_po 			(json.getString("dt_po"      	));
    	if(json.containsKey("cd_item"    	)) this.setCd_item 		(json.getString("cd_item"    	));
    	if(json.containsKey("nm_item"   	)) this.setNm_item 		(json.getString("nm_item"    	));
    	if(json.containsKey("qt_po_mm"	)) this.setQt_po_mm	(json.getString("qt_po_mm"   	));    	
    	if(json.containsKey("cd_unit_mm")) this.setUm 				(json.getString("cd_unit_mm" 	));
    	if(json.containsKey("um"         	)) this.setUm 			(json.getString("um"         	));
    	if(json.containsKey("am"         	)) this.setAm 			(json.getString("am"         	));
    	if(json.containsKey("qt_rcv_mm"	)) this.setQt_rcv_mm	(json.getString("qt_rcv_mm" 	));
    	if(json.containsKey("qt_diff"    	)) this.setQt_diff 		(json.getString("qt_diff"    		));
    	if(json.containsKey("fg_post"    	)) this.setFg_post		(json.getString("fg_post"    	));
    	if(json.containsKey("dt_limit"    	)) this.setDt_limit			(json.getString("dt_limit"    	));	
    	*/
    	
	}
	
	@Override
	public String asGridJson() {
		String json = "{";
        json += "\"id\" : \"" + this.getNum() + "\",";
        json += "\"data\" : [";
        
        json += "\"" + this.getNum()			+ "\",";
        json += "\"" + this.getCd_partner()			+ "\",";
        json += "\"" + this.getNo_io()			+ "\",";
        json += "\"" + this.getDt_io()		+ "\",";
        json += "\"" + this.getCd_item()			+ "\",";       
        json += "\"" + this.getNm_item()			+ "\",";       
        json += "\"" + this.getStnd_item()			+ "\",";       
        json += "\"" + this.getQt_io() 		+ "\",";
        json += "\"" + this.getQt_good_inv() 		+ "\",";      
        json += "\"" + this.getNm_cd_exch()	+ "\","; 
        json += "\"" + this.getUm_ex()				+ "\",";
        json += "\"" + this.getAm_ex() 				+ "\",";
        json += "\"" + this.getYn_am()	+ "\",";
        json += "\"" + this.getNo_rev()	+ "\",";        
        json += "\"" + this.getNo_pso_mgmt()	+ "\"";
                
        json += "]}";
        return json;
	}




	public String getCd_partner() {
		return cd_partner;
	}

	public void setCd_partner(String cd_partner) {
		this.cd_partner = cd_partner;
	}

	public String getNo_io() {
		return no_io;
	}

	public void setNo_io(String no_io) {
		this.no_io = no_io;
	}

	public String getDt_io() {
		return dt_io;
	}

	public void setDt_io(String dt_io) {
		this.dt_io = dt_io;
	}

	public String getCd_item() {
		return cd_item;
	}

	public void setCd_item(String cd_item) {
		this.cd_item = cd_item;
	}

	public String getNm_item() {
		return nm_item;
	}

	public void setNm_item(String nm_item) {
		this.nm_item = nm_item;
	}

	public String getStnd_item() {
		return stnd_item;
	}

	public void setStnd_item(String stnd_item) {
		this.stnd_item = stnd_item;
	}

	public String getQt_io() {
		return qt_io;
	}

	public void setQt_io(String qt_io) {
		this.qt_io = qt_io;
	}

	public String getQt_good_inv() {
		return qt_good_inv;
	}

	public void setQt_good_inv(String qt_good_inv) {
		this.qt_good_inv = qt_good_inv;
	}

	public String getNm_cd_exch() {
		return nm_cd_exch;
	}

	public void setNm_cd_exch(String nm_cd_exch) {
		this.nm_cd_exch = nm_cd_exch;
	}

	public String getUm_ex() {
		return um_ex;
	}

	public void setUm_ex(String um_ex) {
		this.um_ex = um_ex;
	}

	public String getAm_ex() {
		return am_ex;
	}

	public void setAm_ex(String am_ex) {
		this.am_ex = am_ex;
	}

	public String getYn_am() {
		return yn_am;
	}

	public void setYn_am(String yn_am) {
		this.yn_am = yn_am;
	}

	public String getNo_rev() {
		return no_rev;
	}

	public void setNo_rev(String no_rev) {
		this.no_rev = no_rev;
	}

	public String getNo_pso_mgmt() {
		return no_pso_mgmt;
	}

	public void setNo_pso_mgmt(String no_pso_mgmt) {
		this.no_pso_mgmt = no_pso_mgmt;
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDtFr() {
		return dtFr;
	}

	public void setDtFr(String dtFr) {
		this.dtFr = dtFr;
	}

	public String getDtTo() {
		return dtTo;
	}

	public void setDtTo(String dtTo) {
		this.dtTo = dtTo;
	}

	public String getRc_partner() {
        return rc_partner;
    }

    public void setRc_partner(String rc_partner) {
        this.rc_partner = rc_partner;
    }   

	
	@Override
	public String toString() {
		return "RcVO [cd_partner=" + cd_partner + ", no_io=" + no_io
				+ ", dt_io=" + dt_io + ", cd_item=" + cd_item + ", nm_item="
				+ nm_item + ", stnd_item=" + stnd_item + ", qt_io="
				+ qt_io + ", qt_good_inv=" + qt_good_inv + ", nm_cd_exch="
				+ nm_cd_exch + ", um_ex=" + um_ex + ", am_ex=" + am_ex + ", yn_am="
				+ yn_am + ", no_rev=" + no_rev + ", id=" + id + ", num="
				+ num + ", userId=" + userId + ", dtFr=" + dtFr + ", dtTo="
				+ dtTo + ", rc_partner=" + rc_partner + "]";
	}	
}
