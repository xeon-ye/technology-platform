//拓展Array map方法
if(!Array.prototype.map){Array.prototype.map=function(i,h){var b,a,c,e=Object(this),f=e.length>>>0;if(h){b=h}a=new Array(f);c=0;while(c<f){var d,g;if(c in e){d=e[c];g=i.call(b,d,c,e);a[c]=g}c++}return a}};
//拓展Array foreach方法
if(!Array.prototype.forEach){Array.prototype.forEach=function forEach(g,b){var d,c;if(this==null){throw new TypeError("this is null or not defined")}var f=Object(this);var a=f.length>>>0;if(typeof g!=="function"){throw new TypeError(g+" is not a function")}if(arguments.length>1){d=b}c=0;while(c<a){var e;if(c in f){e=f[c];g.call(d,e,c,f)}c++}}};
//拓展Array filter方法
if(!Array.prototype.filter){Array.prototype.filter=function(b){if(this===void 0||this===null){throw new TypeError()}var f=Object(this);var a=f.length>>>0;if(typeof b!=="function"){throw new TypeError()}var e=[];var d=arguments[1];for(var c=0;c<a;c++){if(c in f){var g=f[c];if(b.call(d,g,c,f)){e.push(g)}}}return e}};

if (!window.console) {
	// IE9下避免 console错误
	window.console = window.console || (function() {
	var c = {}; c.log = c.warn = c.debug = c.info = c.error = c.time = c.dir = c.profile
	= c.clear = c.exception = c.trace = c.assert = function() { };
	return c;
	})();
}

var _hideSecrecylevel = function() {
	// 隐藏密级相关操作
	return true;
}


// 获取树项结构的字段 code
var TREE_DICKIND_CODE = {
	ROOT_KJPT_YTDW: '/unit-api/getTreeList' //依托单位
	,ROOT_KJPT_JSLY: '/techFamily-api/getTreeList' // 技术领域
	,ROOT_KJPT_YYJSLYCPL:'/sysDictionary-api/getAllList/ROOT_KJPT_YYJSLYCPL' //应用技术领域产品类
	,ROOT_KJPT_YYJSLYJSLJSL:'/sysDictionary-api/getAllList/ROOT_KJPT_YYJSLYJSLJSL' //应用技术领域技术类
	,ROOT_KJPT_XMBJ:'/sysDictionary-api/getAllList/ROOT_KJPT_XMBJ' //项目背景

};

// 对Date的扩展，将 Date 转化为指定格式的String   
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，   
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)   
// 例子：   
// new Date().format('yyyy-MM-dd hh:mm:ss') ==> 2006-07-02 08:09:04
if (!Date.prototype.format) {
	Date.prototype.format = function(fmt) { //author: meizz   
		var o = {   
			"M+" : this.getMonth()+1,                 //月份   
			"d+" : this.getDate(),                    //日   
			"h+" : this.getHours(),                   //小时   
			"m+" : this.getMinutes(),                 //分   
			"s+" : this.getSeconds(),                 //秒   
			"q+" : Math.floor((this.getMonth()+3)/3), //季度   
			"S"  : this.getMilliseconds()             //毫秒   
		};   
		if(/(y+)/.test(fmt))   
			fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));   
		for(var k in o)   
			if(new RegExp("("+ k +")").test(fmt))   
		fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
		return fmt;   
	}
}
// 生产随机ID
function randomID() {
  var id = 'fileTableList_' + (Math.random() + '').substring(2) +'_'+ (Math.random() + '').substring(2);
  if (sessionStorage.getItem(id)) {
    id = randomID();
  } else {
    sessionStorage.setItem(id, '1');
  }
  return id;
}

// 获取地址参数
function getQueryVariable(key) {
  var variable = null;
  if (window.location.search) {
    var query = window.location.search.substring(1);
		var vars = query.split("&");
    variable = {};

    for (var i=0; i<vars.length; i++) {
			if (vars[i]) {
				var pair = vars[i].split("=");
				variable[pair[0]] = (function() {
					if (pair[1] && pair[1] !== 'undefined' && pair[1] !== 'null') {
						return pair[1];
					} else { return ''; }
				})();
			}
    }

    if (key) {
      variable = variable[key];
    }
  }
  return variable;
}
//获取用户信息
function getUserInfo(){
	var userInfo = null;
	httpModule({
		url: '/getCurrentUserInfo',
		type: 'GET',
		async:false,
		success: function(relData) {
			userInfo = relData;
		}
	})
	return userInfo;
};
// layui 表格http请求返回结果转换可识别
function layuiParseData(RelData, callback, number) {
	var codeData = { code: '-1', msg: '无数据', count: 0, data: []};
	if (RelData) {
		codeData = {
			"code": RelData.code, //解析接口状态
			"msg": RelData.message, //解析提示文本
			"count": (function(){
				if (RelData.data) {
					return RelData.data.total || 0;
				} else {
					return 0;
				}
			})(), //解析数据长度
			"data": (function(){
				if (RelData.data) {
					return switchHttpData(RelData.data.list, '-');
				} else {
					return [];
				}
			})() //解析数据列表
		};
	}

	if (!RelData || RelData.code === '-1' || !RelData.success) {
		// 出错提示
		dialogError(RelData || {
			code: '-1',
			data: 'error',
			message: '请求接口异常，后台报错，但不返回值'
		})
	}

  if (callback) {
    callback(codeData);
  } else {
		if (number) {
			codeData.data = codeData.data.filter(function(val, i) { if (i < number) {return val; } });
		}
    return codeData;
  }
}

// 会话窗口临时数据传递
function setDialogData(data, key) {
	// data 为一个JSON 或者 Array 对象时 设置sessionStorage的值；不能传递 HTML元素
	// key 则为存储的key, 可以为空值， 空值时 使用默认的key 'dialog-data';
	if (typeof(data) === 'object' || typeof(data) == 'number') {
		if (key && typeof(key) === 'string') {
			sessionStorage.setItem(key, JSON.stringify(data))
		} else {
			sessionStorage.setItem('dialog-data', JSON.stringify(data))
		}
	}
}

// 获取弹窗传递的参数
function getDialogData(id) {
	var tempData = null;
	if (id && typeof(id) !== 'object') {
		var tempData = sessionStorage.getItem(id);
		try {
			tempData = JSON.parse(tempData);
			// 数据获取完成后删除临时数据，避免重复
			sessionStorage.removeItem(id);
			return tempData;
		} catch (error) {
			sessionStorage.removeItem(id);
			return tempData;
		}
	}
	return tempData;
}

// 转换HTTP请求数据
function switchHttpData(dataJson, value, callback) {
	if (dataJson != null && typeof(dataJson) === 'object') {
		var tempData = null;
		if (dataJson.length) {
			tempData = [];
			for (var i = 0; i < dataJson.length; i++) {
				if (typeof(dataJson[i]) === 'object') {
					tempData[i] = switchHttpData(dataJson[i], value, callback);
				} else if (!dataJson[i] && (dataJson[i] !== 0 && dataJson[i] !== '0')) {
					tempData[i] = value || '';
				} else {
					tempData[i] = dataJson[i];
				}
			}
		} else if (dataJson.length === 0) {
			tempData = [];
		} else {
			tempData = {};
			for (var key in dataJson) {
				if (typeof(dataJson[key]) === 'object') {
					tempData[key] = switchHttpData(dataJson[key], value, callback);
				} else if (!dataJson[key] && (dataJson[key] !== 0 && dataJson[key] !== '0')) {
					tempData[key] = value || '';
				} else {
					tempData[key] = dataJson[key];
				}
			}

			if (callback) {
				tempData = callback(tempData);
			}
		}
		
		return tempData;
	} else {
		return dataJson;
	}
}

