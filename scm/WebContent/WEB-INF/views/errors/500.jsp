<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isErrorPage="true"%>
<%@ page import="com.pnt.context.config.Configurator"%>
<%@ page import="org.apache.log4j.Logger"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%
    response.setHeader("Cache-Control", "no-cache");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);
    
    Logger logger = Logger.getLogger(this.getClass());
    logger.error("", exception);
%>

<c:set var="domain" value="<%=Configurator.getInstance().getFullDomain(request)%>" />

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
<table  width="565"><tr><td>
	<table  width="563" height="287">
		<tr>
			<td background="${pageContext.request.contextPath}/images/pre/bi_img3.gif" width="563" height="287" alt="" border="0" class="ac at">
				<table width="500">
					<tr>
						<td class="pt30 at adTxt_03 pl30"><spring:message code="page.500.m1"></spring:message> </td>
					</tr>
					<tr>
						<td height="30"></td>
					</tr>
					<tr>
						<td class="pl40">
							<table width="500"><col width="140"><col width="">
								<tr>
									<td rowspan="2"><img src="${pageContext.request.contextPath}/images/pre/bi_img5.gif" width="102" height="124" alt="" border="0" style="margin:0 0 0 10"></td>
									<td class="txt20">
									<spring:message code="page.500.m1"></spring:message> <br>
									<spring:message code="page.common.contact.admin"></spring:message>
									</td>
								</tr>
							</table></td>
					</tr>
					<tr>
						<td  height="40" align="right">
<!--							<img src="${pageContext.request.contextPath}/images/pre/btn_ok2.gif" align="absmiddle" style="cursor: hand" onclick="location.href='../sign/page.do'">-->
						</td>
					</tr>
				</table>

			</td>
		</tr>
	</table>

</td></tr></table>
</div>
</body>
</html>
