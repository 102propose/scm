PNT.namespace('PNT.pages.po');

//////////////////////////////////////////////////////////////////////////////////////////////////
//////page functions
//////////////////////////////////////////////////////////////////////////////////////////////////
var dhxWins, w1, w2, sel;
function openPopup(_uid, _cid, _yyyymm) {
	var param = '';
	
	if (_cid && _uid && _yyyymm) {
		param = '?UMODE&uid=' + _uid;
		param += '&cid=' + _cid;
		param += '&yyyymm=' + _yyyymm;
	}
	
	dhxWins = new dhtmlXWindows();
    dhxWins.enableAutoViewport(true);
    dhxWins.setImagePath(PNT.Constants.context + '/resources/dhtmlx/imgs/');
    w1 = dhxWins.createWindow("w1", 400, 100, 535, 275);
    w1.hideHeader();
    w1.attachURL(PNT.Constants.context + "/comment/save/pop.do" + param);
    w1.center();
    dhxWins.window('w1').setModal(true);
}

//그리드 셀 선택시 발주번호 get
function rowSelectFunc(id, cind) { sel = dxGrid.cells(id, 1).getValue(); } 

function doPreview(){
	
	var poNo = sel;
	
	if(poNo == ''){
		alert('구매주문 내역을 먼저 선택해주세요.');
	} else {
		alert(poNo);		
		var df = $('#cond_date_fr').val();
		var dt = $('#cond_date_to').val();	
		var strFullUrl = PNT.Constants.context + "/preview/purchaseOrder.do?po_no="+poNo+"&df="+df+"&dt="+dt;	
		
		var yn_scroll;
		var popWidth = 200;
		var popHeight = 500;
		var screeWidth = (window.screen.availwidth - popWidth) / 2;
		var screeHeight = (window.screen.availHeight - popHeight) / 2;
		
		if(window.screen.availHeight <= popHeight){
			popHeight = window.screen.availHeight;
			popWidth = 1000;
			yn_scroll = 'yes';
		} else {
			yn_scroll = 'no';
		}
		
		window.open(strFullUrl, "레이캅코리아 PURCHASE ORDER", "top=0, letf="+screenWidth+", width="+popWidth+", height=100%, resizable=yes, fullscreen=no, menubar=no, toolbar=no, location=no, status=no, scrollbars=yes");
	}	
}

function doPdfSave(){
	
	var poNo = sel;
	
	if(poNo == ''){
		alert('구매주문 내역을 먼저 선택해주세요.');
	} else {
//		alert(poNo);
		var df = $('#cond_date_fr').val();
		var dt = $('#cond_date_to').val();		
		var strFullUrl = PNT.Constants.context + "/pdf/savePdf.do?po_no="+poNo+"&df="+df+"&dt="+dt;	
		
		$.ajax({
			type: 'post',
			url : strFullUrl,
			//data : {json: $.toJSON(data)},
			success : function(_response) {	
				var fileNm = _response;
				var callPdf = PNT.Constants.context+fileNm;
				//alert('fileNm == '+callPdf);
				
				window.open(callPdf);
			},
			error : new PNT.third.$.ajax.interceptor.Error().error
		});		
	}	
}

