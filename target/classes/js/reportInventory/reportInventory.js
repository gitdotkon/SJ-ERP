var factoryModel = {
	"JDTAW" : "JD904,JD954,JD1054,JD1104,JD1204,JD1354,JD1404,JD5-750,JD5-754,JD5-800,JD5-804,JD5-850,JD5-854,JD5-900,JD5-904,JD5-950,JD5-1000",
	"JDJW" : "C100,C110,C230,L50,L60,L70,W210,W70,W80,Y210,Y215,Y110,Y115,204C玉米割台(4行),500mm行距四行玉米割台,206C 6行650mm行距玉米割台,208R刚性割台(L50、L60),209F挠性割台（L50、L60）,210R刚性割台(W70),209R刚性割台(L50、L60),209R 2.75米刚性割台（L70）,209R刚性割台(L60),210R 3.2米刚性割台,212P-12带式捡拾器(C230、W210),212R-24小麦割台（C100、W80）,213F挠性割台(W70),215F-24挠性割台(C100、W80),215F-12挠性割台(C230、W210),215F-12挠性割台(C110),215R-24刚性割台(C100、W80),215R-12刚性割台(C230、W210),215R-12刚性割台(C110),218F-12 挠性割台(C230、W210）,218F-24 挠性割台(C100、W80）,218R-12刚性割台(C230、W210),五行玉米割台,210F 3.2米挠性割台,七行500mm行距玉米割台,八行500mm行距玉米割台,600mm行距三行玉米割台,9970",
	"JDNW" : "JD504,JDB504,JD500,JD550,JD550-1,JD554,JD554-1,JDX550,JDB550,JDX554,JDB554,JD354,JDB354,JD350,JD280,JD284,JD300,JD304,JD320,JD324,JD400,JD404,JD450,JD454,JD480,JD484,JD350-3,JDB350,JD704,JD704-1,JD700,JD700-1,JD654,JD654-1,JD650,JD650-1,JD604,JD604-1,JD600,JD600-1,R40,新型R40,CH330,KOTA",
	"JDHBW" : "1654,1854,2054,2104,S660,608C,625F,615P,4630,2204,2854",
	"PEXT" : "旋耕机2.5米,深松联合整地机2.5米,旋耕机3.0米,旋耕深松机2.3米,旋耕深松机2.0米,1.4米旋耕机,1.5米旋耕机,1.6米旋耕机,1.8米旋耕机,2.0米旋耕机,2.2米旋耕机,2.3米旋耕机,Heavy Duty 2.0 m Wide Rotary Tiller,Heavy Duty 2.2 m Wide Rotary Tiller,Heavy Duty 2.3 m Wide Rotary Tiller,Heavy Duty 2.5 m Wide Rotary Tiller,2.0米旋耕机RT3220,2.2米旋耕机RT3220,2.3米旋耕机RT3223,2.5米旋耕机RT3220,2.6米旋耕机RT3220,2.8米旋耕机RT3220,3.0米旋耕机RT3220,3.2米旋耕机RT3220,3.4米旋耕机RT3220,1.2米旋耕机,600 L Cannon Sprayer,800 L Cannon Sprayer,药箱400L,药箱600L,药箱800L,药箱1000L,5铧液压翻转犁,4铧液压翻转犁,旋耕施肥播种机,RS6220S Rotary Tiller with Shredder,甘蔗转运车"
};

var reportInventory = {
	factoryText : "",
	dischargeType : "",
	factoryModel : "",
	num : ""
};
// 每条数据新增id
var addId = 0;

/**
 * [过滤]初始化工厂input框自动过滤功能
 */
