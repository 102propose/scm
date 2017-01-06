package com.pnt.main;

import net.sf.json.JSONObject;

import com.pnt.common.data.i.GridDataTransformBean;
import com.pnt.common.data.i.JsonBean;
import com.pnt.common.util.CoreUtils;

public class NoticeVO implements GridDataTransformBean, JsonBean {
	private int MSG_SEQ;
	private String CD_PARTNER;
	private String DT_MSG_START;
	private String DT_MSG_END;
	private String TITLE_MSG;
	private String TXT_MSG;
	private String CHK_YN;
	private int QTY_READ;
	private String DT_INSERT;
	private String ID_INSERT;

	private String LN_PARTNER;
	
	public int getMSG_SEQ() {
		return MSG_SEQ;
	}

	public void setMSG_SEQ(int mSG_SEQ) {
		MSG_SEQ = mSG_SEQ;
	}

	public String getCD_PARTNER() {
		return CD_PARTNER;
	}

	public void setCD_PARTNER(String cD_PARTNER) {
		CD_PARTNER = cD_PARTNER;
	}

	public String getDT_MSG_START() {
		return DT_MSG_START;
	}

	public void setDT_MSG_START(String dT_MSG_START) {
		DT_MSG_START = dT_MSG_START;
	}

	public String getDT_MSG_END() {
		return DT_MSG_END;
	}

	public void setDT_MSG_END(String dT_MSG_END) {
		DT_MSG_END = dT_MSG_END;
	}

	public String getTITLE_MSG() {
		return TITLE_MSG;
	}

	public void setTITLE_MSG(String tITLE_MSG) {
		TITLE_MSG = tITLE_MSG;
	}

	public String getTXT_MSG() {
		return TXT_MSG;
	}

	public void setTXT_MSG(String tXT_MSG) {
		TXT_MSG = tXT_MSG;
	}

	public String getCHK_YN() {
		if ("".equals(CoreUtils.ifNull(CHK_YN))) {
			return "N";
		}
		return CHK_YN;
	}

	public void setCHK_YN(String cHK_YN) {
		CHK_YN = cHK_YN;
	}

	public int getQTY_READ() {
		return QTY_READ;
	}

	public void setQTY_READ(int qTY_READ) {
		QTY_READ = qTY_READ;
	}

	public String getDT_INSERT() {
		return DT_INSERT;
	}

	public void setDT_INSERT(String dT_INSERT) {
		DT_INSERT = dT_INSERT;
	}
	
	public String getID_INSERT() {
		return ID_INSERT;
	}

	public void setID_INSERT(String iD_INSERT) {
		ID_INSERT = iD_INSERT;
	}

	public String getLN_PARTNER() {
		return LN_PARTNER;
	}

	public void setLN_PARTNER(String lN_PARTNER) {
		LN_PARTNER = lN_PARTNER;
	}

	@Override
	public void serialize(JSONObject json) throws Exception {
	}

	@Override
	public String asGridJson() {
		String json = "{";
        json += "\"id\" : \"" + this.getMSG_SEQ() + "\",";
        json += "\"data\" : [";
        json += "\"" +this.getMSG_SEQ() + "\",";
        json += "\"" + this.getTITLE_MSG() + "\",";
        json += "\"" + this.getDT_INSERT() + "\",";
        json += "\"" + this.getQTY_READ() + "\",";
        json += "\"" + this.getCHK_YN() + "\""; 
        json += "]}";
        return json;
	}

	@Override
	public String toString() {
		return "NoticeVO [MSG_SEQ=" + MSG_SEQ + ", CD_PARTNER=" + CD_PARTNER
				+ ", DT_MSG_START=" + DT_MSG_START + ", DT_MSG_END="
				+ DT_MSG_END + ", TITLE_MSG=" + TITLE_MSG + ", TXT_MSG="
				+ TXT_MSG + ", CHK_YN=" + CHK_YN + ", QTY_READ=" + QTY_READ
				+ ", DT_INSERT=" + DT_INSERT + ", ID_INSERT=" + ID_INSERT
				+ ", LN_PARTNER=" + LN_PARTNER + "]";
	}

}
