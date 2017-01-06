<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="com.pnt.context.config.Configurator"%>
<%@ page import="com.pnt.context.User"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%
    response.setHeader("Cache-Control", "no-cache");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);
%>

<c:set var="domain" value="<%=Configurator.getInstance().getFullDomain(request)%>" />
<c:set var="timestamp" value="<%=System.nanoTime()%>" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title><spring:message code="page.common.title"></spring:message></title>
		
		<meta http-equiv="X-UA-Compatible" content="IE=edge" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="Cache-Control" content="no-cache" />
		<meta http-equiv="Expires" content="0" />
		<meta http-equiv="Pragma" content="no-cache" />
		
		<link rel="shortcut icon" href="${pageContext.request.contextPath}/favicon.ico" type="image/x-icon"/>
		<link rel="icon" href="${pageContext.request.contextPath}/favicon.ico" type="image/x-icon"/>
		<link rel="stylesheet" type="text/css" href="${domain}${pageContext.request.contextPath}/css/basic_1366_x.css?${timestamp}"/>
		<link rel="stylesheet" type="text/css" href="${domain}${pageContext.request.contextPath}/css/PNT.progressbar.css?${timestamp}"/>
		<style type="text/css">
			* {margin:0; padding:0; /* font:normal 12px 'Dotum'; */}
			html, body {height: 100%; overflow: hidden; position: static; }
		</style>
		
		<script src="${pageContext.request.contextPath}/resources/jquery/jquery-1.11.1.min.js"></script>
	    <script src="${pageContext.request.contextPath}/resources/jquery/jquery.json-2.4.min.js"></script>
	    
		<script src="${pageContext.request.contextPath}/js/PNT.js?${timestamp}"></script>
		<script src="${pageContext.request.contextPath}/js/main.js?${timestamp}"></script>
	</head>
	
	<body>
		<div id="GAWrap">
			<div id="header">
				<h1 onclick="goMain();" style="cursor: pointer;">&nbsp;</h1>
				<div id="menuWrapper">
					<div class="GAgnb"></div>
					<div class="gnbList" style="display: none;">
						<div id="subMenuList"></div>
					</div>
				</div>
				<div class="userInfo">
					<span>접속시간 :</span><span id="datetime_content" ></span> <span><font size = "4px"'>${SESSION_SIGNED_USER.name}</font></span>
				</div>
				<div class="topMenu">
					<div class="main-logout-icon"></div><a href="#" onclick="logout(); return false;"><span> 로그아웃</span></a>
				</div>
			</div>
			<div id="container">	
						<iframe id="iLContainer" name="iLContainer" src="${pageContext.request.contextPath}/info/page.do" style="width:50%; height:90%;" scrolling="no" frameborder="-1"></iframe>
						<iframe id="iRContainer" name="iRContainer" src="${pageContext.request.contextPath}/notice/page.do" style="width:49%; height:90%;" scrolling="no" frameborder="-1"></iframe>									
			</div>
		</div>
		
		<jsp:include page="/WEB-INF/views/include/common.jsp"></jsp:include>
		
	</body>
</html>
