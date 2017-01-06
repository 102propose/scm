"use strict";

var PNT = {};

PNT.Constants = {
	domain : 'localhost:8080',
	context : ''
};

PNT.namespace = function(_ns) {
	var exist = function(_pkg) {
		try {
			_pkg = _pkg.substring(0, _pkg.length - 1);
			var e = eval('(' + _pkg + ')');
			if (!e) {
				eval(_pkg + ' = {};');
			}
		} catch (e) {
			alert(e.message);
		}
	};

	var p = '', nspl = _ns.split('.');

	for ( var i = 0; i < nspl.length; i++) {
		p += nspl[i] + '.';
		exist(p);
	}
};

PNT.namespace('PNT.lang');
PNT.lang.Message = function() {
	var self = this;
	this.locale;
	
	this.getMessage = function(_code, _args) {
		if (_args) {
			if ($.type(_args) !== 'array') {
				alert(this.getMessage('app.msg.001'));
			}
		} else {
			_args = [];
		}
		
		var msg;
		try {
//			alert(this.locale);
			msg = PNT.i18n.message[this.locale][_code];
			for (var i = 0; i < _args.length; i++) {
				msg = msg.replace('{' + i + '}', _args[i]);
			}
		} catch(e) {
			msg = '메세지코드[' + _code + ']를 가져올수 없습니다.';
		}
		
		return msg;
	};
	
	(function() {
		if (navigator.userLanguage) {// Explorer
		  self.locale = navigator.userLanguage;
		} else if (navigator.language) {// FF
		  self.locale = navigator.language;
		} else {
		  self.locale = "ko";
		}
		
		if (self.locale == 'ko-KR') {
			self.locale = "ko";
		}
		
		// todo : ajax로 i18n_ko.js 파일 로드... jsp에서 i18n.js 파일 include는 제거하게 한다.
	})();
};

PNT.namespace('PNT.util');
PNT.util = {
    checkBoxToggle : function(_id, _bool) {
    	var checkbox = document.getElementById(_id);
    	
    	if (typeof(_bool) === 'string') {
    		_bool = 'true' === _bool.toLowerCase() || 'y' === _bool.toLowerCase() ? true : false;
	    }

    	switch($.type(_bool)) {
    	    case 'string':
    	    	_bool = ('true' === _bool.toLowerCase() || 'y' === _bool.toLowereCase()) ? true : false;
    		case 'boolean':
    			if (_bool && !checkbox.checked) {
        			checkbox.click();
        		}
        		if (!_bool && checkbox.checked) {
        			checkbox.click();
        		}
    			break;
    		default:
    			if (!checkbox.checked) {
            		checkbox.click();
        		}
        		if (checkbox.checked) {
        			checkbox.click();
        		}
    	}
    }
};

PNT.util.window = {
	getResolution : function() {
		var resolution = {};
		var w = window.screen.width;
//		alert('w : ' + w + ', h : ' + window.screen.height);
		
		if (w >= 1920) {
			resolution.w = 1920;
			resolution.h = 1050;
//			resolution.h = 1080;
			resolution.css = 1920;
		} else if (w < 1920 && w >= 1366) {
//			resolution.w = 1314;
			resolution.h = 738;
			resolution.w = 1366;
//			resolution.h = 764;
			resolution.css = 1366;
		} else {
//			resolution.w = 1212;
			resolution.h = 690;
			resolution.w = 1280;
//			resolution.h = 719;
			resolution.css = 1280;
		}
		return resolution;
	},
	resize : function(_resolution) {
		window.moveTo(0, 0);
		
		var resolution = _resolution || this.getResolution();
		window.resizeTo(resolution.w, resolution.h);
		
		var w = window.screen.width;
		var h = window.screen.height;
		var x = (w <= resolution.w) ? 0 : (w - resolution.w) / 2;
		var y = (h <= resolution.h) ? 0 : (h - resolution.h) / 2;
		
		window.moveTo(x, y);
	}	
};

PNT.util.String = {
	lpad : function (_str, _len, _add) {
	    var result = _str + '';
	    var templen = _len - result.length;

	    for (var i = 0; i < templen; i++) {
	        result = _add + result;
	    }

	    return result;
	},
	rpad : function (_str, _len, _add) {
	    var result = _str + '';
	    var templen = _len - result.length;

	    for (var i = 0; i < templen; i++) {
	        result = result + _add;
	    }

	    return result;
	},
	trim : function(_str) {
		return _str.replace( /(^\s*)|(\s*$)/g, "" );
	},
	allTrim : function compactTrim(_str) {
		return _str.replace( /(\s*)/g, "" );
	} 
};

