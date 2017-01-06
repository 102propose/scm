package com.pnt.po.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.pnt.common.jdbc.SqlSessionDaoSupport;
import com.pnt.po.PoInfoVO;
import com.pnt.po.PoVO;

@Repository("poDAO")
public class PoDAO extends SqlSessionDaoSupport {

    public List<PoVO> getList(PoVO vo) throws Exception {
        List<PoVO> result = super.getSqlSession().selectList("Po.getList", vo);
        return result;
    }
    
    public PoInfoVO selectPoHeadInfo(PoInfoVO vo) throws Exception {
        PoInfoVO result = super.getSqlSession().selectOne("Po.selectPoHeadInfo", vo);
        return result;
    }
    
    public List<PoInfoVO> selectPoInfo(PoInfoVO vo) throws Exception {
        List<PoInfoVO> result = super.getSqlSession().selectList("Po.selectPoInfo", vo);
        return result;
    }
    
    public List<Map<String,String>> selectPoPartners(Map<String,String> vo) throws Exception {
        List<Map<String,String>> result = super.getSqlSession().selectList("Po.selectPoPartners", vo);
        return result;
    }
    
    public int insert(PoVO vo) throws Exception {
        int count = super.getSqlSession().insert("Po.insertShop", vo);
        return count;
    }

    public int update(PoVO vo) throws Exception {
        int count = super.getSqlSession().update("Po.updateShop", vo);
        return count;
    }

    public int delete(String id) throws Exception {
        int count = super.getSqlSession().delete("Po.deleteShop", id);
        return count;
    }
    
    public void firstOpenPdf(PoVO vo) throws Exception {
    	 super.getSqlSession().update("Po.firstOpenPdf1", vo);
    	 super.getSqlSession().update("Po.firstOpenPdf2", vo);
    }
}
