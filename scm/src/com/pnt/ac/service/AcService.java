package com.pnt.ac.service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.pnt.ac.AcVO;

public interface AcService {
    
    public List<AcVO> selectAcListByKey(String id, String dtFr, String dtTo, String acPartner) throws Exception;
    public List<Map<String,String>> selectAcPartners(String dtFr, String dtTo) throws Exception;
}