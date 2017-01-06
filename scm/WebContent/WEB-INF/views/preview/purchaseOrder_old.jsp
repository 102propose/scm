<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="com.pnt.context.config.Configurator"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%
    response.setHeader("Cache-Control", "no-cache");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);
    
    String po_no = request.getParameter("po_no");
    String df = request.getParameter("df");
    String dt = request.getParameter("dt");
%>

<c:set var="domain" value="<%=Configurator.getInstance().getFullDomain(request) %>" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html kor>
<head>
  <meta content="text/html; charset=UTF-8"  http-equiv="content-type">
  <meta name="viewport" content="width=device-width, initial-scale=1"/>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <meta http-equiv="X-UA-Compatible" content="IE=edge"/>

  <script src="${pageContext.request.contextPath}/resources/jquery/jquery-1.11.1.min.js"></script>
  <script src="${pageContext.request.contextPath}/resources/jquery/jquery.json-2.4.min.js"></script>
  
  <script src="${pageContext.request.contextPath}/resources/dhtmlx/4.0.3/commons/dhtmlxcommon.js"></script>
  <script src="${pageContext.request.contextPath}/resources/dhtmlx/4.0.3/grid/dhtmlxgrid.js"></script>
  
  <script src="${pageContext.request.contextPath}/js/PNT.js?${timestamp}"></script>
  <script src="${pageContext.request.contextPath}/js/purchaseOrder.js?${timestamp}"></script>
  <title></title>
</head>
<body style="width: 1068px;" onload="init()">
<big><big><big><big>
</big></big></big></big>
<table style="text-align: left; width: 1054px; height: 139px;"
 border="0" cellpadding="2" cellspacing="2">
  <tbody>
    <tr>
      <td colspan="1" rowspan="2"  style="width: 469px; height: 73px;"><img style="height: 133px; width: 400px;" src="${pageContext.request.contextPath}/images/logo/preview_logo.jpg" alt="Logo"></td>
      <td style="text-align: right; font-weight: bold; width: 565px; height: 60px; font-family: Helvetica,Arial,sans-serif; color: rgb(139, 150, 169);">
      <big><big><big><big>PURCHASE</big></big></big></big></td>
    </tr>
    <tr style="font-weight: bold; font-family: Helvetica,Arial,sans-serif; color: rgb(139, 150, 169);" align="right">
      <td style="height: 73px;"><big><big><big><big>ORDER</big></big></big></big></td>
    </tr>
  </tbody>
</table>
<big style="color: rgb(153, 153, 255);"><big style="font-weight: bold;"><big><big><span style="font-family: Arial;">RaycopKorea &nbsp;Inc.</span></big></big></big></big><br>
인천광역시 남동구 청능대로 450 (고잔동 104B - 1L)<br>
&nbsp; &nbsp;Tel: +82-32-814-1760, &nbsp;Fax:
&nbsp;+82-32-814-8768<br>
<br>
<table style="border-collapse: collapse; text-align: left; width: 1055px; height: 83px;" border="0" cellpadding="2" cellspacing="2">
  <tbody>
    <tr>
      <td style="height: 27px; width: 196px; font-family: Arial; text-align: left;"><big>Purchase Order NO</big></td>
      <td style="font-family: monospace; height: 27px; width: 380px;"></td>
      <td colspan="1" rowspan="3" style="text-align: center; background-color: rgb(204, 204, 204); width: 24px; font-family: SimSun; height: 34px;"><big>결<br><br>제</big></td>
      <td style="text-align: center; background-color: rgb(204, 204, 204); height: 27px; width: 101px; font-family: SimSun;"><big>작&nbsp;성</big></td>
      <td style="text-align: center; background-color: rgb(204, 204, 204); height: 27px; width: 99px; font-family: SimSun;"><big>검&nbsp;토</big></td>
      <td style="text-align: center; background-color: rgb(204, 204, 204); height: 27px; width: 100px; font-family: SimSun;"><big>검&nbsp;토</big></td>
      <td style="text-align: center; background-color: rgb(204, 204, 204); height: 27px; width: 95px; font-family: SimSun;"><big>승&nbsp;인</big></td>
    </tr>
    <tr>
      <td style="width: 196px; text-align: left; height: 32px; font-family: monospace;"><big>&nbsp;&nbsp; &nbsp;발주일자 : </big></td>
      <td style="font-family: monospace; width: 380px; height: 32px;"></td>
      <td colspan="1" rowspan="2" style="background-color: rgb(239, 239, 239); width: 101px; font-family: SimSun; height: 34px;"></td>
      <td colspan="1" rowspan="2" style="background-color: rgb(239, 239, 239); width: 99px; font-family: SimSun; height: 34px;"></td>
      <td colspan="1" rowspan="2" style="background-color: rgb(239, 239, 239); width: 100px; font-family: SimSun; height: 34px;"></td>
      <td colspan="1" rowspan="2" style="background-color: rgb(239, 239, 239); width: 95px; font-family: SimSun; height: 34px;"></td>
    </tr>
    <tr>
      <td style="width: 196px; text-align: left; height: 34px; font-family: monospace;"><big>&nbsp; &nbsp; 담당자 : </big></td>
      <td style="width: 380px; height: 34px;"></td>
    </tr>
  </tbody>
