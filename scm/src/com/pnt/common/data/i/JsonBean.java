package com.pnt.common.data.i;

import net.sf.json.JSONObject;

public interface JsonBean {
    public void serialize(JSONObject json) throws Exception;
}
