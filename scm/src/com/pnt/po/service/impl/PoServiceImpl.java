package com.pnt.po.service.impl;

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

@Service("poService")
public class PoServiceImpl implements PoService {
    @Resource(name = "pntMessageSource")
    private PntMessageSource messageSource;
    
    @Resource(name = "poDAO")
    private PoDAO poDAO;
    
    public List<PoVO> selectPoListByKey(String id, String dtFr, String dtTo, String poPartner) throws Exception {
        if (dtFr != null) {
            dtFr = dtFr.replace("-", "");
        }
        if (dtTo != null) {
            dtTo = dtTo.replace("-", "");
        }
        
        PoVO vo = new PoVO();      
        vo.setUserId(id);
        vo.setDtFr(dtFr);
        vo.setDtTo(dtTo);
        vo.setPo_partner(poPartner);
        
        List<PoVO> poList = this.poDAO.getList(vo);
        
        return poList;
    }
        
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
    
    public void firstOpenPdf(String po_no) throws Exception{
    	PoVO vo = new PoVO();  
    	vo.setNo_po(po_no);
    	
    	this.poDAO.firstOpenPdf(vo);
    }
}
