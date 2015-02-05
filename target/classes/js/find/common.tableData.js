var pageObj = {
		count : 1,
		current : 1,
		curSize : 0,
		size : 10,
		total : 0
};
var pageCallBack = null;

//var refreshTree = false;
//var itemHideFlg = false;
var listColumns = {};

function objectToJson(object) {
	var obj = objectToKeyValue(object);
	var json = "";
	for (var key in obj) {
		if (json.length > 0) {
			json += ",";
		}
		if (typeof (obj[key])  == 'string'){
	        json += "\"" + key + "\":" + "\"" + obj[key] + "\"";
	    }else{
	        json += "\"" + key + "\":" + obj[key];
	    }
	}
	
	json = "{" + json + "}";
	return json;
}
/**
 * 转换JS对象为KEY/VALUE对形式
 * @param obj
 * @returns  返回 {KEY:VALUE}格式的键/值对
 */
function objectToKeyValue(obj) {

	if (typeof(obj) != "object") {
		return null;
	}
	
	var result = {};
	var value;
	for(var key in obj) {
		value = obj[key];
		if (typeof(value) == "string" && value != null) {
			result[key] = value;
			continue;
		}
		
		if (typeof(value) == "object" && value != null) {
			if (value instanceof Array) {
				value = arrayToKeyValue(key, obj[key]);
				
				if (value == null) {
					continue;
				}
				for (var subKey in value) {
					result[subKey] = value[subKey];
				}
			}
			else {
				value = objectToKeyValue(obj[key]);
				
				if (value == null) {
					continue;
				}
				for (var subKey in value) {
					result[key + "." + subKey] = value[subKey];
				}
			}
		}

	}
	return result;
}

/**
 * 转换JS数组为KEY/VALUE对形式
 * @param name      数组变量名
 * @param array     数组对象
 * @returns         返回 {KEY:VALUE}格式的键/值对
 */
function arrayToKeyValue(name, array) {
	if (typeof(name) != "string" || name == "") {
		return null;
	}
	
	if (!(array instanceof Array)) {
		return null;
	}
	
	var result = {};
	var value;
	
	for(var index = 0; index < array.length; index++) {
		value = array[index];
		
		if (typeof(value) == "string" && value != null) {
			result[name + "[" + index + "]"] = value;
			continue;
		}
		
		if (typeof(value) == "object" && value != null) {
			if (value instanceof Array) {
				value = arrayToKeyValue(name + "[" + index + "]", array[index]);
				
				if (value == null) {
					continue;
				}
				else {
					for (var subKey in value) {
						result[subKey] = value[subKey];
					}
				}
			}
			else {
				value = objectToKeyValue(array[index]);
				
				if (value == null) {
					continue;
				}
				else {
					for (var subKey in value) {
						result[name + "[" + index + "]" + "." + subKey] = value[subKey];
					}
				}
			}
		}

	}
	return result;
}

/**
 * 转换JS对象, 写入到form表单中, 作为表单项发送到服务端
 * @param name          对象名称, 对应action中定义的变量名称
 * @param data          数据
 *                      数据结构与服务端变量保持一致
 * @param formId        表单ID
 * @returns {Boolean}
 */
function objectToFormData(name, data, formId) {
	if (typeof(name) != "string" || name == "") {
		return false;
	}
	if (typeof(data) != "object" || (typeof(formId) != "string" || formId == "") ) {
		return false;
	}
	
	var obj = data;
	if (obj instanceof Array) {
		obj = arrayToKeyValue(name, obj);
		
		for(var key in obj) {
			value = obj[key];
			
			var newNode = document.createElement("input");
			newNode.setAttribute("type", "hidden");
			newNode.setAttribute("name", key);
			newNode.setAttribute("value", value);
			document.getElementById(formId).appendChild(newNode);
		}
	}
	else {
		obj = objectToKeyValue(obj);
		
		for(var key in obj) {
			value = obj[key];

			var newNode = document.createElement("input");
			newNode.setAttribute("type", "hidden");
			newNode.setAttribute("name", name + "." + key);
			newNode.setAttribute("value", value);
			document.getElementById(formId).appendChild(newNode);
		}
	}
	
	return true;
}