$(function() {
	// 工厂-input框，得到焦点时的处理
	$("#belongsFactory").autocomplete({
		source : [ '宁波工厂', '天津工厂', '哈尔滨工厂', '佳木斯工厂', '农具' ],
		delay : 100,
		autoFocus : true,
		select : function(event, ui) {
			/* 设置值相等的为选中 */
			var existFlag = false;
			var val = ui.item.value;
			var selectEl = document.getElementById("factory");
			if (selectEl.length > 0) {
				// 遍历工厂下拉框
				for ( var i = 0; i < selectEl.options.length; i++) {
					// 判断当前弹出数据是否存在于下拉框
					if (selectEl.options[i].text == val) {
						// 设置该值被选中，即显示该数据
						selectEl.options[i].selected = true;
						changeType();
						existFlag = true;
						break;
					}
				}
			}
			// 遍历工厂下拉框，如果不存在时
			if (!existFlag) {
				var selectEl = document.getElementById("factory");
				if (selectEl.length > 0) {
					for ( var i = 0; i < selectEl.options.length; i++) {
						if (selectEl.options[i].text == "宁波工厂") {
							// 设置默认值-宁波工厂
							selectEl.options[i].selected = true;
							changeType();
							$("#belongsFactory").val("宁波工厂");
							break;
						}
					}
				}
			}
		}
	});
});
/**
 * [失焦]当自动过滤不满足，工厂-input框失去焦点时调用
 */
onblurFactory = function(obj) {
	var val = $(obj).val();
	var existFlag = false;
	var selectEl = document.getElementById("factory");
	// 遍历工厂下拉框，当前工厂input框的值存在于工厂下拉框
	if (selectEl.length > 0) {
		for ( var i = 0; i < selectEl.options.length; i++) {
			if (selectEl.options[i].text == val) {
				selectEl.options[i].selected = true;
				changeType();
				existFlag = true;
				break;
			}
		}
	}
	// 遍历工厂下拉框，当前工厂input框的值不存在于工厂下拉框，设为默认值
	if (!existFlag) {
		var selectEl = document.getElementById("factory");
		if (selectEl.length > 0) {
			for ( var i = 0; i < selectEl.options.length; i++) {
				if (selectEl.options[i].text == "宁波工厂") {
					selectEl.options[i].selected = true;
					changeType();
					$("#belongsFactory").val("宁波工厂");
					break;
				}
			}
		}
	}
};

/**
 * [新增]按钮按下处理（根据输入数据在表里生成一行或多行数据）jqGrid
 */
addGrid = function() {
	var flag = true;
	var repeatArr = new Array();
	var factory = $("#factory").val();
	var factoryText = $("#factory option:selected").text();
	var ObjSelect = document.frm1.ObjSelect;
	var len = ObjSelect.options.length;
	var dischargeType = $(":radio:checked").val();
	if (factory == "") {
		$("#errorFactory").show();
		flag = false;
		return flag;
	} else {
		$("#errorFactory").hide();
	}
	if (ObjSelect && ObjSelect.options && ObjSelect.options.length > 0) {
		$("#errorModel").hide();
	} else {
		$("#errorModel").show();
		flag = false;
		return flag;
	}
	if (dischargeType == "") {
		$("#errorRadio").show();
		flag = false;
		return flag;
	} else {
		$("#errorRadio").hide();
	}
	if (!flag) {
		return;
	}

	// 根据[机型]和[排放类型]的组合进行判断，不可新增重复数据。
	var obj = $("#rowedTable").jqGrid("getRowData");
	// 如果列表存在数据则插入时验证[机型]和[排放类型]
	if (obj.length > 0) {
		$(obj)
				.each(
						function() {
							var dischargeTypeTab = this["dischargeType"];
							var factoryModelTab = this["factoryModel"];
							for ( var i = 0; i < len; i++) {
								if (dischargeType == dischargeTypeTab
										&& factoryModelTab == ObjSelect.options[i].text) {
									// 把重复项添加到repeatArr中
									repeatArr.push(i);
								}
							}
						});
		var repeatArrLen = repeatArr.length;
		if (repeatArrLen == 0) {
			for ( var j = 0; j < len; j++) {
				reportInventory.factoryText = factoryText;
				reportInventory.dischargeType = dischargeType;
				reportInventory.factoryModel = ObjSelect.options[j].text;
				jQuery("#rowedTable").jqGrid('addRowData', addId,
						reportInventory);
				addId++;
			}
			// 点击新增按钮成功后，需要将右侧列表中的值在移入左侧
			moveLeftOrRight(document.frm1.ObjSelect, document.frm1.factoryModel);
			return;
		}
		// 所有数据都是重复项
		if (len == repeatArrLen) {
			alert("存在[机型]和[排放类型]重复！\n" + (len - repeatArrLen) + "件被添加");
			return;
		}
		// 添加重复项 数据
		if (repeatArrLen > 0 && repeatArrLen != len) {
			for ( var j = 0; j < len; j++) {
				if ((repeatArr + "").indexOf(j) == -1) {// indexOf
					// 返回某个指定的字符串值在字符串中首次出现的位置,0(存在)/-1(不存在)
					// A.indexOf()
					// 在原生JS中只能用于String对象
					reportInventory.factoryText = factoryText;
					reportInventory.dischargeType = dischargeType;
					reportInventory.factoryModel = ObjSelect.options[j].text;
					jQuery("#rowedTable").jqGrid('addRowData', addId,
							reportInventory);
					addId++;
				}
			}
			// 点击新增按钮成功后，需要将右侧列表中的值在移入左侧
			moveLeftOrRight(document.frm1.ObjSelect, document.frm1.factoryModel);

			// 添加数据部分重复
			alert("存在[机型]和[排放类型]重复！\n" + (len - repeatArrLen) + "件被添加");
		}

	} else {// 原列表不存在数据时直接插入
		for ( var i = 0; i < len; i++) {
			reportInventory.factoryText = factoryText;
			reportInventory.dischargeType = dischargeType;
			reportInventory.factoryModel = ObjSelect.options[i].text;
			jQuery("#rowedTable").jqGrid('addRowData', addId, reportInventory);
			addId++;
		}
		// 点击新增按钮成功后，需要将右侧列表中的值在移入左侧
		moveLeftOrRight(document.frm1.ObjSelect, document.frm1.factoryModel);
	}
};