function dialogError(data) {
	if (top.layer) {
		if (top.$('.http-error-dialog').length === 0) {
			top.layer.open({
				type: 1,
				title: null,
				// closeBtn: 0,
				skin: 'http-error-dialog',
				area: ['400px', '220px'],
				shade: 0.1,
				btn: ['关闭'],
				yes: function(index) {
					if (top.layer) {
						top.layer.close(index);
					} else {
						window.layer(index);
					}
				},
				content: (function() {
					var layerHtml = '<div class="http-error-content">';
					layerHtml += '<div class="error-content">';
					layerHtml += '<div class="error-title-text">错误提示</div><ul>';
					layerHtml += '<li><span class="error-text">'+ (data.message || data.msg || '服务器处理出错！') +'</span></li>';
					if (data.data && typeof(data.data) !== 'boolean') {
						if (typeof(data.data) === 'string' || typeof(data.data) === 'number') {
							layerHtml += '<li><span class="error-text">'+ data.data +'</span></li>';
						} else if (typeof(data.data) === 'object'){
							layerHtml += '<li><span class="error-text">'+ JSON.stringify(data.data) +'</span></li>';
						}
					}
					layerHtml += '</ul></div>';
					layerHtml += '<div class="error-icon-block"><i class="layui-icon layui-icon-close"></i></div></div>';
					return layerHtml;
				})(),
			})
		}
	}
}

// 统一HTTP请求方法调用， 配置项与 $.ajax 参数一样；
function httpModule(config) {
	if (config && typeof(config) === 'object') {
		var httpType = config.type || 'GET';
		if (httpType === 'get') {
			httpType = 'GET';
		} else if (httpType === 'post') {
			httpType = 'POST';
		}

		var options = {
			url: config.url,
			type: httpType,
			data: (function() {
				if(config.useForm === true) {
					return config.data;
				}
				if (config.hasOwnProperty('data')) { 
					if (httpType === 'GET') {
						return config.data;
					} else {
						return JSON.stringify(config.data);
					}
				}
			})(),
			dataType: config.dataType || "json",
			contentType: config.contentType || 'application/json',
			async: (function() {
				if (config.hasOwnProperty('async')) {
					return config.async;
				} else {
					return true;
				}
			})(),
			beforeSend: function(xmlhr) {
				if (config.hasOwnProperty('beforeSend')) {
					return config.beforeSend(xmlhr);
				} else {
					return true;
				}
			},
			success: function(reldata) {
				if (config.hasOwnProperty('success')) {
					config.success(reldata);
				}
				if (reldata.hasOwnProperty('code')) {
					if (reldata.code === '-1') {
						dialogError(reldata);
					}
				} else if (reldata.hasOwnProperty('success')) {
					if (!reldata.success) {
						dialogError(reldata);
					}
				}
			},
			error: function(err) {
				if (config.hasOwnProperty('error')) {
					config.error(err);
				}
				dialogError(err);
			},
			complete: function(XHR, TS) {
				if (config.hasOwnProperty('complete')) {
					config.complete(XHR, TS);
				}
			},
			dataFilter: function(data, dataType) {
				if (dataType === 'json') {
					try {
						// 临时隐藏密级 设置默认值公开
						relData = data.split('"secretLevel":""');
						if (relData.length === 2 && relData[0] && relData[1]) {
							var relStr = relData[0] + '"secretLevel":"0"' + relData[1];
							return JSON.stringify(switchHttpData(JSON.parse( relStr )));
						}
						return JSON.stringify(switchHttpData(JSON.parse(data)));
					} catch (err) {
						if (!data) {
							return JSON.stringify({
								code: '-1',
								data: 'error',
								message: '请求接口异常，后台报错，但不返回值'
							})
						}
					}
				} else {
					return data;
				}
			}
		}

		

		for (var key in config) {
			if (!options.hasOwnProperty(key)) {
				options[key] = options[key];
			}
		}

		// 调用 $.ajax;
		$.ajax(options);
		
	} else {
		top.layer.msg('HTTP请求配置有误！', {icon: 2});
	}
}
/*关闭标签页*/
function closeTabsPage(index){
	var iframe=parent.$("#LAY_app_body div").eq(parseFloat(index)+1).find("iframe")
	var iframeSrc=$(iframe).attr("src")
    $(iframe).attr('src',iframeSrc);
    var index=parent.$("#LAY_app_body div.layui-show").index()-1;
    parent.$("#LAY_app_tabsheader li").eq(index).find('.layui-tab-close').trigger('click');
}

//驾驶舱页面跳转
function jscPup(page,name) { 
	$('#top-header-nav', parent.document).find('.tab_button').removeClass('btnactive');
	$('#top-header-nav .transR' , parent.document).each(function(item){
		var itemHref = $(this).attr('href');
		if(itemHref == page){
				$(this).addClass('btnactive');
		}
	})
	window.location.href='/jsc_web/front/'+page+'.html?name='+ name;
 }

// 获取字典总数据
function _getDicStore(key, type, callback) {
	var store = null;
	if (key && typeof(key) !== 'object') {
		if (!top.__base_dic_store[key]) {
			top.__base_dic_store[key] = {
				data: []
			};
			_commonLoadDic(key, function(relData) {
				if (type === 'form') {
					$(document).trigger('dicLoad_' + key, {data: relData});
				} else {
					$(document).trigger('dicTarget_' + key, {data: relData});
				}
			});
		}
		store = switchHttpData(top.__base_dic_store[key].data, null);
	}
	if (callback) {
		callback(store);
	} else {
		return store;
	}
}

// 设置总字典数据
function _commonLoadDic(dicKindCode, callback) {
	if (dicKindCode && typeof(dicKindCode) !== 'object') {
		var httpUrl = '/sysDictionary-api/getChildsListByCode/' + dicKindCode;
		if(dicKindCode === 'ROOT_KJPT_XXMJ') {
			httpUrl = '/sysDictionary-api/getLessThanUserSecretDicList'  //信息密级
		} 

		if (TREE_DICKIND_CODE[dicKindCode]) {
			httpUrl = TREE_DICKIND_CODE[dicKindCode];
		}
		httpModule({
			url: httpUrl,
			type: 'GET',
			async: false,
			success: function(relData) {
				var success = false;
				if (relData.hasOwnProperty('code')) {
					if (relData.code === '0') {
						success = true;
					}
				} else if (relData.success) {
					success = true;
				}
				if (TREE_DICKIND_CODE[dicKindCode]) {
					success = true;
				}

				if (success) {
					var __dicData = null;
					if (TREE_DICKIND_CODE[dicKindCode]) {
						__dicData = (function() {
							if (typeof(relData.children) === 'object') {
								return relData.children;
							} else if (typeof(relData.data) === 'object') {
								return relData.data.children;
							} else {
								return [];
							}
						})();
					} else {
						if (!relData.data) {
							__dicData = [];
						} else {
							__dicData = relData.data;
						}
					}
					
					if (__dicData.length) {
						__dicData = switchHttpData(__dicData, null, function(itemData) {
							if (!itemData.value) {
								itemData.value = (function() {
									if (itemData.numValue || itemData.numValue === 0) {
										return itemData.numValue;
									} else {
										return itemData.code;
									}
								})();
							}
							return itemData;
						})
					}
					top.__base_dic_store[dicKindCode].data = __dicData;
					if (callback) {
						callback(__dicData);
					}
				}
			}
		});
  }
}
  