/**
 * AJAX请求数据通用方法
 * @param url          URL
 * @param data         输入数据参数可以为字符串, 也可以是{KEY : VALUE}格式的对象
 * @param successFn    请求成功回调函数  默认传入回调函数的参数为服务器返回的json数据
 * @param tableId      回传输入写入的表格ID, 用于默认的回调函数处理
 * @param errorFn      请求失败回调函数
 */
function post(url, data, successFn, tableId, errorFn) {
	var Fn = (typeof(successFn) == "function") ? successFn :
		function(json) {   		
		
		if (typeof(json) == "string") {
			json = eval("(" + json + ")");
		}
		if (typeof(json) != "object" || json == null) {
			return ;
		}
		update_page(json, tableId);
    };
    
    var error = (typeof(errorFn) == "function") ? errorFn :
		function(json) {
    		if (typeof(json) == "string") {
    			alert(json);
    		}
    		else {
    			alert("数据读取错误");
    		}
    	};
	$.ajax({
    	// 后台处理程序
    	url : url,
    	// 数据发送方式
    	type : "post",
    	// 接受数据格式
    	dataType : "json",
    	// 要传递的数据
    	data : data,
    	// 回传函数
    	success : Fn,
    	
    	error : error
	});
}

function setUrl(actionName, pageNo, pageSize) {
	return actionName + "?page.pageNo=" +  pageNo + "&page.pageSize=" + pageSize;
}

//分页组件回掉函数的数据请求
function pageClick(clickedbutton) {
	var pageclickednumber = 1;
	var curPage = pageObj.current;
	var pageCount = pageObj.count;
	
	switch (clickedbutton) {
	 case "first":
	 	if (curPage == 1) {
	 		return;
	 	}
	 	pageclickednumber = 1;
	     break;
	 case "prev":
	 	if (curPage == 1) {
	 		return;
	 	}
	 	pageclickednumber = curPage - 1;
	     break;
	 case "next":
	 	if (curPage == pageCount) {
	 		return;
	 	}
	 	pageclickednumber = curPage + 1;
	     break;
	 case "last":
	 	if (curPage == pageCount) {
	 		return;
	 	}
	 	pageclickednumber = pageCount;
	     break;
	 }
	 
	pageCallBack(pageclickednumber);
};

/**
 * 刷新显示list数据
 * update_page在进入界面或者点击搜索，删除按钮后被调用，result是object型
 *            也可以在点击上一页或者下一页用，result是string型
 * @param result
 * @param tableId
 */
function update_page(result, tableId) {
	
	var json;
	//初始化参数
	if (typeof(result) == "object") {
		json = result;
	}
	else {
		json = eval( "("+result+")" );
	}
	tableId = (tableId == null) ? "myTable" : tableId;

//	if(typeof(json.failmsg) != "undefined") {
//		refreshTree = false;
//		return;
//	} 

	//设置分页组件
	if ($("ul#pager").length > 0) {
		
		pageObj.count = json.pagecount;
		pageObj.current = json.pageno;
		pageObj.size = json.pagesize;
		pageObj.total = json.resultcount;
		pageObj.curSize = json.curSize;

		$("#currPage").attr("value", json.pageno);
		
		//存储页面总数
		$("p1").text(json.pagecount);
		//存储查询结果总条数
		$("p0").text(json.resultcount);
		
	}
	
	//tbody 是用来生成table的数据
	var tbody = $('<tbody></tbody>');

	initList(tableId);
	
	initSubList("findSubTable");
	
	addRows(tbody, json.data, json.pagesize, tableId);

	//替换table的数据
	$("#" + tableId).children("tbody").replaceWith(tbody);	
	//重新查询后，设置全选按钮为不选中
	//document.getElementsByName("chks")[0].checked = false;
	$("#" + tableId).children("thead").find("input[type=checkbox]").attr("checked", "");
	presentCount  = 0;
}

/**
 * 初始化表格列名集合
 * @param tableId
 */
function initList(tableId) {

	if (typeof(listColumns[tableId]) == "undefined"
		|| listColumns[tableId] == null) {
		var heads = $("#" + tableId + " thead tr:last th");
		if (heads == null) {
			return ;
		}
		
		var columns = [];
		
		for(var index = 0; index < heads.length; index++) {
			columns[index] = initColumnItem(heads.eq(index));
		}
		
		listColumns[tableId] = columns;
	}
}

