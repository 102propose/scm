package com.pnt.po.service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.pnt.po.PoInfoVO;
import com.pnt.po.PoVO;

public interface PoService {
    
    public List<PoVO> selectPoListByKey(String id, String dtFr, String dtTo, String poPartner) throws Exception;
    
    public PoInfoVO selectPoHeadInfo(String po_no, String dtFr, String dtTo) throws Exception;
    
    public List<PoInfoVO> selectPoInfo(String po_no, String dtFr, String dtTo) throws Exception;
    
    public List<Map<String,String>> selectPoPartners(String dtFr, String dtTo) throws Exception;
    
    public void savePo(JSONArray jsonArray) throws Exception;
    
    public void deletePo(JSONArray jsonArray) throws Exception;
    
    public void firstOpenPdf(String po_no) throws Exception;
    
}