PNT.util.Map = function() {
	this.map = new Object();
};
PNT.util.Map.prototype = {
	put : function(key, value) {
		this.map[key] = value;
	},
	get : function(key) {
		return this.map[key];
	},
	containsKey : function(key) {
		return key in this.map;
	},
	containsValue : function(value) {
		for (var prop in this.map) {
			if (this.map[prop] == value)
				return true;
		}
		return false;
	},
	isEmpty : function(key) {
		return (this.size() == 0);
	},
	clear : function() {
		for ( var prop in this.map) {
			delete this.map[prop];
		}
	},
	remove : function(key) {
		delete this.map[key];
	},
	keys : function() {
		var keys = new Array();
		for (var prop in this.map) {
			keys.push(prop);
		}
		return keys;
	},
	values : function() {
		var values = new Array();
		for (var prop in this.map) {
			values.push(this.map[prop]);
		}
		return values;
	},
	size : function() {
		var count = 0;
		for (var prop in this.map) {
			count++;
		}
		return count;
	}
};

PNT.util.Validator = function() {
	/**
	 * 특정문자열 validator
	 * @param _obj<object || string> : input text object or 문자열
	 * @param _regexStr<string || array> : 필터링할 문자열, ex) '!@#$%^7' or ['!','@','#','$','%','^','1234']
	 */
	this.text = function(_obj, _regexp, _errMsg) {
	    var v, isText;
	    
	    switch($.type(_obj)) {
	    	case 'string':
	    		v = _obj;
	    		isText = true;
	    		break;
	    	default :
	    		v = _obj.value;
	    		isText = false;
	    }
	    
	    var b = false, regExp;
	    
	    switch ($.type(_regexp)) {
		    case 'regexp' :
		    	regExp = _regexp;
	 	        if (regExp.test(v)) {
	 	            b = true;
	 	        }
		    	break;
		    case 'string':
		    	_regexp = _regexp.split('');
		    case 'array':
		    	 var db = false, dephault = '\''.split('');
//		 	    var db = false, dephault = '!@#$%^&*()_+=-~`\';:"/.,?><|}{][\\'.split('');
		 	    
		 	    var c, regexpStr = _regexp || dephault;
		 	    
		 	    for (var i = 0; i < regexpStr.length; i++) {
		 	        c = regexpStr[i];

		 	        for (var k = 0; k < dephault.length; k++) {
		 	            if (regexpStr[i].indexOf(dephault[k]) > -1) {
		 		        	db = true;
		 		        	break;
		 	            }
		 	        }
		 	        
		 	        if (db === true) {
		 	            switch(c) {
		 	                case '.':
		 	                case '?':
		 	                case '*':
		 	                case '+':
		 	                case '$':
		 	                case '|':
		 	                    c = '[' + c + ']';
		 	                    break;
		 	                default:
		 	                    c = '\\' + c;
		 	            }
		 	        }

		 	        regExp = new RegExp(c, 'g');

		 	        if (regExp.test(v)) {
		 	            b = true;
		 	            break;
		 	        }
		 	    }
	    }

	    _errMsg = _errMsg || "[" + v + "]에는 허용되지 않는 문자를 입력 하셨습니다. 특정문자를 사용하실 수 없습니다.";

	    if (b) {
	        alert(_errMsg);
	        if (!isText) {
	        	 v =  v.replace(regExp, '');
	 	        _obj.value = v;
	        }
	       
	        return false;
	    } else {
	    	return true;
	    }
	};
	
	this.ip = function(_o, _t) {
	    if (_o.value) {
		var expUrl = /^(1|2)?\d?\d([.](1|2)?\d?\d){3}$/;
		if (!expUrl.test(_o.value)) {
		    alert("ip[" + _o.value + "]가 유효하지 않습니다.");
		    _o.value = '';
		    _o.focus();
		    if (_t) {
			_t.value = '';
		    }
		}
	    }
	};
};

