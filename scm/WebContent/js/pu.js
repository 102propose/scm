PNT.namespace('PNT.pages.doe');

//////////////////////////////////////////////////////////////////////////////////////////////////
//////page functions
//////////////////////////////////////////////////////////////////////////////////////////////////
function initilizeGrid() {
	if (dxGrid) {
		dxGrid.destructor();
		dxGrid = '';
	}
	dxGrid = new dhtmlXGridObject('grid_box');
	dxGrid.setImagePath(PNT.Constants.context + '/resources/dhtmlx/4.0.3/grid/imgs/dhxgrid_skyblue/');
	
	var header1 = "발주번호, 품목코드, 품명, 발주일자, 환종, 발주수량, 당일D/O수량, 납품일자, 전달사항";
	var header2 = ['순번', '단위', '규격', '납기일자', '단가', '발주잔량', '납품수량', '금액', '납품장소'];
	var headerAlign = [];
	for (var i = 0; i < 10; i++) {
		headerAlign.push("text-align:center; vertical-align:middle;");
	}
	dxGrid.setHeader(header1, null, headerAlign);
	dxGrid.attachHeader(header2,headerAlign);
	dxGrid.setInitWidths("120,100,*,100,100,100,100,80,150");
	dxGrid.setColAlign("left, left, left, center, right, right, right, right, right");
	dxGrid.setColTypes("ro,ro,ro,ro,ro,ro,ed,ed,ed");
	dxGrid.enableMultiselect(true);
	
	/* custom method extends */
    dxGrid.setUserData = PNT.third.dhx.grid.setUserData;
    dxGrid.getUserData = PNT.third.dhx.grid.getUserData;
    
	dxGrid.init();
	
	dxGrid.enableAlterCss("dhx-grid-even","dhx-grid-uneven");
	
	dxGrid.attachEvent("onRowSelect", function(_rid, _cind){
		$(".selectGrid").removeClass("rowselected");
		var rind = dxGrid.getRowIndex(_rid);
		
		if ((parseInt(rind % 2)) == 0) {
			$(".selectGrid").eq(rind).addClass("rowselected");
			$(".selectGrid").eq(rind+1).addClass("rowselected");
			
		} else {
			$(".selectGrid").eq(rind).addClass("rowselected");
			$(".selectGrid").eq(rind-1).addClass("rowselected");
		}
		
	});
	
	dxGrid.attachEvent("onCellChanged", function(_rid, _cind, _nValue){
		if(searchFlag){
			if(_cind > 5){
				var rind = dxGrid.getRowIndex(_rid);
				gChangedValues['r_' + rind] = _rid;
				
				if ((parseInt(rind % 2)) == 1) {
					if(8 > _cind){
						if (isNaN(_nValue)) {
							//alert('숫자형식이 아닙니다.');
							dxGrid.cells(_rid, _cind).setValue(0);
						}
					}
				}				
			}
		}
	});	
	/*
	dxGrid.attachEvent("onEditCell", function(_stage, _rid, _cind){
		//alert('수정하기 위해 셀을 클릭');
		var rind = dxGrid.getRowIndex(_rid);
						
		if(_stage == 1){ //셀이 수정중인 상태
			
			dxGrid.cells(_rid, _cind).setValue();
			if ((parseInt(rind % 2)) == 0) {
				if(_cind == 6){
					dxGrid.selectRow(rind, false, true, true);
					
				}
			}
			return true;
		}
		
		
		
		return true;
	});*/
	
	dxGrid.attachEvent("onEditCell", function(_stage, _rid, _cind){
		var rind = dxGrid.getRowIndex(_rid);
		
		if(_stage == 1){
			
			this.editor.obj.select(); 			
			
			if ((parseInt(rind % 2)) == 0) {
				if(_cind == 6){
					dxGrid.selectRow(rind, false, true, true);
				}
			}
			return true;
		}
		return true;
	});
}

