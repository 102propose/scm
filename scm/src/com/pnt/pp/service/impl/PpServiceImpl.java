package com.pnt.pp.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pnt.common.message.PntMessageSource;
import com.pnt.po.PoInfoVO;
import com.pnt.po.PoVO;
import com.pnt.po.dao.PoDAO;
import com.pnt.po.service.PoService;
import com.pnt.pp.PpVO;
import com.pnt.pp.dao.PpDAO;
import com.pnt.pp.service.PpService;

@Service("ppService")
public class PpServiceImpl implements PpService {
    @Resource(name = "pntMessageSource")
    private PntMessageSource messageSource;
    
    @Resource(name = "ppDAO")
    private PpDAO ppDAO;
    
    public List<PpVO> selectPpListByKey(String id, String dtFr, String dtTo) throws Exception {
        if (dtFr != null) {
            dtFr = dtFr.replace("-", "");
        }
        if (dtTo != null) {
            dtTo = dtTo.replace("-", "");
        }
        
        PpVO vo = new PpVO();      
        vo.setUserId(id);
        vo.setDtFr(dtFr);
        vo.setDtTo(dtTo);
       
        
        List<PpVO> ppList = this.ppDAO.getList(vo);
        
        return ppList;
    }
   /*     
	public PoInfoVO selectPoHeadInfo(String po_no, String dtFr, String dtTo) throws Exception {
		PoInfoVO vo = new PoInfoVO();       
		vo.setPo_no(po_no);
		vo.setDtFr(dtFr);
		vo.setDtTo(dtTo);
	 
	    PoInfoVO poHeadInfo = this.poDAO.selectPoHeadInfo(vo);
	    
	    return poHeadInfo;
	}
    
    public List<PoInfoVO> selectPoInfo(String po_no, String dtFr, String dtTo) throws Exception {
    		PoInfoVO vo = new PoInfoVO();       
    		vo.setPo_no(po_no);
    		vo.setDtFr(dtFr);
    		vo.setDtTo(dtTo);
    	 
        List<PoInfoVO> poList = this.poDAO.selectPoInfo(vo);
        
        return poList;
    }
    
    public List<Map<String,String>> selectPoPartners(String dtFr, String dtTo) throws Exception {
        Map<String,String> param = new HashMap<String,String>();
        param.put("dtFr", dtFr);
        param.put("dtTo", dtTo);
        
        return this.poDAO.selectPoPartners(param);
    }
    
    @Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
    public void savePo(JSONArray jsonArray) throws Exception {
        int count;
        PoVO vo;
        
        for (int i = 0; i < jsonArray.size(); i++) {
            vo = new PoVO(jsonArray.getJSONObject(i));

            count = this.poDAO.update(vo);
            
            if (count == 0) {
                this.poDAO.insert(vo);
            }
        }
    }
    
    @Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
    public void deletePo(JSONArray jsonArray) throws Exception {
        PoVO vo;
        
        for (int i = 0; i < jsonArray.size(); i++) {
            vo = new PoVO(jsonArray.getJSONObject(i));
            //this.poDAO.delete(vo.getId());
        }
    }
    */
}
