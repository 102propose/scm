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
		<title>입고예정(D/O) 입력</title>

		<link href="${pageContext.request.contextPath}/resources/bootstrap/3.2.0/css/bootstrap.min.css" rel="stylesheet">
		<%-- <link href="${pageContext.request.contextPath}/resources/bootstrap/calendar/datepicker3.css" rel="stylesheet"> --%>
		<%-- <link href="${domain}${pageContext.request.contextPath}/resources/dhtmlx/4.0.3/grid/dhtmlxgrid.css" rel="stylesheet">
		<link href="${domain}${pageContext.request.contextPath}/resources/dhtmlx/4.1.3/windows/skin/dhtmlxwindows_dhx_web.css" rel="stylesheet"> --%>
		<link href="${domain}${pageContext.request.contextPath}/resources/dhtmlx/4.1.3/codebase/dhtmlx.css" rel="stylesheet">
		<link href="${domain}${pageContext.request.contextPath}/css/common.css" rel="stylesheet">
		<style type="text/css">
			* {margin:0; padding:0; /* font:normal 12px 'Dotum'; */}
			html, body {height: 100%; overflow: hidden; position: static;}
			.container {width:100%; height:100%;}
			.condition {min-width: 0px; margin: 10px 0px 10px 10px; }
			.buttonForm {width:100%; height:20%;}
			.contents {width:100%; height:50px;}
			div.gridbox.gridbox_dhx_skyblue table.row20px.obj tr.rowselected td, div.gridbox.gridbox_dhx_skyblue table.row20px.obj tr.rowselected.odd_dhx_skyblue td {background-color : initial !important;}
		</style>
		
		<script src="${pageContext.request.contextPath}/resources/jquery/jquery-1.11.1.min.js"></script>
	    <script src="${pageContext.request.contextPath}/resources/jquery/jquery.json-2.4.min.js"></script>
	    <%-- <script src="${pageContext.request.contextPath}/resources/dhtmlx/4.0.3/commons/dhtmlxcommon.js"></script>
	    <script src="${pageContext.request.contextPath}/resources/dhtmlx/4.0.3/grid/dhtmlxgrid.js"></script>
	    <script src="${pageContext.request.contextPath}/resources/dhtmlx/4.1.3/commons/dhtmlxcontainer.js"></script>
	    <script src="${pageContext.request.contextPath}/resources/dhtmlx/4.1.3/windows/dhtmlxwindows.js"></script> --%>
	    <script src="${pageContext.request.contextPath}/resources/dhtmlx/4.1.3/codebase/dhtmlx.js"></script>
	    
	    <script src="${pageContext.request.contextPath}/js/PNT.js?${timestamp}"></script>
		<script src="${pageContext.request.contextPath}/js/puPopup.js?${timestamp}"></script>	    
	</head>
	<body>
		<div class="container" >
			<div style="width: 100%;"></div>			
			<div class="condition" style="float: left;">					
					<img src="${pageContext.request.contextPath}/images/pre/images/admin_ico01.jpg" align="absmiddle"> <font class="adTxt_01">납품등록 내역</font> &nbsp;&nbsp;
					<button type="button" class="btn btn-primary btn-sm" onclick="doHistory();">
						<span class="glyphicon glyphicon-search" aria-hidden="true"></span> PDF보기
					</button>
			</div>
			<div id="grid_box" style="width:100%; height: 85%"></div>
		</div>
	    
		<jsp:include page="/WEB-INF/views/include/common.jsp"></jsp:include>
		
		<script>
			
			// java controller에서 내려온 값을 받는 변수
			var noRcv = '${noRcv}';			
			
		</script>
	</body>
</html>