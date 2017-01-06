package com.pnt.pp.service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import com.pnt.pp.PpInfoVO;
import com.pnt.pp.PpVO;

public interface PpService {
    
    public List<PpVO> selectPpListByKey(String id, String dtFr, String dtTo) throws Exception;
    /*
    public PpInfoVO selectPpHeadInfo(String po_no, String dtFr, String dtTo) throws Exception;
    
    public List<PpInfoVO> selectPpInfo(String po_no, String dtFr, String dtTo) throws Exception;
    
    public List<Map<String,String>> selectPpPartners(String dtFr, String dtTo) throws Exception;
    
    public void savePp(JSONArray jsonArray) throws Exception;
    
    public void deletePp(JSONArray jsonArray) throws Exception;
    */
    
}