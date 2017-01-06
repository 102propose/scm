package com.pnt.doe.controller;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.pnt.common.data.DHTMLXGridDataTransformer;
import com.pnt.common.data.DataTransformer;
import com.pnt.common.message.PntMessageSource;
import com.pnt.common.secure.SecureUtils;
import com.pnt.common.util.CoreUtils;
import com.pnt.context.User;
import com.pnt.context.config.Configurator;
import com.pnt.doe.DoVO;
import com.pnt.doe.service.DoService;

@Controller
@RequestMapping(value = "/do")
public class DoController {
    private Logger logger = Logger.getLogger(this.getClass());

    @Resource(name = "pntMessageSource")
    private PntMessageSource messageSource;
    
    @Resource(name = "doService")
    private DoService doService;
    
    @Resource(name = "printWriterView")
    private View printWriterView;
    
    @RequestMapping(value = "/page.do")
    public String main(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return "do/do";
    }
    @RequestMapping(value = "/modal/save.do")
    public String modal(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = sdf.format(GregorianCalendar.getInstance().getTime());
        model.addAttribute("currentDate", currentDate);
        
        String maxDate = CoreUtils.addDate(currentDate, 1);
        model.addAttribute("maxDate", maxDate);
        
        return "do/doSave";
    }
    
    @RequestMapping(value = "/select/list.do")
    public View selectPoList(HttpServletRequest request, Model model, HttpSession session) throws Exception {
        
        String condDate = SecureUtils.getParameter(request, "condDate", null);
        String poPartner = SecureUtils.getParameter(request, "poPartner", "");
        
        User user = (User)session.getAttribute(Configurator.Constants.SESSION_SIGNED_USER);
        
        if ("admin".equalsIgnoreCase(user.getId())) {
        	 if ("".equals(poPartner)) {
        		 poPartner = user.getId();
        	 }
         } else {
        	 poPartner = user.getId();
         }
        
        List<DoVO> data = this.doService.selectByDate(poPartner, condDate);

        DataTransformer dtf = new DHTMLXGridDataTransformer();
        dtf.transform(data);

        String json = dtf.getTransformData();

        model.addAttribute("OUT_DATA", json);

        return this.printWriterView;
    }
    
    @RequestMapping(value = "/select/partners.do")
    public View selectPoPartners(HttpServletRequest request, Model model, HttpSession session) throws Exception {
        
        String condDate = SecureUtils.getParameter(request, "dtFr", null);
        
        List<Map<String,String>> data = this.doService.selectDoPartners(condDate);

        model.addAttribute("OUT_DATA", JSONArray.fromObject(data));

        return this.printWriterView;
    }
    
    @RequestMapping(value = "/insert.do")
    public View insertDoQty(HttpServletRequest request, Model model, HttpSession session) throws Exception {
        
        String partnerCode = SecureUtils.getParameter(request, "pcd");
        String itemCode = SecureUtils.getParameter(request, "icd");
        String jsonArrayData = SecureUtils.getParameter(request, "iter");
 
        jsonArrayData = jsonArrayData.replaceAll("\\&quot\\;", "\"");
//        jsonArrayData = jsonArrayData.replaceAll("\\&quot\\;", "\"");  MS
        
        int result = this.doService.insertDO(partnerCode, itemCode, jsonArrayData);

        model.addAttribute("OUT_DATA", result + "건 저장 되었습니다.");

        return this.printWriterView;
    }
}