function bindSelectorDic(selector, dicKindCode, form, filter, type) {
	var __dicData = _getDicStore(dicKindCode, 'form');
	if (type === 'xm-select') {
		if (__dicData.length) {
			form.data(filter, 'local', {arr: __dicData});
		} else {
			$(document).on('dicLoad_' + dicKindCode, function(event, param) {
				form.data(filter, 'local', {arr: param.data});
			});
		}
	} else if (type === 'select') {
		if (selector.attr('placeholder')) {
			selector.append('<option value=""></option>');
		} else {
			selector.append(new Option('请选择', ('')));
		}
		if (__dicData.length) {
			$.each(__dicData, function(i, item){
				selector.append(new Option(item.name, (item.numValue || item.value)));
			});
			if (_hideSecrecylevel()) {
				if (selector.attr('name') == 'secretLevel') {
					selector.val('0').closest('.layui-row').hide().siblings('.secret-level-line').hide();
					$('#scope_list_layout').hide();
				}
			}
			form.render('select');
		} else {
			$(document).on('dicLoad_' + dicKindCode, function(event, param) {
				var data = param.data;
				$.each(data, function(i, item){
					selector.append(new Option(item.name, (item.numValue || item.value)));
				});
				if (_hideSecrecylevel()) {
					if (selector.attr('name') == 'secretLevel') {
						selector.val('0').closest('.layui-row').hide().siblings('.secret-level-line').hide();
						$('#scope_list_layout').hide();
					}
				}
				
				form.render('select');
			});
		
			// if (__dicData !== 0) {
			// 	_commonLoadDic(dicKindCode);
			// }
		}
		
	}
}
  

function transInputDic(input, dicKindCode) {
	
	var code = input.val();
 
	var __dicData = _getDicStore(dicKindCode, 'form');

	if (__dicData != null && __dicData.length) {
		for(var i = 0; i<__dicData.length; i++) {
			if(__dicData[i].numValue === code) {
				input.val(__dicData[i].name) ; 
			}
		}
 		
	} else {
		$(document).on('dicLoad_' + dicKindCode, function(event, param) {

			var data = param.data;
			var text = code;
			for(var i=0;i<data.length;i++) {
				if(data[i].numValue === code) {
					text = data[i].name;
					break;
				}
			}

			input.val(text) ; 
		});
  
		// if (__dicData !== 0) {
		// 	_commonLoadDic(dicKindCode);
		// }
	}
  
}

function transFieldDic(dicKindCode, code) {

	if (code == null) {
		return '';
	}
	var __dicData = _getDicStore(dicKindCode, 'form');

	if (__dicData != null && __dicData.length) {
		for(var i=0;i<__dicData.length;i++) {
			if(__dicData[i].numValue === code) {
				return __dicData[i].name; 
			}
		}
 
		return code;
	} else {
		var attr = "trans-dic-" + dicKindCode + "='" + code + "'";

		$(document).on('dicLoad_' + dicKindCode, function(event, param) {

			var data = param.data;
			var text = code;
			for(var i=0;i<data.length;i++) {
				if(data[i].numValue === code) {
					text = data[i].name;
					break;
				}
			}

			$("span[" + attr + "]").text(text);
		});
  
		// if (__dicData !== 0) {
		// 	_commonLoadDic(dicKindCode);
		// }
  
		return "<span " + attr + ">" + code + "</span>";
	}
}


function dateFieldText(d) {
	if(d==null) return '';
	var d = new Date(d);
	return d.format('yyyy-MM-dd');
}

function getObjectData(dataJson, value) {
	var tempData = null;
	if (dataJson != null) {
		if (typeof(dataJson) === 'object' && dataJson.length) {
			// 当前数据时个数组
			for (var i = 0; i < dataJson.length; i++) {
				if (dataJson[i].value === value || dataJson[i].numValue === value || dataJson[i].code === value) {
					tempData = dataJson[i].name;
					break;
				}
				// 判断是否有子集， 有子集则从子集中去匹配
				var tempDataArr = [];
				if (dataJson[i].hasOwnProperty('childNodes') && dataJson[i].childNodes.length) {
					tempDataArr = tempDataArr.concat(dataJson[i].childNodes);
				}
				if (dataJson[i].hasOwnProperty('children') && dataJson[i].children.length) {
					tempDataArr = tempDataArr.concat(dataJson[i].children);
				}
				if (tempDataArr.length) {
					tempData = getObjectData(tempDataArr, value);
					// 如果从子集中拿到相应的值后、则退出循环
					if (tempData) {
						break;
					}
				}
			}
		}
	}
	return tempData;
}
function setVal(data){
	var json={};
	var custromLength = $('#custromFrom').find('.custrom-box').length;
	customQueryConditionStr=[];
	if(custromLength >= 1){
			json=data.field || data;
			delete(json['columnName']);
			delete(json['condition']);
			delete(json['value']);
			customQueryConditionStr=setCustromFrom();
			json['customQueryConditionStr']=customQueryConditionStr;
	}else{
			json=data.field || data;
	}
	return json;
}

function _getArrKeyValue(data, labelKey) {
	var targetValue = '';
	if (labelKey.indexOf(',') !== -1) {
		var keyArr = labelKey.split(',');
		for (var i = 0; i < keyArr.length; i++) {
			targetValue += ',' + getObjectData(data, keyArr[i]);
		}
		targetValue = targetValue.substring(1);
	} else {
		targetValue = getObjectData(data, labelKey);
	}
	if (!targetValue) {
		targetValue = '-';
	}
	return targetValue;
}

// 给非form表单域标签统一赋值
function setTargetNameValue(data) {
	if (data && typeof(data) === 'object' && !data.length) {
		$('[diy-form-value]').each(function(index, item) {
			var labelKey = $(this).attr('diy-form-value'),
			dicKindCode = $(this).attr('diy-dic-data'),
			$this = $(this),
			targetValue = '';
			if (dicKindCode) {
				// 从字典中读取数据
				var __dicData = _getDicStore(dicKindCode, 'target');
				// 是否获取到相应字典数据
				if (__dicData.length) {
					targetValue = _getArrKeyValue(__dicData, data[labelKey]);
				} else {
					// 当前字典数据暂未获取到，绑定获取数据成功事件
					$(document).on('dicTarget_' + dicKindCode, function(event, param) {
						$this.text(_getArrKeyValue(param.data, data[labelKey]));
					})
				}

			} else {
				targetValue = data[labelKey];
			}
			$(this).text(targetValue);
		})
	} else {
		top.layer.msg('赋值数据有误！', {icon: 2});
	}
}

function setNotClassName(notClass) {
	var className = '';
	if (notClass.indexOf(',') !== -1) {
		var classArr = notClass.split(',');
		$.each(notClass.split(','), function(i, name) {
			className = ',' + setNotClassName(name);
		})
		className = className.substring(1);
	} else if (notClass.indexOf('.') !== -1) {
		className = notClass;
	} else {
		className = '.' + notClass;
	}
	return notClass;
}

