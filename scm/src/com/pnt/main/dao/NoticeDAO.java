package com.pnt.main.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.pnt.common.jdbc.SqlSessionDaoSupport;
import com.pnt.main.NoticeVO;

/**
 * @author ERP 시스템즈 이주용
 * @since 2015.04.18
 * @version 1.0
 * @see
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 * 
 *     수정일         수정자               수정내용
 *  --------------    ------------    ---------------------------
 *  2015.04.18        이주용              최초 생성
 * </pre>
 */

@Repository("noticeDAO")
public class NoticeDAO extends SqlSessionDaoSupport {

    public List<NoticeVO> selectCommonNoticeList(NoticeVO vo) throws Exception {
    	List<NoticeVO> result = super.getSqlSession().selectList("Notice.selectCommonNoticeList", vo);
        return result;
    }
    public List<NoticeVO> selectPartnerNoticeList(NoticeVO vo) throws Exception {
    	List<NoticeVO> result = super.getSqlSession().selectList("Notice.selectPartnerNoticeList", vo);
    	return result;
    }
    
    public NoticeVO selectContent(int msgSeq) throws Exception {
    	return super.getSqlSession().selectOne("Notice.selectContent", msgSeq);
    }
    
    public int updateReadCount(int msgSeq) throws Exception {
        int count = super.getSqlSession().update("Notice.updateReadCount", msgSeq);
        return count;
    }

    public int updateCheckReadY(int msgSeq) throws Exception {
        int count = super.getSqlSession().update("Notice.updateCheckReadY", msgSeq);
        return count;
    }

}
