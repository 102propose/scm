PNT.namespace('PNT.pages.doe');

//////////////////////////////////////////////////////////////////////////////////////////////////
//////page functions
//////////////////////////////////////////////////////////////////////////////////////////////////
function initilizeGrid() {
	if (dxGrid) {
		dxGrid.destructor();
		dxGrid = '';
	}
	
	var headerText = "";
	var d = new Date($('#condDate').val());
	
	var yy, mm, dd, date;
	for (var x0 = 0; x0 < 7; x0++) {
		date = d.addDays(x0);
		yy = date.getFullYear();
		yy = (yy + '').substring(2,4);
		mm = date.getMonth() + 1;
		mm = (mm + '').length == 1 ? '0' + mm : mm;
		dd = date.getDate();
		dd = (dd + '').length == 1 ? '0' + dd : dd;
		
		headerText += yy + '.' + mm + '.' + dd + ', ';
	}
	
	dxGrid = new dhtmlXGridObject('grid_box');
	dxGrid.setImagePath(PNT.Constants.context + '/resources/dhtmlx/4.0.3/grid/imgs/dhxgrid_skyblue/');
	
	var header1 = "품목코드, 품목명, 입력, 발주수량, " + headerText + " 소용수량합계";
	var header2 = ['단위', '규격', '#rspan', '발주잔량', '#rspan', '#rspan', '#rspan', '#rspan', '#rspan', '#rspan', '#rspan', 'DO계획합계'];
	var headerAlign = [];
	for (var i = 0; i < 12; i++) {
		headerAlign.push("text-align:center; vertical-align:middle;");
	}
	dxGrid.setHeader(header1, null, headerAlign);
	dxGrid.attachHeader(header2,headerAlign);
	dxGrid.setInitWidths("85,*,50,100,100,100,100,100,100,100,100,100");
	dxGrid.setColAlign("left, left, center, right, right, right, right, right, right, right, right, right");
	dxGrid.setColTypes("ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro");
	dxGrid.enableMultiselect(true);
	
	/* custom method extends */
    dxGrid.setUserData = PNT.third.dhx.grid.setUserData;
    dxGrid.getUserData = PNT.third.dhx.grid.getUserData;
    
	dxGrid.init();
	
	dxGrid.enableAlterCss("dhx-grid-even","dhx-grid-uneven");
	
	dxGrid.attachEvent("onRowSelect", function(_rid, _cind){
		var rind = dxGrid.getRowIndex(_rid);
		
		if ((parseInt(rind % 2)) == 0) {
			dxGrid.selectRow(rind + 1, false, true, true);
		} else {
			dxGrid.selectRow(rind - 1, false, true, true);
		}
	});
	dxGrid.attachEvent("onRowDblClicked", function(_rid, _cind){
		viewDoInsertPage(_rid);
//		var rind = dxGrid.getRowIndex(_rid);
//		
//		if ((parseInt(rind % 2)) == 0) {
//		} else {
//			var pcd = dxGrid.getUserData(_rid, 'partnerCode');
//			var icd = dxGrid.getUserData(_rid, 'itemCode');
//			
//			var qt = [];
//			for (var x0 = 3; x0 <= 9; x0++ ) {
//				qt.push(dxGrid.cells(_rid, x0).getValue().replace(/,/g,''));
//			}
//			
//			initilizeModal({pcd:pcd, icd: icd, qty:qt.join('|')});
//		}
	});
}

function viewDoInsertPage(_rid) {
	var rind = dxGrid.getRowIndex(_rid + '');
	
	if ((parseInt(rind % 2)) == 0) {
	} else {
		var pcd = dxGrid.getUserData(_rid, 'partnerCode');
		var icd = dxGrid.getUserData(_rid, 'itemCode');
		
		var qt = [];
		for (var x0 = 3; x0 <= 10; x0++ ) {
			qt.push(dxGrid.cells(_rid, x0).getValue().replace(/,/g,''));
		}
		
		var po = [];
		var poRowID=dxGrid.getRowId(rind - 1);
		for (var x0 = 3; x0 <= 10; x0++ ) {
			po.push(dxGrid.cells(poRowID, x0).getValue().replace(/,/g,''));
		}
		
		initilizeModal({pcd:pcd, icd: icd, qty:qt.join('|'), po:po.join('|')});
	}
}

function initilizeModal(_params) {
	var params = _params ? _params : {};
	params.date = $('#condDate').val();
	
	dxWins = new dhtmlXWindows();
//	w1 = dxWins.createWindow("w1", 400, 200, 820, 230);
	w1 = dxWins.createWindow("w1", 400, 200, 820, 250);
	w1.setText("D/O 입력");
	w1.attachURL(PNT.Constants.context + "/do/modal/save.do", null, params);
	w1.center();
	w1.setModal(true);
	w1.denyResize();
	
	var pos = w1.getPosition();
	w1.setPosition(pos[0], pos[1] - 100);
}

function doSearch() {
	
	initilizeGrid();
	
	var param = {
		'condDate' : $('#condDate').val(),
		'poPartner' : $('#cond_po_partner').val()
	};
//	alert($.toJSON(param));
	$.ajax({
		type: 'post',
		url : PNT.Constants.context + "/do/select/list.do",
		data : param,
		beforeSend : function(_xhr, _settings) {
			gProgressBar.show();
		},
		complete : function(_xhr, _status) {
			gProgressBar.hide();
		},
		success : function(_response) {
			try {
//				alert(_response); 
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
}

function doSearchDoPartners() {
	var param = {
		'dtFr' : $('#condDate').val()
	};
//	alert($.toJSON(param));
	$.ajax({
		async: false,
		type: 'post',
		url : PNT.Constants.context + "/do/select/partners.do",
		data : param,
		beforeSend : function(_xhr, _settings) {
			gProgressBar.show();
		},
		complete : function(_xhr, _status) {
			gProgressBar.hide();
		},
		success : function(_response) {
//			alert(_response); 
			var partners = $.parseJSON(_response);
			
			$('#cond_po_partner').empty().append('<option value="">--선택--</option>');
			$.each(partners, function(_i, _d) {
				$('#cond_po_partner').append('<option value="' + _d.CD_PARTNER + '">' + _d.LN_PARTNER + '</option>');
			});
		},
		error : new PNT.third.$.ajax.interceptor.Error().error
	});
}

function setConditionDate(_cond) {
	var to;
	var yyyy, mm, dd;
	var date = new Date();
	
	switch (_cond) {
	default:
		yyyy = date.getFullYear();
		mm = date.getMonth() + 1;
		mm = (mm + '').length == 1 ? '0' + mm : mm;
		dd = date.getDate();
		dd = (dd + '').length == 1 ? '0' + dd : dd;
		
		to = yyyy + '-';
		to += mm + '-';
		to += dd;
	}
	
	$("#condDate").val(to);
}

var dxGrid, dxCalendar, dxWins, w1;
var gProgressBar;
$(document).ready(function() {
	gProgressBar = new PNT.libs.Progressbar();

	dxCalendar = new dhtmlXCalendarObject("condDate");
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
		
	dxCalendar.attachEvent("onClick", function(date){
	   doSearchDoPartners();
	});
	
	setConditionDate();
	doSearchDoPartners();
	
	initilizeGrid();
}); 