// 设置表单元素不可读取 disabled
function setFomeDisabled(filter, notClass) {
	var formItems = $('form[lay-filter="'+ filter +'"]')[0];
	$.each(formItems, function(i, item) {
		if ($(item).attr('placeholder')) {
			$(item).attr('tips-text', $(item).attr('placeholder'));
		}
		$(item).not(setNotClassName(notClass) + ',.close-all-dialog')
		.prop('disabled', true).attr('placeholder', '');
	})
}
// 取消表单元素不可读取 disabled
function setFomeUnDisabled(filter, notClass) {
	var formItems = $('form[lay-filter="'+ filter +'"]')[0];
	$.each(formItems, function(i, item) {
		if ($(item).attr('tips-text')) {
			$(item).attr('placeholder', $(item).attr('tips-text'));
		}
		$(item).not(setNotClassName(notClass) + ',.close-all-dialog').prop('disabled', false);
	})
}


// 添加文件大小单位
function setFileSize(number) {
	var size = '0 k';
	if (typeof(number) == 'number') {
		if (number/1024 > 1024) {
			if ( number/(1024 * 1024) > 1024 ) {
				size = parseFloat(number/(1024 * 1024 * 1024)).toFixed(2) + 'G';
			} else {
				size = parseFloat(number/(1024 * 1024)).toFixed(2) + 'Mb';
			}
		} else {
			size = parseFloat(number/1024).toFixed(2) + ' kb';
		}
	}
	return size;
}

// 模版下载
function downloadExeclTemplet(name, type) {
	if (name) {
		var fileName = name;
		if (type) {
			fileName += '.'+type;
		} else if (fileName.indexOf('.xlsx') === -1 && fileName.indexOf('.xls') === -1) {
			fileName += '.xlsx';
		}

		window.open('/data/'+ fileName, '_blank');
	}
}

// 添加数字换算单位
function conversionNumber(data) {
	var value = 0;
	if (data >= 10000 && data < 100000000) {
		value = (function() {
			if ((data /10000)%1) {
				return (data /10000).toFixed(1)+ '万';
			} else {
				return data /10000 + '万';
			}
		})();
	} else if (data >= 100000000) {
		value = (function() {
			if ((data /100000000)%1) {
				return (data /100000000).toFixed(1)+ '亿';
			} else {
				return data /100000000 + '亿';
			}
		})();
	} else if (data) {
		value = data;
	}

	return value;
}

// 左右滚动效果
function commonItemInto(config) {

	if( !config ||
		!config.elem ||
		!config.cols ||
		typeof(config.cols) !== 'object'
		|| !config.cols.length
	) {
		return;
	};


	var itemList = '',
	$itemScroll = (function() {
		if (typeof(config.elem) === 'object') {
			return config.elem;
		} else {
			return $(config.elem);
		}
	})(),
	$itemBox = $itemScroll.find('.itemBlock:eq(0)');

	$.each(config.cols, function(i, item) {
		if(item.url!='#'){
            itemList += ('<li class="top-item middle-block"><a lay-href="'+item.url+'" lay-text="'+item.title+'">'+
                '<div class="item-cell">'+
                '<div class="title-cl-item">'+
                '<span class="text-icon"><img src="/images/'+ item.iconName +'.png" alt=""></span>'+
                '<span class="text-title">'+ item.title +'</span>'+
                '</div>'+
                '<div class="number-cl-item">'+
                '<span class="number" id="'+ item.id +'" num-label="'+ item.label +'">0</span><span class="text">'+ item.unit +'</span>'+
                '</div>'+
                '</div></a></li>');
		}else {
            itemList += ('<li class="top-item middle-block">'+
                '<div class="item-cell">'+
                '<div class="title-cl-item">'+
                '<span class="text-icon"><img src="/images/'+ item.iconName +'.png" alt=""></span>'+
                '<span class="text-title">'+ item.title +'</span>'+
                '</div>'+
                '<div class="number-cl-item">'+
                '<span class="number" id="'+ item.id +'" num-label="'+ item.label +'">0</span><span class="text">'+ item.unit +'</span>'+
                '</div>'+
                '</div></li>');
		}

	});

	$itemBox.html(itemList);

	var configItem = {
		element: $itemScroll,
		itemMinWidth: config.itemMinWidth || 76
	},
	resizeTime = null;

	itemScrollBiullClick(configItem);

	$(window).resize(function() {
		if (resizeTime) {
			clearTimeout(resizeTime);
		}
		resizeTime = setTimeout(function() {
			itemScrollBiullClick(configItem);
		}, 300)
	})
}
function itemScrollBiullClick(config) {
	var $element = config.element;
		  $itemBox = $element.find('.itemBlock:eq(0)').removeAttr('style'),
			maxWidth = $element.find('.itemScroll:eq(0)').outerWidth(),
			$li = $itemBox.children('li').removeAttr('style'),
			itemWidth = maxWidth / Math.floor(maxWidth / config.itemMinWidth),
			leftNumber = 0;

	if ($itemBox.outerWidth() < maxWidth) {
		$element.find('.btnContent').hide();
		itemWidth = maxWidth / $li.length;
	} else {
		$element.find('.btnContent').show();
		$element.find('.itemBtn').removeClass('active').each(function() {
			if ($(this).hasClass('right-btn')) {
				$(this).addClass('active');
			}
		});
	}

	$element.find('.itemBtn').off('click').on('click', function(){
		if($(this).hasClass('active') && !$itemBox.hasClass('animate')) {
			$itemBox.addClass('animate');

			if($(this).is('.left-btn')) {
				leftNumber += maxWidth;
				$(this).siblings('.right-btn').addClass('active');
				if(leftNumber > 0 ) {
					leftNumber = 0;
					$(this).removeClass('active');
				}
			} else if($(this).is('.right-btn')) {
				leftNumber -= maxWidth;
				$(this).siblings('.left-btn').addClass('active');
				if(Math.abs(leftNumber) > ($itemBox.outerWidth() - maxWidth) ) {
					leftNumber = -($itemBox.outerWidth() - maxWidth)
					$(this).removeClass('active');
				}
			}

			$itemBox.animate({ 'left': leftNumber }, 500, function() {
				$itemBox.removeClass('animate');
			})
		}
	})

	$li.css({ width: itemWidth });
}

// 关闭当前弹窗
function closeCurrentDialog() {
	var currentIndex = top.layer.getFrameIndex(window.name);
	top.layer.close(currentIndex);
}
// 处理自定义查询方法
function setCustromFrom() { 
	var list=[];
			$('#custromFrom').find('.custrom-box').each(function(index,item){
				var obj={};
			 var optionType = $(this).find('select[name="columnName"]').find("option:selected").attr("data-optionType"); 
			 var columnName= $(this).find('select[name="columnName"]').find("option:selected").attr('data-columnName');
			 var notes= $(this).find('select[name="columnName"]').find("option:selected").attr('data-notes');
			 var attributeName= $(this).find('select[name="columnName"]').find("option:selected").attr('data-attributeName');
			 var condition= $(this).find('select[name="condition"]').find("option:selected").attr('name');
			 var columnType= $(this).find('select[name="columnName"]').find("option:selected").attr('data-columntype');
			 if(optionType == 1){
				var value= $(this).find('input[name="value"]').val();
			 }else{
				var value= $(this).find('select[name="value"]').find("option:selected").attr('name');
			 }
			 obj['columnName']=columnName;
			 obj['condition']=condition;
			 obj['value']=value;
			 obj['notes']=notes
			 obj['attributeName']=attributeName
			 obj['columnType']=columnType
			 list.push(obj)
		})
		return JSON.stringify(list);
 }