function doSave() {
	dxGrid.clearSelection();
	//alert($.toJSON(gChangedValues));
	var checker = 0;

	var pcd, icd;
	var gridArry = new Array();
	
	var nopoCheck = '';
	
	$.each(gChangedValues, function(_i, _qty) {
		var idx = _i.replace('r_', '');
		
		
		
		var rowObj = new Object();
		if(icd != dxGrid.getUserData(_qty, 'itemCode')){
		//if(dxGrid.getUserData(0, 'produceNum'))	
						
			//맨처음 납품등록한 또는 수정한 납품등록 발주번호를 체크.
			if(idx == 1){
				nopoCheck = dxGrid.getUserData(_qty, 'produceNum');
			}			
			
			//납품등록 입력시 발주번호가 다른 납품은 동시에 저장하지 않음.
			//if(nopoCheck == dxGrid.getUserData(_qty, 'produceNum')){
				
				pcd = dxGrid.getUserData(_qty, 'produceNum');
				icd = dxGrid.getUserData(_qty, 'itemCode');
				
				rowObj.NO_PO = pcd;
				rowObj.CD_ITEM = icd;
				rowObj.CD_PARTNER = dxGrid.getUserData(_qty, 'partnerCode');
				rowObj.DTFR = $('#cond_date_fr').val();
				rowObj.DTTO = $('#cond_date_to').val();
							
				if ((parseInt(idx % 2)) == 0) { //짝수
					rowObj.SEQ_RCV = dxGrid.cells2(parseInt(idx)+1, 0).getValue(); //순번
					rowObj.CD_UNIT_MM = dxGrid.cells2(parseInt(idx)+1,1).getValue(); //단위
					rowObj.NM_ITEM = dxGrid.cells2(parseInt(idx),2).getValue(); //품목명
					rowObj.STND_ITEM = dxGrid.cells2(parseInt(idx)+1,2).getValue(); //규격
					rowObj.DT_RCV = dxGrid.cells2(parseInt(idx), 7).getValue(); //납품일자
					rowObj.QT_RCV = dxGrid.cells2(parseInt(idx)+1, 6).getValue(); //납품수량
					rowObj.DESC_RCV = dxGrid.cells2(parseInt(idx), 8).getValue(); //전달사항
					rowObj.DST_RCV = dxGrid.cells2(parseInt(idx)+1, 8).getValue(); //납품장소
					//alert("전달장소1 == "+rowObj.DESC_RCV);
					//alert("납품장소1 == "+rowObj.DST_RCV);
				} else { //홀수
					rowObj.SEQ_RCV = dxGrid.cells2(parseInt(idx), 0).getValue(); //순번
					rowObj.CD_UNIT_MM = dxGrid.cells2(parseInt(idx),1).getValue(); //단위
					rowObj.NM_ITEM = dxGrid.cells2(parseInt(idx)-1,2).getValue(); //품목명
					rowObj.STND_ITEM = dxGrid.cells2(parseInt(idx),2).getValue(); //규격
					rowObj.DT_RCV = dxGrid.cells2(parseInt(idx)-1, 7).getValue(); //납품일자
					rowObj.QT_RCV = dxGrid.cells2(parseInt(idx), 6).getValue(); //납품수량
					rowObj.DESC_RCV = dxGrid.cells2(parseInt(idx)-1, 8).getValue(); //전달사항
					rowObj.DST_RCV = dxGrid.cells2(parseInt(idx), 8).getValue(); //납품장소
					//alert("전달장소2 == "+rowObj.DESC_RCV);
					//alert("납품장소2 == "+rowObj.DST_RCV);
				}
				gridArry.push(rowObj);
				checker++;
			//}
		}
	});
	
	//alert(data);
	if (checker === 0) {
		alert('변경 내용이 없습니다.');
		return;
	}
	var gridObj = new Object();
	gridObj.iter = gridArry;
	
	//var data = $.toJSON(gridObj);
	//var data = JSON.stringify( gridObj );
	var data = JSON.stringify( gridArry );
	
//	if (PNT.pages.doe.save.form.validate()) {
	
	$.ajax({
			type: 'post',
			url : PNT.Constants.context + "/pu/insert.do",
			data : "iter="+data,
			beforeSend : function(_xhr, _settings) {
				gProgressBar.show();
			},
			complete : function(_xhr, _status) {
				gProgressBar.hide();
				gChangedValues = {};
			},
			success : function(_response) {				
				//alert('발주번호 : '+ nopoCheck +'의 납품정보가 등록 되었습니다.\n\r\n\r거래명세표를 발행합니다.');
				
				var fileNm = _response;
				var callPdf = PNT.Constants.context+fileNm;
				
				doSearchPuHistory();
				
				window.open(callPdf);
			},
			error : new PNT.third.$.ajax.interceptor.Error().error
		});
	
//	}
}


function doSearchPuPartners() {
	var param = {
		'dtFr' : $('#cond_date_fr').val(),
		'dtTo' : $('#cond_date_to').val()
	};
//			alert($.toJSON(param));
	$.ajax({
		async: false,
		type: 'post',
		url : PNT.Constants.context + "/pu/select/partners.do",
		data : param,
		success : function(_response) {
//				alert(_response); 
			var partners = $.parseJSON(_response);
			
			$('#cond_partner').empty().append('<option value="">--선택--</option>');
			$.each(partners, function(_i, _d) {
				$('#cond_partner').append('<option value="' + _d.CD_PARTNER + '">' + _d.LN_PARTNER + '</option>');
			});
		},
		error : new PNT.third.$.ajax.interceptor.Error().error
	});
}