/**
 * [显示] dropList,工厂和机型二级联动
 */
changeType = function() {
	var factoryModelKeys = new Array();
	// 工厂切换时清空机型左侧数据
	var selectElement = $("#factoryModel");
	selectElement.empty();
	// 工厂切换时清空机型右侧数据
	var objectElement = $("#ObjSelect");
	objectElement.empty();
	var factoryId = $("#factory").val();
	var modelsStr = factoryModel[factoryId];
	if (modelsStr != "") {
		factoryModelKeys = modelsStr.split(",");
		for ( var i = 0; i < factoryModelKeys.length; i++) {
			selectElement.append("<option value='" + i + "' name=''>"
					+ factoryModelKeys[i] + "</option>");
		}
	}
};

// {移动]机型选中项向左移动或向右移动
function moveLeftOrRight(fromObj, toObj) {
	var fromObjOptions = fromObj.options;
	for ( var i = 0; i < fromObjOptions.length; i++) {
		if (fromObjOptions[i].selected) {
			toObj.appendChild(fromObjOptions[i]);
			i--;
		}
	}
	// 添加完数据后再进行排序
	sortfactoryData();
}

// 左边全部右移动，或右边全部左移
function moveLeftOrRightAll(fromObj, toObj) {
	var fromObjOptions = fromObj.options;
	for ( var i = 0; i < fromObjOptions.length; i++) {
		fromObjOptions[0].selected = true;
		toObj.appendChild(fromObjOptions[i]);
		i--;
	}
	// 移动数据后再进行排序
	sortfactoryData();
}

/**
 * 对现有机型数据进行排序
 */
sortfactoryData = function() {
	// 得到左侧数据
	var factorySelect = document.frm1.factoryModel;
	var factorylen = factorySelect.options.length;
	// 得到右侧数据
	var ObjSelect = document.frm1.ObjSelect;
	var objectlen = ObjSelect.options.length;
	// 总数据量
	var totallen = factorylen + objectlen;
	// new 一个 factoryModel-左侧下拉框
	var factoryTemp = $('<select style="width: 260px;height: 85px;" id="factoryModel"  name="factoryModel" class="conditionInput" multiple ondblclick="moveLeftOrRight(document.frm1.factoryModel,document.frm1.ObjSelect)"></select>');
	// new 一个ObjSelect-右侧下拉框
	var objectTemp = $('<select style="width: 260px;height: 85px;" name="ObjSelect" id="ObjSelect"  multiple ondblclick="moveLeftOrRight(document.frm1.ObjSelect,document.frm1.factoryModel)"></select>');
	// 排序左侧机型数据
	if (factorySelect.length > 0) {
		for ( var i = 0; i < totallen; i++) {
			for ( var l = 0; l < factorylen; l++) {
				if (factorySelect.options[l] != null) {
					if (factorySelect.options[l].value == i) {
						factoryTemp.append(factorySelect.options[l]);
					}
				}
			}
		}
	}
	// 排序右侧机型数据
	if (ObjSelect.length > 0) {
		for ( var i = 0; i < totallen; i++) {
			for ( var l = 0; l < objectlen; l++) {
				if (ObjSelect.options[l] != null) {
					if (ObjSelect.options[l].value == i) {
						objectTemp.append(ObjSelect.options[l]);
					}
				}
			}
		}
	}
	// 添加option--排序后的机型数据
	$("#factoryModel").replaceWith(factoryTemp);
	$("#ObjSelect").replaceWith(objectTemp);
};

