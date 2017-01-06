<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="com.pnt.context.config.Configurator" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<c:set var="domain" value="<%= Configurator.getInstance().getFullDomain(request) %>" />
<c:set var="timestamp" value="<%=System.nanoTime()%>" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>메인</title>

		<link href="${pageContext.request.contextPath}/resources/bootstrap/3.2.0/css/bootstrap.min.css" rel="stylesheet">
		<%-- <link href="${pageContext.request.contextPath}/resources/bootstrap/calendar/datepicker3.css" rel="stylesheet"> --%>
		<%-- <link href="${domain}${pageContext.request.contextPath}/resources/dhtmlx/4.0.3/grid/dhtmlxgrid.css" rel="stylesheet">
		<link href="${domain}${pageContext.request.contextPath}/resources/dhtmlx/4.1.3/windows/skin/dhtmlxwindows_dhx_web.css" rel="stylesheet"> --%>
		<link href="${domain}${pageContext.request.contextPath}/resources/dhtmlx/4.1.3/codebase/dhtmlx.css" rel="stylesheet">
		<link href="${domain}${pageContext.request.contextPath}/css/common.css" rel="stylesheet">
		<style type="text/css">
			* {margin:0; padding:0; /* font:normal 12px 'Dotum'; */}
			html, body {height: 100%; overflow: hidden; position: static; }
			.container {width:98%; min-width: 1024px; margin:10px 0 10px 10px;}
			.container table {width:100%; height: 90%;}
		</style>
		
		<script src="${pageContext.request.contextPath}/resources/jquery/jquery-1.11.1.min.js"></script>
	    <script src="${pageContext.request.contextPath}/resources/jquery/jquery.json-2.4.min.js"></script>
	    
	    <script src="${pageContext.request.contextPath}/resources/dhtmlx/4.1.3/codebase/dhtmlx.js"></script>
	    
	    <script src="${pageContext.request.contextPath}/js/PNT.js?${timestamp}"></script>
	    <script src="${pageContext.request.contextPath}/js/info.js?${timestamp}"></script>
	</head>
	<body>
	
		<div class="lcontainer" style="padding-left: 5px; padding-bottom: 5px">
			
			<!-- <div style="height: 91%; margin-right: 15px; width: 48%; float: left;"></div>		 -->	
			<div id="info_grid_box1" style="width:100%; height: 28%; margin-bottom: 10px; float: left;"></div>			
			<div id="info_grid_box2" style="width:100%; height: 25%; margin-bottom: 10px; float: left;"></div>
			<div id="info_grid_box3" style="width:100%; height: 25%; margin-bottom: 10px; float: left;"></div>
		</div>
	    
		<jsp:include page="/WEB-INF/views/include/common.jsp"></jsp:include>
	</body>
</html>