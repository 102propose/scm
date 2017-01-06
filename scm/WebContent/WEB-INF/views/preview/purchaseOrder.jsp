<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="com.pnt.context.config.Configurator"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%
    response.setHeader("Cache-Control", "no-cache");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);
    
    String po_no = request.getParameter("po_no");
    String df = request.getParameter("df");
    String dt = request.getParameter("dt");
%>

<c:set var="domain" value="<%=Configurator.getInstance().getFullDomain(request) %>" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html kor>
  <head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8">
    <meta name="generator" content="CoffeeCup HTML Editor (www.coffeecup.com)">
    <meta name="created" content="월, 04 5 2015 18:31:42 GMT">
    <meta name="description" content="">
    <meta name="keywords" content="">
    
    <meta content="text/html; charset=UTF-8"  http-equiv="content-type">
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>

    <script src="${pageContext.request.contextPath}/resources/jquery/jquery-1.11.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/jquery/jquery.json-2.4.min.js"></script>
  
    <script src="${pageContext.request.contextPath}/resources/dhtmlx/4.0.3/commons/dhtmlxcommon.js"></script>
    <script src="${pageContext.request.contextPath}/resources/dhtmlx/4.0.3/grid/dhtmlxgrid.js"></script>
  
    <script src="${pageContext.request.contextPath}/js/PNT.js?${timestamp}"></script>
    <script src="${pageContext.request.contextPath}/js/purchaseOrder.js?${timestamp}"></script>
    
    <title></title>    
  </head>
  <body>
  		<table style="width: 1040px; border=0;"> <!-- A4 : 595  X 842 pixel -->
          <tr><!-- Row 1 -->
            <td colspan="1" rowspan="2" style="padding-top:0px" >			 	 
			 	 <img src="${pageContext.request.contextPath}/images/logo/preview_logo.jpg"  width="378" height="120" alt="" title="" align="top" />			 	 
			</td><!-- Col 1 -->
			<td align="right">
			 	 <b style="font-family:Arial; font-size:65px; color: rgb(139, 150, 169);">PURCHASE</b>
			</td>			
		  </tr>
		  <tr>
			 <td style="text-align:right;">
			 	 <b style="font-family:Arial; font-size:65px; color: rgb(139, 150, 169);">ORDER</b>
			 </td>			 
          </tr>
		  <tr>
		  	  <td>
		  	  	  <b style="font-family:Arial; font-size:40px;">RaycopKorea Inc.</b><br/>
				  <b style="font-family:Arial;">인천광역시 남동구 청능대로 450 (고잔동 104B-1L)</b></br>
				  <b style="font-family:Arial;">&nbsp;&nbsp;&nbsp;&nbsp; Tel: +82-32-814-1760. &nbsp; Fax: +82-32-814-8768</b>
		  	  </td>	  
		  </tr>
		  
        </table>
        <jsp:include page="/WEB-INF/views/include/common.jsp"></jsp:include>
  </body>
</html>