</table>
<br>
<table style="border-collapse: collapse; text-align: left; width: 1056px; height: 32px;" border="0" cellpadding="2" cellspacing="2">
  <tbody>
    <tr>
      <td style="width: 594px; background-color: rgb(204, 204, 204); font-family: Arial;">To.</td>
      <td style="width: 442px; background-color: rgb(204, 204, 204); font-family: Arial;">Ship&nbsp;To.</td>
    </tr>
  </tbody>
</table>
<table style="border-collapse: collapse; text-align: left; width: 1056px;" border="0" cellpadding="2" cellspacing="2">
  <tbody>
    <tr>
      <td style="width: 90px;">파트너사명 : </td>
      <td style="width: 194px;"></td>
      <td style="width: 132px;"></td>
      <td style="width: 158px;"></td>
      <td style="width: 442px; font-family: Arial;">RAYCOP&nbsp;KOREA&nbsp; INC.</td>
    </tr>
    <tr>
      <td style="width: 90px;"></td>
      <td style="width: 194px;"></td>
      <td style="width: 132px;"></td>
      <td style="width: 158px;"></td>
      <td style="width: 442px; font-family: Arial;">450. Cheongneungdae-ro Namdong-Gu, Incheon, Korea</td>
    </tr>
    <tr>
      <td style="text-align: left; width: 90px;">TEL :</td>
      <td style="width: 194px;"></td>
      <td style="width: 132px;">FAX:</td>
      <td style="width: 158px;"></td>
      <td style="font-family: Arial;">Tel : +82-32-814-1760, &nbsp; Fax : &nbsp;+82-32-814-8768</td>
    </tr>
    <tr>
      <td style="width: 90px;"></td>
      <td style="width: 194px;"></td>
      <td style="width: 132px;"></td>
      <td style="width: 158px;"></td>
      <td style="width: 442px;"></td>
    </tr>
  </tbody>
</table>
<br>
<table style="border-collapse: collapse; text-align: left; width: 1055px; height: 32px;" border="0" cellpadding="2" cellspacing="2">
  <tbody>
    <tr>
      <td style="width: 141px; text-align: center; background-color: rgb(204, 204, 204); font-family: MS Gothic;">지급조건</td>
      <td style="text-align: center; background-color: rgb(204, 204, 204); font-family: MS Gothic; width: 211px;">부가세</td>
      <td style="text-align: center; background-color: rgb(204, 204, 204); font-family: MS Gothic; width: 157px;">환  종</td>
      <td style="text-align: center; background-color: rgb(204, 204, 204); font-family: MS Gothic; width: 201px;">입고 창고</td>
      <td style="text-align: center; background-color: rgb(204, 204, 204); font-family: MS Gothic; width: 301px;">적&nbsp; &nbsp;요</td>
    </tr>
  </tbody>
</table>
<table style="border-collapse: collapse; text-align: left; width: 1055px; height: 32px;" border="0" cellpadding="2" cellspacing="2">
  <tbody>
    <tr>
      <td style="width: 142px;"></td>
      <td style="width: 211px;"></td>
      <td style="width: 156px;"></td>
      <td style="width: 202px;"></td>
      <td style="width: 300px;"></td>
    </tr>
  </tbody>
</table>
<br>
<table style="border-collapse: collapse; text-align: left; width: 1056px; height: 60px;" border="1" cellpadding="2" cellspacing="2">
  <tbody>
    <tr>
      <td style="width: 141px; text-align: center; background-color: rgb(204, 204, 204);">품목코드</td>
      <td style="width: 412px; text-align: center; background-color: rgb(204, 204, 204);">품목명</td>
      <td style="width: 157px; text-align: center; background-color: rgb(204, 204, 204);">수&nbsp;량</td>
      <td style="width: 151px; text-align: center; background-color: rgb(204, 204, 204);">단&nbsp;가</td>
      <td style="width: 151px; text-align: center; background-color: rgb(204, 204, 204);">금&nbsp; &nbsp;액</td>
    </tr>
    <tr id='poTd' >
      <td style="width: 141px; text-align: center; background-color: rgb(204, 204, 204);">단&nbsp;위</td>
      <td style="width: 412px; text-align: center; background-color: rgb(204, 204, 204);">규&nbsp;격</td>
      <td style="width: 157px; text-align: center; background-color: rgb(204, 204, 204);">납기일자</td>
      <td style="width: 151px; text-align: center; background-color: rgb(204, 204, 204);">계&nbsp;정</td>
      <td style="width: 151px; text-align: center; background-color: rgb(204, 204, 204);">비&nbsp; &nbsp;고</td>
    </tr>    
  </tbody>
