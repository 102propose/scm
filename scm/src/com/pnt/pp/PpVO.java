package com.pnt.pp;

import net.sf.json.JSONObject;

import com.pnt.common.data.i.GridDataTransformBean;
import com.pnt.common.data.i.JsonBean;

public class PpVO implements GridDataTransformBean, JsonBean {
	private String no_wo			; //작업지시
	private String cd_item		; //품목코드
	private String nm_item		; //모델명
	private String qt_item		; //지시수량
	private String dt_work		; //작업일자
	private String qt_work		; //작업일자
	
	private String id					; //사용자id
	private String num				; //순번
	
	// 조회조건
    private String userId;
    private String dtFr;
    private String dtTo;
   
    public PpVO() {
    }
    
    public PpVO(JSONObject json) throws Exception {
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
        json += "\"" + this.getNo_wo()		+ "\",";
        json += "\"" + this.getCd_item()			+ "\",";       
        json += "\"" + this.getNm_item()			+ "\",";       
        json += "\"" + this.getQt_item() 		+ "\",";               
        json += "\"" + this.getDt_work()			+ "\",";
        json += "\"" + this.getQt_work()	+ "\"";
        
        
        json += "]}";
        return json;
	}

	public String getNo_wo() {
		return no_wo;
	}

	public void setNo_wo(String no_wo) {
		this.no_wo = no_wo;
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

	public String getDt_work() {
		return dt_work;
	}

	public void setDt_work(String dt_work) {
		this.dt_work = dt_work;
	}

	public String getQt_item() {
		return qt_item;
	}

	public void setQt_item(String qt_item) {
		this.qt_item = qt_item;
	}

	public String getQt_work() {
		return qt_work;
	}

	public void setQt_work(String qt_work) {
		this.qt_work = qt_work;
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

	@Override
	public String toString() {
		return "PpVO [no_wo=" + no_wo + ", cd_item=" + cd_item + ", nm_item=" + nm_item
				+ ", qt_item=" +qt_item + ", dt_work=" + dt_work
				+ ", qt_work=" + qt_work + ", id=" + id + ", num="
				+ num + ", userId=" + userId + ", dtFr=" + dtFr + ", dtTo="
				+ dtTo + "]";
	}

	/*
	@Override
	public String toString() {
		return "PoVO [id=" + id + ", num=" + num + ", no_po=" + no_po
				+ ", ln_partner=" + ln_partner + ", dt_po=" + dt_po
				+ ", cd_item=" + cd_item + ", nm_item=" + nm_item
				+ ", qt_po_mm=" + qt_po_mm + ", cd_unit_mm=" + cd_unit_mm
				+ ", um=" + um + ", am=" + am + ", qt_rcv_mm=" + qt_rcv_mm
				+ ", qt_diff=" + qt_diff + ", fg_post=" + fg_post
				+ ", dt_limit=" + dt_limit + ", userId=" + userId + ", dtFr="
				+ dtFr + ", dtTo=" + dtTo + ", po_partner=" + po_partner + "]";
	}
	*/
	
}
