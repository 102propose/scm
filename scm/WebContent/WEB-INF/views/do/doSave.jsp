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

		<link href="${domain}${pageContext.request.contextPath}/resources/dhtmlx/4.1.3/codebase/dhtmlx.css" rel="stylesheet">
		<link href="${domain}${pageContext.request.contextPath}/css/common.css" rel="stylesheet">
		<style type="text/css">
			* {margin:0; padding:0; /* font:normal 12px 'Dotum'; */}
			html, body {height: 100%; overflow: hidden; position: static;}
			.container {width:100%; height:100%;}
			.buttonForm {width:100%; height:20%;}
			.contents {width:100%; height:50px;}
			
			/* div.gridbox.gridbox_dhx_skyblue table.row20px.obj tr.rowselected td, div.gridbox.gridbox_dhx_skyblue table.row20px.obj tr.rowselected.odd_dhx_skyblue td {background-color : initial !important;} */
			
			div.gridbox table.obj tr.rowselected,
    		div.gridbox table.obj tr.rowselected td,
    		div.gridbox table.obj tr.rowselected td.cellselected,
    		div.gridbox table.obj td.cellselected,
			{
	 			background-color: #000000;
			}
		</style>
		
		<script src="${pageContext.request.contextPath}/resources/jquery/jquery-1.11.1.min.js"></script>
	    <script src="${pageContext.request.contextPath}/resources/jquery/jquery.json-2.4.min.js"></script>
	    
	    <script src="${pageContext.request.contextPath}/resources/dhtmlx/4.1.3/codebase/dhtmlx.js"></script>
	    
	    <script src="${pageContext.request.contextPath}/js/PNT.js?${timestamp}"></script>
	    <script src="${pageContext.request.contextPath}/js/doSave.js?${timestamp}"></script>
	</head>
	<body>
		<div class="container">
			<div style="margin: 15px 0 5px 10px; font-weight: bold;">발주수량</div>
			<div id="contents_po" class="contents"></div>
			<div id="buttonForm" class="buttonForm"></div>
			<div id="contents" class="contents"></div>
			<div style="position: absolute; top: 105px; margin: 15px 0 5px 10px; font-weight: bold;">발주잔량</div>
		</div>
	    
		<jsp:include page="/WEB-INF/views/include/common.jsp"></jsp:include>
		<script>
			PNT.pages.doe.save.params = {};
			PNT.pages.doe.save.params.date = '${param.date}';
			PNT.pages.doe.save.params.currentDate = '${currentDate}';
			PNT.pages.doe.save.params.maxDate = '${maxDate}';
			PNT.pages.doe.save.params.pcd = '${param.pcd}';
			PNT.pages.doe.save.params.icd = '${param.icd}';
			PNT.pages.doe.save.params.qty = '${param.qty}';
			PNT.pages.doe.save.params.po = '${param.po}';
		</script>
	</body>
</html>