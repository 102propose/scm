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
		<%-- <link href="${pageContext.request.contextPath}/resources/bootstrap/calendar/datepicker3.css" rel="stylesheet"> --%>
		<%-- <link href="${domain}${pageContext.request.contextPath}/resources/dhtmlx/4.0.3/grid/dhtmlxgrid.css" rel="stylesheet">
		<link href="${domain}${pageContext.request.contextPath}/resources/dhtmlx/4.1.3/windows/skin/dhtmlxwindows_dhx_web.css" rel="stylesheet"> --%>
		<link href="${domain}${pageContext.request.contextPath}/resources/dhtmlx/4.1.3/codebase/dhtmlx.css" rel="stylesheet">
		<link href="${domain}${pageContext.request.contextPath}/css/common.css" rel="stylesheet">
		<style type="text/css">
			* {margin:0; padding:0; /* font:normal 12px 'Dotum'; */}
			html, body {height: 100%; overflow: hidden; position: static; }
			.container {width:98%; margin:0 0 10px 10px;}
			.container .title {width:100%;}
			.container .condition {width:100%; margin:5px 0 5px 0;;}
			.container .condition input[type="text"] {height:25px; width:100px; text-align:center;}
			
			.dhx-grid-even {background-color:#fff;}
			/* .dhx-grid-even td {border-bottom : 1px dotted #ededed !important;} */
			.dhx-grid-uneven {background-color:#fff;}
			.dhx-grid-uneven td {border-bottom : 1px solid #ededed !important;}
			
			div.gridbox.gridbox_dhx_skyblue table.row20px.obj tr.rowselected td, div.gridbox.gridbox_dhx_skyblue table.row20px.obj tr.rowselected.odd_dhx_skyblue td {border-top : 1px solid #b5deff !important;}
			div.gridbox.gridbox_dhx_skyblue table.obj tr.rowselected td, div.gridbox.gridbox_dhx_skyblue table.obj tr.rowselected.odd_dhx_skyblue td {border-bottom : 1px solid #b5deff !important;}
		</style>
		
		<script src="${pageContext.request.contextPath}/resources/jquery/jquery-1.11.1.min.js"></script>
	    <script src="${pageContext.request.contextPath}/resources/jquery/jquery.json-2.4.min.js"></script>
	    <%-- <script src="${pageContext.request.contextPath}/resources/dhtmlx/4.0.3/commons/dhtmlxcommon.js"></script>
	    <script src="${pageContext.request.contextPath}/resources/dhtmlx/4.0.3/grid/dhtmlxgrid.js"></script>
	    <script src="${pageContext.request.contextPath}/resources/dhtmlx/4.1.3/commons/dhtmlxcontainer.js"></script>
	    <script src="${pageContext.request.contextPath}/resources/dhtmlx/4.1.3/windows/dhtmlxwindows.js"></script> --%>
	    <script src="${pageContext.request.contextPath}/resources/dhtmlx/4.1.3/codebase/dhtmlx.js"></script>
	    
	    <script src="${pageContext.request.contextPath}/js/PNT.js?${timestamp}"></script>
	    <script src="${pageContext.request.contextPath}/js/pu.js?${timestamp}"></script>
	</head>
	<body>
	
		<div class="container">
			<div class="title"><img src="${pageContext.request.contextPath}/images/pre/images/admin_ico01.jpg" align="absmiddle"> <font class="adTxt_01">납품등록관리</font></div>
			<div class="condition">
				납기일자 
				<input  id="cond_date_fr" type="text" /> ~ <input  id="cond_date_to" type="text" />&nbsp;
				납품등록일 
				<input  id="cond_date" type="text"  />&nbsp;
				<c:if test="${SESSION_SIGNED_USER.id == 'admin'}">
				협력업체명
				<select id="cond_partner" style="width:200px;"></select>&nbsp;&nbsp;
				</c:if>
				<button type="button" class="btn btn-primary btn-sm" onclick="doSearch();">
					<span class="glyphicon glyphicon-search" aria-hidden="true"></span> 조회
				</button>
				<button type="button" class="btn btn-primary btn-sm" onclick="doSave();">
					<span class="glyphicon glyphicon-download-alt" aria-hidden="true"></span> 납품등록
				</button>
				&nbsp;&nbsp;&nbsp;&nbsp;
				납품등록내역
				<select id="cond_no_rcv_his" style="width:200px;"></select>&nbsp;&nbsp;
				<button type="button" class="btn btn-primary btn-sm" onclick="doHistory();">
					<span class="glyphicon glyphicon-search" aria-hidden="true"></span> 조회
				</button>
			</div>
			<div id="grid_box" style="width:100%; height: 80%"></div>
		</div>
	    
		<jsp:include page="/WEB-INF/views/include/common.jsp"></jsp:include>
		
	</body>
</html>