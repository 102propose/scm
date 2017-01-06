package com.pnt.rc.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.pnt.common.jdbc.SqlSessionDaoSupport;
import com.pnt.rc.RcVO;

@Repository("rcDAO")
public class RcDAO extends SqlSessionDaoSupport {

    public List<RcVO> getList(RcVO vo) throws Exception {
        List<RcVO> result = super.getSqlSession().selectList("Rc.getList", vo);
        return result;
    }   

    public List<Map<String,String>> selectRcPartners(Map<String,String> vo) throws Exception {
        List<Map<String,String>> result = super.getSqlSession().selectList("Rc.selectRcPartners", vo);
        return result;
//    	return null;
    }


}
