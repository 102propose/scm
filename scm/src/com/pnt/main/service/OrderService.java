package com.pnt.main.service;

import java.util.List;

import com.pnt.main.OrderVO;

public interface OrderService {
    
	public List<OrderVO> selectCommonOrderList(String partnerCode) throws Exception;
//	public List<NoticeVO> selectPartnerNoticeList(String partnerCode) throws Exception;
//	public NoticeVO selectContent(String type, int msgSeq) throws Exception;

}