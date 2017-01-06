package com.pnt.pp.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.pnt.common.jdbc.SqlSessionDaoSupport;
import com.pnt.po.PoInfoVO;
import com.pnt.po.PoVO;
import com.pnt.pp.PpVO;

@Repository("ppDAO")
public class PpDAO extends SqlSessionDaoSupport {

    public List<PpVO> getList(PpVO vo) throws Exception {
        List<PpVO> result = super.getSqlSession().selectList("Pp.getList", vo);
        return result;
    }
}
