package com.pnt.pp;

import net.sf.json.JSONObject;

import com.pnt.common.data.i.GridDataTransformBean;
import com.pnt.common.data.i.JsonBean;

public class PpInfoVO implements GridDataTransformBean, JsonBean {
	
	private String num;
	private String cd_item;
	private String nm_item;
	private String qt_po_nm;
	private String um_ex;
	private String am_ex;
	private String cd_unit_mm;
	private String stnd_item;
	private String dt_limit;
	private String nm_cls_item;
	private String dc_rmk21;
	private String vat;
	
	//pdf파일 검색조건
	private String po_no;
	private String dtFr;
    private String dtTo;
    
    //pdf파일 헤더부분
    private String no_po;
    private String dt_po;
    private String ln_partner;
    private String dc_ads1_h;
    private String no_tel;
    private String no_fax;
    private String tp_job;
    private String cls_job;    

    public PpInfoVO() {
    }
    
    public PpInfoVO(JSONObject json) throws Exception {
        this.serialize(json);
    }    
    
    @Override
	public void serialize(JSONObject json) throws Exception {
    	if( json.containsKey("cd_item"		)) this.setCd_item		(json.getString("cd_item"			));
    	if( json.containsKey("nm_item"    )) this.setNm_item     	(json.getString("nm_item"      ));
    	if( json.containsKey("qt_po_nm"   )) this.setQt_po_nm    (json.getString("qt_po_nm"     ));
    	if( json.containsKey("um_ex"      )) this.setUm_ex       	(json.getString("um_ex"        ));
    	if( json.containsKey("am_ex"      )) this.setAm_ex       	(json.getString("am_ex"        ));
    	if( json.containsKey("cd_unit_mm" )) this.setCd_unit_mm  (json.getString("cd_unit_mm"   ));
    	if( json.containsKey("stnd_item"  )) this.setStnd_item   	(json.getString("stnd_item"    ));
    	if( json.containsKey("dt_limit"   )) this.setDt_limit   		 (json.getString("dt_limit"     ));
    	if( json.containsKey("nm_cls_item")) this.setNm_cls_item (json.getString("nm_cls_item"  ));
    	if( json.containsKey("dc_rmk21"   )) this.setDc_rmk21    (json.getString("dc_rmk21"     ));
    	if( json.containsKey("po_no"   	)) this.setPo_no   		 (json.getString("po_no"     ));
    	if( json.containsKey("dtFr"   		)) this.setDtFr   			 (json.getString("dtFr"     ));
    	if( json.containsKey("dtTo"   		)) this.setDtTo   			 (json.getString("dtTo"     ));
    	if( json.containsKey("vat"   		)) this.setDtTo   			 (json.getString("vat"     ));

	}

	@Override
	public String asGridJson() {
		String json = "{";
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
		
		/*String json = this.getCd_item()+","+ this.getNm_item()+","+this.getQt_po_nm()+","+ this.getUm_ex()+","+this.getAm_ex() +
				","+this.getCd_unit_mm()+","+this.getStnd_item()+","+this.getDt_limit()+","+this.getNm_cls_item()+","+this.getDc_rmk21();*/
		
        return json;
	}
	
	
	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
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

	public String getQt_po_nm() {
		return qt_po_nm;
	}

	public void setQt_po_nm(String qt_po_nm) {
		this.qt_po_nm = qt_po_nm;
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

	public String getCd_unit_mm() {
		return cd_unit_mm;
	}

	public void setCd_unit_mm(String cd_unit_mm) {
		this.cd_unit_mm = cd_unit_mm;
	}

	public String getStnd_item() {
		return stnd_item;
	}

	public void setStnd_item(String stnd_item) {
		this.stnd_item = stnd_item;
	}

	public String getDt_limit() {
		return dt_limit;
	}

	public void setDt_limit(String dt_limit) {
		this.dt_limit = dt_limit;
	}

	public String getNm_cls_item() {
		return nm_cls_item;
	}

	public void setNm_cls_item(String nm_cls_item) {
		this.nm_cls_item = nm_cls_item;
	}

	public String getDc_rmk21() {
		return dc_rmk21;
	}

	public void setDc_rmk21(String dc_rmk21) {
		this.dc_rmk21 = dc_rmk21;
	}

	public String getDtFr() {
		return dtFr;
	}

	public void setDtFr(String dtFr) {
		this.dtFr = dtFr;
	}	
	
	public String getPo_no() {
		return po_no;
	}

	public void setPo_no(String po_no) {
		this.po_no = po_no;
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

	public String getDt_po() {
		return dt_po;
	}

	public void setDt_po(String dt_po) {
		this.dt_po = dt_po;
	}

	public String getLn_partner() {
		return ln_partner;
	}

	public void setLn_partner(String ln_partner) {
		this.ln_partner = ln_partner;
	}

	public String getDc_ads1_h() {
		return dc_ads1_h;
	}

	public void setDc_ads1_h(String dc_ads1_h) {
		this.dc_ads1_h = dc_ads1_h;
	}

	public String getNo_tel() {
		return no_tel;
	}

	public void setNo_tel(String no_tel) {
		this.no_tel = no_tel;
	}

	public String getNo_fax() {
		return no_fax;
	}

	public void setNo_fax(String no_fax) {
		this.no_fax = no_fax;
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

	public String getVat() {
		return vat;
	}

	public void setVat(String vat) {
		this.vat = vat;
	}
	
}