PNT.namespace('PNT.libs');
PNT.libs.DateTimeHandler = function(_container, _type) {
	var self = this;
	this.timer;
	this.duration = 1000;
	this.container = _container;
	this.type = _type || 'CURRENT_TIME';
	this.startSec = 10; // default 10초, type이 DOWN_SECOND 에서 사용되는 property
	
	this.trigger;
	
	this.render = function() {
		var html;
		switch(self.type) {
		    case 'DOWN_SECOND':
		    	html = '<strong>' + (self.startSec--) + '</strong>';
		    	if (self.trigger && self.startSec === -1) {
		    		self.turnOff();
		    		self.trigger();
		    	}
		        break;
		    case 'CURRENT_MIN':
		    	var d = new Date(); 
				var year = d.getFullYear();
				var month = PNT.util.String.lpad((d.getMonth() + 1), 2, '0');
				var date =  PNT.util.String.lpad(d.getDate(), 2, '0');
				var hour =  PNT.util.String.lpad(d.getHours(), 2, '0');
				var min =  PNT.util.String.lpad(d.getMinutes(), 2, '0');
				html = year + '.' + month + '.' +date + ' ' + hour + ':' + min;
				break;
		    case 'CURRENT_TIME':
			default:
				var d = new Date(); 
				var year = d.getFullYear();
				var month = PNT.util.String.lpad((d.getMonth() + 1), 2, '0');
				var date =  PNT.util.String.lpad(d.getDate(), 2, '0');
				var hour =  PNT.util.String.lpad(d.getHours(), 2, '0');
				var min =  PNT.util.String.lpad(d.getMinutes(), 2, '0');
				var sec =  PNT.util.String.lpad(d.getSeconds(), 2, '0');
				html = year + '.' + month + '.' +date + ' ' + hour + ':' + min+ ':' + sec;
		}
		
		$('#' + self.container).html(html);
	};
	
	this.turnOn = function() {
//		if (this.type === 'CURRENT_TIME') {
			this.timer = setInterval(this.render, this.duration);
//		}
	};
	
	this.turnOff = function() {
		clearInterval(this.timer);
	};
	
	(function() {
		if (!_container) {
			alert('container should be defined!');
		}
	})();
};

PNT.libs.Progressbar = function() {
	var self = this;
	this.progressBox;
	
	this.show = function() {
		$(this.progressBox).css({'display' : 'block'});
	};
	
	this.hide = function() {
		$(this.progressBox).css({'display' : 'none'});
	};
	
	(function() {
		var html = '<div class="img_progress"></div><div class="progress_back_panel" ></div>';
		
		self.progressBox = $('<div></div>', {
			'id' : 'progress_box',
			'class' : 'progress_box'
		});
		self.progressBox.html(html);
		
		$(document.body).append(self.progressBox);
	})();
};

PNT.libs.Paginator = function(_id, _container) {
	var self = this;
	
	this.id;
	this.container;
	
	this.currPage;
	this.totalPageCount;
	this.countPage;
	this.pageBlockBasket = {};	
	
	this.click = function(_targetPage) {
		this.clickHandler(_targetPage);
	};
	/* override this when u need */
	this.clickHandler = function(_targetPage) {
		// type your click event handler codes here when u override this...
	};
	
	this.createPageBlock = function() {
		var pageBlockCount = parseInt(this.totalPageCount / this.countPage) + (this.totalPageCount % this.countPage > 0 ? 1 : 0);
			
		for (var index = 0; index < pageBlockCount; index++) {
			var pageBlock = [];
			
			bc:
			for(var c = 1; c <= this.countPage; c++) {
				var page = index * this.countPage + c;
				pageBlock.push(page);
				
				if (index == (pageBlockCount - 1) && page == this.totalPageCount) {
					break bc;
				}
					
			}
			
			this.pageBlockBasket[index + ''] = pageBlock;
			this.pageBlockBasket['LAST_BLOCK_INDEX'] = index;
		}
	};
	
	this.getPageBlock = function(_blockIndex) {
		this.pageBlockIndex = (_blockIndex || _blockIndex === 0)? _blockIndex : parseInt(this.currPage / this.countPage);
		
		/* this.currPage가 -999이면 goPageBlock method에 의해 강제 설정 된것임 */
		if (this.currPage > 0 && (this.currPage % this.countPage) === 0) {
			this.pageBlockIndex = this.pageBlockIndex - 1;
		}
		
		return this.pageBlockBasket[this.pageBlockIndex];
	};
	
	this.goBlockIndex = 0;
	this.goPageBlock = function(_next) {
		
		if (_next == -1 && this.goBlockIndex === 0) {
			return;
		}
		if (_next == 1 && this.goBlockIndex === this.pageBlockBasket['LAST_BLOCK_INDEX']) {
			return;
		}
		
		switch(_next) {
			case 'first':
				this.goBlockIndex = 0;
				break;
			case 'last':
				this.goBlockIndex = this.pageBlockBasket['LAST_BLOCK_INDEX'];
				break;
			default:
				this.goBlockIndex = this.pageBlockIndex + _next;
		}
		
		//this.currPage = -999; // getPageBlock method 에서 조건에 걸리지 않기 위해 -999로 강제 설정
		
//		this.render(this.currPage, this.totalPageCount, this.countPage, goBlockIndex);
		this.render(this.goBlockIndex);
	};
	
//	this.render = function(_currPage, _totalPageCount, _countPage, _blockIndex) {
	this.render = function(_blockIndex) {
//		this.currPage = _currPage;
//		this.totalPageCount = _totalPageCount;
//		this.countPage = _countPage;
		
		if (this.totalPageCount === 0) {
			return;
		}
		
		this.createPageBlock();
		
		var html = '<a href="#" onclick="' + this.id + '.goPageBlock(\'first\');" style="color: black"><img src="../images/nav_first.gif" alt="FIRST" valign="absmiddle"></a>&nbsp;&nbsp;';
		html += '<a href="#" onclick="' + this.id + '.goPageBlock(-1);" style="color: black"><img src="../images/nav_prev.gif" alt="PREV" valign="absmiddle" style="margin:0 9px 0 1px;"></a>&nbsp;&nbsp;';
//		alert(this.currPage);
		var pageBlock = this.getPageBlock(_blockIndex);
		for (var i = 0; i < pageBlock.length; i++) {
			var pageStr = this.currPage == pageBlock[i] ? '<strong>' + pageBlock[i] + '</strong>' : pageBlock[i];
			html += '<a href="#" onclick="' + this.id + '.click(' + pageBlock[i] + ');" style="color: black">' + pageStr + '</a> ';
		}
		
		html += '<a href="#" onclick="' + this.id + '.goPageBlock(1);" style="color: black"><img src="../images/nav_next.gif" alt="NEXT" valign="absmiddle" style="margin:0 1px 0 9px;"></a>&nbsp;&nbsp;';
		html += '<a href="#" onclick="' + this.id + '.goPageBlock(\'last\');" style="color: black"><img src="../images/nav_last.gif" alt="LAST" valign="absmiddle"></a>&nbsp;&nbsp;';
		
//		alert(html);
		
		$('#' + this.container).html(html);
	};
	
	/* constructor */
	(function() {
		self.id = _id;
		self.container = _container;
	})();
	
};

