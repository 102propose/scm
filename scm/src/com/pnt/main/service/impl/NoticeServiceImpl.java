package com.pnt.main.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.pnt.common.message.PntMessageSource;
import com.pnt.main.NoticeVO;
import com.pnt.main.dao.NoticeDAO;
import com.pnt.main.service.NoticeService;

@Service("noticeService")
public class NoticeServiceImpl implements NoticeService {
    @Resource(name = "pntMessageSource")
    private PntMessageSource messageSource;
    
    @Resource(name = "noticeDAO")
    private NoticeDAO noticeDAO;
    
    @Override
    public List<NoticeVO> selectCommonNoticeList(String partnerCode) throws Exception {
    	NoticeVO vo = new NoticeVO();
    	vo.setCD_PARTNER(partnerCode);
    	
        List<NoticeVO> noticeList = this.noticeDAO.selectCommonNoticeList(vo);
        
        return noticeList;
    }
    @Override
    public List<NoticeVO> selectPartnerNoticeList(String partnerCode) throws Exception {
    	NoticeVO vo = new NoticeVO();      
    	
    	if (!"admin".equalsIgnoreCase(partnerCode)) {
    		vo.setCD_PARTNER(partnerCode);
    	}
    	
    	List<NoticeVO> noticeList = this.noticeDAO.selectPartnerNoticeList(vo);
    	
    	return noticeList;
    }
	@Override
	public NoticeVO selectContent(String type, int msgSeq) throws Exception {
		NoticeVO notice = this.noticeDAO.selectContent(msgSeq);
		
		if ("common".equals(type)) {
			this.noticeDAO.updateReadCount(msgSeq);
		} else {
			this.noticeDAO.updateCheckReadY(msgSeq);
			this.noticeDAO.updateReadCount(msgSeq);
		}
		
		return notice;
	}
	
}
