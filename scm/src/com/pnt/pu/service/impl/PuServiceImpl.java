package com.pnt.pu.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.formula.functions.Replace;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pnt.common.message.PntMessageSource;
import com.pnt.common.util.CoreUtils;
import com.pnt.po.PoInfoVO;
import com.pnt.po.PoVO;
import com.pnt.pu.PuInfoVO;
import com.pnt.pu.PuVO;
import com.pnt.pu.service.PuService;
import com.pnt.pu.dao.PuDAO;

@Service("puService") 
public class PuServiceImpl implements PuService {
    @Resource(name = "pntMessageSource")
    private PntMessageSource messageSource;
    
    @Resource(name = "puDAO")
    private PuDAO puDAO;
    
    
    
    public List<Map<String,String>> selectPartners(String dtFr, String dtTo) throws Exception {
        Map<String,String> param = new HashMap<String,String>();
        param.put("dtFr", dtFr);
        param.put("dtTo", dtTo);
        
        return this.puDAO.selectPartners(param);
    }
    
    public List<PuVO> selectList(String id, String dtFr, String dtTo, String dt, String partner) throws Exception{
    	if (dtFr != null) {
            dtFr = dtFr.replace("-", "");
        }
        if (dtTo != null) {
            dtTo = dtTo.replace("-", "");
        }
        if (dt != null) {
            dt = dt.replace("-", "");
        }
        PuVO vo = new PuVO();      
        vo.setUserId(id);
        vo.setDtFr(dtFr);
        vo.setDtTo(dtTo);
        vo.setDt(dt);
        vo.setPartner(partner);
        
        List<PuVO> puList = this.puDAO.getList(vo);
        
        return puList;
    }

    public int insertPu(String jsonParam) throws Exception {
        int result = 0;
        
        Map<String,Object> maxParam = new HashMap<String,Object>();
        Map<String,Object> param = new HashMap<String,Object>();
         
        String insertDate;
        int maxSeq, dataYn, check;
        String doQty;
        
        JSONObject jsonData;
        JSONArray jsonArray = JSONArray.fromObject(jsonParam);
        
        jsonData = jsonArray.getJSONObject(0);
        maxParam.put("cdPartner", jsonData.getString("CD_PARTNER"));
        maxParam.put("dtRcv", jsonData.getString("DT_RCV"));
        maxSeq = this.puDAO.selectMaxSeqDO(maxParam);
        
        for (int x0 = 0; x0 < jsonArray.size(); x0++) {
        	jsonData = jsonArray.getJSONObject(x0);
        	
            param.put("cdPartner", jsonData.getString("CD_PARTNER"));
            param.put("cdItem", jsonData.getString("CD_ITEM"));
            param.put("dtRcv", jsonData.getString("DT_RCV"));
            
            //dataYn = this.puDAO.selectDataYn(param);
            
            //if(dataYn > 0){
            //	this.puDAO.deleteData(param);
            //}
                    	
        	param.put("seqRcv", maxSeq);
        	param.put("noRcv", jsonData.getString("CD_PARTNER") + jsonData.getString("DT_RCV").replaceAll("-", "") + String.format("%02d", maxSeq) );
        	param.put("seqRcv", jsonData.getString("SEQ_RCV"));
        	param.put("noPo", jsonData.getString("NO_PO"));
        	param.put("qtRcv", jsonData.getInt("QT_RCV"));
        	param.put("descRcv", jsonData.getString("DESC_RCV"));
        	param.put("dstRcv", jsonData.getString("DST_RCV"));
        	
        	
        	result += this.puDAO.insertData(param);
        	
        	/*
        	
            
            insertDate = jsonData.getString("dt");
            param.put("date", insertDate);
            
            //maxSeq = this.doDAO.selectMaxSeqDO(param);
            //param.put("seq", maxSeq);
            
            doQty = CoreUtils.ifNull(jsonData, "qty");
            doQty = doQty.replaceAll(",", "");
            
            if (StringUtils.isEmpty(doQty)) {
                continue;
            }
            param.put("qt", Integer.valueOf(doQty));
            */
            //result += this.doDAO.insertDo(param);
        }
        
        return result;
    }
    
    public PuInfoVO selectPuHeadInfo(String po_no, String dtFr, String dtTo) throws Exception {
		PuInfoVO vo = new PuInfoVO();       
		vo.setPo_no(po_no);
		vo.setDtFr(dtFr);
		vo.setDtTo(dtTo);
	 
	    PuInfoVO puHeadInfo = this.puDAO.selectPuHeadInfo(vo);
	    
	    return puHeadInfo;
	}
    
    public List<Map<String, String>> selectPuHistory(PuVO vo) throws Exception {
    	
    	return this.puDAO.selectPuHistory(vo);
    }
    
    public List<PuVO> selectHistoryList(PuVO vo) throws Exception {
    	
    	List<PuVO> puList = this.puDAO.selectHistoryList(vo);
        
        return puList;
    }
    
    public PuInfoVO selectPuHistoryHeadInfo(PuVO vo) throws Exception {
    	
    	PuInfoVO puHistoryHeadInfo = this.puDAO.selectPuHistoryHeadInfo(vo);
	    
	    return puHistoryHeadInfo;
    }
    
    public List<PuInfoVO> selectPuHistoryPdfList(PuVO vo) throws Exception {
    	
    	List<PuInfoVO> puHistoryPdfList = this.puDAO.selectPuHistoryPdfList(vo);
    	
    	return puHistoryPdfList;
    }
}