PNT.namespace('PNT.third.$.ajax.interceptor');
PNT.third.$.ajax.interceptor.Error = function (_e, _pre, _post) {
	var self = this;
	
	this.func = function (_xhr, _ajaxOptions, _thrownError) {
	};
	
	this.pre = function (_xhr, _ajaxOptions, _thrownError) {
		var checker = true;
		switch (_xhr.status) {
			case 8500:
				top.location.replace(PNT.Constants.context + '/error/invalid/request.do');
				checker = false;
				break;
			case 8501:
				top.location.replace(PNT.Constants.context + '/error/session/expired.do');
				checker = false;
				break;
			case 8502:
				top.location.replace(PNT.Constants.context + '/error/invalid/user.do');
				checker = false;
				break;
			case 8510: // sso invalid authn
			case 8511: // sap sso invalid authn
				top.location.replace(PNT.Constants.context + '/error/invalid/sso/user.do');
				checker = false;
				break;
			case 8520: // must change user password 
				top.location.replace(PNT.Constants.context + '/user/pwd/notify.do');
				checker = false;
				break;
//			case 8800:
//				alert('이미 시스템권한이 존재 합니다.\n변경하시려면 기존 시스템권한을 제거 후 재지정 하세요.');
//				checker = false;
//				break;
			case 404:
				checker = false;
				top.location.replace(PNT.Constants.context + '/error/404.do');
				break;
			case 500:
			default:
//				alert(_xhr.status);
//				alert(_xhr.responseText);
				checker = false;
				top.location.replace(PNT.Constants.context + '/error/500.do');
		}
		
		return checker;
	};
	
	this.post = function(_xhr, _ajaxOptions, _thrownError) {
	};
	
	this.error = function (_xhr, _ajaxOptions, _thrownError) {
		self.pre = _pre || self.pre;
		self.post = _post || self.post;
		
		self.func = _e || self.func;
		
		var checker = self.pre(_xhr, _ajaxOptions, _thrownError);
		
		if (checker) {
			self.func(_xhr, _ajaxOptions, _thrownError);
		}
		
		self.post(_xhr, _ajaxOptions, _thrownError);
	};
};

PNT.namespace('PNT.third.dhx');
PNT.third.dhx = {
	'grid' : {
		setUserData : function(_gridData) {
			var self = this;
			self.userData = self.userData ? self.userData : {};
			var rows = _gridData.rows;
			$.each(rows, function(_i, _d) {
				if (_d.userdata) {
					self.userData[_d.id] = _d.userdata;
				}
			});
		},
		getUserData : function(_rowid, _name) {
			var self = this;
			self.userData = self.userData ? self.userData : {};
			var z = self.userData[_rowid];
			return z ? z[_name] : '';
		}
	}
};

Date.prototype.addDays = function(days) {
    var dat = new Date(this.valueOf());
    dat.setDate(dat.getDate() + days);
    return dat;
}
