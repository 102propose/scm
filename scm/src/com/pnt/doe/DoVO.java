package com.pnt.doe;

import net.sf.json.JSONObject;

import com.pnt.common.data.i.GridDataTransformBean;
import com.pnt.common.data.i.JsonBean;
import com.pnt.common.util.CoreUtils;

public class DoVO implements GridDataTransformBean, JsonBean {
	
	private String itemCode;
	private String itemName;
	private String poMonthQt;
	private String remainQt;
	
	private String unit; 
    private String stndItem;
    private String partnerCode;
	
	private String d1;
	private String d2;
	private String d3;
	private String d4;
	private String d5;
	private String d6;
	private String d7;
	private String sum;
	
	private String seq;
	private String comCol1; 
	private String comCol2; 
    private String comQty;
	
	// 조회조건
    private String condDate;
    private String condPartnerCode;

    public DoVO() {
    }
    
    public DoVO(JSONObject json) throws Exception {
        this.serialize(json);
    }    
    
    @Override
	public void serialize(JSONObject json) throws Exception {
//    	if(json.containsKey("no_po"      	)) this.setNo_po 		(json.getString("no_po"      	));
	}

	@Override
	public String asGridJson() {
	    long rowid = System.nanoTime();
	    
		String json = "{";
        json += "\"id\" : \"" + rowid + "\",";
        json += "\"userdata\" : {\"partnerCode\" : \"" + this.getPartnerCode() + "\", \"itemCode\" : \"" + this.getItemCode() + "\"},";
        json += "\"data\" : [";
        json += "\"" +this.getComCol1() + "\",";
        json += "\"" + this.getComCol2() + "\",";
        json += "\"" + ("1".equals(this.seq) ? "" : "<button onclick='viewDoInsertPage(" + rowid + ");'>입력</button>") + "\",";
//        json += "\"" + ("1".equals(this.seq) ? "" : "<button onclick=\"viewDoInsertPage('" + rowid + "')\">입력</button>") + "\",";
        json += "\"" + this.getComQty() + "\",";
        json += "\"" + this.getD1() + "\",";
        json += "\"" + this.getD2() + "\",";
        json += "\"" + this.getD3() + "\",";
        json += "\"" + this.getD4() + "\",";
        json += "\"" + this.getD5() + "\",";
        json += "\"" + this.getD6() + "\",";
        json += "\"" + this.getD7() + "\","; 
        json += "\"" + this.getSum() + "\""; 
        json += "]}";
        return json;
	}

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getPoMonthQt() {
        return poMonthQt;
    }

    public void setPoMonthQt(String poMonthQt) {
        this.poMonthQt = poMonthQt;
    }

    public String getRemainQt() {
        return remainQt;
    }

    public void setRemainQt(String remainQt) {
        this.remainQt = remainQt;
    }

    public String getD1() {
        return d1;
    }

    public void setD1(String d1) {
        this.d1 = d1;
    }

    public String getD2() {
        return d2;
    }

    public void setD2(String d2) {
        this.d2 = d2;
    }

    public String getD3() {
        return d3;
    }

    public void setD3(String d3) {
        this.d3 = d3;
    }

    public String getD4() {
        return d4;
    }

    public void setD4(String d4) {
        this.d4 = d4;
    }

    public String getD5() {
        return d5;
    }

    public void setD5(String d5) {
        this.d5 = d5;
    }

    public String getD6() {
        return d6;
    }

    public void setD6(String d6) {
        this.d6 = d6;
    }

    public String getD7() {
        return d7;
    }

    public void setD7(String d7) {
        this.d7 = d7;
    }

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getStndItem() {
        return stndItem;
    }

    public void setStndItem(String stndItem) {
        this.stndItem = stndItem;
    }

    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }


    public String getComCol1() {
        return comCol1;
    }

    public void setComCol1(String comCol1) {
        this.comCol1 = comCol1;
    }

    public String getComCol2() {
        String ret = CoreUtils.ifNull(this.comCol2);
        ret = ret.replaceAll("\"", "&quot;");
        return comCol2;
    }

    public void setComCol2(String comCol2) {
        this.comCol2 = comCol2;
    }

    public String getComQty() {
        return comQty;
    }

    public void setComQty(String comQty) {
        this.comQty = comQty;
    }

    public String getCondDate() {
        return condDate;
    }

    public void setCondDate(String condDate) {
        this.condDate = condDate;
    }

    public String getCondPartnerCode() {
        return condPartnerCode;
    }

    public void setCondPartnerCode(String condPartnerCode) {
        this.condPartnerCode = condPartnerCode;
    }

    @Override
    public String toString() {
        return "DoVO [itemCode=" + itemCode + ", itemName=" + itemName + ", poMonthQt=" + poMonthQt + ", remainQt=" + remainQt + ", unit=" + unit
                + ", stndItem=" + stndItem + ", partnerCode=" + partnerCode + ", d1=" + d1 + ", d2=" + d2 + ", d3=" + d3 + ", d4=" + d4 + ", d5="
                + d5 + ", d6=" + d6 + ", d7=" + d7 + ", sum=" + sum + ", seq=" + seq + ", comCol1=" + comCol1 + ", comCol2=" + comCol2 + ", comQty="
                + comQty + ", condDate=" + condDate + ", condPartnerCode=" + condPartnerCode + "]";
    }
}