</table>
<div id="dataContainer">
<table style="text-align: left; width: 1056px; height: 60px;" border="0" cellpadding="2" cellspacing="2">
  <tbody>
    <tr>
      <td style="width: 141px; text-align: center; "></td>
      <td style="width: 412px; text-align: center; "></td>
      <td style="width: 157px; text-align: center; "></td>
      <td style="width: 151px; text-align: center; "></td>
      <td style="width: 151px; text-align: center; "></td>
    </tr>
    <tr>
      <td style="width: 141px; text-align: center; "></td>
      <td style="width: 412px; text-align: center; "></td>
      <td style="width: 157px; text-align: center; "></td>
      <td style="width: 151px; text-align: center; "></td>
      <td style="width: 151px; text-align: center; "></td>
    </tr>    
  </tbody>
</table>
</div>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<hr style="width: 100%; height: 2px;">
<table style="text-align: left; width: 1068px; height: 186px;" border="0" cellpadding="2" cellspacing="2">
  <tbody>
    <tr>
      <td style="width: 771px;">* 주1. &nbsp;수입검사는 Sample 검사로 진행되며 부적합 발생 시 귀사에서</td>
      <td style="width: 117px; text-align: right; font-weight: bold;"><big>Subtotal.</big></td>
      <td style="width: 152px; background-color: rgb(204, 204, 204); text-align: right; font-weight: bold;"><big>0.00</big></td>
    </tr>
    <tr>
      <td style="width: 771px;">&nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp;선별하여야 하며 선별 불가시 전체를 반품할 예정입니다.</td>
      <td style="width: 117px; text-align: right; font-weight: bold;"><big>VAT.</big></td>
      <td style="width: 152px; background-color: rgb(204, 204, 204); text-align: right; font-weight: bold;"><big>0.00</big></td>
    </tr>
    <tr>
      <td style="width: 771px;">* 주2. &nbsp;납품된 물품은 당사 수입검사에 합격하셔야 납품이 인정됩니다.</td>
      <td style="width: 117px; text-align: right; font-weight: bold;"><big>Total.</big></td>
      <td style="width: 152px; background-color: rgb(204, 204, 204); text-align: right; font-weight: bold;"><big>0.00</big></td>
    </tr>
    <tr>
      <td style="width: 771px;">* 주3. &nbsp;부득이한 사정으로 귀사에 납기가 지연되는 경우 본 조직의</td>
      <td style="width: 117px;"></td>
      <td style="width: 152px;"></td>
    </tr>
    <tr>
      <td style="width: 771px;">&nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp;구매담당자와 반드시 협의하여야 합니다.</td>
      <td style="width: 117px;"></td>
      <td style="width: 152px;"></td>
    </tr>
    <tr>
      <td style="width: 771px;">* 주4. &nbsp;납품서류에는 본 P/O 번호를 기재하셔야 합니다.</td>
      <td style="width: 117px;"></td>
      <td style="width: 152px;"></td>
    </tr>
    <tr>
      <td style="width: 771px;">* 주5. &nbsp;납품자재에는 반드시 Code No. 가 표기되어야 합니다.</td>
      <td style="width: 117px;"></td>
      <td style="width: 152px;"></td>
    </tr>
  </tbody>
</table>
<br>
<br>
&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
&nbsp; &nbsp;
&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
&nbsp; &nbsp; &nbsp; &nbsp;
&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
&nbsp; &nbsp; &nbsp; &nbsp;
&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
&nbsp; &nbsp; &nbsp; &nbsp;
&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
&nbsp; &nbsp; &nbsp; &nbsp;
&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
&nbsp; &nbsp; &nbsp; &nbsp;
&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
&nbsp; &nbsp; &nbsp; &nbsp;
&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
&nbsp; &nbsp;Authorized Signature Of<br>
&nbsp;
&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
&nbsp; &nbsp; &nbsp;
&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
&nbsp; &nbsp; &nbsp; &nbsp; Confirmed &nbsp;
&nbsp;Sign &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
&nbsp;
&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
&nbsp; &nbsp; &nbsp; &nbsp;
&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
&nbsp; &nbsp; &nbsp; &nbsp; RaycopKorea &nbsp;Inc.<br>
<hr style="width: 100%; height: 2px;"><br>

<input type="hidden" name="po_no" id="po_no" value=<%=po_no %> />
<input type="hidden" name="df" id="df" value=<%=df %> />
<input type="hidden" name="dt" id="dt" value=<%=dt %> />

<jsp:include page="/WEB-INF/views/include/common.jsp"></jsp:include>
</body>
</html>
