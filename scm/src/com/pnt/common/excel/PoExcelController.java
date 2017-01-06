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
import com.pnt.po.PoVO;
import com.pnt.po.service.PoService;

@Controller
public class PoExcelController extends AbstractController{

    @Resource(name = "poService")
    private PoService poService;
	
    @RequestMapping(value = "/excel/po/getExcelList.do")
    @Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
		HttpServletResponse response) throws Exception {
    	
    	String dtFr = SecureUtils.getParameter(request, "dtFr", null);
    	String dtTo = SecureUtils.getParameter(request, "dtTo", null);
    	String poPartner = SecureUtils.getParameter(request, "poPartner", null);
    	String filePath = SecureUtils.getParameter(request, "filePath", null);
    	
    	User user = (User)request.getSession().getAttribute(Configurator.Constants.SESSION_SIGNED_USER);
    	
    	List<PoVO> poData = this.poService.selectPoListByKey(user.getId(), dtFr, dtTo, poPartner);
    	
    	Map<String, Object> mapPoData = new HashMap<String, Object>();
    	mapPoData.put("mapPoData", poData);
    	mapPoData.put("fileName", filePath);
    	
    	return new ModelAndView("PoExcelConvertView", "mapPoData", mapPoData);
    	
    }
}
