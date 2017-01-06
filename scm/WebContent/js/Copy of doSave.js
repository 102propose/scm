PNT.namespace('PNT.pages.doe.save');

//////////////////////////////////////////////////////////////////////////////////////////////////
//////page functions
//////////////////////////////////////////////////////////////////////////////////////////////////
function initilizeDate() {
	PNT.pages.doe.save.inputLabel = [];
	PNT.pages.doe.save.inputLabelCore = [];
	var d = new Date(PNT.pages.doe.save.params.date);
	
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
	
	var formData = PNT.pages.doe.save.form.getFormData();
//	data.date = PNT.pages.doe.save.params.date;

	var data = {iter : []};
	data.pcd = PNT.pages.doe.save.params.pcd;
	data.icd = PNT.pages.doe.save.params.icd;
	
	var inputLabel = PNT.pages.doe.save.inputLabelCore;
	for (var x0 = 0; x0 < inputLabel.length; x0++) {
		data.iter.push({
			dt : inputLabel[x0],
			qty : formData["d" + (x0+1)]
		});
	}
	
	data.iter = $.toJSON(data.iter);
//	alert($.toJSON(data));
	
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

var gProgressBar;
$(document).ready(function() {
	gProgressBar = new PNT.libs.Progressbar();
		
	initilizeDate();
	var inputLabel = PNT.pages.doe.save.inputLabel;
	var values = PNT.pages.doe.save.params.qty.split('|');
	
	var formStructure = [
	    {type:"settings",position:"label-left", labelWidth: 80, inputWidth: 100, offsetLeft:10},
	    {type: "fieldset", label: "D/O", inputWidth: 235, list:[
                {type:"input", name: 'base_date', label: '기준일', required: true, readonly: true, style:'border:none;', value: PNT.pages.doe.save.params.date},
	            {type:"input", name: 'd1', label: inputLabel[0], validate: "NotEmpty", style:'text-align: right;', maxLength: 8, value : new Number(values[0]).toLocaleString()},
	            {type:"input", name:"d2", label: inputLabel[1], validate: "NotEmpty", style:'text-align: right;', maxLength: 8, value : new Number(values[1]).toLocaleString()},
	            {type:"input", name:"d3", label: inputLabel[2], validate: "NotEmpty", style:'text-align: right;', maxLength: 8, value : new Number(values[2]).toLocaleString()},
	            {type:"input", name:"d4", label: inputLabel[3], validate: "NotEmpty", style:'text-align: right;', maxLength: 8, value : new Number(values[3]).toLocaleString()},
	            {type:"input", name:"d5", label: inputLabel[4], validate: "NotEmpty", style:'text-align: right;', maxLength: 8, value : new Number(values[4]).toLocaleString()},
	            {type:"input", name:"d6", label: inputLabel[5], validate: "NotEmpty", style:'text-align: right;', maxLength: 8, value : new Number(values[5]).toLocaleString()},
	            {type:"input", name:"d7", label: inputLabel[6], validate: "NotEmpty", style:'text-align: right;', maxLength: 8, value : new Number(values[6]).toLocaleString()},
	            {type:"button", name:"submit", value:"저장"}
	    ]}
	];
	PNT.pages.doe.save.form = new dhtmlXForm("contents", formStructure);
	PNT.pages.doe.save.form.attachEvent("onButtonClick", function(name) {
		doSave();
	});
	PNT.pages.doe.save.form.attachEvent("onFocus", function(name){
		if(this.isReadonly(name)) {
			$(PNT.pages.doe.save.form.getInput(name)).blur();
		}
	});
	PNT.pages.doe.save.form.attachEvent("onBlur", function(name){
		if(!this.isReadonly(name)) {
			if (isNaN(this.getItemValue(name))) {
				alert('숫자형식이 아닙니다.');
				this.setItemValue(name, '');
			}
			else {
				var money = new Number(this.getItemValue(name));
				this.setItemValue(name, money.toLocaleString());
			}
		}
	});
//	PNT.pages.doe.save.form.attachEvent("onInputChange", function(inp, ev, name, value){
//	    input.value = value + '_' + name;
//	});
	
	var maxDate = PNT.pages.doe.save.params.maxDate;
	maxDate = maxDate.replace(/-/g,'');
	var inputLabelCore = PNT.pages.doe.save.inputLabelCore;
	$.each(inputLabelCore, function(_i, _v) {
//		alert(_i + ' / ' + _v + ' / ' + maxDate);
		if (_v <= maxDate) {
			$(PNT.pages.doe.save.form.getInput("d" + (_i + 1))).css('background-color', '#ededed');
			PNT.pages.doe.save.form.setReadonly("d" + (_i + 1), true);
		}
	});
//	PNT.pages.doe.save.form.setReadonly("myInput", true);
}); 
