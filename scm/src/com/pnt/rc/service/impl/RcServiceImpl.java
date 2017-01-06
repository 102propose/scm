package com.pnt.rc.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pnt.common.message.PntMessageSource;
import com.pnt.rc.RcInfoVO;
import com.pnt.rc.RcVO;
import com.pnt.rc.dao.RcDAO;
import com.pnt.rc.service.RcService;

@Service("rcService")
public class RcServiceImpl implements RcService {
    @Resource(name = "pntMessageSource")
    private PntMessageSource messageSource;
    
    @Resource(name = "rcDAO")
    private RcDAO rcDAO;
    
    public List<RcVO> selectRcListByKey(String id, String dtFr, String dtTo, String rcPartner) throws Exception {
        if (dtFr != null) {
            dtFr = dtFr.replace("-", "");
        }
        if (dtTo != null) {
            dtTo = dtTo.replace("-", "");
        }
        
        RcVO vo = new RcVO();      
        vo.setUserId(id);
        vo.setDtFr(dtFr);
        vo.setDtTo(dtTo);
        vo.setRc_partner(rcPartner);        
       
        
        List<RcVO> rcList = this.rcDAO.getList(vo);
        
        return rcList;
    }
    public List<Map<String,String>> selectRcPartners(String dtFr, String dtTo) throws Exception {
        Map<String,String> param = new HashMap<String,String>();
        param.put("dtFr", dtFr);
        param.put("dtTo", dtTo);
        
        return this.rcDAO.selectRcPartners(param);
    }
    
 }
