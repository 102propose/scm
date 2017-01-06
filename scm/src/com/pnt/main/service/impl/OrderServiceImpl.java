package com.pnt.main.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.pnt.common.message.PntMessageSource;
import com.pnt.main.OrderVO;
import com.pnt.main.dao.OrderDAO;
import com.pnt.main.service.OrderService;

@Service("orderService")
public class OrderServiceImpl implements OrderService {
    @Resource(name = "pntMessageSource")
    private PntMessageSource messageSource;
    
//    @Resource(name = "noticeDAO")
//    private NoticeDAO noticeDAO;
    
    @Resource(name = "orderDAO")
    private OrderDAO orderDAO;
    
    @Override
    public List<OrderVO> selectCommonOrderList(String partnerCode) throws Exception {
    	OrderVO vo = new OrderVO();
    	vo.setCD_PARTNER(partnerCode);
    	
    	List<OrderVO> orderList = this.orderDAO.selectCommonOrderList(vo);
        
        return orderList;
    }
    /*@Override
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
	}*/
	
}
