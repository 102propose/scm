PNT.namespace('PNT.pages.pu.popup');

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
			
	doSearch();
	
}


function doSearch() {
		
	var param = {
			'noRcv': noRcv
	}
	
	$.ajax({
		type: 'post',
		url : PNT.Constants.context + "/pu/select/histroyList.do",
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

function doHistory(){	
	
	var param = {
			'noRcv': noRcv
	}
	
	var strFullUrl =  PNT.Constants.context + "/puHistoryPdf/savePdf.do?";	
		
	$.ajax({
		type: 'post',
		url : strFullUrl,
		data : param,
		success : function(_response) {	
//			alert(_response); 
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
	initilizeGrid();
}); 


