"use strict";

PNT.namespace('PNT.pages.main');

PNT.pages.main.Constants = {
	SYNC_POPUP_ID : 'SYNC_POPUP_ID',
	SYSTEM_ADMIN_MENU : {
		id : 'SYSTEM_ADMIN_MENU',
		name : '시스템관리',
		link : '/admin/page.do'
	},
	sync_running : false,
	is_leftm_visible : true,
	menu_slideup_running : false
};

PNT.pages.main.MenuHandler = function(_rootMenu) {
	var self = this;

	this.id;
	this.ROOT_MENU;
	this.container;

	this.getMenu = function(_mid) {
		var m;
		
		if (PNT.pages.main.Constants.SYSTEM_ADMIN_MENU.id === _mid) {
			m = PNT.pages.main.Constants.SYSTEM_ADMIN_MENU;
		} else {
			for ( var i = 0; i < this.ROOT_MENU.item.length; i++) {
				m = this.ROOT_MENU.item[i];
				if (m.id == _mid) {
					break;
				}
			}
		}
		return m;
	};
	this.getMenuByCuid = function(_cuid) {
		var m;

		for ( var i = 0; i < this.ROOT_MENU.item.length; i++) {
			m = this.ROOT_MENU.item[i];
			if (m.link == _cuid) {
				break;
			}
		}
		
		return m;
	};
	
	this.getChildMenu = function(_pid) {
		var m, children = [];
		for ( var i = 0; i < this.ROOT_MENU.item.length; i++) {
			var m = this.ROOT_MENU.item[i];
			if (m.pid == _pid) {
				children.push(m);
			}
		}
		return children;
	};
	
	this.getName = function(_o) {
		var name;
		
		if (_o.name && _o.name.indexOf('.') == 2) {
			name = _o.name.split('.')[1];
		} else {
			name = _o.name;
		}
		
		return name;
	}
	
//	this.writeLog = function(_mid) {
//		$.ajax({
//			async : false,
//			type : 'post',
//			url : PNT.Constants.context + '/log/menu.do',
//			data : { mid : _mid },
//			success : function(_data) {
//			},
//			error : new PNT.third.$.ajax.interceptor.Error().error
//		});
//	};

	this.generate = function() {
		// top menu
		var tmBucket = [], tmHtml = '<ul>';
		$.each(self.ROOT_MENU.item, function(_i,_o) {
			if (_o.level === '1') {
				tmBucket.push(_o);
				tmHtml += '<li><a id="tm_' + _o.id + '" class="TOP_MENU" href="#" onclick="toggleMenu(this);" onmouseover="topMenuMouseOver(this);"><span>' + self.getName(_o) + '</span></a></li>';
			}
		});
		tmHtml += '</ul>';
		$('.GAgnb').html(tmHtml);
		
		// sub menu
		var smHtml = '';
		$.each(tmBucket, function(_i0,_tm) {
			smHtml += '<ul id="sm_' + _tm.id + '" class="' + (_i0 === 0 ? 'subList' : '')  + ' MID_MENU" onmouseover="toggleMenu(this);">';
			
			var childMenus = self.getChildMenu(_tm.id);
			$.each(childMenus, function(_i1,_sm) {
				smHtml += '<li>';
				smHtml += '<a href="#" onclick="clickSubMenu(\'' + _sm.id + '\');">' + self.getName(_sm) + ' </a>';
				smHtml += '</li>';
			});
			smHtml += '</ul>';
		});
		
		$('#subMenuList').html(smHtml);
	};
	
	/* constructor */
	(function() {
		if (!PNT.Constants.context) {
			alert('PNT.Constants.context must be defined first...');
			return;
		}
		self.ROOT_MENU = _rootMenu ? _rootMenu : null;
	})();
};

