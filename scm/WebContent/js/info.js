"use strict";

// ////////////////////////////////////////////////////////////////////////////////////////////////
// ////page functions
// ////////////////////////////////////////////////////////////////////////////////////////////////

var gProgressbar, dxCommonGrid1, dxPartnerGrid2, dxPartnerGrid3;
$(document).ready(function() {
	gProgressbar = new PNT.libs.Progressbar();
	
	dxCommonGrid1 = new dhtmlXGridObject('info_grid_box1');
	dxCommonGrid1.setImagePath(PNT.Constants.context + '/resources/dhtmlx/4.0.3/grid/imgs/dhxgrid_skyblue/');
		
	dxCommonGrid1.setHeader("◆ 발주 정보 ◆,#cspan,#cspan,#cspan");
//	dxCommonGrid1.attachHeader("내용1,내용2,내용3,내용4", ["text-align:center","text-align:center","text-align:center","text-align:center","text-align:center"]);
	dxCommonGrid1.attachHeader("구분, 발주 미수신 건수, 총 발주 건수, 납기지연 건수", ["text-align:center","text-align:center","text-align:center","text-align:center","text-align:center"]);

	dxCommonGrid1.setInitWidths("*,150,150,150");
	dxCommonGrid1.setColAlign("left, center, center, center");
	dxCommonGrid1.setColTypes("ro,ro,ro,ro");
	dxCommonGrid1.init();
		
	dxCommonGrid1.attachEvent('onRowSelect',function (_id, _ind) {
		selectContent(1); //발주관리 이동
	});	
	
	dxPartnerGrid2 = new dhtmlXGridObject('info_grid_box2');
	dxPartnerGrid2.setImagePath(PNT.Constants.context + '/resources/dhtmlx/4.0.3/grid/imgs/dhxgrid_skyblue/');	
	
	dxPartnerGrid2.setHeader("◆ 정보2 ◆,#cspan,#cspan,#cspan,#cspan");
	dxPartnerGrid2.attachHeader("내용1,내용2,내용3,내용4,내용5", ["text-align:center","text-align:center","text-align:center","text-align:center","text-align:center"]);
	
	dxPartnerGrid2.setInitWidths("100,300,100,80,*");
	dxPartnerGrid2.setColAlign("center, center, center, center, center");
	dxPartnerGrid2.setColTypes("ro,ro,ro,ron,ro");
	dxPartnerGrid2.init();
		
//	dxPartnerGrid2.attachEvent('onRowSelect',function (_id, _ind) {
//		selectContent(2); 
//	});
	
	dxPartnerGrid3 = new dhtmlXGridObject('info_grid_box3');
	dxPartnerGrid3.setImagePath(PNT.Constants.context + '/resources/dhtmlx/4.0.3/grid/imgs/dhxgrid_skyblue/');	
	
	dxPartnerGrid3.setHeader("◆ 정보3 ◆,#cspan,#cspan,#cspan,#cspan");
	dxPartnerGrid3.attachHeader("내용1,내용2,내용3,내용4,내용5", ["text-align:center","text-align:center","text-align:center","text-align:center","text-align:center"]);
	
	dxPartnerGrid3.setInitWidths("100,300,100,80,*");
	dxPartnerGrid3.setColAlign("center, center, center, center, center");
	dxPartnerGrid3.setColTypes("ro,ro,ro,ron,ro");
	dxPartnerGrid3.init();
		
//	dxPartnerGrid2.attachEvent('onRowSelect',function (_id, _ind) {
//		selectContent(3);
//	});

	selectOrderList();
	//selectCommonList();	
	//selectPartnerList();
});

function selectOrderList() {
	$.ajax({
		type : 'post',
		url : PNT.Constants.context + '/order/common/list.do',
		beforeSend : function() {
			gProgressbar.show();
		},
		complete :function() {
			gProgressbar.hide();
		},
		success : function(_data) {
//			alert(_data);
			var gridData = $.parseJSON(_data);
			
			dxCommonGrid1.clearAll();
			dxCommonGrid1.parse(gridData,"json");
			
			dxCommonGrid1.setUserData(gridData);
		},
		error : new PNT.third.$.ajax.interceptor.Error().error
	});
}

function selectCommonList() {
	$.ajax({
		type : 'post',
		url : PNT.Constants.context + '/notice/common/list.do',
		beforeSend : function() {
			gProgressbar.show();
		},
		complete :function() {
			gProgressbar.hide();
		},
		success : function(_data) {
//			alert(_data);
			var gridData = $.parseJSON(_data);
			
			dxCommonGrid.clearAll();
			dxCommonGrid.parse(gridData,"json");
			
			dxCommonGrid.setUserData(gridData);
		},
		error : new PNT.third.$.ajax.interceptor.Error().error
	});
}

function selectPartnerList() {
	$.ajax({
		type : 'post',
		url : PNT.Constants.context + '/notice/partner/list.do',
		beforeSend : function() {
			gProgressbar.show();
		},
		complete :function() {
			gProgressbar.hide();
		},
		success : function(_data) {
//			alert(_data);
			var gridData = $.parseJSON(_data);
			
			dxPartnerGrid1.clearAll();
			dxPartnerGrid1.parse(gridData,"json");
			
			dxPartnerGrid1.setUserData(gridData);
		},
		error : new PNT.third.$.ajax.interceptor.Error().error
	});
}


function selectContent(num) {
	var move;
	
	switch(num){ //move 정보는 SignServiceImpl.java에 명시되어있음.
		case 1:	move = '101'; break;
		//case 2:	move = '201'; break;
	}
	parent.clickSubMenu(move); //발주관리로 이동
}
