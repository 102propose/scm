package com.pnt.doe.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.pnt.common.message.PntMessageSource;
import com.pnt.common.util.CoreUtils;
import com.pnt.doe.DoVO;
import com.pnt.doe.dao.DoDAO;
import com.pnt.doe.service.DoService;

@Service("doService")
public class DoServiceImpl implements DoService {
    @Resource(name = "pntMessageSource")
    private PntMessageSource messageSource;
    
    @Resource(name = "doDAO")
    private DoDAO doDAO;
    
    @Override
    public List<DoVO> selectByDate(String partnerCode, String date) throws Exception {
        String condDate = date;
        if (!StringUtils.isEmpty(condDate)) {
            condDate = condDate.replace("-", "");
        }
        
        DoVO vo = new DoVO();      
        vo.setCondDate(condDate);
        vo.setCondPartnerCode(partnerCode);
        
        List<DoVO> doList = this.doDAO.selectByDate(vo);
        
        return doList;
    }
    
    @Override
    public List<Map<String,String>> selectDoPartners(String condDate) throws Exception {
        return this.doDAO.selectDoPartners(condDate);
    }

    @Override
    public int insertDO(String partnerCode, String itemCode, String jsonParam) throws Exception {
        int result = 0;
        
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("partnerCode", partnerCode);
        param.put("itemCode", itemCode);
        
        String insertDate;
        int maxSeq;
        String doQty;
        
        JSONObject jsonData;
        JSONArray jsonArray = JSONArray.fromObject(jsonParam);
        
        for (int x0 = 0; x0 < jsonArray.size(); x0++) {
            jsonData = jsonArray.getJSONObject(x0);
            
            insertDate = jsonData.getString("dt");
            param.put("date", insertDate);
            
            maxSeq = this.doDAO.selectMaxSeqDO(param);
            param.put("seq", maxSeq);
            
            doQty = CoreUtils.ifNull(jsonData, "qty");
            doQty = doQty.replaceAll(",", "");
            
            if (StringUtils.isEmpty(doQty)) {
                continue;
            }
            param.put("qt", Integer.valueOf(doQty));
            
            result += this.doDAO.insertDo(param);
        }
        
        return result;
    }
    
}
