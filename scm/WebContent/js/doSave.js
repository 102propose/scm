PNT.namespace('PNT.pages.doe.save');

//////////////////////////////////////////////////////////////////////////////////////////////////
//////page functions
//////////////////////////////////////////////////////////////////////////////////////////////////
function initilizeDate() {
	PNT.pages.doe.save.inputLabel = [];
	PNT.pages.doe.save.inputLabelCore = [];
	var d = new Date(PNT.pages.doe.save.params.date);
	
	/* init */
	PNT.pages.doe.save.inputLabel.push('발주잔량');
	PNT.pages.doe.save.inputLabelCore.push('발주잔량');
	
	var yyyy, yy, mm, dd, date;
	for (var x0 = 0; x0 < 7; x0++) {
		date = d.addDays(x0);
		yyyy = date.getFullYear();
		yy = (yyyy + '').substring(2,4);
		mm = date.getMonth() + 1;
		mm = (mm + '').length == 1 ? '0' + mm : mm;
		dd = date.getDate();
		dd = (dd + '').length == 1 ? '0' + dd : dd;
		
		PNT.pages.doe.save.inputLabel.push(mm + '.' + dd + '');
		PNT.pages.doe.save.inputLabelCore.push(yyyy + '' + mm + '' + dd);
	}
}

function doSave() {
	dxPoGrid.clearSelection();
	dxGrid.clearSelection();
	
	var data = {iter : []};
	data.pcd = PNT.pages.doe.save.params.pcd;
	data.icd = PNT.pages.doe.save.params.icd;
	
	var checker = 0;
	var inputLabel = PNT.pages.doe.save.inputLabelCore;
	
	$.each(gChangedValues, function(_i, _qty) {
		var idx = _i.replace('d', '');
		data.iter.push({
			dt : inputLabel[idx],
			qty : _qty
		});
		
		checker++;
	});
	
	if (checker === 0) {
		alert('변경 내용이 없습니다.');
		return;
	}
	
	data.iter = $.toJSON(data.iter);
	
	if (PNT.pages.doe.save.form.validate()) {
		$.ajax({
			type: 'post',
			url : PNT.Constants.context + "/do/insert.do",
			data : data,
			beforeSend : function(_xhr, _settings) {
				gProgressBar.show();
			},
			complete : function(_xhr, _status) {
				gProgressBar.hide();
			},
			success : function(_response) {
				alert(_response);
				parent.doSearch();
				parent.dxWins.unload();
			},
			error : new PNT.third.$.ajax.interceptor.Error().error
		});
	}
}

