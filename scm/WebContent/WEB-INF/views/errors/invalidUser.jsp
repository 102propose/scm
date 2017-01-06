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
                        <td class="pt30 at adTxt_03 pl30">허가되지 않은 사용자</td>
                    </tr>
                    <tr>
                        <td height="40"></td>
                    </tr>
                    <tr>
                        <td class="pl40">
                            <table>
                                <tr>
				    <td width="10"></td>
                                    <td><img src="${pageContext.request.contextPath}/images/pre/bi_img6.gif" width="53" height="115" alt="" border="0"></td>
                                    <td class="txt20 pl20">
                                        <strong>아이디 또는 비밀번호가 잘못 되었습니다.<br> 아이디 또는 비밀번호를 정확히 입력 하셨는지 확인 해주세요 <br>로그인이 안될경우 RayCop SCM 관리자에게 문의 하세요.</strong>
                                    </td>
                                </tr>
                            </table></td>
                    </tr>
                    <tr>
                        <td  height="40" align="right">
                       		<img src="${pageContext.request.contextPath}/images/pre/btn_ok2.gif" width="63" height="23" alt="" border="0" style="cursor: hand" onclick="doReturn();">
                        </td>
                    </tr>
                </table>

            </td>
        </tr>
    </table>
	</td></tr></table>
</div>

<script>
     function doReturn() {
    	 top.location.replace('${pageContext.request.contextPath}');
     }
</script>
</body>
</html>
