package com.pnt.pu.service;

import java.util.List;
import java.util.Map;

import com.pnt.pu.PuInfoVO;
import com.pnt.pu.PuVO;

public interface PuService { 
    
	public List<Map<String, String>> selectPartners(String dtFr, String dtTo) throws Exception;

	public List<PuVO> selectList(String id, String dtFr, String dtTo, String dt, String partner) throws Exception;

	public int insertPu(String jsonArrayData) throws Exception;
	
	public PuInfoVO selectPuHeadInfo(String po_no, String dtFr, String dtTo) throws Exception; 
	
	public List<Map<String, String>> selectPuHistory(PuVO vo) throws Exception;
	
	public List<PuVO> selectHistoryList(PuVO vo) throws Exception;
	
	public PuInfoVO selectPuHistoryHeadInfo(PuVO vo) throws Exception; 
	
	public List<PuInfoVO> selectPuHistoryPdfList(PuVO vo) throws Exception;
    
}