var dxGrid, dxPoGrid;
var gProgressBar, gChangedValues = {};
$(document).ready(function() {
	gProgressBar = new PNT.libs.Progressbar();
		
	initilizeDate();
	
	/* grid header */
	var inputLabel = PNT.pages.doe.save.inputLabel;
	
	inputLabel[0] = '발주수량';
	var header1 = inputLabel.join(',');
	var headerAlign = [];
	for (var i = 0; i < inputLabel.length; i++) {
		headerAlign.push("text-align:center; vertical-align:middle;");
	}
	
	/* po grid */
	var values = PNT.pages.doe.save.params.qty.split('|');

	dxPoGrid = new dhtmlXGridObject('contents_po');
	dxPoGrid.setImagePath(PNT.Constants.context + '/resources/dhtmlx/4.0.3/grid/imgs/dhxgrid_skyblue/');
	
	var readOnlyIndexCnt;
	var readOnly = [];
	var columnColor = [];
	var maxDate = PNT.pages.doe.save.params.maxDate;
	maxDate = maxDate.replace(/-/g,'');
	var inputLabelCore = PNT.pages.doe.save.inputLabelCore;
	$.each(inputLabelCore, function(_i, _v) {
		readOnly.push('ron');
		columnColor.push('#ffffff');
	});
	
	dxPoGrid.setHeader(header1, null, headerAlign);
	dxPoGrid.setInitWidths("100,100,100,100,100,100,100,100");
	dxPoGrid.enableAutoHeight(true);
	dxPoGrid.setColAlign("right, right, right, right, right, right, right, right");
	dxPoGrid.setColTypes(readOnly.join(','));
	dxPoGrid.setColumnColor(columnColor.join(','));
	dxPoGrid.setNumberFormat("0,000",0,".",",");
	dxPoGrid.setNumberFormat("0,000",1,".",",");
	dxPoGrid.setNumberFormat("0,000",2,".",",");
	dxPoGrid.setNumberFormat("0,000",3,".",",");
	dxPoGrid.setNumberFormat("0,000",4,".",",");
	dxPoGrid.setNumberFormat("0,000",5,".",",");
	dxPoGrid.setNumberFormat("0,000",6,".",",");
	dxPoGrid.setNumberFormat("0,000",7,".",",");
	dxPoGrid.init();

	var poValues = PNT.pages.doe.save.params.po.split('|');
	var gridPoData = {"rows" : [{"id" : "2627093229995","data" : poValues}]};
	dxPoGrid.parse(gridPoData,"json");
	
	
	
	
	
	
	
	
	/* do grid */
	var values = PNT.pages.doe.save.params.qty.split('|');

	dxGrid = new dhtmlXGridObject('contents');
	dxGrid.setImagePath(PNT.Constants.context + '/resources/dhtmlx/4.0.3/grid/imgs/dhxgrid_skyblue/');
	
//	var header1 = inputLabel.join(',');
//	var headerAlign = [];
//	for (var i = 0; i < inputLabel.length; i++) {
//		headerAlign.push("text-align:center; vertical-align:middle;");
//	}
//	
	var readOnlyIndexCnt;
	var readOnly = [];
	var columnColor = [];
	var maxDate = PNT.pages.doe.save.params.maxDate;
	maxDate = maxDate.replace(/-/g,'');
	var inputLabelCore = PNT.pages.doe.save.inputLabelCore;
	$.each(inputLabelCore, function(_i, _v) {
		if (_v === '발주잔량' || _v <= maxDate) {
			readOnlyIndexCnt = _i;
			readOnly.push('ron');
			columnColor.push('#ededed');
		} else {
			readOnly.push('edn');
			columnColor.push('#ffffff');
		}
	});
	
	inputLabel[0] = '발주잔량';
	var header1 = inputLabel.join(',');
	headerAlign = [];
	for (var i = 0; i < inputLabel.length; i++) {
		headerAlign.push("text-align:center; vertical-align:middle;");
	}
	
	dxGrid.setHeader(header1, null, headerAlign);
	dxGrid.setInitWidths("100,100,100,100,100,100,100,100");
	dxGrid.enableAutoHeight(true);
	dxGrid.setColAlign("right, right, right, right, right, right, right, right");
	dxGrid.setColTypes(readOnly.join(','));
	dxGrid.setColumnColor(columnColor.join(','));
	dxGrid.setNumberFormat("0,000",0,".",",");
	dxGrid.setNumberFormat("0,000",1,".",",");
	dxGrid.setNumberFormat("0,000",2,".",",");
	dxGrid.setNumberFormat("0,000",3,".",",");
	dxGrid.setNumberFormat("0,000",4,".",",");
	dxGrid.setNumberFormat("0,000",5,".",",");
	dxGrid.setNumberFormat("0,000",6,".",",");
	dxGrid.setNumberFormat("0,000",7,".",",");
	dxGrid.init();

	var gridData = {"rows" : [{"id" : "2627093229995","data" : values}]};
	dxGrid.parse(gridData,"json");
	
	dxGrid.attachEvent("onCellChanged", function(rId,cInd,nValue){
		if (isNaN(nValue)) {
			alert('숫자형식이 아닙니다.');
			dxGrid.cells(rId, cInd).setValue(inputLabelCore[cInd]);
		}
		else {
			if (nValue === 0) {
				dxGrid.cells(rId, cInd).setValue("0");
			}
			gChangedValues['d' + cInd] = nValue;
//		    alert($.toJSON(gChangedValues));
		}
	});
	dxGrid.attachEvent("onRowSelect", function(rid,ind){
		
		$.each(inputLabelCore, function(_i, _v) {
			var color;
			if (_i <= readOnlyIndexCnt) {
				color = '#ededed';
			} else {
				color = '#ffffff';
			}
			
			dxGrid.cells(rid,_i).setBgColor(color); 
		});
	});
	
	dxGrid.attachEvent("onEditCell", function(_stage, _rid, _cind){
		var rind = dxGrid.getRowIndex(_rid);
		
		if(_stage == 1){
			this.editor.obj.select(); 			
		}
		return true;
	});
	
	
	
	var formStructure = [
	    {type:"settings",position:"label-left", labelWidth: 80, inputWidth: 100, offsetLeft:245},
	    {type: "block", inputWidth: 235, list:[
	            {type:"button", name:"submit", value:"저장"}
	    ]}
	];
	PNT.pages.doe.save.form = new dhtmlXForm("buttonForm", formStructure);
	PNT.pages.doe.save.form.attachEvent("onButtonClick", function(name) {
		doSave();
	});

}); 
