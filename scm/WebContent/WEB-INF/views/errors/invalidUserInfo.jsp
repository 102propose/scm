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

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><spring:message code="page.common.title"></spring:message></title>
    <link rel="STYLESHEET" type="text/css" href="${domain}${pageContext.request.contextPath}/css/common.css?${timestamp}">
</head>

<body>
<center>

<table  width="1280"><tr><td class="ac" style="padding:100 0 0 0">
    <table  width="563" height="287">
        <tr>
            <td background="${pageContext.request.contextPath}/images/pre/bi_img3.gif" width="563" height="287" alt="" border="0" class="ac at">
                <table width="500">
                    <tr>
                        <td class="pt40 at adTxt_03"><spring:message code="page.error.invalid.user"></spring:message></td>
                    </tr>
                    <tr>
                        <td height="40"></td>
                    </tr>
                    <tr>
                        <td class="ac">
                            <table>
                                <tr>
				    <td width="10"></td>
                                    <td><img src="${pageContext.request.contextPath}/images/pre/bi_img6.gif" width="53" height="115" alt="" border="0"></td>
                                    <td class="txt20 pl20">
                                    	<ul>
                                    		<li><strong><spring:message code="page.invalidUserInfo.m1"></spring:message></strong></li>
                                    		<li><spring:message code="page.invalidUserInfo.m2"></spring:message></li>
                                    		<li><strong><spring:message code="page.invalidUserInfo.m3"></spring:message></strong></li>
                                    	</ul>
<%--                                         <strong><spring:message code="page.invalidUserInfo.m1"></spring:message><br> <spring:message code="page.invalidUserInfo.m2"></spring:message><br> <spring:message code="page.invalidUserInfo.m3"></spring:message></strong> --%>
                                    </td>
                                </tr>
                            </table></td>
                    </tr>
                    <tr>
                        <td  height="40" class="ar"><img src="${pageContext.request.contextPath}/images/pre/btn_ok2.gif" width="63" height="23" alt="" border="0" style="cursor: hand" onclick="doReturn();"></td>
                    </tr>
                </table>

            </td>
        </tr>
    </table>

</td></tr></table>

</center>

<script>
     function doReturn() {
    	 location.replace('${pageContext.request.contextPath}/sign/page.do');
     }
</script>
</body>
</html>
