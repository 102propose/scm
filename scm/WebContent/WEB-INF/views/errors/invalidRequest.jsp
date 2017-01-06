<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="com.pnt.context.config.Configurator"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%
    response.setHeader("Cache-Control", "no-cache");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);
%>

<c:set var="domain" value="<%=Configurator.getInstance().getFullDomain(request)%>" />
<c:set var="timestamp" value="<%=System.nanoTime()%>" />

<!DOCTYPE html>
<html>
<head>
    <title><spring:message code="page.common.title"></spring:message></title>
    
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="Cache-Control" content="no-cache" />
	<meta http-equiv="Expires" content="0" />
	<meta http-equiv="Pragma" content="no-cache" />
	
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/favicon.ico" type="image/x-icon">
	<link rel="icon" href="${pageContext.request.contextPath}/favicon.ico" type="image/x-icon">
    <link rel="stylesheet" type="text/css" href="${domain}${pageContext.request.contextPath}/css/common.css">
</head>
<body>
<div style="margin:0 auto; width:565px; padding-top:15%;">
	<table width="565"><tr><td>
    <table  width="563" height="287">
        <tr>
            <td background="${pageContext.request.contextPath}/images/pre/bi_img3.gif" width="563" height="287" alt="" border="0" class="ac at">
                <table width="500">
                    <tr>
                        <td class="pt30 at adTxt_03 pl30"><spring:message code="page.invalid.request.m1"></spring:message></td>
                    </tr>
                    <tr>
                        <td height="40"></td>
                    </tr>
                    <tr>
                        <td class="pl40">
                            <table>
                                <tr>
                                    <td><img src="${pageContext.request.contextPath}/images/pre/bi_img5.gif" width="102" height="124" alt="" border="0"></td>
                                    <td class="txt20 pl20">
                                        <spring:message code="page.invalid.request.m2"></spring:message>
                                    </td>
                                </tr>
                            </table></td>
                    </tr>
                    <tr>
                        <td  height="40" align="right">
<!--                        	<img src="${pageContext.request.contextPath}/images/pre/btn_ok2.gif" width="63" height="23" alt="" border="0" style="cursor: hand" onclick="doClose();">-->
                        </td>
                    </tr>
                </table>

            </td>
        </tr>
    </table>

</td></tr></table>
</div>
<script>
     function doClose() {
    	 top.location.replace('${pageContext.request.contextPath}');
     }
</script>
</body>
</html>
