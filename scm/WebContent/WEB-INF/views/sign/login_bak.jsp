<%--
  UI name :  
  Description : 
  Modification Information
 
      수정일          수정자            수정내용
    ---------------   ------------    ---------------------------
     2015.04.18     이주용             최초작성
 
    author   : ERP 시스템즈
    since    : 2015.04.18 
    ver      : 1.0
--%>

<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="com.pnt.context.config.Configurator" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
	    <title><spring:message code="page.common.title"></spring:message></title>
	    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate">
		<meta http-equiv="Pragma" content="no-cache">
		<meta http-equiv="Expires" content="-1">
		
		<link rel="shortcut icon" href="${pageContext.request.contextPath}/favicon.ico" type="image/x-icon">
		<link rel="icon" href="${pageContext.request.contextPath}/favicon.ico" type="image/x-icon">
	    <link rel="stylesheet" type="text/css" href="${domain}${pageContext.request.contextPath}/css/basic_o.css?${timestamp}" /> 
	    
	    <style>
	        .input001 {border-top:1px solid #b3bec6;border-left:1px solid #b3bec6;border-right:1px solid #b3bec6;border-bottom:1px solid #b3bec6;font:12px 돋음;height:20px;vertical-align:middle;background:#FFFFFF;}
	        .login-progress {
	        	background: pink;
	        	position: absolute;
				top: 320px;
				left: 650px;
				width: 345px;
				height: 80px;
			}
	    </style>
	    
	    <script src="${pageContext.request.contextPath}/resources/jquery/jquery-1.11.1.min.js"></script>
	    <script src="${pageContext.request.contextPath}/resources/jquery/jquery.json-2.4.min.js"></script>
	    
	    <script src="${pageContext.request.contextPath}/js/PNT.js?${timestamp}"></script>
		<script src="${pageContext.request.contextPath}/js/i18n.js?${timestamp}"></script>
	</head>
	<body background="${pageContext.request.contextPath}/images/pre/login_bg.jpg">
	   <form name="f">
	   <table width="100%">
		<tr>
		    <td class="ac">
		        <table width="847">
		            <tr>
		                <td background="${pageContext.request.contextPath}/images/pre/login_bg2.jpg" width="847" height="562" class="ac at">
		                
		                <table><tr><td style="padding:330 0 0 255">
		        
		                    <!---로그인 입력 테이블 시작------------> 
		                    <table><col width="165"><col width="">
		                        <tr>
		                            <td><input id="userid" name="userid" type="Text" class="input001" style="width:160px;" value="" tabindex="1"></td>
		                            <td rowspan="3"><a href="#" onclick="login();"><img src="${pageContext.request.contextPath}/images/pre/login_btn01.gif" alt="LOGIN"  tabindex="4"></a></td>
		                        </tr>
		                        <tr>
		                            <td height="4"></td>
		                        </tr>
		                        <tr>
		                            <td><input id="userpwd" name="userpwd" type="password" class="input001" style="width:160px;" value="hwg2014eis" tabindex="2"></td>
		                        </tr>
		                        <tr>
		                            <td height="4"></td>
		                        </tr>
		                        <tr>
		                            <td><input id="bwuser" name="bwuser" type="hidden"></td>
		                            <td rowspan="3"><input id="chk_admin" name="chk_admin" type="checkbox"  tabindex="3">as Admin</td>
		                        </tr>
		                    </table>
		                <!---로그인 입력 테이블 끝------------>  
		                </td></tr></table>
		                
		                </td>
		            </tr>
		        </table>
		
		    </td>
		    
		</tr></table>
		</form>
		
		<!-- <div id="login_progress" class="login-progress">
			<h2>로그인 중입니다.</h2>
		</div> -->
		
		<script type="text/javascript">
			$(window).load(function() {
				PNT.util.window.resize();

				getid();
				
				if ($('#userid').val() == '') {
					$('#userid').focus();
				} else {
					$('#userpwd').focus();
				}
				
				var keyPressHandler = function(_event) {
					if(_event.keyCode === 13) {
						login();
					}
				}; 
				$('#userid').keyup(keyPressHandler);
				$('#userpwd').keyup(keyPressHandler);
				$('#chk_admin').keyup(keyPressHandler);
			});
			
			function login() {
				var userid = $('#userid').val(), userpwd = $('#userpwd').val();
				
				if (userid == '') {
					alert('사용자 아이디를 입력 하세요');
					$('#userid').focus();
					return;
				}
				
				if (userpwd == '') {
					alert('사용자 패스워드를 입력 하세요');
					$('#userpwd').focus();
					return;
				}
				
				saveit(document.f);
				
				document.f.method = 'post';
				document.f.action = '${pageContext.request.contextPath}/sign/progress.do',
				document.f.bwuser.value = $("input:checkbox[id='chk_admin']").is(":checked") + "";
				document.f.submit();
			}
			
			function setCookie(name, value, expires) {
				document.cookie = name + "=" + escape(value) + "; path=/; expires=" + expires.toGMTString();
			}
			function getCookie(name) {
				var search = name + "=";
				if (document.cookie.length > 0) { // 쿠키가 설정되어 있다면
					offset = document.cookie.indexOf(search);
					if (offset != -1) { // 쿠키가 존재하면
						offset += search.length;
						// set index of beginning of value
						end = document.cookie.indexOf(";", offset);
						// 쿠키 값의 마지막 위치 인덱스 번호 설정
						if (end == -1) {
							end = document.cookie.length;
						}
						return unescape(document.cookie.substring(offset, end));
					}
				}
				return "";
			}
			function saveit(form) {
				var expdate = new Date();
				// 기본적으로 365일동안 기억하게 함. 일수를 조절하려면 * 365에서 숫자를 조절하면 됨
				//if (form.checksaveid.checked)
				if (true) {
					expdate.setTime(expdate.getTime() + 1000 * 3600 * 24 * 365); // 365일
				} else {
					expdate.setTime(expdate.getTime() - 1); // 쿠키 삭제조건
				}
				setCookie("saveid",$('#userid').val(), expdate);
				setCookie("savecheckadm",$("input:checkbox[id='chk_admin']").is(":checked") + '', expdate);
			}
			function getid() {
				$('#userid').val(getCookie("saveid"));
				if (getCookie("savecheckadm") == 'true') {
					$("input:checkbox[id='chk_admin']").attr("checked", true);
				}
			}
		</script>
	</body>
</html>