function initColumnItem(item) {
	var colId = item.attr("id");
	var colName = item.attr("name");
	var eventKey = item.attr("eventKey");
	var eventName = item.attr("eventName");
	var show = item.attr("show");
	var element = item.attr("element");
	var colMsg = item.attr("message");

	if (typeof(colId) == "undefined" || colId == null || colId == "") {
		colId = item.index() + "";
	}
	if (typeof(colName) == "undefined" || colName == null || colName == "") {
		colName = colId;
	}
	if (typeof(eventKey) == "undefined" || eventKey == null) {
		eventKey = "";
	}
	if (typeof(eventName) == "undefined" || eventName == null) {
		eventName = "";
	}
	if (typeof(show) == "undefined" || show == null) {
		show = "";
	}
	if (typeof(element) == "undefined" || element == null) {
		element = "";
		if (item.children("input[type=checkbox]").length > 0) {
			element = "checkbox";
		}
	}
	
	if (typeof(colMsg) == "undefined" || colMsg == null) {
		colMsg = "";
	}
	
	column = {};
	column.key = colId;
	var keySet = colId.split(";");
	column.keySet = [];
	column.separator = [];
	for (var index = 0; index < keySet.length; index++) {
		var line = keySet[index].split(",");
		for (var i = 0; i < line.length; i++) {
			if (line[i] != "") {
				column.keySet.push(line[i]);
				column.separator.push("SPACE");
			}
		}
		column.separator.pop();
		column.separator.push("ENTER");
	}
	column.separator.pop();
	
	column.paramName = colName;
	column.event = eventKey.trim().split(",");
	column.eventName = eventName.trim().split(",");
	column.show = show.trim().split(",");
	for(var index = 0; index < column.show.length; index++) {
		var temp = column.show[index];
		column.show[index] = (temp != "0" && temp != "false") ? true : false;
	}
	for(var index = column.show.length; index < column.event.length; index++) {
		column.show[index] = true;
	}
	column.element = element.trim();
	
	column.msg = colMsg.trim();
	
	column.hide = ((item.css("display") == "none") ? true : false);
	column.width = item.css("width");
	return column;
}

/**
 * 
 * @param index
 * @param body
 * @param hide
 * @returns {Boolean}
 */
function refreshDataTable(index, body, hide) {
	if(index < 0 || typeof(body) != "object" || body == null) {
		return false;
	}

	body.find("tr").each(function() {
		$(this).children("td").eq(index).css("display", hide ? "none" : "");
	});
	return true;
}

/**
 * 更新一列的状态
 * @param id       列ID
 * @param tableId
 * @returns {Boolean}
 */
function updateColumnItemById(id, tableId) {
	if (typeof(id) != "string" || id == "") {
		return false;
	}
	
	var columns = null;
	var index = -1;
	var refresh = false;
	if (typeof(listColumns[tableId]) == "undefined"
		|| listColumns[tableId] == null) {
		initList(tableId);
		refresh = true;
	}
	
	columns = listColumns[tableId];
	for(index = 0; index < columns.length; index++) {
		if (id == columns[index].key) {
			break;
		}
	}
	if (index >= columns.length) {
		return false;
	}
	
	if (! refresh) {
		var heads = $("#" + tableId).children("thead").children("tr:last").children("th");
		if (heads == null) {
			return false;
		}
		columns[index] = initColumnItem(heads.eq(index));
		listColumns[tableId] = columns;
	}
	
	refreshDataTable(index, $("#" + tableId).children("tbody"), columns[index].hide);
	return true;
}

/**
 * 更新一列的状态
 * @param index     列索引
 * @param tableId
 * @returns {Boolean}
 */
