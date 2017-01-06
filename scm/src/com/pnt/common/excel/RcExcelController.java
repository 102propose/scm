package com.pnt.common.excel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.pnt.common.secure.SecureUtils;
import com.pnt.context.User;
import com.pnt.context.config.Configurator;
import com.pnt.rc.RcVO;
import com.pnt.rc.service.RcService;

@Controller
public class RcExcelController extends AbstractController{

    @Resource(name = "rcService")
    private RcService rcService;
	
    @RequestMapping(value = "/excel/rc/getExcelList.do")
    @Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
		HttpServletResponse response) throws Exception {
    	
    	String dtFr = SecureUtils.getParameter(request, "dtFr", null);
    	String dtTo = SecureUtils.getParameter(request, "dtTo", null);
    	String rcPartner = SecureUtils.getParameter(request, "rcPartner", null);
    	String filePath = SecureUtils.getParameter(request, "filePath", null);
    	
    	User user = (User)request.getSession().getAttribute(Configurator.Constants.SESSION_SIGNED_USER);
    	
//    	List<PoVO> poData = this.poService.selectPoListByKey(user.getId(), dtFr, dtTo, poPartner);
    	List<RcVO> rcData = this.rcService.selectRcListByKey(user.getId(), dtFr, dtTo, rcPartner);
    	
    	Map<String, Object> mapRcData = new HashMap<String, Object>();
    	mapRcData.put("mapRcData", rcData);
    	mapRcData.put("fileName", filePath);
    	
    	return new ModelAndView("RcExcelConvertView", "mapRcData", mapRcData);
    	
    }
}
