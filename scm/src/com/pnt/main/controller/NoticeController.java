package com.pnt.main.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.pnt.common.data.DHTMLXGridDataTransformer;
import com.pnt.common.data.DataTransformer;
import com.pnt.context.User;
import com.pnt.context.config.Configurator;
import com.pnt.main.NoticeVO;
import com.pnt.main.service.NoticeService;

@Controller
@RequestMapping(value = "/notice")
public class NoticeController {
    private Logger logger = Logger.getLogger(this.getClass());

    @Resource(name = "printWriterView")
    private View printWriterView;
    
    @Resource(name = "noticeService")
    private NoticeService noticeService;

    @RequestMapping(value = "/page.do")
    public String main(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return "main/notice";
    }
    
    @RequestMapping(value = "/popup.do")
    public String popup(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return "main/noticeContent";
    }
    
    @RequestMapping(value = "/common/list.do")
    public View selectCommonNoticeList(HttpServletRequest request, HttpServletResponse response, Model model, HttpSession session) throws Exception {

    	try {
    		User user = (User) session.getAttribute(Configurator.Constants.SESSION_SIGNED_USER);
    		
    		List<NoticeVO> noticeList = this.noticeService.selectCommonNoticeList(user.getId());

    		DataTransformer dtf = new DHTMLXGridDataTransformer();
            dtf.transform(noticeList);

            String json = dtf.getTransformData();
            
            this.logger.debug("====================================================================");
            this.logger.debug(json);
            this.logger.debug("====================================================================");

            model.addAttribute("OUT_DATA", json);
    	} catch (Exception e){
    		this.logger.error("", e);
    		response.sendError(500);
    	}
    	
        return this.printWriterView;
    }
    @RequestMapping(value = "/partner/list.do")
    public View selectPartnerNoticeList(HttpServletRequest request, HttpServletResponse response, Model model, HttpSession session) throws Exception {

    	try {
    		User user = (User) session.getAttribute(Configurator.Constants.SESSION_SIGNED_USER);
            
    		List<NoticeVO> noticeList = this.noticeService.selectPartnerNoticeList(user.getId());

    		DataTransformer dtf = new DHTMLXGridDataTransformer();
            dtf.transform(noticeList);

            String json = dtf.getTransformData();
            
            this.logger.debug("====================================================================");
            this.logger.debug(json);
            this.logger.debug("====================================================================");

            model.addAttribute("OUT_DATA", json);
    	} catch (Exception e){
    		this.logger.error("", e);
    		response.sendError(500);
    	}
    	
        return this.printWriterView;
    }
    
    @RequestMapping(value = "/common/content/{msgSeq}.do")
    public View selectCommonContent(HttpServletRequest request, HttpServletResponse response, @PathVariable("msgSeq") int msgSeq, Model model) throws Exception {

    	try {
    		NoticeVO notice = this.noticeService.selectContent("common", msgSeq);

            this.logger.debug("====================================================================");
            this.logger.debug(notice);
            this.logger.debug("====================================================================");

            model.addAttribute("OUT_DATA", JSONObject.fromObject(notice));
    	} catch (Exception e){
    		this.logger.error("", e);
    		response.sendError(500);
    	}
    	
    	return this.printWriterView;
    }
    
    @RequestMapping(value = "/partner/content/{msgSeq}.do")
    public View selectPartnerContent(HttpServletRequest request, HttpServletResponse response, @PathVariable("msgSeq") int msgSeq, Model model) throws Exception {

    	try {
    		NoticeVO notice = this.noticeService.selectContent("partner", msgSeq);

            this.logger.debug("====================================================================");
            this.logger.debug(notice);
            this.logger.debug("====================================================================");

            model.addAttribute("OUT_DATA", JSONObject.fromObject(notice));
    	} catch (Exception e){
    		this.logger.error("", e);
    		response.sendError(500);
    	}
    	
    	return this.printWriterView;
    }
}