// ////////////////////////////////////////////////////////////////////////////////////////////////
// ////page functions
// ////////////////////////////////////////////////////////////////////////////////////////////////
function topMenuMouseOver(_o) {
	if ($('.gnbList').css('display') === 'block') {
		toggleMenu(_o);
	}
}
function toggleMenu(_o) {
	if (_o && _o.id.indexOf('sm_') > -1) {
		var nodeId = _o.id;
		nodeId = nodeId.replace('sm_','tm_');
		_o = document.getElementById(nodeId);
	}
	
	var Constants = PNT.pages.main.Constants;
	if (_o) {
		if (!Constants.menu_slideup_running) {
			var selectedTM;
			$.each($('.TOP_MENU'), function(_i, _v) {
				$(_v).parent().removeClass('on');
				
				if (_o && $(_o).get(0) == $(_v).get(0)) {
					selectedTM = _i;
					$(_v).parent().addClass('on');
				}
			});
			$.each($('.MID_MENU'), function(_i, _v) {
				$(_v).removeClass('on');
				if (selectedTM === _i) {
					$(_v).addClass('on');
				}
			});
			
			$('.gnbList').slideDown('slow');
		}
	} else {
		Constants.menu_slideup_running = true;
		$('.gnbList').slideUp('slow', function() {
			$.each($('.TOP_MENU'), function(_i, _v) {
				$(_v).parent().removeClass('on');
			});
			Constants.menu_slideup_running = false;
		});
	}
}

function clickSubMenu(_mid) {
	
	var m = menuHandler.getMenu(_mid);
	var name = menuHandler.getName(m);
	//alert(m.name + ' : ' + m.link);
	//document.all.iLContainer.style.width = '0';
	//document.all.iRContainer.style.width = '100';
	var ilc = document.getElementById('iLContainer');
	ilc.style.width = '0%';
	ilc.style.height = '0%';
	
	var irc = document.getElementById('iRContainer');
	irc.style.width = '100%';	
	
	iRContainer.location.href = PNT.Constants.context + m.link;
	
	toggleMenu();
}

function logout() {
	if (confirm('로그아웃 하시겠습니까?')) {
		window.location.replace(PNT.Constants.context + "/sign/out.do");
	}
}

function goMain() {	
	var ilc = document.getElementById('iLContainer');
	ilc.style.width = '49%';
	ilc.style.height = '90%';
	
	var irc = document.getElementById('iRContainer');
	irc.style.width = '50%';	
	
	document.iLContainer.location.href = PNT.Constants.context + "/info/page.do";
	document.iRContainer.location.href = PNT.Constants.context + "/notice/page.do";	
}

var gTimer, gProgressbar
var menuHandler;
$(document).ready(function() {
	gTimer = new PNT.libs.DateTimeHandler('datetime_content', 'CURRENT_MIN');
	gTimer.render();
	
	gProgressbar = new PNT.libs.Progressbar();
	
	$('#menuWrapper').mouseleave(function() {
		toggleMenu();
	});
	
	$.ajax({
		type : 'post',
		url : PNT.Constants.context + '/main/menu/json.do',
		beforeSend : function() {
			gProgressbar.show();
		},
		complete :function() {
			gProgressbar.hide();
		},
		success : function(_data) {
//			alert(_data);
			var gm = $.parseJSON(_data);
//			alert('json : ' + gm);
			 
			menuHandler = new PNT.pages.main.MenuHandler(gm);
			menuHandler.generate();
		},
		error : new PNT.third.$.ajax.interceptor.Error().error
	});
	
});


//$(window).resize(function() {
//	var containerWidth = $('#container').innerWidth();
//	var containerHeight = $(window).innerHeight();
////	$('#datetime_content').text( containerWidth +', ' + window.innerWidth + ' / h =' + containerHeight );
//	
//	var gap;
//	var resol = PNT.util.window.getResolution();
//	
//	switch(resol.css) {
//	case 1280:
//	case 1366:
//		gap = 47; break;
//	case 1920:
//	default:
//		gap = 70;
//	}
//	
//	$('#container').css('height', containerHeight - gap + 'px');
//	dxTabbar.setSizes();
//});
