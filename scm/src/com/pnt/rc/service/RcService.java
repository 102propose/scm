package com.pnt.rc.service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.pnt.pp.PpInfoVO;
import com.pnt.pp.PpVO;
import com.pnt.rc.RcVO;

public interface RcService {
    
    public List<RcVO> selectRcListByKey(String id, String dtFr, String dtTo, String rcPartner) throws Exception;
    public List<Map<String,String>> selectRcPartners(String dtFr, String dtTo) throws Exception;
}