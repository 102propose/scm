package com.pnt.pu;

import com.pnt.common.data.i.GridDataTransformBean;
import com.pnt.common.data.i.JsonBean;

import net.sf.json.JSONObject;

public class PuInfoVO implements GridDataTransformBean, JsonBean {
			
	//pdf파일 검색조건
	private String po_no;
	private String dtFr;
    private String dtTo;
    
    //pdf파일 헤더부분
    private String no_po;
    private String no_company;
    private String ln_partner;
    private String nm_ceo;
    private String dc_ads1_h;   
    private String tp_job;
    private String cls_job;    
    
    //pdf 리스트 부분
    //private String no_po;
    private String dt_rcv;
    private String cd_item;    
    private String nm_item;
    private String stnd_item;    
    private String cd_unit_mm;
    private String qt_rcv;
    private String desc_rcv;
    
    
    public PuInfoVO() {
    }
    
    public PuInfoVO(JSONObject json) throws Exception {
        this.serialize(json);
    }    
    
    @Override
	public void serialize(JSONObject json) throws Exception {    	
    	if( json.containsKey("po_no"   	)) this.setPo_no   		 (json.getString("po_no"     ));
    	if( json.containsKey("dtFr"   		)) this.setDtFr   			 (json.getString("dtFr"     ));
    	if( json.containsKey("dtTo"   		)) this.setDtTo   			 (json.getString("dtTo"     ));
    	if( json.containsKey("vat"   		)) this.setDtTo   			 (json.getString("vat"     ));
	}

	@Override
	public String asGridJson() {
		String json = "";
		/*
		json = "{";
        json += "\"id\" : \"" + this.getNum() + "\",";
        json += "\"data\" : [";
        
        json += "\"" + this.getNum() 			+ "\",";
        json += "\"" + this.getCd_item() 		+ "\",";
        json += "\"" + this.getNm_item() 		+ "\",";
        json += "\"" + this.getQt_po_nm() 		+ "\",";
        json += "\"" + this.getUm_ex() 			+ "\",";
        json += "\"" + this.getAm_ex() 			+ "\",";
        json += "\"" + this.getCd_unit_mm()	+ "\",";
        json += "\"" + this.getStnd_item() 		+ "\",";
        json += "\"" + this.getVat() 				+ "\",";
        json += "\"" + this.getDt_limit() 			+ "\"";
        //json += "\"" + this.getNm_cls_item()	+ "\",";
        //json += "\"" + this.getDc_rmk21()		+ "\"";
        //json += "\"" + this.getDtFr()				+ "\"";
        //json += "\"" + this.getDtTo()				+ "\"";
        
        json += "]}";
		*/		
        return json;
	}

	public String getPo_no() {
		return po_no;
	}

	public void setPo_no(String po_no) {
		this.po_no = po_no;
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

	public String getNo_po() {
		return no_po;
	}

	public void setNo_po(String no_po) {
		this.no_po = no_po;
	}

	public String getNo_company() {
		return no_company;
	}

	public void setNo_company(String no_company) {
		this.no_company = no_company;
	}

	public String getLn_partner() {
		return ln_partner;
	}

	public void setLn_partner(String ln_partner) {
		this.ln_partner = ln_partner;
	}

	public String getNm_ceo() {
		return nm_ceo;
	}

	public void setNm_ceo(String nm_ceo) {
		this.nm_ceo = nm_ceo;
	}

	public String getDc_ads1_h() {
		return dc_ads1_h;
	}

	public void setDc_ads1_h(String dc_ads1_h) {
		this.dc_ads1_h = dc_ads1_h;
	}

	public String getTp_job() {
		return tp_job;
	}

	public void setTp_job(String tp_job) {
		this.tp_job = tp_job;
	}

	public String getCls_job() {
		return cls_job;
	}

	public void setCls_job(String cls_job) {
		this.cls_job = cls_job;
	}

	public String getDt_rcv() {
		return dt_rcv;
	}

	public void setDt_rcv(String dt_rcv) {
		this.dt_rcv = dt_rcv;
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
	
	public String getCd_unit_mm() {
		return cd_unit_mm;
	}

	public void setCd_unit_mm(String cd_unit_mm) {
		this.cd_unit_mm = cd_unit_mm;
	}

	public String getQt_rcv() {
		return qt_rcv;
	}

	public void setQt_rcv(String qt_rcv) {
		this.qt_rcv = qt_rcv;
	}	

	public String getDesc_rcv() {
		return desc_rcv;
	}

	public void setDesc_rcv(String desc_rcv) {
		this.desc_rcv = desc_rcv;
	}

	@Override
	public String toString() {
		return "PuInfoVO [po_no=" + po_no + ", dtFr=" + dtFr + ", dtTo=" + dtTo
				+ ", no_po=" + no_po + ", no_company=" + no_company
				+ ", ln_partner=" + ln_partner + ", nm_ceo=" + nm_ceo
				+ ", dc_ads1_h=" + dc_ads1_h + ", tp_job=" + tp_job
				+ ", cls_job=" + cls_job + ", dt_rcv=" + dt_rcv + ", cd_item="
				+ cd_item + ", nm_item=" + nm_item + ", stnd_item=" + stnd_item
				+ ", cd_unit_mm=" + cd_unit_mm + ", qt_rcv=" + qt_rcv
				+ ", desc_rcv=" + desc_rcv + "]";
	}

	
	
	
    	
}
