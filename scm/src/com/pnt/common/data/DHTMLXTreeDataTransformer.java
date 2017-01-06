package com.pnt.common.data;

import java.util.List;

import com.pnt.common.data.i.TreeDataTransformBean;

/**
 * @author ERP 시스템즈 이주용
 * @since 2015.04.18
 * @version 1.0
 * @see
 * 
 *      <pre>
 * << 개정이력(Modification Information) >>
 * 
 *     수정일         수정자               수정내용
 *  --------------    ------------    ---------------------------
 *  2015.04.18        이주용              최초 생성
 * </pre>
 */

public class DHTMLXTreeDataTransformer extends DataTransformer {

	private String rootName;

	public String getRootName() {
		return rootName == null ? "ROOT MENU" : this.rootName;
	}

	public void setRootName(String rootName) {
		this.rootName = rootName;
	}

	public void transform(Object data) throws Exception {
		List<?> list = (List<?>) data;

		
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><tree id=\"0\"><item id=\"ROOT_MENU\" text=\"" + this.getRootName() + "\"><userdata name=\"type\">folder</userdata>";

		for (int i = 0; i < list.size(); i++) {
			xml += ((TreeDataTransformBean) list.get(i)).asTreeXml();
		}

		xml += "</item></tree>";

		this.text = xml;

	}

}
