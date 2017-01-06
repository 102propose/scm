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
		<title>입고예정(D/O) 입력</title>

		<link href="${domain}${pageContext.request.contextPath}/resources/dhtmlx/4.1.3/codebase/dhtmlx.css" rel="stylesheet">
		<link href="${domain}${pageContext.request.contextPath}/css/common.css" rel="stylesheet">
		<style type="text/css">
			* {margin:0; padding:0; /* font:normal 12px 'Dotum'; */}
			html, body {height: 100%; overflow: hidden; position: static;}
			.container {width:100%; height:100%;}
			.contents {width:100%; height:100%;}
		</style>
		
		<script src="${pageContext.request.contextPath}/resources/jquery/jquery-1.11.1.min.js"></script>
	    <script src="${pageContext.request.contextPath}/resources/jquery/jquery.json-2.4.min.js"></script>
	    
	    <script src="${pageContext.request.contextPath}/resources/dhtmlx/4.1.3/codebase/dhtmlx.js"></script>
	    
	    <script src="${pageContext.request.contextPath}/js/PNT.js?${timestamp}"></script>
	    <script src="${pageContext.request.contextPath}/js/doSave.js?${timestamp}"></script>
	</head>
	<body>
		<div class="container">
			<div id=contents class="contents"></div>
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
		</script>
	</body>
</html>