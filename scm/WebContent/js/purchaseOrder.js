var pogrid = null;
var poList = null;
var arryList = null;

function init() {		
	searchConts();
}

function searchConts(){	
	
	var po_no = $('#po_no').val();
	
	var df = $('#df').val();
	var dt = $('#dt').val();
	var dtFr = df.replace(/-/g,'');
	var dtTo = dt.replace(/-/g,'');
	
	var param = {
			'info' : "po",
			'po_no' : po_no,
			'dtFr' : dtFr,
			'dtTo' : dtTo
	};
//	alert($.toJSON(param));
	$.ajax({
		type: 'post',
		url : PNT.Constants.context + "/preview/select/getPreviewInfo.do",		
		data : param,
		//		dataType:'json',
		beforeSend : function(_xhr, _settings) {
			//showProgressBar(true);
		},
		complete : function(_xhr, _status) {
			//showProgressBar(false);
		},
		success : function(_response) {
//			alert(_response);
			var data = $.parseJSON(_response);
//			alert(data);
 
    		var html = '<table style=" border-collapse: collapse; text-align: left; width: 1056px; height: 60px;" border="1" cellpadding="2" cellspacing="2" bordercolor="black"><tbody>';
			
			$.each(data, function(_i, _d) {
				html += '<tr>';
				html += '<td style="width: 141px; text-align: left; ">' + _d.cd_item + '</td>';
				html += '<td style="width: 412px; text-align: left; ">' + _d.nm_item + '</td>';
				html += '<td style="width: 157px; text-align: right; ">' + _d.qt_po_nm + '</td>';
				html += '<td style="width: 151px; text-align: right; ">' + _d.um_ex + '</td>';
				html += '<td style="width: 151px; text-align: right; ">' + _d.am_ex + '</td>';
				html += '</tr>';
				
				html += '<tr>';
				html += '<td style="width: 141px; text-align: left; ">' + _d.cd_unit_mm + '</td>';
				html += '<td style="width: 412px; text-align: left; ">' + _d.stnd_item + '</td>';
				html += '<td style="width: 157px; text-align: right; ">' + _d.dt_limit + '</td>';
				html += '<td style="width: 151px; text-align: center; ">' + _d.nm_cls_item + '</td>';
				html += '<td style="width: 151px; text-align: center; ">' + _d.dc_rmk21 + '</td>';
				html += '</tr>';
			});
			 html += '</tbody></table>';
			 $('#dataContainer').html(html);
		},
		error : new PNT.third.$.ajax.interceptor.Error().error
	});
}

function addRow(poList){
	
	//alert(poList);
	
	var poTd = document.getElementById('poTd');	
	var trIndex = poTd.parentNode.rows.length;
	var tdLength = poTd.cells.length;
	var tr = poTd.parentNode.insertRow(trIndex);
	for(var i=0; i < tdLength; i++){		
		var td = tr.insertCell(i);
		td.innerHTML = trIndex +'-'+poTd.cells[i].innerHTML;
	}
}
