"use strict";

// ////////////////////////////////////////////////////////////////////////////////////////////////
// ////page functions
// ////////////////////////////////////////////////////////////////////////////////////////////////

var gProgressbar, dxCommonGrid, dxPartnerGrid;
$(document).ready(function() {
	gProgressbar = new PNT.libs.Progressbar();
	dxCommonGrid = new dhtmlXGridObject('common_notice_grid_box');
	dxCommonGrid.setImagePath(PNT.Constants.context + '/resources/dhtmlx/4.0.3/grid/imgs/dhxgrid_skyblue/');
		
	dxCommonGrid.setHeader("◆ 전체 공지사항 ◆,#cspan,#cspan,#cspan");
	dxCommonGrid.attachHeader("순번,제목,등록일자,조회수", ["text-align:center","text-align:center","text-align:center","text-align:center","text-align:center"]);

	dxCommonGrid.setInitWidths("50,*,100,100");
	dxCommonGrid.setColAlign("center, center, center, center");
	dxCommonGrid.setColTypes("ro,ro,ro,ro");
	dxCommonGrid.init();
		
	dxCommonGrid.attachEvent('onRowSelect',function (_id, _ind) {
		selectContent('common', _id);
	});	
	
	dxPartnerGrid = new dhtmlXGridObject('partner_notice_grid_box');
	dxPartnerGrid.setImagePath(PNT.Constants.context + '/resources/dhtmlx/4.0.3/grid/imgs/dhxgrid_skyblue/');	
	
	dxPartnerGrid.setHeader("■ 개별 공지사항 ■,#cspan,#cspan,#cspan,#cspan");
	dxPartnerGrid.attachHeader("순번,제목,등록일자,조회수,읽음여부", ["text-align:center","text-align:center","text-align:center","text-align:center","text-align:center"]);
	
	dxPartnerGrid.setInitWidths("50,*,100,100,80");
	dxPartnerGrid.setColAlign("center, center, center, center, center");
	dxPartnerGrid.setColTypes("ro,ro,ro,ron,ro");
	dxPartnerGrid.init();
		
	dxPartnerGrid.attachEvent('onRowSelect',function (_id, _ind) {
		selectContent('partner', _id);
	});	

	selectCommonList();
	
	selectPartnerList();
});

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
			
			dxPartnerGrid.clearAll();
			dxPartnerGrid.parse(gridData,"json");
			
			dxPartnerGrid.setUserData(gridData);
		},
		error : new PNT.third.$.ajax.interceptor.Error().error
	});
}
function selectContent(_type, _msgSeq) {
	function initilizeModal(_params) {
		var params = _params ? _params : {};
//		alert($.toJSON(params));
		var dxWins = new dhtmlXWindows();
		var w1 = dxWins.createWindow("w1", 1000, 1000, 600, 610);
		w1.setText("공지사항 상세내용");
		w1.attachURL(PNT.Constants.context + "/notice/popup.do", null, params);
		w1.center();
		w1.setModal(true);
		w1.denyResize();

		w1.attachEvent("onClose", function(win) {
			
			if ('common' === _type) {
				selectCommonList();
			} else {
				selectPartnerList();
			}
			
		    return true;
		});
		
		var pos = w1.getPosition();
		w1.setPosition(pos[0], pos[1]+5);
	}
	
	$.ajax({
		type : 'post',
		url : PNT.Constants.context + '/notice/' + _type + '/content/' + _msgSeq + '.do',
		beforeSend : function() {
			gProgressbar.show();
		},
		complete :function() {
			gProgressbar.hide();
		},
		success : function(_data) {
//			alert(_data);
			var data = $.parseJSON(_data);
			initilizeModal(data);
		},
		error : new PNT.third.$.ajax.interceptor.Error().error
	});
}
