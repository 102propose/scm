package com.pnt.pdf.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.pnt.common.message.PntMessageSource;
import com.pnt.pdf.dao.PdfDao;
import com.pnt.pdf.service.PdfService;



@Service("pdfService")
public class PdfServiceImpl implements PdfService {
	
	@Resource(name = "pntMessageSource")
    private PntMessageSource messageSource;
    
    @Resource(name = "pdfDAO")
    private PdfDao pdfDAO;

	
    
    

}
