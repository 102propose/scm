package com.pnt.pu;

import net.sf.json.JSONObject;

import com.pnt.common.data.i.GridDataTransformBean;
import com.pnt.common.data.i.JsonBean;

public class PuVO implements GridDataTransformBean, JsonBean {
	
	private String partnerCode        	; //협력명체코드
	private String produceNum        	; //발주번호
	private String itemCode        		; //품목코드
	
	private String comCol1; 
	private String comCol2;
	private String comCol3;
	private String comCol4;
	private String comCol5;
	private String comCol6;
	private String comCol7;
	private String comCol8;
	private String comCol9;
	private String seq;
	
	//조회조건
	private String pa_dt_rcv; //납품등록내역 조회
	private String cd_partner;
	private String dt_rcv;	
	private String seq_rcv;
	private String noRcv;
	
	// 조회조건
    private String userId;
    private String dtFr;
    private String dtTo;
    private String dt;
    private String partner;

    public PuVO() {}
    
    public PuVO(JSONObject json) throws Exception {
        this.serialize(json);
    }    
    
    @Override
	public void serialize(JSONObject json) throws Exception {

	}

	@Override
	public String asGridJson() {
		long rowid = System.nanoTime();
		
		String json = "{";
        json += "\"id\" : \"" + rowid + "\",";
        json += "\"userdata\" : {\"partnerCode\" : \"" + this.getPartnerCode() + "\", \"produceNum\" : \"" + this.getProduceNum() + "\", \"itemCode\" : \"" + this.getItemCode() + "\"},";
        json += "\"data\" : [";
        json += "\"" + this.getComCol1()	+ "\",";
        json += "\"" + this.getComCol2()	+ "\",";
        json += "\"" + this.getComCol3()	+ "\",";
        json += "\"" + this.getComCol4()	+ "\",";
        json += "\"" + this.getComCol5()	+ "\",";
        json += "\"" + this.getComCol6()	+ "\",";
        json += "\"" + this.getComCol7()	+ "\",";
        json += "\"" + this.getComCol8()	+ "\",";
        json += "\"" + this.getComCol9() 	+ "\"";        
        json += "]}";
        return json;
	}


	public String getPartnerCode() {
		return partnerCode;
	}

	public void setPartnerCode(String partnerCode) {
		this.partnerCode = partnerCode;
	}

	public String getProduceNum() {
		return produceNum;
	}

	public void setProduceNum(String produceNum) {
		this.produceNum = produceNum;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getComCol1() {
		return comCol1;
	}

	public void setComCol1(String comCol1) {
		this.comCol1 = comCol1;
	}

	public String getComCol2() {
		return comCol2;
	}

	public void setComCol2(String comCol2) {
		this.comCol2 = comCol2;
	}

	public String getComCol3() {
		return comCol3;
	}

	public void setComCol3(String comCol3) {
		this.comCol3 = comCol3;
	}

	public String getComCol4() {
		return comCol4;
	}

	public void setComCol4(String comCol4) {
		this.comCol4 = comCol4;
	}

	public String getComCol5() {
		return comCol5;
	}

	public void setComCol5(String comCol5) {
		this.comCol5 = comCol5;
	}

	public String getComCol6() {
		return comCol6;
	}

	public void setComCol6(String comCol6) {
		this.comCol6 = comCol6;
	}

	public String getComCol7() {
		return comCol7;
	}

	public void setComCol7(String comCol7) {
		this.comCol7 = comCol7;
	}

	public String getComCol8() {
		return comCol8;
	}

	public void setComCol8(String comCol8) {
		this.comCol8 = comCol8;
	}

	public String getComCol9() {
		return comCol9;
	}

	public void setComCol9(String comCol9) {
		this.comCol9 = comCol9;
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

	public String getDt() {
		return dt;
	}

	public void setDt(String dt) {
		this.dt = dt;
	}

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}
	
    public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getPa_dt_rcv() {
		return pa_dt_rcv;
	}

	public void setPa_dt_rcv(String pa_dt_rcv) {
		this.pa_dt_rcv = pa_dt_rcv;
	}

	public String getCd_partner() {
		return cd_partner;
	}

	public void setCd_partner(String cd_partner) {
		this.cd_partner = cd_partner;
	}

	public String getDt_rcv() {
		return dt_rcv;
	}

	public void setDt_rcv(String dt_rcv) {
		this.dt_rcv = dt_rcv;
	}	

	public String getSeq_rcv() {
		return seq_rcv;
	}

	public void setSeq_rcv(String seq_rcv) {
		this.seq_rcv = seq_rcv;
	}
	
	public String getNoRcv() {
		return noRcv;
	}

	public void setNoRcv(String noRcv) {
		this.noRcv = noRcv;
	}

	@Override
	public String toString() {
		return "PuVO [partnerCode=" + partnerCode + ", produceNum="
				+ produceNum + ", itemCode=" + itemCode + ", comCol1="
				+ comCol1 + ", comCol2=" + comCol2 + ", comCol3=" + comCol3
				+ ", comCol4=" + comCol4 + ", comCol5=" + comCol5
				+ ", comCol6=" + comCol6 + ", comCol7=" + comCol7
				+ ", comCol8=" + comCol8 + ", comCol9=" + comCol9 + ", seq="
				+ seq + ", pa_dt_rcv=" + pa_dt_rcv + ", cd_partner="
				+ cd_partner + ", dt_rcv=" + dt_rcv + ", userId=" + userId
				+ ", dtFr=" + dtFr + ", dtTo=" + dtTo + ", dt=" + dt
				+ ", partner=" + partner + "]";
	}
	
	
}
