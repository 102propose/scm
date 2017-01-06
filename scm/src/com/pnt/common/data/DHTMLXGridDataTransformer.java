package com.pnt.common.data;

import java.util.List;

import com.pnt.common.data.i.GridDataTransformBean;

/**
 * @author ERP 시스템즈 이주용
 * @since 2015.04.18
 * @version 1.0
 * @see
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 * 
 *     수정일         수정자               수정내용
 *  --------------    ------------    ---------------------------
 *  2015.04.18        이주용              최초 생성
 * </pre>
 */

public class DHTMLXGridDataTransformer extends DataTransformer {
    
    public void transform(Object data) throws Exception {
        List<?> list = (List<?>) data;
        
        String json = "{\"rows\" : [";

        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                json += ((GridDataTransformBean) list.get(i)).asGridJson() + ",";
            }

            if (list.size() > 0) {
                json = json.substring(0, json.length() - 1);
            }
        }

        json += "]}";

        this.text = json;
    }

}