function doSearchPuHistory(){
	var param = {
			'dt' : $('#cond_date').val(),
			'partner' : $('#cond_partner').val()
	};
	//alert("파트너 선택============== "+$('#cond_partner').val());
	$.ajax({
		async: false,
		type: 'post',
		url : PNT.Constants.context + "/pu/select/puHistory.do",
		data : param,
		success : function(_response) {
//			alert(_response); 
			var puHistory = $.parseJSON(_response);
			$('#cond_no_rcv_his').empty().append('<option value="">--선택--</option>');			
			$.each(puHistory, function(_i, _d) {
				$('#cond_no_rcv_his').append('<option value="' + _d.PA_DT_RCV + '">' + _d.PA_DT_RCV + '</option>');
			});
		},
		error : new PNT.third.$.ajax.interceptor.Error().error
	});
}

function doSearch() {
	
	initilizeGrid();
	
	var param = {
			'dtFr' : $('#cond_date_fr').val(),
			'dtTo' : $('#cond_date_to').val(),
			'dt' : $('#cond_date').val(),
			'partner' : $('#cond_partner').val()
		};
	
	
//	alert($.toJSON(param));
	$.ajax({
		type: 'post',
		url : PNT.Constants.context + "/pu/select/list.do",
		data : param,
		beforeSend : function(_xhr, _settings) {
			gProgressBar.show();
			searchFlag = false;
		},
		complete : function(_xhr, _status) {
			gProgressBar.hide();
			searchFlag = true;
			gChangedValues = {};
		},
		success : function(_response) {
			try {
				
				//alert(_response); 
				var gridData = $.parseJSON(_response);
				dxGrid.clearAll();
				dxGrid.parse(gridData,"json");
				dxGrid.setUserData(gridData);
			}
			catch(e) {
				alert(e);
			}
		},
		error : new PNT.third.$.ajax.interceptor.Error().error
	});
	
	if($('#cond_partner').val() == ""){
		doSearchPuPartners();
	}
	
	if($('#cond_no_rcv_his').val() == ""){
		doSearchPuHistory();
	}
	
}

function setConditionDate(_cond) {
	var fr,to;
	var yyyy, mm, dd, preDd;
	var date = new Date();
	
	switch (_cond) {
	default:
		yyyy = date.getFullYear();
		mm = date.getMonth() + 1;
		mm = (mm + '').length == 1 ? '0' + mm : mm;
		dd = date.getDate();
		dd = (dd + '').length == 1 ? '0' + dd : dd;
		
		if(dd=='1'){
			preDd = 1;	
		} else {
			preDd = date.getDate()-1;
			if(preDd==0){
				preDd = '01';
			}
		}
		
		dd = (dd + '').length == 1 ? '0' + dd : dd;
		preDd = (preDd + '').length == 1 ? '0' + preDd : preDd;
		
		fr = yyyy + '-';
		fr += mm + '-';
		fr += preDd;
		
		to = yyyy + '-';
		to += mm + '-';
		to += dd;
	}
	
	$("#cond_date_fr").val(fr);
	$("#cond_date_to").val(to);
	$("#cond_date").val(to);
}

function doPdfSave(){ //PDF 버튼 클릭시
	
	var strFullUrl =  PNT.Constants.context + "/puPdf/puPdfSave.do?";	
	
	$.ajax({
		type: 'post',
		url : strFullUrl,
		//data : {json: $.toJSON(data)},
		success : function(_response) {	
			var fileNm = _response;
			var callPdf = PNT.Constants.context+fileNm;
						
			window.open(callPdf);
		},
		error : new PNT.third.$.ajax.interceptor.Error().error
	});
	
}



//화면 처음 열릴때 셋팅
var dxGrid, dxCalendar, sel; //dxWins, w1, sel;
var gProgressBar, gChangedValues = {}, rChangeValues = {};
var searchFlag = false;
var gridArray = new Array();
$(document).ready(function() {
	gProgressBar = new PNT.libs.Progressbar();
		
	dxCalendar = new dhtmlXCalendarObject(["cond_date_fr","cond_date_to", "cond_date"]);
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
	doSearchPuPartners();
	doSearchPuHistory();
	initilizeGrid();
//	doSearch();	
	
}); 

function doHistory(){		
	var params = {};
	//alert("no_rcv ===== "+$('#cond_no_rcv_his').val());
	var noRcv = $('#cond_no_rcv_his').val();
	
	params.dtFr = $('#cond_date_fr').val();
	params.dtTo = $('#cond_date_to').val();
	params.noRcv = noRcv;
	
	
	
	
	if(noRcv == "" || noRcv == null)
	{
		alert("※ 선택한 납품등록내역이 없습니다. 선택해 주세요. ※");
	} else {
		var dxWins = new dhtmlXWindows();
		var w1 = dxWins.createWindow("w1", 380, 200, 1200, 500);
		w1.setText("납품등록 내역");
		w1.attachURL(PNT.Constants.context + "/pu/popup.do", null, params);
		w1.center();
		//w1.setModal(true);
		//w1.denyResize();
		
		var pos = w1.getPosition();
		w1.setPosition(pos[0], pos[1]-30);
	}
	
}