function doExlSave(){ //엑셀파일로 저장
	
//	var nowday = new Date();
//	var now = "" + nowday.getFullYear() + "" + nowday.getMonth() + "" + nowday.getDate() + "" + nowday.getHours() + "" + nowday.getSeconds();
	//var result = Math.floor(Math.random() * 10000) + 1;
		
	var dtFr = $('#cond_date_fr').val();
	var dtTo = $('#cond_date_to').val();
	
	var tempFileName = "발주관리("+dtFr+"~"+dtTo+")조회.xls";
	
	var param = {
			'dtFr' : dtFr,
			'dtTo' : dtTo,
			'poPartner' : $('#cond_po_partner').val(),
//			'filePath' : "Z:\\" + tempFileName
			'filePath' : tempFileName
		};
		$.ajax({
			type: 'post',
			url : PNT.Constants.context + "/excel/po/getExcelList.do",
			data : param,
			beforeSend : function(_xhr, _settings) {
				gProgressBar.show();
			},
			complete : function(_xhr, _status) {
				gProgressBar.hide();
			},
			success : function(_response) {
//				document.location = 'data:Application/octet-stream,' + encodeURIComponent(dataToDownload);
				window.open("../download/" + tempFileName);
//				var gridData = $.parseJSON(_response);
//				dxGrid.clearAll();
//				dxGrid.parse(gridData,"json");
			},
			error : new PNT.third.$.ajax.interceptor.Error().error
		});
		
//		sel = ''; //이전 선택 row 초기화
		
}

function doAdd() {
	openPopup();
}

function doUpdate(_uid, _cid, _yyyymm) {
	openPopup(_uid, _cid, _yyyymm);
}

function doDelete() {
	var checked = dxGrid.getCheckedRows(0);
	checked = checked.indexOf(',') === -1 ? [checked] : checked.split(',');
	
	var data = [];
	for (var i = 0; i < checked.length; i++) {
		if(checked[i] != '') {
			var uid = dxGrid.cells(checked[i], 1).getValue();
			var cid = dxGrid.cells(checked[i], 3).getValue();
			var yyyymm = dxGrid.cells(checked[i], 5).getValue();
			
			data.push({
				uid: uid,
				cid: cid,
				yyyymm: yyyymm
			});
		}
	}
	
	if (data.length == 0) {
		alert('선택하세요');
		return;
	} else {
		if (!confirm('삭제하시겠습니까?')) {
			return;
		}
	}
//	alert($.toJSON(data));
	$.ajax({
		type: 'post',
		url : PNT.Constants.context + "/comment/delete.do",
		data : {json: $.toJSON(data)},
		success : function(_response) {
			//alert(_response);
			doSearch();
		},
	    error : new PNT.third.$.ajax.interceptor.Error().error
	});
}

function doSearch() {
	
	var selPrtn = $('#cond_po_partner').val();
	
//	if(selPrtn === '') {
//		alert('거래처를 선택해야 합니다.');
//		return;
//	}
	
	var param = {
		'dtFr' : $('#cond_date_fr').val(),
		'dtTo' : $('#cond_date_to').val(),
		'poPartner' : $('#cond_po_partner').val()
	};
//		alert($.toJSON(param));
	$.ajax({
		type: 'post',
		url : PNT.Constants.context + "/po/select/getList.do",
		data : param,
		beforeSend : function(_xhr, _settings) {
			gProgressBar.show();
		},
		complete : function(_xhr, _status) {
			gProgressBar.hide();
		},
		success : function(_response) {
			//alert(_response); 
			var gridData = $.parseJSON(_response);
			dxGrid.clearAll();
			dxGrid.parse(gridData,"json");
		},
		error : new PNT.third.$.ajax.interceptor.Error().error
	});
	
	sel = ''; //이전 선택 row 초기화
	
	doSearchPoPartners();
}

function setConditionDate(_cond) {
	var fr, to;
	var yyyy, mm, dd, preDd;
	var date = new Date();
	
	switch (_cond) {
	default:
		yyyy = date.getFullYear();
		mm = date.getMonth() + 1;
		mm = (mm + '').length == 1 ? '0' + mm : mm;
		dd = date.getDate();
		
		if(dd=='1'){
			preDd = 1;	
		} else {
			preDd = date.getDate()-1;
		}
		
		dd = (dd + '').length == 1 ? '0' + dd : dd;
		preDd = (preDd + '').length == 1 ? '0' + preDd : preDd;
		
		fr = yyyy + '-';
		fr += mm + '-';
		fr += preDd;
		//fr = '2015-05-01';
		
		to = yyyy + '-';
		to += mm + '-';
		to += dd;
	}
	
	$("#cond_date_fr").val(fr);
	$("#cond_date_to").val(to);
}

