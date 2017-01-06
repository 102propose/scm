<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="com.pnt.common.util.CoreUtils" %>
<%@ page import="com.pnt.context.config.Configurator" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%
    response.setHeader("Cache-Control", "no-cache");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);
    
    String lfm = CoreUtils.ifNull(request.getParameter("lfm"));
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
	    <link rel="stylesheet" type="text/css" href="${domain}${pageContext.request.contextPath}/css/login.css?${timestamp}" /> 
	    
	    <script src="${pageContext.request.contextPath}/resources/jquery/jquery-1.11.1.min.js"></script>
	    <script src="${pageContext.request.contextPath}/resources/jquery/jquery.json-2.4.min.js"></script>
	    
	    <script src="${pageContext.request.contextPath}/js/PNT.js?${timestamp}"></script>
	</head>
	<body>
	   <form name="f">
	   
	   <table width="100%" height="100%">
		<tr><td class="aT aC">
		<div align="center">
			<table width="1210" border='0'>
				
				<tr>
					<td><img src="${pageContext.request.contextPath}/images/logo/pdfLogo.png" alt="Logo" style="height:45px"></td>
				</tr>				
				<tr>
					<!-- <td background="${pageContext.request.contextPath}/images/login/login_bg1.jpg" width="1110" height="546" class="aT">  -->
					<td background="${pageContext.request.contextPath}/images/login/login_raycop.png" width="1210" height="780" class="aT">
					<table width="1110">
					<tr><col width="710"><col width="">
						<td></td>
						<td style="padding-top:150px">						
						
						<!---로그인 입력 시작 -------------->
						<table>
							<tr><col width="55"><col width="190"><col width="">
								<td><img src="${pageContext.request.contextPath}/images/login/txt_id.png"  alt="사용자"></td>
								<td height="39" class="aT"><input id="userid" name="userid"  type="Text" class="input" style="width:183px;" tabindex="1" value=""></td>
								<td rowspan="2"><a href="#" onclick="login();"><img src="${pageContext.request.contextPath}/images/login/btn_login.png"  alt="Login" tabindex="3"></a></td>
							</tr>
							<tr>
								<td><img src="${pageContext.request.contextPath}/images/login/txt_pw.png"  alt="비밀번호"></td>
								<td><input id="userpwd" name="userpwd" type="password" class="input" style="width:183px;" tabindex="2" value=""></td>
							</tr>
							<tr>
								<td colspan="3" height="50"><input id="chk_saveid" name="chk_saveid" type="checkbox" value="" style="margin-left:55px"> <img src="${pageContext.request.contextPath}/images/login/txt_idSave.png" alt="아이디저장"></td>
							</tr>
							<tr>
								<td colspan="3">
									<div id="login-message" class="login-message"></div>
								</td>
							</tr>
						</table>				
						<!---로그인 입력 끝 -------------->				
				
				
						</td>
					</tr>
					</table>			
					</td>
				</tr>
			</table>
		</div>
		</td></tr></table>
		
		
		</form>
		
		<script type="text/javascript">
			$(window).load(function() {
				var lfm = '<%= lfm%>';

				if (lfm != "") {
					$('#login-message').css('display','block').html('인증에 실패 하였습니다.');
				}
				
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

				$('#login-message').css('display','block').html('로그인 중 입니다.');
				
				saveit(document.f);
				
				document.f.method = 'post';
				document.f.action = '${pageContext.request.contextPath}/sign/in.do',
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
				if (form.chk_saveid.checked) {
					expdate.setTime(expdate.getTime() + 1000 * 3600 * 24 * 365); // 365일
				} else {
					expdate.setTime(expdate.getTime() - 1); // 쿠키 삭제조건
				}
				setCookie("saveid",$('#userid').val(), expdate);
				setCookie("savecheckid",$("input:checkbox[id='chk_saveid']").is(":checked") + '', expdate);
			}
			function getid() {
				if (getCookie("savecheckid") == 'true') {
					$('#userid').val(getCookie("saveid"));
					$("input:checkbox[id='chk_saveid']").attr("checked", true);
				}
			}
		</script>
	</body>
</html>