/**
 * 使用jqGrid初始化表格
 */
loadTable = function() {
	jQuery("#rowedTable").jqGrid({
		datatype : "local",
		height : 138,
		width : 685,
		colNames : [ '工厂', '机型', '排放类型', '数量' ],
		colModel : [ {
			name : 'factoryText',
			index : 'factoryText',
			sortable : true,
			width : 120,
			align : "center"
		}, {
			name : 'factoryModel',
			index : 'factoryModel',
			sortable : true,
			width : 340,
			align : "center"
		}, {
			name : 'dischargeType',
			index : 'dischargeType',
			sortable : true,
			width : 210,
			align : "center"
		}, {
			name : 'num',
			index : 'num',
			sortable : true,
			width : 200,
			align : "center",
			editable : true,
			editrules : {
				integer : true,
				minValue : 1
			}
		} // formatter:"number", summaryType:'sum',
		],
		// 定义是否可以多选
		multiselect : true,
		// 启用或者禁用单元格编辑功能
		cellEdit : true,
		// 定义了单元格内容保存位置
		cellsubmit : 'clientArray',// 单元格提交方法，数据保存在客户端。不需设置url:""
		// 定义对form编辑时的url
		editurl : "http://trirand.com/blog/jqgrid/jqgrid.html",
		// 设置表格可以显示的记录数
		rowNum : 100000,
		// 表格名称
		caption : "",
		footerrow : true,// 会在翻页栏之上增加一行
		// 当表格所有数据都加载完成而且其他的处理也都完成时触发此事件，排序，翻页同样也会触发此事件
		gridComplete : function() {
			var rowNum = parseInt($(this).getGridParam('records'), 10);
			// 当前表格存在数据
			if (rowNum > 0) {
				// 显示汇总行
				$(".ui-jqgrid-sdiv").show();
				// 处理添进来的数据
				var numTotal = jQuery(this).getCol('num', false, 'sum');
				if (numTotal == 0) {
					numTotal = "";
				}

				var flag = false;
				$(this).footerData("set", {
					dischargeType : "汇总",
					num : numTotal
				});
				$(".ui-jqgrid-ftable").find("td").each(function() {
					if ($(this).text() == " " && flag == false) {
						$(this).css("border-right-width", "0");// 第一次加载进来时设置前三列右边框宽度为0，即实现隐藏效果
					} else {
						$(this).css("font-weight", "normal"); // 每次加载进来数据时，设置数量列的字体与其他字段保持一致，默认字体为bold
						flag = true;
					}
				});

			} else {
				// 隐藏汇总行
				$(".ui-jqgrid-sdiv").hide();
			}
		},
		// 编辑数量后进行汇总刷新
		afterSaveCell : function(rowid, name, val, iRow, iCol) {
			// 取得数量的总计值
			var numTotal = jQuery(this).getCol('num', false, 'sum');
			// 设置当前数量的总计值
			$(this).footerData("set", {
				num : numTotal
			});
		}
	});
};

/**
 * [删除]删除表格数据
 */
delGrid = function() {
	var grs = jQuery("#rowedTable").jqGrid('getGridParam', 'selarrrow');
	if (grs.length > 0)
		jQuery("#rowedTable").jqGrid('delGridRow', grs, {
			reloadAfterSubmit : false
		});
	else
		alert("请选择要删除行！");
	// 若点击了全选按钮，重置全选按钮框
	$("#rowedTable").jqGrid('resetSelection');
};

/**
 * [提交]点击提交按钮传输数据
 */