/*动态添加tr*/
function addTr(id) {
    var off=$("#"+id).find(".layui-none");
    $(off).hide();
    var index=$("#"+id+" tbody tr").length
    var trHtml='<tr>' +
        '<td>'+index+'</td>' +
        '<td><input type="text"  placeholder="请填写姓名" autocomplete="off" class="layui-input"></td>' +
        '<td><select class="sex">' +
        '<option value=""></option>' +
        '</select></td>' +
        '<td><input type="text"  placeholder="请填写..." autocomplete="off" class="layui-input"></td>' +
        '<td><input type="text"  placeholder="请填写..." autocomplete="off" class="layui-input"></td>' +
        '<td><a style="color: #F44C4C;cursor: pointer;" class="deleTr">删除</a></td>' +
        '</tr>';
    window.createElement({code:'ROOT_KJPT_XB',id: id,className:'sex',element:'option',index:index})
    $("#"+id+" tbody").append(trHtml)
}
function deleTr(id){
    $(".deleTr").click(function () {
        $(this).parents("tr").remove()
        var tr=$("#"+id+" tbody tr")
        $(tr).map(function (index, item) {
            if($(this).attr("class")!="layui-none"){
                $(this).find("td:nth-child(1)").html(index)
            }

        });
    })
}
function backfill(data, id, type) {
	var readonly='';
    type=="view" ?  readonly='true': readonly='false'
    var dataArr=data.split("$")

    if(dataArr.length>0){
        var off=$("#"+id).find(".layui-none");
        $(off).hide();
        if(type=="view"){
					$("#"+id+" thead tr th:eq(5)").hide();
            dataArr.map(function (item, index) {
                var itemArr=item.split("#")
				var sex='男'
				if(itemArr[1]!=="null"){
                	if(itemArr[1]!=1){
                        sex=='女'
					}
				}
                var trHtml='<tr>' +
                    '<td>'+(index+1)+'</td>' +
                    '<td><input type="text" disabled="disabled" value="'+(itemArr[0]=='null' ? '': itemArr[0])+'" placeholder="请填写姓名" autocomplete="off" class="layui-input"></td>' +
                    '<td><input type="text" disabled="disabled" value="'+(itemArr[1]=='null' ? '': sex)+'" autocomplete="off" class="layui-input"></td>' +
                    '<td><input type="text" disabled="disabled" placeholder="请填写..." value="'+(itemArr[2]=='null' ? '': itemArr[2])+'" autocomplete="off" class="layui-input"></td>' +
                    '<td><input type="text" disabled="disabled" placeholder="请填写..."   value="'+(itemArr[3]=='null' ? '': itemArr[3])+'" autocomplete="off" class="layui-input"></td>' +
                    '</tr>';
                $("#"+id+" tbody").append(trHtml)
            })
			return false
        }
        dataArr.map(function (item, index) {
			var itemArr=item.split("#")
            var trHtml='<tr>' +
                '<td>'+(index+1)+'</td>' +
                '<td><input type="text"  value="'+(itemArr[0]=='null' ? '': itemArr[0])+'" placeholder="请填写姓名" autocomplete="off" class="layui-input"></td>' +
                '<td><select class="sex">' +
                '<option value=""></option>' +
                '</select></td>' +
                '<td><input type="text"  placeholder="请填写..." value="'+(itemArr[2]=='null' ? '': itemArr[2])+'" autocomplete="off" class="layui-input"></td>' +
                '<td><input type="text"  placeholder="请填写..."   value="'+(itemArr[3]=='null' ? '': itemArr[3])+'" autocomplete="off" class="layui-input"></td>' +
                '<td><a style="color: #F44C4C;cursor: pointer;" class="deleTr">删除</a></td>' +
                '</tr>';
            window.createElement({code:'ROOT_KJPT_XB',id:id,className:'sex',element:'option',index:index+1,value:(itemArr[1]=='null' ? '': itemArr[1])})
            $("#"+id+" tbody").append(trHtml)
        })
    }
    deleTr(id)
}
/*获取数据*/
function getTableData(id){
    var tr=$("#"+id+" tbody tr")
	var trStr=''
    $(tr).map(function (index, item) {
        if(index!==0){
        	var tdOne=$(this).find("td:nth-child(2) input").val()
            var tdTwo=$(this).find("td:nth-child(3) select option:selected").val()
            var tdThree=$(this).find("td:nth-child(4) input").val()
            var tdFour=$(this).find("td:nth-child(5) input").val()
            if(tdOne==''){tdOne=null}
            if(tdTwo==''){tdTwo=null}
            if(tdThree==''){tdThree=null}
            if(tdFour==''){tdFour=null}
            trStr+=tdOne+"#"+tdTwo+"#"+tdThree+"#"+tdFour+"$"
        }
    })
	return trStr.substring(0, trStr.length - 1);
}
 

 
// 设置菜单栏选中项
function setNavMeunSelected(index) {
	// index: home-item | 0 | 1 | 2 | 3 | 4 | 5 ...;
	var indexClass = null;
	if (typeof(index) === 'object') {
		layHref = index.attr('lay-id');
		var navItem = top.$('#index_main_left_menu').find('[lay-href="'+ layHref +'"]').filter(function() {
			if (!$(this).closest('#dlCollect').length && $(this).data('id')) {
				return $(this);
			}
		});
	
		if (navItem.length === 1) {
			var meunId = navItem.closest('ul').attr('id');
			if (meunId !== 'nav') {
				indexClass = top.$('#up'+ meunId.substring(3)).attr('nav-index') || 'home-item';
			} else {
				indexClass = 'home-item';
			}
			navItem.closest('ul').find('dd, li').removeClass('layui-this layui-nav-itemed')
			navItem.parent('dd').addClass('layui-this').closest('li').addClass('layui-nav-itemed');
		} else {
			indexClass = 'home-item';
		}
	} else {
		indexClass = (function() {
			if (index || index === 0) {
				return index + '';
			} else {
				return 'home-item';
			}
		})();
	}

	top.$('#layuiHeaderNav .header-nav-item').removeClass('layui-this').each(function(i, elem) {
		if ($(this).hasClass(indexClass)) {
			$(this).addClass('layui-this');
			if (indexClass === 'home-item') {
				top.$('#index_main_left_menu').children('ul').addClass('layui-hide');
				top.$('#nav').removeClass('layui-hide');
			} else {
				top.$('#index_main_left_menu').children('ul').addClass('layui-hide');
				top.$('#nav'+ $(this).children('a').attr('id')).removeClass('layui-hide');
			}
		}
	})
}


//按钮权限封装
function _getButtonRoles() {
    var iframes = $(window.top.document).contents().find("IFRAME.layadmin-iframe");
    var f = null;
    for(var i=0;i<iframes.length;i++) {
      if(iframes[i].contentWindow === window) {
        f = iframes[i];
        break;
      }
    }

    if(f) { 
      return $(f).attr('data-functionbuttons');
    }else {
      return null;
    }
}