function doSearchPoPartners() {
	var param = {
			'dtFr' : $('#cond_date_fr').val(),
			'dtTo' : $('#cond_date_to').val()
		};
//			alert($.toJSON(param));
		$.ajax({
			async: false,
			type: 'post',
			url : PNT.Constants.context + "/po/select/partners.do",
			data : param,
			success : function(_response) {
//				alert(_response); 
				var partners = $.parseJSON(_response);
				
				$('#cond_po_partner').empty().append('<option value="">--선택--</option>');
				$.each(partners, function(_i, _d) {
					$('#cond_po_partner').append('<option value="' + _d.CD_PARTNER + '">' + _d.LN_PARTNER + '</option>');
				});
			},
			error : new PNT.third.$.ajax.interceptor.Error().error
		});
}
	
var dxGrid, dxCalendar;
var gProgressBar;
$(window).load(function() {
	gProgressBar = new PNT.libs.Progressbar();
	
	dxGrid = new dhtmlXGridObject('grid_box');
	dxGrid.setImagePath(PNT.Constants.context + '/resources/dhtmlx/4.0.3/grid/imgs/dhxgrid_skyblue/');
	//dxGrid.setHeader('&nbsp;,화면,#cspan,코멘트,#cspan,기준년월,코멘트',null,["text-align:center","text-align:center","text-align:center","text-align:center","text-align:center","text-align:center","text-align:center"]);
	dxGrid.setHeader("순번,발주번호,거래처명,발주일자,품목코드,품목명,규격,발주수량,환종,단가,금액,입고수량,발주잔량,발주상태,납기일자", null, ["text-align:left","text-align:center","text-align:center","text-align:center","text-align:center","text-align:center","text-align:center","text-align:center","text-align:center","text-align:center","text-align:center","text-align:center","text-align:center","text-align:center","text-align:center"]);
	dxGrid.setInitWidths("35,110,100,100,100,180,180,80,50,80,100,80,80,80,100");
	dxGrid.setColAlign("center, left, left, center, left, left, left, right, center, right, right, right, right, center, center");
	//dxGrid.setColTypes("ch,ro,ro,ro,ro,ro,ro");
	dxGrid.init();
		
	dxGrid.attachEvent('onRowSelect',rowSelectFunc);	
	
	dxGrid.attachEvent("onRowDblClicked", function(_id, _ind) {
		var uid = dxGrid.cells(_id, 1).getValue();
		var cid = dxGrid.cells(_id, 3).getValue();
		var yyyymm = dxGrid.cells(_id, 5).getValue();
		
//		doUpdate(uid, cid, yyyymm);
	});
	
	dxCalendar = new dhtmlXCalendarObject(["cond_date_fr","cond_date_to"]);
	dxCalendar.hideTime();
	//------------------------------S 달력 한글화 ----------------------------------
	dhtmlXCalendarObject.prototype.langData["kor"] = {
			//dateformat: "%d.%m.%Y",
			dateformat: "%Y-%m-%d",
			monthesFNames: ["1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"],
			monthesSNames: [
			                "1월", "2월", "3월", "4월", "5월", "6월",
			                "7월", "8월", "9월", "10월", "11월", "12월"
			            ],
			            daysFNames: [
			                "일요일", "월요일", "화요일", "수요일",
			                "목요일", "금요일", "토요일"
			            ],
			            daysSNames: ["일", "월", "화", "수", "목", "금", "토"],
			            weekstart: 1,
			            weekname: "w"
	};
	
	dxCalendar.loadUserLanguage("kor");	
	//------------------------------E 달력 한글화 -----------------------------------
	
	setConditionDate();
	
	doSearchPoPartners();
	//doSearch();
}); 
