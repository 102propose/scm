package com.pnt.ac.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.pnt.common.jdbc.SqlSessionDaoSupport;
import com.pnt.ac.AcVO;

@Repository("acDAO")
public class AcDAO extends SqlSessionDaoSupport {

    public List<AcVO> getList(AcVO vo) throws Exception {
        List<AcVO> result = super.getSqlSession().selectList("Ac.getList", vo);
        return result;
    }   

    public List<Map<String,String>> selectAcPartners(Map<String,String> vo) throws Exception {
        List<Map<String,String>> result = super.getSqlSession().selectList("Ac.selectAcPartners", vo);
        return result;
    }


}