function _useButtonRoles() { 
	var btnRoles = _getButtonRoles();
	if (console && console.log) {
		console.log('btnRoles =>', btnRoles);
	}
	if(btnRoles && btnRoles.indexOf('ALL') == -1) {
		$("[button-role]").each(function(index, item) { 
			var btn = $(item), role = ',' + btn.attr('button-role');
			if(btnRoles.indexOf(role)<0) {
//				btn.css('display', 'none');
				btn.remove();
			}
		});  
	}
}
function gray(){
	$(".layui-form-label").removeClass("label-required")
	$(".xm-select-sj,.layui-edge").hide()
	$(".xm-select-parent .xm-input").css("borderColor","#eee")
}
	
/* 知悉范围 *--- start ----*/
function setJurisdictionScope(config) {
	/*
	config = {
		elem: ElementHtml || ElementID,
		knowledgeScope: '', // 已添加的用户账号
		knowledgePerson: '', // 已添加的用户名称
		secretLevel: string, // 初始保密级别
		disabled: boolean // 是否为只读状态
	}
	*/

	$scope = (function() {
		if (typeof(config.elem) === 'object') {
			return $(config.elem);
		} else if (typeof(config.elem) === 'string') {
			return (config.elem.indexOf('#') === 0 ? $(config.elem) : $('#' + config.elem));
		} else {
			return null;
		}
	})();

	// 公开禁用的直接不加载
	if (config.disabled && config.secretLevel == '0') {
		$scope.remove();
		return;
	}

	if ($scope) {
		layui.use(['table', 'laypage'], function() {
			var table = layui.table,
			laypage = layui.laypage,
			initScope = {
				idkey: 'userName', // 用户账号key
				namekey: 'userDisp', // 用户名称key
				secretLevel: config.secretLevel || '0', // 单据密级 保密级别为0时，不显示知悉范围
				formFilter: $scope.closest('.layui-form').attr('lay-filter'),
				scopeTableId: randomID(),
				pageID: randomID(),
				loadData: false,
				activeListData: {}, // 所有选中的值
				tableData: { data: [], pageIndex: 1, pageSize: 5, total: 0, limits: [5, 10, 20, 30, 50]},
				$selected: null,
				$scopeInput: null,
				$valueInput: null,
				$nameInput: null,
				setFormInputValue: function() {
					// 设置表单 用户账号，用户名的值
					var valueID = '', nameID = '';
					for (var key in initScope.activeListData) {
						valueID += (',' + key);
						nameID += (',' + initScope.activeListData[key][initScope.namekey]);
					}
					valueID = valueID.substring(1);
					nameID = nameID.substring(1);
					initScope.$valueInput.val(valueID);
					initScope.$nameInput.val(nameID);
				},
				reloadTable: function() {
					// 表格渲染
					if (!initScope.loadData) {
						table.render({
							elem: ('#' + initScope.scopeTableId)
							,cols: [[ //表头
								{type: 'checkbox', width: 80}
								,{field: 'userDisp', title: '用户名称' }
								,{field: 'userUnitName', title: '所在单位' }
							]],
							data: initScope.tableData.data,
							done: function(data) {
								if (!initScope.loadData) {
									initScope.loadData = true;
									table.reload(initScope.scopeTableId);
								}
							},
							page: false //开启分页
						});
					} else {
						table.reload(initScope.scopeTableId, {data: initScope.tableData.data});
					}
				},
				selectedItemData: function() {
					// 显示选中的值
					if (!initScope.$selected) {
						return false;
					}	
					var labelHtml = '';
					for (var key in initScope.activeListData) {
						labelHtml += '<label class="sed-item-list">';
						labelHtml += '<span class="name-item">'+initScope.activeListData[key][initScope.namekey]+'</span>';
						labelHtml += '<span class="delete-item" item-id="'+ initScope.activeListData[key][initScope.idkey] +'"></span></label>';
					}
					initScope.$selected.empty().append(labelHtml);
					// 选中值添加到表单中
					initScope.setFormInputValue();
				},
				getTableData: function(Data) {
					// 获取表格数据
					var searchData = Data || {}, searchHttp = '';
					searchData.page = initScope.tableData.pageIndex;
					searchData.limit = initScope.tableData.pageSize;
					searchData.recodeLevel = initScope.secretLevel || 0;

					for (var key in searchData) {
						searchHttp += ('&' + key + '=' + searchData[key]);
					}
					searchHttp = searchHttp.substring(1);

					httpModule({
						url: '/user-api/getSysUserPageByRecodeLevel?' + searchHttp, // 数据接口
						// type: 'POST',
						// data: searchData || null,
						success: function(res) {
							if (res.code === 0) {
								initScope.tableData.data = [];
								$.each(res.data, function(i, item) {
									var tmepItem = switchHttpData(item);
									if (initScope.activeListData[item[initScope.idkey]]) {
										tmepItem.LAY_CHECKED = true;
									} else {
										tmepItem.LAY_CHECKED = false;
									}
									initScope.tableData.data.push(tmepItem);
								});
								initScope.tableData.total =	res.count;
								// 渲染表格
								initScope.reloadTable();

								if (initScope.tableData.total > initScope.tableData.pageSize) {
									$('#' + initScope.pageID).show();
									// 配置分页 实时更新
									laypage.render({
										elem: initScope.pageID,
										count: initScope.tableData.total,
										limit: initScope.tableData.pageSize,
										curr: initScope.tableData.pageIndex,
										limits: initScope.tableData.limits,
										jump: function(obj, isFirst) {
											if (!isFirst) {
												initScope.tableData.pageIndex = obj.curr;
												initScope.tableData.pageSize = obj.limit;
												initScope.getTableData({name: initScope.$scopeInput.val()});
											}
										}
									});
								} else {
									$('#' + initScope.pageID).hide();
								}
							}
						}
					});
				},
				initActiveValue: function() {
					var tempID = (function() {
						if (config.knowledgeScope.indexOf(',') !== 0) {
							return config.knowledgeScope.split(',');
						} else {
							return config.knowledgeScope.substring(1).split(',');
						}
					})(),
					tempName = (function() {
						if (config.knowledgePerson.indexOf(',') !== 0) {
							return config.knowledgePerson.split(',');
						} else {
							return config.knowledgePerson.substring(1).split(',');
						}
					})();
					$.each(tempID, function(i, item) {
						initScope.activeListData[item] = {};
						initScope.activeListData[item][initScope.idkey] = item;
						initScope.activeListData[item][initScope.namekey] = tempName[i];
					})
				}
			};

			// 判断初始是否有需要显示的内容
			if (config.knowledgeScope) {
				initScope.initActiveValue();
			}
	
			// 加载知悉范围HTML
			$scope.load('/html/components/scope.html', function(html) {
				var $hideLayout = $scope.find('.hide-table-layout:eq(0)');
				initScope.$valueInput = $scope.find('[name="knowledgeScope"]'); // 知悉范围 value
				initScope.$nameInput = $scope.find('[name="knowledgePerson"]'); // 知悉范围 name
				initScope.$selected = $scope.find('[active-item]:eq(0)').empty(); // 清空显示值区域

				// 判断是否禁用
				if (config.disabled) {
					// 只读状态
					$hideLayout.remove();
					$scope.find('[label-id="downPlan"]').remove();
					if (config.knowledgeScope) {
						initScope.selectedItemData();
					}
				} else {
					$hideLayout.hide();

					// 添加分页ID
					$scope.find('.table-page-layout:eq(0)').attr('id', initScope.pageID);
					// 获取单据密级
					initScope.secretLevel = commonLayuiForm.val(initScope.formFilter).secretLevel || '0';
					// 知悉范围搜索框
					initScope.$scopeInput = $scope.find('[_name="search_name"]').eq(0);

					// 保密级别为0时，不显示知悉范围
					if ( !initScope.secretLevel || initScope.secretLevel === '0') {
						$scope.hide();
					} else if (config.knowledgeScope || initScope.secretLevel > 0) {
						if (config.knowledgeScope) {
							initScope.selectedItemData();
						}
						$scope.find('[label-id="downPlan"]').text('添加').attr('_show-table', 'false');
					}

					// 删除选中用户
					initScope.$selected.on('click', '.delete-item', function(e) {
						var itemID = $(this).attr('item-id');
						if (initScope.activeListData[itemID]) {
							delete initScope.activeListData[itemID];
							$(this).closest('label').remove();
							for (var i = 0; i < initScope.tableData.data.length; i++) {
								if (initScope.tableData.data[i][initScope.idkey] === itemID) {
									initScope.tableData.data[i].LAY_CHECKED = false;
								}
							}
							// 重新渲染表格
							initScope.reloadTable();

							// 选中值添加到表单中
							initScope.setFormInputValue();
						}
					});
					// 给表格添加随机ID
					$scope.find('table:eq(0)').attr({
						'id': initScope.scopeTableId, 'lay-filter':initScope.scopeTableId
					});

					// 监听表格选择与否
					table.on('checkbox('+initScope.scopeTableId+')', function(rowData) {
						if (rowData.type === 'all') {
							$.each(initScope.tableData.data, function(i, item) {
								if (rowData.checked) {
									var tempItem = switchHttpData(item);
									tempItem.LAY_CHECKED = rowData.checked;
									initScope.activeListData[item[initScope.idkey]] = tempItem;
								} else if (initScope.activeListData[item[initScope.idkey]]) {
									delete initScope.activeListData[item[initScope.idkey]];
									$('[item-id="'+ item[initScope.idkey] +'"]').closest('label').remove();
								}
							});
						} else if (rowData.type === 'one') {
							if (rowData.checked) {
								var tempItem = switchHttpData(rowData.data);
								tempItem.LAY_CHECKED = rowData.checked;
								initScope.activeListData[rowData.data[initScope.idkey]] = tempItem;
							} else if (initScope.activeListData[rowData.data[initScope.idkey]]) {
								delete initScope.activeListData[rowData.data[initScope.idkey]];
								$('[item-id="'+ rowData.data[initScope.idkey] +'"]').closest('label').remove();
							}
						}
						// 更新选中项
						initScope.selectedItemData();
					});
					
					// 展开收起
					$scope.on('click', '[label-id="downPlan"]', function(e) {
						if ($(this).attr('_show-table') === 'true') {
							$(this).text('添加').attr('_show-table', 'false');
							$hideLayout.hide();
						} else {
							$(this).text('收起').attr('_show-table', 'true');
							$hideLayout.show();

							if (!initScope.loadData) {
								// 第一次展开时查询数据
								initScope.getTableData({name: initScope.$scopeInput.val()});
							} else {
								// 再次展开表格，重新渲染样式
								initScope.reloadTable();
							}
						}
					});

					// 监控单据保密级别变更
					commonLayuiForm.on('select(secretLevel)', function(data) {
						if (data.value && data.value !== '0') {
							$scope.show();
							initScope.$nameInput.attr('lay-verify', 'required');
							initScope.$valueInput.attr('lay-verify', 'required');

							if (data.value !== initScope.secretLevel) {
								initScope.secretLevel = data.value;
								initScope.tableData.pageIndex = 1;
		
								if (data.value !== config.secretLevel) {
									initScope.activeListData = {}; // 清空已选知悉范围
									initScope.$nameInput.val('');
									initScope.$valueInput.val('');
									initScope.$selected.empty();
								} else if (config.knowledgeScope) {
									// 还原初始化
									initScope.initActiveValue();
									initScope.selectedItemData();
								}

								// 更新表格数据
								var useName = initScope.$scopeInput.val() || '';
								initScope.getTableData({name: useName });
							}
						} else {
							$scope.hide();
							initScope.$nameInput.attr('lay-verify', '').val('');
							initScope.$valueInput.attr('lay-verify', '').val('');
						}
					});

					// 搜索知悉范围数据
					$scope.on('click', '.search-data-btn', function(e) {
						initScope.tableData.pageIndex = 1;
						initScope.getTableData({name: initScope.$scopeInput.val()});
					})
				}
			});
		})
	} else {
		console.error('elem => 不能为空！');
		return false;
	}
}
/* 知悉范围 *--- end ----*/


