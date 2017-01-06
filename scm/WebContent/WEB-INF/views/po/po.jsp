<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="com.pnt.context.config.Configurator" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<c:set var="domain" value="<%= Configurator.getInstance().getFullDomain(request) %>" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Soil.Work.Pilot - Admin Role List</title>

		<link href="${pageContext.request.contextPath}/resources/bootstrap/3.2.0/css/bootstrap.min.css" rel="stylesheet">
		<link href="${pageContext.request.contextPath}/resources/bootstrap/calendar/datepicker3.css" rel="stylesheet">
		<link href="${domain}${pageContext.request.contextPath}/resources/dhtmlx/4.0.3/grid/dhtmlxgrid.css" rel="stylesheet">
		<link href="${domain}${pageContext.request.contextPath}/resources/dhtmlx/4.0.3/calendar/dhtmlxcalendar.css" rel="stylesheet">
		<link href="${domain}${pageContext.request.contextPath}/css/common.css" rel="stylesheet">
		<style type="text/css">
			* {margin:0; padding:0; /* font:normal 12px 'Dotum'; */}
			html, body {height: 100%; overflow: hidden; position: static; }
			.container {width:98%; margin:0 0 10px 10px;}
			.container .title {width:100%;}
			.container .condition {width:100%; margin:5px 0 5px 0;;}
			.container .condition input[type="text"] {height:25px; width:100px; text-align:center;}
		</style>
		
		<script src="${pageContext.request.contextPath}/resources/jquery/jquery-1.11.1.min.js"></script>
	    <script src="${pageContext.request.contextPath}/resources/jquery/jquery.json-2.4.min.js"></script>
	    
	    <script src="${pageContext.request.contextPath}/resources/dhtmlx/4.0.3/commons/dhtmlxcommon.js"></script>
	    <script src="${pageContext.request.contextPath}/resources/dhtmlx/4.0.3/grid/dhtmlxgrid.js"></script>
	    <script src="${pageContext.request.contextPath}/resources/dhtmlx/4.0.3/calendar/dhtmlxcalendar.js"></script>
	    
	    <script src="${pageContext.request.contextPath}/js/PNT.js?${timestamp}"></script>
	    <script src="${pageContext.request.contextPath}/js/po.js?${timestamp}"></script>
	</head>
	<body>
	
		<div class="container">
			<div class="title"><img src="${pageContext.request.contextPath}/images/pre/images/admin_ico01.jpg" align="absmiddle"> <font class="adTxt_01">발주 관리</font></div>
			<div class="condition">
				조회일자 
				<input  id="cond_date_fr" type="text" /> ~ <input  id="cond_date_to" type="text" />&nbsp;
				
				<c:if test="${SESSION_SIGNED_USER.id == 'admin'}">
				거래처
				<select id="cond_po_partner" style="width:200px;"></select>&nbsp;&nbsp;
				</c:if>
				<button type="button" class="btn btn-primary btn-sm" onclick="doSearch();">
					<span class="glyphicon glyphicon-search" aria-hidden="true"></span> 조회
				</button>				
				<button type="button" class="btn btn-primary btn-sm" onclick="doPdfSave();">
					<span class="glyphicon glyphicon-download-alt" aria-hidden="true"></span> PDF보기
				</button>
				<button type="button" class="btn btn-primary btn-sm" onclick="doExlSave();">
					<span class="glyphicon glyphicon-download-alt" aria-hidden="true"></span> Excel변환
				</button>
			</div>
			<div id="grid_box" style="width:100%; height: 80%"></div>
		</div>
	    
		<jsp:include page="/WEB-INF/views/include/common.jsp"></jsp:include>
		
	</body>
</html>