function updateColumnItemByColumnIndex(index, tableId) {
	if (typeof(index) != "number" || index < 0) {
		return false;
	}
	
	var refresh = false;
	if (typeof(listColumns[tableId]) == "undefined"
		|| listColumns[tableId] == null) {
		initList(tableId);
		refresh = true;
	}

	var columns = listColumns[tableId];
	if (index >= columns.length) {
		return false;
	}
	
	if (! refresh) {
		var heads = $("#" + tableId).children("thead").children("tr:last").children("th");
		if (heads == null) {
			return false;
		}
		
		columns[index] = initColumnItem(heads.eq(index));
		listColumns[tableId] = columns;
	}
	
	refreshDataTable(index, $("#" + tableId).children("tbody"));
	return true;
}

/**
 * 取得表格列名集合
 * @param tableId
 * @returns   返回null表示无结果, 返回数组为集合数据
 *            数组元素为对象类型
 *            {
 *                key : 列名(对应表格头部th/td元素的id)
 *                event : 操作事件定义
 *                eventName : 操作事件名称, 用于点击链接的文字显示
 *            }
 */
function getListByTableId(tableId) {
	if (typeof(tableId) != "string"
		|| tableId == null || tableId == "") {
		return null;
	}
	
	if (typeof(listColumns[tableId]) == "undefined"
		|| listColumns[tableId] == null) {
		initList(tableId);
	}
	
	return listColumns[tableId];
}

/**
 * 取得表格列名集合
 * @param tableId
 * @returns         返回null表示无结果, 返回数组为集合数据
 *                  数组元素为列名(对应表格头部th/td元素的id)字符串
 */
function getColumnIdListByTableId(tableId) {
	if (typeof(tableId) != "string"
		|| tableId == null || tableId == "") {
		return null;
	}
	
	if (typeof(listColumns[tableId]) == "undefined"
		|| listColumns[tableId] == null) {
		initList(tableId);
	}
	
	var colNameList = [];
	var tempList = listColumns[tableId];
	for(var index = 0; index < tempList.length; index++){
		colNameList[index] = tempList[index].key + "";
	}
	return colNameList;
}

/**
 * 取得表格列名集合
 * @param tableId
 * @returns         返回null表示无结果, 返回数组为集合数据
 *                  数组元素为列名(对应表格头部th/td元素的name)字符串
 */
function getColumnNameListByTableId(tableId) {
	if (typeof(tableId) != "string"
		|| tableId == null || tableId == "") {
		return null;
	}
	
	if (typeof(listColumns[tableId]) == "undefined"
		|| listColumns[tableId] == null) {
		initList(tableId);
	}
	
	var colNameList = [];
	var tempList = listColumns[tableId];
	for(var index = 0; index < tempList.length; index++){
		colNameList[index] = tempList[index].paramName + "";
	}
	return colNameList;
}

/**
 * 插入list数据
 * 参见update_page
 * 
 * @param result
 * @param tableId
 * @param index
 */
function insert(result, tableId, index) {
	
	var json;
	//参数初始化
	if (typeof(result) == "object") {
		json = result;
	}
	else {
		json = eval( "("+result+")" );
	}
	tableId = (typeof(tableId) == "undefined" 
		|| tableId == null || tableId == "") ? "myTable" : tableId;
	index = (typeof(index) == "undefined" || index == null) ? -1 : index;
	
	//取得当前table表格对象
	var tbody = $("#" + tableId).children("tbody").eq(0);

	initList(tableId);
	//插入table数据
	insertRows(tbody, json.data, index, tableId);	
	
	//重新查询后，设置全选按钮为不选中
	//document.getElementsByName("chks")[0].checked = false;
	$("#" + tableId).children("thead").find("input[type=checkbox]").attr("checked", "");
}

/**
 * 更新表格
 * 添加数据
 * @param tbody
 * @param data
 * @param pageSize
 * @param tableId
 */
function addRows(tbody, data, pageSize, tableId) {
	
	for(var i = 0; i < data.length; i ++ ) {
	    tbody.append(getRow(data[i], tableId));
	}
	for (var j = data.length; j < pageSize; j++ ) {
		tbody.append(getemptyRow(tableId));
	}
}

/**
 * 更新表格
 * 插入数据
 * @param tbody
 * @param data
 * @param index
 * @param tableId
 */