//渲染字典
var commonLayuiForm = null;
layui.use(['form', 'formSelects','laydate'], function() {
	commonLayuiForm = layui.form;
	laydate = layui.laydate;

	// 自定义表单校验规则
	commonLayuiForm.verify({
		length: function(value, item) {
			// <input type="text" lay-filter="length" length="20">
			var lengthNumber = $(item).attr('length') || 10;
			if ((''+value).length > lengthNumber) {
				return '字符长度不能超过 '+ lengthNumber + '个';
			}
		},
		doubleFore:function(value,item){
			var regNum=/^\d+(\.\d{1,4})?$/;
			if(!regNum.test(value)){
				return '小数点后只能输入四位'
			}
		}
	})


	if ($('.layui-form-screen').length) {
		$('.layui-form-screen').attr('fold-panel', 'close').each(function() {
			var $foldBtn = $(this).find('.layui-fold-btn').empty().text('高级筛选');
			
			var $form = $(this);
			$foldBtn.off('click').on({
				'click': function() {
					if ($form.attr('fold-panel') === 'close') {
						$form.attr('fold-panel', 'open');
						$(this).text('收起筛选').closest('.layui-col-btn').removeClass('form-col-ly4');
					} else {
						$form.attr('fold-panel', 'close');
						$(this).text('高级筛选').closest('.layui-col-btn').addClass('form-col-ly4');
					}
				}	
			}).closest('.layui-col-btn').addClass('form-col-ly4');
		});
	}
	
	//自定义条件查询设置
	$('.layui-fold-btn-custom').on('click',function(){
		if($('.layui-colla-content').hasClass('layui-hide')){
			$('.layui-colla-content').removeClass('layui-hide').addClass('layui-show')
		}else{
			$('.layui-colla-content').removeClass('layui-show').addClass('layui-hide')
		}
	});
	var count = 0
	$('#custormAdd').on('click',function(){
			var custromLen = $('.custrom-box').length;
			if(custromLen == '0'){
				custromFrom();
			}else{
				$('.custrom-box').remove();
			}
	});

	function custromFrom(){
		var tableName = returnTableName();
		count++;
		httpModule({
			url: "/expert-api/getCustomQueryConditionList/"+tableName,
			type: 'GET',
			success: function (relData) {
				if (relData.success) {
					var optionStr = '';
					optionStr=relData.data.map(function(item){
						return '<option value="'+item.columnName+'" data-notes="'+item.notes+'" data-columnName="'+item.columnName+'" data-optionCode="'+item.optionCode+'" data-optionType="'+item.optionType+'" name="'+item.attributeName+'" data-attributeName="'+item.attributeName+'" data-columnType="'+item.columnType+'">'+item.notes+'</option>'
					})
					var id = 'dt'+count;
					var formid = 'form'+count;
					var dataId = 'data'+count;
					var optionCode = 'optionCode'+count;
					var addStr = '<button class="add-more-item-btn" type="button">'+
					'<i class="layui-icon">&#xe654;</i>'+
					'</button>';
					var str='<div class="custrom-box"><div class="layui-col-xs12 layui-col-sm6 layui-col-md3 layui-col-btn"></div>'+
						'<div class="layui-col-xs12 layui-col-sm6 layui-col-md2">'+
						'<div class="layui-form-item">'+
						'<div class="layui-input-block">'+
						'<select name="columnName" placeholder="请选择" lay-filter="columnName" >'+
						'<option value="" placeholder="请选择"></option>'+
							optionStr +
						'</select>'+
						'</div>'+
						'</div>'+
						'</div>'+
						'<div class="layui-col-xs12 layui-col-sm6 layui-col-md2">'+
						'<div class="layui-form-item">'+
						'<div class="layui-input-block">'+
						'<select name="condition" class="dt" dic-base-data="ROOT_XTGL_ZDYCXTJ" id="'+id+'">'+
						'<option value=""></option>'+
						'</select>'+
						'</div>'+
						'</div>'+
						'</div>'+
						'<div class="layui-col-xs12 layui-col-sm6 layui-col-md2">'+
						'<div class="layui-form-item">'+
						'<div class="layui-input-block select-hide">'+
						'<select class="dt" name="value" id="'+optionCode+'">'+
						'<option value=""></option>'+
						'</select>'+
						'</div>'+
						'<div class="layui-input-block hide-selete input-hide input-hide-input">'+
						' <input type="text" name="" placeholder="请输入" autocomplete="off" class="layui-input">'+
						'</div>'+
						'<div class="layui-input-block hide-selete  input-hide input-hide-date">'+
						' <input type="text" name="" id="'+dataId+'" placeholder="请输入" autocomplete="off" class="laydate-input">'+
						'</div>'+
						'</div>'+
						'</div>'+
						'<div class="layui-col-xs12 layui-col-sm6 layui-col-md1 layui-col-btn more-item">'+
						'<div class="layui-form-item" style="padding-left: 20px;">'+
						'	<label class="layui-form-label custrom-label" style="text-align: left">'+
							addStr +
						'</label>'+
						'</div>'+
						'</div>'
						'</div>'
						
						$('#custromFrom').append(str);
				}
				window.createElement({code:'ROOT_XTGL_ZDYCXTJ',id: id,className:'dt',element:'option',index:count,dt:'dt'})
			}
		});
	};
	//自定义增加
	$(document).on('click','.add-more-item-btn',function(){
		custromFrom();
		var delStr='<button class="more-item-del-btn custromDel" type="button">' +
					  					'<i class="layui-icon">&#x1006;</i>' +
											'</button>';
			$(this).parents('.more-item').find('.custrom-label').append(delStr);
			$(this).hide();
	})
	
	//自定义删除
	$(document).on('click','.custromDel',function(){
		var addStr = '<button class="add-more-item-btn" type="button">'+
											'<i class="layui-icon">&#xe654;</i>'+
											'</button>';
		$(this).parents('.custrom-label').append(addStr);
		$(this).parents('.custrom-box').remove();

	})

	commonLayuiForm.on('select(columnName)', function(data) {
		var optionType = $(data.elem).find("option:selected").attr("data-optionType");
		var columnType = $(data.elem).find("option:selected").attr("data-columnType");   
		if(optionType == 1){
			$(this).parents('.custrom-box').find('.select-hide').addClass('hide-selete');
			$(this).parents('.custrom-box').find('.select-hide select').attr('name','')
			if(columnType == 'int'){
				$(this).parents('.custrom-box').find('.input-hide-input').removeClass('hide-selete');
				$(this).parents('.custrom-box').find('.input-hide-input input').attr('name','value');
				$(this).parents('.custrom-box').find('.input-hide-input').attr('lay-verify','number');
			}else if(columnType == 'date'){	
				var id = $(this).parents('.custrom-box').find(".input-hide-date input").attr("id"); 
				$(this).parents('.custrom-box').find('.input-hide-date').removeClass('hide-selete');
				$(this).parents('.custrom-box').find('.input-hide-date input').attr('name','value');
				$(this).parents('.custrom-box').find('.input-hide-input').addClass('hide-selete');
				laydate.render({elem: '#'+id, trigger:'click',});
			}else if(columnType == 'string'){
				$(this).parents('.custrom-box').find('.input-hide-input').removeClass('hide-selete');
				$(this).parents('.custrom-box').find(' .input-hide-date').addClass('hide-selete');
				$(this).parents('.custrom-box').find('.input-hide-input input').attr('name','value');
			}
		}else {
			$(this).parents('.custrom-box').find('.select-hide select').attr('name','value')
			$(this).parents('.custrom-box').find('.input-hide input').attr('name','')
			var id =$(this).parents('.custrom-box').find('select[name="value"]').attr('id')
			var optionCode = $(data.elem).find("option:selected").attr("data-optionCode"); 
			$(this).parents('.custrom-box').find('.input-hide').addClass('hide-selete');
			$(this).parents('.custrom-box').find('.select-hide').removeClass('hide-selete');
			window.createElement({code:optionCode,id:id,className:'dt',element:'option',index:count,dt:'dt'})
		}
	})
	
    /*动态生成元素*/
    window.createElement=function (param) {
        httpModule({
            url: "/sysDictionary-api/getChildsListByCode/"+param.code,
						type: 'GET',
            success: function(relData) {
                if (relData.success === true) {
									$("#"+param.id).find('option').remove();
                    relData.data.map(function(item){
                        if(param.element=="option"){
														if(param.dt=='dt'){
															$("#"+param.id).append("<option value='"+item.numValue+"' name='"+item.numValue+"'>"+item.name+"</option>")
														}
                          	if(param.value==item.numValue){
                              $("#"+param.id+" tbody tr:eq("+param.index+")").find("."+param.className).append("<option value='"+item.numValue+"' selected='selected'>"+item.name+"</option>")
                          	}else {
                                $("#"+param.id+" tbody tr:eq("+param.index+")").find("."+param.className).append("<option value='"+item.numValue+"'>"+item.name+"</option>")
                            }
                        }
                    });
                    commonLayuiForm.render()
                }
            }
        });
    }
	// 关闭 top层 所有弹窗
	$('.close-all-dialog').click(function() {
		top.layer.closeAll();
	})
	// 关闭当前弹窗；即关闭本身;
	$('.close-current-dialog').click(function(e) {
		closeCurrentDialog();
	});

	// 自动渲染下拉框
	$('select[dic-base-data]').each(function() {
		if ($(this).attr('dic-base-data')) {
			$(this).empty();
			if ($(this).attr('xm-select')) {
				bindSelectorDic($(this), $(this).attr('dic-base-data'), layui.formSelects, $(this).attr('xm-select'), 'xm-select');
			} else {
				bindSelectorDic($(this), $(this).attr('dic-base-data'), layui.form, $(this).attr('lay-filter'), 'select');
			}
		}
	});

	// 模版下载
	$('[download-templet]').on('click', function() {
		if ($(this).attr('download-templet')) {
			downloadExeclTemplet($(this).attr('download-templet'));
		}
	})
	
	
	// 配置按鈕權限
	_useButtonRoles();
})
