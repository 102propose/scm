package com.pnt.pu.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.pnt.common.jdbc.SqlSessionDaoSupport;
import com.pnt.po.PoInfoVO;
import com.pnt.po.PoVO;
import com.pnt.pu.PuInfoVO;
import com.pnt.pu.PuVO;

@Repository("puDAO") 
public class PuDAO extends SqlSessionDaoSupport {

    public List<Map<String,String>> selectPartners(Map<String,String> vo) throws Exception {
        List<Map<String,String>> result = super.getSqlSession().selectList("Pu.selectPartners", vo);
        return result;
    }

	public List<PuVO> getList(PuVO vo) throws Exception{
		List<PuVO> result = super.getSqlSession().selectList("Pu.getList", vo);
        return result;
	}

	public int selectMaxSeqDO(Map<String, Object> param) throws Exception{
		int result = super.getSqlSession().selectOne("Pu.selectMaxSeq", param);
		return result;
	}

	public int selectDataYn(Map<String, Object> param) throws Exception{
		int result = super.getSqlSession().selectOne("Pu.selectDataYn", param);
		return result;
	}

	public void deleteData(Map<String, Object> param) throws Exception{
		super.getSqlSession().delete("Pu.deleteData", param);
	}

	public int insertData(Map<String, Object> param) throws Exception{
		int result = super.getSqlSession().insert("Pu.insertData", param);
        return result;
	}

	public PuInfoVO selectPuHeadInfo(PuInfoVO vo) throws Exception {
		PuInfoVO result = super.getSqlSession().selectOne("Pu.selectPuHeadInfo", vo);
        return result;
    }
	
	public List<Map<String,String>> selectPuHistory(PuVO vo) throws Exception {
        List<Map<String,String>> result = super.getSqlSession().selectList("Pu.selectPuHistory", vo);
        return result;
    }
	
	public List<PuVO> selectHistoryList(PuVO vo) throws Exception{
		List<PuVO> result = super.getSqlSession().selectList("Pu.selectHistoryList", vo);
        return result;
	}
	
	public PuInfoVO selectPuHistoryHeadInfo(PuVO vo) throws Exception{
		PuInfoVO result = super.getSqlSession().selectOne("Pu.selectPuHistoryHeadInfo", vo);
        return result;
	}
	
	public List<PuInfoVO> selectPuHistoryPdfList(PuVO vo) throws Exception{
		List<PuInfoVO> result = super.getSqlSession().selectList("Pu.selectPuHistoryPdfList", vo);
        
		return result;
	}

}