function subJsonData() {
	// 是否确认提交
	if (confirmForm()) {
		// 确认提交后验证数量是否为正整数等等check
		if (!(chkBeforeSub())) {
			return;
		}

		// 为form_generated_code表单创建隐藏字段
		createNewFieldToForm("form_generated_code", "JsonData");
		createNewFieldToForm("form_generated_code", "EmailSendTo");
		createNewFieldToForm("form_generated_code", "UUID");
		createNewFieldToForm("form_generated_code", "ReportType");
		createNewFieldToForm("form_generated_code", "USERID");
		// 追加字段--返回URL
		createNewFieldToForm("form_generated_code", "ufoReturnURL");

		document.form_generated_code.method = "post";

		// 取当前日期的毫秒数
		var d = new Date();
		strUID = d.getTime();

		// json格式的表数据
		var jsonData = $("#rowedTable").jqGrid("getRowData");
		// object对象转换成String对象
		var data = JSON.stringify(jsonData);
		// 通过cookie获取的sm_session_cookie
		var smCookie = "sm_session_cookie";
		var userId = getCookie(smCookie);

		document.form_generated_code.JsonData.value = data;
		document.form_generated_code.EmailSendTo.value = "yongjing.lu@hand-china.com";
		document.form_generated_code.ReportType.value = "RI";
		document.form_generated_code.UUID.value = strUID;
		if (typeof (userId) == "undefined" || userId == "") {
			userId = "testuser";
		}
		document.form_generated_code.USERID.value = userId;
		document.form_generated_code.action = "https://ufo.deere.com/servlet/smSecureUFO?12199=1";
		document.form_generated_code.ufoReturnURL.value = "http://dlrdoc.deere.com/china/forms/jddsm/html/returnPage.html";
		document.form_generated_code.submit();
	}
}

/**
 * form_generated_code表单创建4个隐藏字段
 * 
 * @param FormId
 * @param FieldId
 */
function createNewFieldToForm(FormId, FieldId) {
	if (document.getElementById(FormId)[FieldId] == null) {
		var newItem = document.createElement("input");
		newItem.id = FieldId;
		newItem.name = FieldId;
		newItem.type = "hidden";
		newItem.value = " ";
		document.getElementById(FormId).appendChild(newItem);
	}
}

/**
 * [confrm]confirm弹出框
 * 
 * @returns {Boolean}
 */
function confirmForm() {
	var _ret = true;
	var msg;
	msg = "确认提交?";
	if (!confirm(msg)) // if user chooses not to submit their form yet, set
		// _ret to false
		_ret = false;
	else
		// else, set _ret to true (user wants to submit their form)
		_ret = true;
	return _ret; // return the result
}

/**
 * 鼠标从当前行移开时，焦点定位到当前行的第二列
 */
moveFocus = function() {
	var obj = $("#rowedTable").jqGrid("getRowData");
	if (obj.length == 0) {
		return;
	}
	for ( var i = 0; i < obj.length; i++) {
		var num = $.trim(obj[i]["num"]);
		if (num > 0 || num == '') {
		} else {
			// 焦点定位到当前行的第二列
			window.setTimeout(function() {
				$("#rowedTable").jqGrid("editCell", i + 1, 2, true);
			}, 100);
			return;
		}
	}
};

/**
 * [提交后check]点击提交按钮并确认提交后进行数量的check
 */
chkBeforeSub = function() {
	var obj = $("#rowedTable").jqGrid("getRowData");
	var cnt = 0;
	for ( var i = 0; i < obj.length; i++) {
		cnt = cnt + 1;
		var num = obj[i]["num"];
		if (num == "" || num == null || num == 0) {
			// 数量为空的时候焦点定位
			window.setTimeout(function() {
				$("#rowedTable").jqGrid("editCell", i + 1, 4, true);
			}, 100);
			alert("数量必须为正整数");
			return false;
		}
	}
	if (cnt == 0) {
		alert("没有新增记录！");
		return false;
	}
	return true;
};

/**
 * 从cookie中获取sm_session_cookie，赋值给自定义的变量
 */
function getCookie(cookiename) {
	/* 获取浏览器所有cookie将其拆分成数组 */
	var arrstr = document.cookie.split("; ");
	for ( var i = 0; i < arrstr.length; i++) {
		/* 将cookie名称和值拆分进行判断 */
		var temp = arrstr[i].split("=");
		if (temp[0] == cookiename)
			return unescape(temp[1]);
	}
}