function insertRows(tbody, data, index, tableId) {
	
	if (index < 0 || tbody.children("tr").length == 0 || index >= tbody.children("tr").length) {
		for(var i = 0; i < data.length; i ++ ) {
		    tbody.append(getRow(data[i], tableId));
		}
	}
	else {
		var indexTr = tbody.children("tr").eq(index);
		for(var i = 0; i < data.length; i ++ ) {
			indexTr.before(getRow(data[i], tableId));
		}
	}
	
}


function replaceRowData(row) {
	
	switch(typeof(row)) {
	case "object":
		if (row != null) {
			var data = {};
			for (var item in row) {
				data[item] = replaceRowData(row[item]);
			}
			return data;
		}
		else {
			return "";
		}
	case "string":
		return row.replace(/</g, "&lt;").replace(/>/g, "&gt;");//&quot;
	default:
		return "";
	}
}

var precentType = '';
var presentCount  = 0;
//生成表格的一行（有数据）
function getRow(row, tableId) {
	
	var data = replaceRowData(row);

    var tr = $('<tr style="font-family:Arial;font-size:13px;"></tr>');
    
	var columns = listColumns[tableId];
    for (var i = 0;i < columns.length; i++) {
    	var values = [];
    	for (var setIndex = 0; setIndex < columns[i].keySet.length; setIndex++) {
    		var temp = data[columns[i].keySet[setIndex]];
    		
    		if (typeof(temp) == "undefined" || temp == null) {
    			temp = "";
    		}
    		values.push(temp);
    	}
    	var events = columns[i].event;
    	var shows = columns[i].show;
    	var element = columns[i].element;
    	var separator = columns[i].separator;
    	var hide = columns[i].hide;
    	var width = columns[i].width;
    	var msg = columns[i].msg;
    	if (typeof(value) == "undefined" || value == null) {
    		value = "";
		}
    	if (i == 0) {
    		//row的第一个字段
    		if (element == "radio") {
    			tr.append("<td style='text-align:center;vertical-align: middle;" + "width:" + width + ";" +
    					(hide ? " display:none;" :"") + "'><input type='radio' name='chkIDs' value='" 
        				+ values[0] + "'/></td>");
    		}
    		else if(element == "checkbox"){
    			tr.append("<td style='text-align:center;vertical-align: middle;" + "width:" + width + ";" +
    					(hide ? " display:none;" :"") + "'><input type='checkbox' name='chkIDs' value='" 
        				+ values[0] + "' onclick='changeStatus(this);'/></td>");
    		} else if(element == "percent") {
    			var pictureUrl = "";
    			var str = 'images/persent.jpg';
    			var html = '';
    			var strPrecent = "100%";
    			var nowPrecentType = null;
    			var presentColor='';
    		    
    			switch(values[0]) {
    				case '1' :pictureUrl = "images/persent/matchDegree100.png"; 
	    				if(precentType == '') {
	    					precentType = '1';
	    				}
	    				presentCount++;
    					nowPrecentType = '1'; presentColor = '#95A8DF'; break;
    				case '2' :pictureUrl = "images/persent/matchDegree75.png"; 
	    				if(precentType == '') {
	    					precentType = '2';
	    				}
	    				presentCount++;
    					strPrecent = '75%-100%'; nowPrecentType = '2'; presentColor = '#B8C5DF';break;
    				case '3' :pictureUrl = "images/persent/matchDegree50.png"; 
	    				if(precentType == '') {
	    					precentType = '3';
	    				}
	    				presentCount++;
    					strPrecent = '50%-75%';  nowPrecentType = '3'; presentColor = '#E4E4E4';break;
    				case '4' :pictureUrl = "images/persent/matchDegree25.png"; 
	    				if(precentType == '') {
	    					precentType = '4';
	    				}
	    				presentCount++;
    					strPrecent = '25%-50%';  nowPrecentType = '4';presentColor = 'white';break;
    				default :pictureUrl = "images/persent/matchDegree100.png";presentCount++;break;
    			}
    			if((precentType != nowPrecentType) || presentCount == 1 ) {
    				precentType = nowPrecentType;
    				html = '<img src="' + pictureUrl 
    					+ '" width="30" height="30" style="text-align: center;"/>' + '<br>' + strPrecent;
    			} 
    			str = "<td style='text-align:center;vertical-align: middle;border-bottom: hidden ;background-color:"+presentColor+";" 
    				+ "width:" + width + ";" +(hide ? " display:none;" :"") + "'>"+html+"</td>";
    			tr.append(str);
    		} else {
    			tr.append("<td style='text-align:center;vertical-align: middle;" + "width:" + width + ";" +
    					(hide ? " display:none;" :"") + "'>" + values[0] + "</td>");
    		}
    	} else {
			var innerHtml = "";
			for (var index = 0; index < values.length; index++) {
				var value = values[index];
				var valueText = value;
				
				switch(typeof(value)) {
				case "object":
					value = JSON.stringify(value);
					valueText = value;
					break;
				case "string":
					valueText = value.replace(/\\/g, "\\\\").replace(/\"/g, "\\\"");
					valueText = "\"" + valueText + "\"";
					break;
				default:
					value = "";
				valueText = "\"" + value + "\"";
				break;
				}
    		
    		if (element == "radio") {
    			tr.append("<td style='text-align:center;vertical-align: middle;" + "width:" + width + ";" +
    					(hide ? " display:none;" :"") + "'><input type='radio' name='chkIDs' value='" 
        				+ value + "'/></td>");
    		}
    		else if(element == "checkbox"){
    			tr.append("<td style='text-align:center;vertical-align: middle;" + "width:" + width + ";" +
    					(hide ? " display:none;" :"") + "'><input type='checkbox' name='chkIDs' value='" 
        				+ value + "' onclick='changeStatus(this);'/></td>");
    		} else if(element == "picture"){
    			var pictureHtml = '';
    			var orgPictureUrl = 'images/persent/orgCode_gray.png';
    			if($.trim(value) == '1') {
    				orgPictureUrl = 'images/persent/orgCode.png';
    			} 
    			pictureHtml = '<img src="' + orgPictureUrl 
    						+ '" width="50" height="30" style="text-align: center;"/>' ;
    			tr.append("<td style='text-align:center;vertical-align: middle;" + "width:" + width + ";" +
    					(hide ? " display:none;" :"") + "'>"+pictureHtml+"</td>");
    		}
    		
    		else {
    			
    		if (msg.length > 0) {
    			tr.append(getTableTd(values[0], events, data));
    		} else {
        			
        			if (index < events.length && (events[index].length > 0) 
        					&& (events[index] != "NONE" && ! ( (! shows[index]) && (value.length == 0) ))) {
            			
        				var event = events[index].trim();
            			var method = "";
            			var showValue = (index < columns[i].eventName.length) ? columns[i].eventName[index] : "";
            			showValue = (typeof(showValue) == "undefined" || showValue == null) ? "" : showValue;
            			
            			switch(event) {
        				case "view"://查看详细信息
        					method = "onView";
        					showValue = (showValue == "") ? value : showValue;
        					break;
        				case "update":
        					method = "onUpdate";
        					showValue = (showValue == "") ? value : showValue;
        					break;
        				default:
        					method = "on" + event.substr(0, 1).toUpperCase() + event.substring(1);
        					showValue = (showValue == "") ? value : showValue;
        					break;
        				}
            			
            			var rowText = JSON.stringify(data).replace(/\"/g, "&quot;");
            			valueText = valueText.replace(/\"/g, "&quot;");
            			var dispImg = data["dispImg"];
            			if (typeof(dispImg) != "undefined" && dispImg != null && dispImg != "") {
            				innerHtml += '<img src="downloadImg.action?filePath=' + dispImg 
		          			  		  + '" width="100" height="60" style="text-align: center;"/>' + '<br>';            				
            			}
            			            			
            			innerHtml += "<a style='text-decoration:underline;cursor: pointer;' href='javascript:void(0)' onclick=\"" + 
            					method + "(" 
        						+ rowText + ", this, " + valueText + ")\">" + showValue + "</a>";
            		}
            		else {
            			innerHtml += value;
            		}
        			
        			if (index < values.length - 1) {
        				innerHtml += (separator[index] == "ENTER") ? "<br>" : "&nbsp;";
        			}
        		}

        		tr.append("<td style='text-align:center; vertical-align: middle;" + "min-width:" + width + ";" +
    					(hide ? " display:none;" :"") + "'>" + innerHtml + "</td>");    			
    		}
    		}
    	}
    }
    
    return tr;
}

// 取得包含6复数列的表
function getTableTd(value, events, data) {
	var val = [];
	var td = $('<td></td>');
	var table = $('<table width="100%" border="0"></table>');
	
	if (events[0].length > 0) {
		
		var event = events[0].trim();
		var method = "on" + event.substr(0, 1).toUpperCase() + event.substring(1);
		var rowText = JSON.stringify(data).replace(/\"/g, "&quot;");
		var valueText = data["formId"];
		
		var tdEdit = "<td style='cursor: pointer;' onclick=\"" + method + "(" + rowText + ", this, '" + valueText + "')\">" + "</td>";	
		td = $(tdEdit);
	}

	var columns = listSubColumns['findSubTable'];
	
	var index = value.indexOf("#", 0);
	while (index >= 0) {
		val.push(value.substring(0, index));
		value = value.substring(index + 1);
		index = value.indexOf("#", 0);
	} 
	
	if (index == -1) {
		val.push(value);
	}
	
	var width;
	var len = 0;
	var colIndex = 0;
	var colval = [];
	for (var i = 0;i < val.length; i++) {
		
		var rowVal = val[i];
		var tr = $('<tr></tr>');
		colval = [];
		colIndex = rowVal.indexOf(",", 0);
		while (colIndex >= 0) {
			colval.push(rowVal.substring(0, colIndex));
			rowVal = rowVal.substring(colIndex + 1);
			colIndex = rowVal.indexOf(",", 0);
		} 
		
		if (colIndex == -1) {
			colval.push(rowVal);
		}
		
		if (i == 0) len = colval.length;
		
		for (var j = 0;j < columns.length; j++) {
			
			if (typeof(colval[j]) == "undefined" || colval[j] == null || colval[j] == "") continue;			

			width = columns[j].width;
									
			if (j%2 == 0) {
				align = "<td style='text-align:right;' width='" + width + "'>";
			} else {
				if (len == colval.length) {
					align = "<td style='text-align:left;margin-left: 15px;' width='" + width + "'>";
					colval[j] = colval[j];
				} else {
					align = "<td colspan='3' style='text-align:left;margin-left: 15px;'>";	
					colval[j] = colval[j];
				}					
			}
								
			tr.append(align + $.trim(colval[j]) + "</td>");
		}
		
		table.append(tr);
	}
	
	td.append(table);

	return td;
}

//生成表格的一行（无数据）
function getemptyRow(tableId) {
	var tr = $('<tr></tr>');
	var columns = listColumns[tableId];
	for (var i = 0; i < listColumns[tableId].length; i++) {
		var hide = columns[i].hide;
		var width = columns[i].width;
    	tr.append("<td style='text-align:center;" + "width:" + width + ";" +
			(hide ? " display:none;" :"") + "'>&nbsp;</td>");
    }
	
	return tr;
}

//登录用户没有权限的item隐藏
function itemHide(identList, item) {
    if(identList.indexOf(item.attr("url")) == -1) {
    	item.hide();
    	return true;
    }
    
    return false;
}

//检查每页显示条数只能是正整数
function checkCurrent(value) {
	var ex = /^\d+$/;
	if (ex.test(value) === false) {
	    alert("每页条数只能输入正整数");
	    $("#currPage").attr("value", 1);
	}
}
//检查当前页数只能是正整数
function checkPageSize(value) {
	var ex = /^\d+$/;
	if (ex.test(value) === false) {
	    alert("第几页只能输入正整数");
	    $("#pageSize").attr("value", 10);
	}
}

var listSubColumns = {};
/**
 * 初始化表格列名集合
 * @param tableId
 */
function initSubList(tableId) {

	if (typeof(listSubColumns[tableId]) == "undefined"
		|| listSubColumns[tableId] == null) {
		var heads = $("#" + tableId + " thead tr:last th");
		if (heads == null) {
			return ;
		}
		
		var columns = [];
		
		for(var index = 0; index < heads.length; index++) {
			columns[index] = initColumnItem(heads.eq(index));
		}
		
		listSubColumns[tableId] = columns;
	}
}
