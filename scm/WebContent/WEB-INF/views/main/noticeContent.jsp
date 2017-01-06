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
			* {margin:0; padding:0;}
			html, body {height: 100%; overflow-y: auto; overflow-x: hidden; }
			.container {width:98%; margin:10px 0 10px 10px;}
			.container table {width:100%;}
			.container table td:first-child {width: 150px; height: 30px; text-align: center; padding: 10px; font-weight: bold; background-color: #EBF3FF;}
			.container table td {padding: 10px;}
			.container table td.content {height: 330px; vertical-align: top;}
		</style>
		
		<script src="${pageContext.request.contextPath}/resources/jquery/jquery-1.11.1.min.js"></script>
	    <script src="${pageContext.request.contextPath}/resources/jquery/jquery.json-2.4.min.js"></script>
	    
	    <script src="${pageContext.request.contextPath}/resources/dhtmlx/4.1.3/codebase/dhtmlx.js"></script>
	    
	    <script src="${pageContext.request.contextPath}/js/PNT.js?${timestamp}"></script>
	</head>
	<body>
	
		<div class="container">
			<table border="1" cellpadding="10" cellspacing="10">
				<tr>
					<td colspan="2" style="text-align: middle">공지사항</td>
				</tr>
				<tr>
					<td>거래처</td>
					<td>${param.CD_PARTNER}&nbsp;&nbsp;&nbsp;&nbsp;${param.LN_PARTNER}</td>
				</tr>
				<tr>
					<td>공지기간</td>
					<td><div id="notice_term"></div></td>
				</tr>
				<tr>
					<td>제목</td>
					<td>${param.TITLE_MSG}</td>
				</tr>
				<tr>
					<td>내용</td>
					<td class="content">${param.TXT_MSG}</td>
				</tr>
				<tr>
					<td>작성자</td>
					<td>${param.ID_INSERT}</td>
				</tr>
				<tr>
					<td>작성일자</td>
					<td><div id="dt_insert"></div></td>
				</tr>
			</table>
			
		</div>
	    
		<jsp:include page="/WEB-INF/views/include/common.jsp"></jsp:include>
		
		<script>
			function formatDate(_date) {
				if (_date.length != 8) {
					alert('날짜 자릿수가 맞지 않습니다.');
					return '';
				} else {
					var yyyy = _date.substring(0,4);
					var mm = _date.substring(4,6);
					var dd = _date.substring(6,8);
					
					return yyyy + '년 ' + mm + '월 ' + dd + '일';
				}
			}
			
			var noticeTerm = formatDate('${param.DT_MSG_START}') + ' ~ ' + formatDate('${param.DT_MSG_END}');
			$('#notice_term').html(noticeTerm);
			
			var dateInsert = formatDate('${param.DT_INSERT}');
			$('#dt_insert').html(dateInsert);
		</script>
	</body>
</html>