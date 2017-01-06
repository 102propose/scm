package com.pnt.ac.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pnt.common.message.PntMessageSource;
import com.pnt.ac.AcInfoVO;
import com.pnt.ac.AcVO;
import com.pnt.ac.dao.AcDAO;
import com.pnt.ac.service.AcService;

@Service("acService")
public class AcServiceImpl implements AcService {
    @Resource(name = "pntMessageSource")
    private PntMessageSource messageSource;
    
    @Resource(name = "acDAO")
    private AcDAO acDAO;
    
    public List<AcVO> selectAcListByKey(String id, String dtFr, String dtTo, String acPartner) throws Exception {
        if (dtFr != null) {
            dtFr = dtFr.replace("-", "");
        }
        if (dtTo != null) {
            dtTo = dtTo.replace("-", "");
        }
        
        AcVO vo = new AcVO();      
        vo.setUserId(id);
        vo.setDtFr(dtFr);
        vo.setDtTo(dtTo);
        vo.setAc_partner(acPartner);        
       
        
        List<AcVO> acList = this.acDAO.getList(vo);
        
        
        
        
        return acList;
    }
    public List<Map<String,String>> selectAcPartners(String dtFr, String dtTo) throws Exception {
        Map<String,String> param = new HashMap<String,String>();
        param.put("dtFr", dtFr);
        param.put("dtTo", dtTo);
        
        return this.acDAO.selectAcPartners(param);
    }
    
 }
