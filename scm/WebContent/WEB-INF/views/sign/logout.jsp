<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="com.pnt.context.config.Configurator"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="Cache-Control" content="no-cache"/> 
		<meta http-equiv="Expires" content="0"/> 
		<meta http-equiv="Pragma" content="no-cache"/>
		<title><spring:message code="page.common.title"></spring:message></title>
	</head>
	<body>
	    <script>
		    function selfClose() {
				var browser = navigator.appVersion;
				if (browser.indexOf("MSIE") > -1 || browser.indexOf('rv:11') > -1) {
					window.open("blank.html", "_self").close();
				}
				else {
					self.close();
				}
			}

// 	    	selfClose();
	        parent.top.location.replace("${pageContext.request.contextPath}");
	    </script>		
	</body>
</html>
