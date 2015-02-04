var pageCount = 0;
//var refreshTree = false;
//var itemHideFlg = false;
var jsonObectArr = [];



//查询
onSearch = function() {
	getData($("#currPage").val());
};

getData = function(pageclickednumber) {
//	selBtn = 0;
//	var pageClickedNo = pageclickednumber;
//	var pageClickedSize = $("#pageSize").val();
//	if (pageclickednumber > pageCount) pageClickedNo = pageCount; 
//    if (pageclickednumber < 1) pageClickedNo = 1;
	if (typeof(pageclickednumber) != "number") {
		pageclickednumber = 1;
	};

	pageObj.size = $("#pageSize").val();
	$("#pager").pager({ 
		pagenumber: pageclickednumber,
		pagecount: pageObj.count, 
		pagesize: pageObj.size, 
		buttonClickCallback: pageClick });	
//	$("#pager").pager({ pagenumber: pageClickedNo, pagecount: pageCount, pagesize: pageClickedSize, buttonClickCallback: pageClick });	
	var url = setUrl("findData.action", pageclickednumber, pageObj.size);
//	post(url)
	var findItem = document.getElementById("findItem").value;
	switch (findItem) {
	case 'dealer':
		$("#div_dealerTable").show();
		post(url, setParams(), null, "dealerTable");
		break;

	default:
		break;
	}
}

setParams = function() {
	createJsonStr("findItem", document.getElementById("findItem").value);
	return getJson();
}

/**
 * 创建json的nam和value的名值对字符串
 */
function createJsonStr(name,value){
	if (typeof value  == 'string'){
		this.jsonObectArr.push("\""+name+"\":"+"\""+value+"\"");
	}else{
		this.jsonObectArr.push("\""+name+"\":"+value);
	}
};
/**
 * 获取json对象
 */
function getJson(){
	var str = "{"+this.jsonObectArr.join(',')+"}";
	return $.parseJSON(str);
};
////回掉函数的数据请求
//pageClick = function(clickedbutton) {	
//	var pageclickednumber = 0;
//	var pagenumber = $("#currPage").val();
//	
//    switch (clickedbutton) {
//    case "first":
//    	pageclickednumber = 1;
//        break;
//    case "prev":
//    	pageclickednumber = parseInt(pagenumber) - 1;
//        break;
//    case "next":
//    	pageclickednumber = parseInt(pagenumber) + 1;
//        break;
//    case "last":
//    	pageclickednumber = pageCount;
//        break;
//    }
//    
//    getData(pageclickednumber);  
// };




//// 请求数据
//function post(url) {
//	$.ajax({
//    	// 后台处理程序
//    	url : url,
//    	// 数据发送方式
//    	type : "post",
//    	// 接受数据格式
//    	dataType : "json",
//    	// 要传递的数据
//    	data : setParams(),
//    	// 回传函数
//    	success : function(json)
//    	{   		
//			update_page(json);
////    		alert(json.findItem);
////    		if (json != null) {
////    			switch (json.findItem) {
////				case 'dealer':
////					alert("dealer");
////					break;
////
////				default:
////					break;
////				}
////    		}
////    		alert("success:"+json);
//	    }
//	});
//}
//
//function setUrl(actionName, pageNo, pageSize) {
//	return actionName + "?page.pageNo=" +  pageNo + "&page.pageSize=" + pageSize;
//}
//
//// 显示list数据
////update_page在进入界面或者点击搜索，删除按钮后被调用，result是object型
////           也可以在点击上一页或者下一页用，result是string型
//function update_page(result) {
//	var json;
//
//	json = eval( "("+result+")" );
//	if(typeof(json.failmsg) != "undefined") {
//		alert(json.failmsg);
//		refreshTree = false;
//		return;
//	} 
//	
//	pageCount = json.pagecount;
//
//	$("#currPage").attr("value", json.pageno);
//	
//	//存储页面总数
//	$("p1").text(json.pagecount);
//	//存储查询结果总条数
//	$("p0").text(json.resultcount);
//	
//	//设置当前页数，因为修改表格最大显示数后，当前页数可能会变	
//	//tbody 是用来生成table的数据
//	var tbody = $('<tbody></tbody>');
//
//	if (json.isempty === "false") {//查询结果不为空
//		for(var i = 0; i < json.data.length; i ++ ) {
//		    tbody.append(getRow(json.data[i]));
//		}
//	} else {//查询结果为空
//		tbody.append(getemptyRow(json.data[0]));
//	}
//	//生成有数据的tr td
//	//根据表格最大显示条数补足空的tr td
//	for (var j=json.data.length; j < json.pagesize; j++ ) {
//		tbody.append(getemptyRow(json.data[0]));
//	}
//	//替换table的数据
//	$('#myTable tbody').replaceWith(tbody);	
//	//重新查询后，设置全选按钮为不选中
//	if (document.getElementsByName("chks")[0] != " undefined")
//	    document.getElementsByName("chks")[0].checked = false;
//}
//
////生成表格的一行（有数据）
//function getRow(row) {
//  var tr = $('<tr></tr>');
//  //row的第一个字段，必须为dbid，删改都要通过dbid
//	var index = 0;
//
//	//store dbid's value
//	var rowdbid;
//    for (var i in row) {
//    	if (index == 0) {
//    		//row的第一个字段，必须为dbid
//    		rowdbid = row[i];
//    		
//			//generate checkbox
//    		tr.append("<td style='text-align:center;'><input type='checkbox' name='chkIDs' value='" + rowdbid + "' onclick='chgStatus(\"" + row[i] + "\");'/></td>");
//    		//generate modify url
//    		modifystr = "<td><a href='javascript:onUpdate(" + JSON.stringify(row) + ")'>变更</a></td>";
//    	} else if (index == 1){//第一列点击后可以查看详细信息
//    		tr.append("<td><a href='javascript:onView(" + JSON.stringify(row) + ")'>" + row[i] + "</a></td>");
//    	} else {
//    		tr.append('<td>' + row[i] + '</td>');
//    	}
//		index++;
//    }
//    
//    if (itemHideFlg == false) {
//    	tr.append(modifystr);
//    }
//    
//    return tr;
//}
////生成表格的一行（无数据）
//function getemptyRow(row) {
//	var showTh = $("#myTable th").length;
//	if (itemHideFlg == true) {
//		showTh = showTh -1;
//	}
//	var tr = $('<tr></tr>');
//	for (var i=0; i<showTh; i++) {
//    	tr.append('<td>&nbsp;</td>');
//    }
//	
//	return tr;
//}
////检查每页显示条数只能是正整数
//function checkCurrent(value) {
//	var ex = /^\d+$/;
//	if (ex.test(value) === false) {
//	    alert("每页条数只能输入正整数");
//	    $("#currPage").attr("value", 1);
//	}
//}
////检查当前页数只能是正整数
//function checkPageSize(value) {
//	var ex = /^\d+$/;
//	if (ex.test(value) === false) {
//	    alert("第几页只能输入正整数");
//	    $("#pageSize").attr("value", 10);
//	}
//}