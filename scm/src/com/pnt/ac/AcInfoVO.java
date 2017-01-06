package com.pnt.ac;

import net.sf.json.JSONObject;

import com.pnt.common.data.i.GridDataTransformBean;
import com.pnt.common.data.i.JsonBean;

public class AcInfoVO implements GridDataTransformBean, JsonBean {

	private String num;
	private String cd_partner			; //거래처
	private String dt_acct				; //전표일자
	private String nm_note				; //내역
	private String am_dr					; //차변
	private String am_cr					; //대변
	private String am_jan				; //잔액

	//pdf파일 검색조건
	private String dtFr;
    private String dtTo;
    
    //pdf파일 헤더부분

    public AcInfoVO() {
    }
    
    public AcInfoVO(JSONObject json) throws Exception {
        this.serialize(json);
    }    
    
    @Override
	public void serialize(JSONObject json) throws Exception {
    	if( json.containsKey("cd_partner"		)) this.setCd_partner		(json.getString("cd_partner"			));
    	if( json.containsKey("dt_acct"    			)) this.setDt_acct     	(json.getString("dt_acct"      ));
    	if( json.containsKey("nm_note"    		)) this.setNm_note     	(json.getString("nm_note"      ));
    	if( json.containsKey("am_cr"      		)) this.setAm_cr       	(json.getString("am_cr"        ));
    	if( json.containsKey("am_dr"      		)) this.setAm_dr       	(json.getString("am_dr"        ));
    	if( json.containsKey("am_jan"  			)) this.setAm_jan   	(json.getString("am_jan"    ));
    	if( json.containsKey("dtFr"   			)) this.setDtFr   			 (json.getString("dtFr"     ));
    	if( json.containsKey("dtTo"   			)) this.setDtTo   			 (json.getString("dtTo"     ));

	}

	@Override
	public String asGridJson() {
		
		String json = "{";
        json += "\"id\" : \"" + this.getNum() + "\",";
        json += "\"data\" : [";
      
        json += "\"" + this.getNum() 			+ "\",";        
        json += "\"" + this.getCd_partner()			+ "\",";
        json += "\"" + this.getDt_acct()		+ "\",";
        json += "\"" + this.getNm_note()			+ "\",";       
        json += "\"" + this.getAm_cr()				+ "\",";
        json += "\"" + this.getAm_dr() 				+ "\",";
        json += "\"" + this.getAm_jan()	+ "\"";
                
        json += "]}";
		
        return json;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getCd_partner() {
		return cd_partner;
	}

	public void setCd_partner(String cd_partner) {
		this.cd_partner = cd_partner;
	}
	
	public String getDt_acct() {
		return dt_acct;
	}

	public void setDt_acct(String dt_acct) {
		this.dt_acct = dt_acct;
	}

	public String getNm_note() {
		return nm_note;
	}

	public void setNm_note(String nm_note) {
		this.nm_note = nm_note;
	}

	public String getAm_dr() {
		return am_dr;
	}

	public void setAm_dr(String am_dr) {
		this.am_dr = am_dr;
	}

	public String getAm_cr() {
		return am_cr;
	}

	public void setAm_cr(String am_cr) {
		this.am_cr = am_cr;
	}

	public String getAm_jan() {
		return am_jan;
	}

	public void setAm_jan(String am_jan) {
		this.am_jan = am_jan;
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
}
