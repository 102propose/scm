package com.pnt.doe.service;

import java.util.List;
import java.util.Map;

import com.pnt.doe.DoVO;

public interface DoService {
    
    public List<DoVO> selectByDate(String partnerCode, String date) throws Exception;
    
    public List<Map<String,String>> selectDoPartners(String condDate) throws Exception;
    
    public int insertDO(String partnerCode, String itemCode, String jsonParam) throws Exception;
}