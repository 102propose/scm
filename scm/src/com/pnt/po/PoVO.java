package com.pnt.po;

import net.sf.json.JSONObject;

import com.pnt.common.data.i.GridDataTransformBean;
import com.pnt.common.data.i.JsonBean;

public class PoVO implements GridDataTransformBean, JsonBean {
	
	private String id					; //사용자id
	private String num				; //순번
	private String no_po        	; //발주번호
	private String ln_partner   	; //거래처명
	private String dt_po        	; //발주일자
	private String cd_item      	; //품목코드
	private String nm_item      	; //품목명
	private String stnd_item      	; //품목명
	private String qt_po_mm     	; //발주수량		
	private String cd_unit_mm		; //환종
	private String um           	; //원화단가
	private String am           	; //원화금액
	private String qt_rcv_mm    	; //입고수량
	private String qt_diff			; //발주잔량
	private String fg_post      	; //발주상태 
	private String dt_limit			; //납기일자
	
	// 조회조건
    private String userId;
    private String dtFr;
    private String dtTo;
    private String po_partner;

    public PoVO() {
    }
    
    public PoVO(JSONObject json) throws Exception {
        this.serialize(json);
    }    
    
    @Override
	public void serialize(JSONObject json) throws Exception {
    	if(json.containsKey("no_po"      	)) this.setNo_po 		(json.getString("no_po"      	));
    	if(json.containsKey("ln_partner"	)) this.setLn_partner	(json.getString("ln_partner" 	));
    	if(json.containsKey("dt_po"      	)) this.setDt_po 			(json.getString("dt_po"      	));
    	if(json.containsKey("cd_item"    	)) this.setCd_item 		(json.getString("cd_item"    	));
    	if(json.containsKey("nm_item"   	)) this.setNm_item 		(json.getString("nm_item"    	));
    	if(json.containsKey("stnd_item"   	)) this.setStnd_item 		(json.getString("stnd_item"    	));
    	if(json.containsKey("qt_po_mm"	)) this.setQt_po_mm	(json.getString("qt_po_mm"   	));    	
    	if(json.containsKey("cd_unit_mm")) this.setUm 				(json.getString("cd_unit_mm" 	));
    	if(json.containsKey("um"         	)) this.setUm 			(json.getString("um"         	));
    	if(json.containsKey("am"         	)) this.setAm 			(json.getString("am"         	));
    	if(json.containsKey("qt_rcv_mm"	)) this.setQt_rcv_mm	(json.getString("qt_rcv_mm" 	));
    	if(json.containsKey("qt_diff"    	)) this.setQt_diff 		(json.getString("qt_diff"    		));
    	if(json.containsKey("fg_post"    	)) this.setFg_post		(json.getString("fg_post"    	));
    	if(json.containsKey("dt_limit"    	)) this.setDt_limit			(json.getString("dt_limit"    	));	
	}

	@Override
	public String asGridJson() {
		String json = "{";
        json += "\"id\" : \"" + this.getNum() + "\",";
        json += "\"data\" : [";
        
        json += "\"" + this.getNum() 			+ "\",";
        json += "\"" + this.getNo_po() 			+ "\",";
        json += "\"" + this.getLn_partner() 		+ "\",";
        json += "\"" + this.getDt_po() 			+ "\",";
        json += "\"" + this.getCd_item() 		+ "\",";
        json += "\"" + this.getNm_item() 		+ "\",";
        json += "\"" + this.getStnd_item() 		+ "\",";  
        json += "\"" + this.getQt_po_mm() 		+ "\",";      
        json += "\"" + this.getCd_unit_mm() 	+ "\","; 
        json += "\"" + this.getUm() 				+ "\",";
        json += "\"" + this.getAm() 				+ "\",";
        json += "\"" + this.getQt_rcv_mm() 	+ "\",";
        json += "\"" + this.getQt_diff() 			+ "\",";
        json += "\"" + this.getFg_post() 		+ "\",";
        json += "\"" + this.getDt_limit()			+ "\""; 
        
        json += "]}";
        return json;
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

	public String getNo_po() {
		return no_po;
	}

	public void setNo_po(String no_po) {
		this.no_po = no_po;
	}

	public String getLn_partner() {
		return ln_partner;
	}

	public void setLn_partner(String ln_partner) {
		this.ln_partner = ln_partner;
	}

	public String getDt_po() {
		return dt_po;
	}

	public void setDt_po(String dt_po) {
		this.dt_po = dt_po;
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

	
	public String getQt_po_mm() {
		return qt_po_mm;
	}

	public void setQt_po_mm(String qt_po_mm) {
		this.qt_po_mm = qt_po_mm;
	}

	public String getUm() {
		return um;
	}

	public void setUm(String um) {
		this.um = um;
	}

	public String getAm() {
		return am;
	}

	public void setAm(String am) {
		this.am = am;
	}

	public String getQt_rcv_mm() {
		return qt_rcv_mm;
	}

	public void setQt_rcv_mm(String qt_rcv_mm) {
		this.qt_rcv_mm = qt_rcv_mm;
	}

	public String getQt_diff() {
		return qt_diff;
	}

	public void setQt_diff(String qt_diff) {
		this.qt_diff = qt_diff;
	}

	public String getFg_post() {
		return fg_post;
	}

	public void setFg_post(String fg_post) {
		this.fg_post = fg_post;
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

	public String getPo_partner() {
        return po_partner;
    }

    public void setPo_partner(String po_partner) {
        this.po_partner = po_partner;
    }   
    
    public String getDt_limit() {
		return dt_limit;
	}

	public void setDt_limit(String dt_limit) {
		this.dt_limit = dt_limit;
	}
		
	public String getCd_unit_mm() {
		return cd_unit_mm;
	}

	public void setCd_unit_mm(String cd_unit_mm) {
		this.cd_unit_mm = cd_unit_mm;
	}

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
}
