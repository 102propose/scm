package com.pnt.doe.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.pnt.common.jdbc.SqlSessionDaoSupport;
import com.pnt.doe.DoVO;

@Repository("doDAO")
public class DoDAO extends SqlSessionDaoSupport {
    
    public List<DoVO> selectByDate(DoVO vo) throws Exception {
        List<DoVO> result = super.getSqlSession().selectList("Do.selectByDate", vo);
        return result;
    }
    
    public List<Map<String,String>> selectDoPartners(String condDate) throws Exception {
        List<Map<String,String>> result = super.getSqlSession().selectList("Do.selectDoPartners", condDate);
        return result;
    }
    
    public int selectMaxSeqDO(Map<String,Object> param) throws Exception {
        int result = super.getSqlSession().selectOne("Do.selectMaxSeqDO", param);
        return result;
    }
    
    public int insertDo(Map<String,Object> param) throws Exception {
        int result = super.getSqlSession().insert("Do.insertDo", param);
        return result;
    }
}
