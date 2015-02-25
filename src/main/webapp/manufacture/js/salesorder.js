var part = {
	partCode : "",
	partName : "",
	partType : "",
	quantity : "",
	material : ""
};
var addId = 0;
var lastSel = 1;
checkDuplicate = function(TpartCode) {
	var obj = $("#proTable").jqGrid("getRowData");
	var flag = true;
	if (obj.length > 0) {
		$(obj).each(function() {
			var partCode = this["partCode"];
			if (partCode == TpartCode) {
				alert("物料号重复");
				flag = false;
			}
		});
	}
	return flag;

};

addPro = function(rowData) {
	part.partCode = rowData.partCode;

	part.partName = rowData.partName;
	part.partType = rowData.partType;
	part.material = rowData.material;
	$("#partResult").hide();
	if (checkDuplicate(part.partCode)) {

		jQuery("#proTable").jqGrid('addRowData', addId, part);
		addId = addId + 1;

		window.setTimeout(function() {
			$("#proTable").jqGrid("editCell", addId, 4, true);
		}, 10);
	}

};

delGrid = function() {
	var grs = jQuery("#proTable").jqGrid('getGridParam', 'selarrrow');
	if (grs.length > 0)
		jQuery("#proTable").jqGrid('delGridRow', grs, {
			reloadAfterSubmit : false
		});
	else
		alert("请选择要删除行！");
	// 若点击了全选按钮，重置全选按钮框
	$("#proTable").jqGrid('resetSelection');
};

onCheckEnter = function(e) {
	var keyCode = null;

	if (e.which)
		keyCode = e.which;
	else if (e.keyCode)
		keyCode = e.keyCode;

	if (keyCode == 13)
		onPartSearch();

};

onPartSearch = function() {
	$("#partResult").show();
	var partType = document.getElementById("partType").value;
	var partCode = document.getElementById("partCode").value;
	var query = "partCode=" + partCode + "&partType=" + partType;
	$("#partTable").jqGrid('GridUnload');
	$("#partTable").jqGrid({

		onSelectRow : function(id) {
			if (id && id !== lastSel) {
				jQuery('#partTable').restoreRow(lastSel);
				lastSel = id;
			}
			var rowData = $("#partTable").jqGrid("getRowData", id);//
			addPro(rowData);
		},
		url : "searchMachine.action?" + query,
		datatype : "json", // 数据来源，本地数据
		mtype : "POST",// 提交方式
		height : 120,// 高度，表格高度。可为数值、百分比或'auto'
		width : 600,// 这个宽度不能为百分比
		autowidth : true,// 自动宽
		colNames : [ '物料号', '物料名称', '零件类型', '材料' ],
		colModel : [
		// {name:'id',index:'id', width:'10%', align:'center' },

		{
			name : 'partCode',
			index : 'partCode',
			width : '25%',
			align : 'left'
		}, {
			name : 'partName',
			index : 'partName',
			width : '30%',
			align : "left"
		}, {
			name : 'partType',
			index : 'partType',
			width : '15%',
			align : "center"
		}, {
			name : 'material',
			index : 'material',
			width : '15%',
			align : "left"
		} ],
		rownumbers : true,// 添加左侧行号
		// altRows:true,//设置为交替行表格,默认为false
		// sortname:'createDate',
		// sortorder:'asc',
		viewrecords : true,// 是否在浏览导航栏显示记录总数
		rowNum : 15,// 每页显示记录数
		// rowTotal:2000,
		rowList : [ 15, 20, 25 ],// 用于改变显示行数的下拉列表框的元素数组。
		loadonce : true,
		gridview : true,
		viewrecords : true,
		jsonReader : {
			root : "partList",
			repeatitems : false
		},
		pager : $('#partPager')
	});

};

getSystemData = function(day) {
	var myDate;
	if (day == undefined) {
		myDate = new Date();
	} else {
		myDate = new Date(day);
	}
	var strDate = myDate.getFullYear() + "/"
			+ ((myDate.getMonth() + 1) < 10 ? "0" : "")
			+ (myDate.getMonth() + 1) + "/"
			+ (myDate.getDate() < 10 ? "0" : "") + myDate.getDate();
	return strDate;
};

loadTable = function() {
	$("#deliveryDate").datepicker({
		changeMonth : true,
		changeYear : true,
		dateFormat : "yy/mm/dd"
	});
	$("#deliveryDate").val(getSystemData());
	
	$("#dueDate").datepicker({
		changeMonth : true,
		changeYear : true,
		dateFormat : "yy/mm/dd"
	});
	$("#dueDate").val(getSystemData());
	jQuery("#proTable").jqGrid({
		datatype : "local",
		height : 400,
		// width : 685,
		autowidth : true,
		colNames : [ '物料号', '物料名称', '零件类型', '數量' ],
		colModel : [ {
			name : 'partCode',
			index : 'partCode',
			sortable : true,
			width : 120,
			align : "center"
		}, {
			name : 'partName',
			index : 'partName',
			sortable : true,
			width : 340,
			align : "center"
		}, {
			name : 'partType',
			index : 'partType',
			sortable : true,
			width : 210,
			align : "center"
		}, {
			name : 'quantity',
			index : 'quantity',
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
		caption : ""
	});
};





placeOrder = function() {
	var jsonData = $("#proTable").jqGrid("getRowData");
	//jsonData = JSON.stringify(jsonData);
	/*
	 * createNewFieldToForm("data_form", "dataJson");
	 * 
	 * var jsonData = $("#proTable").jqGrid("getRowData"); //
	 * 通过cookie获取的sm_session_cookie stocktake!warehouseEntry
	 * document.data_form.dataJson.value = JSON.stringify(jsonData);
	 */
	// document.stock_form.warehousingDate.value = JSON.stringify(jsonData);
	createNewFieldToForm("data_form", "jsonData");
	document.data_form.jsonData.value = JSON.stringify(jsonData);
	document.data_form.action = "ordermanagement!addOrder";
	document.data_form.method = "post";
	document.data_form.submit();
};



function createNewFieldToForm(FormId, FieldId) {
	if (document.getElementById(FormId)[FieldId] == null) {
		var newItem = document.createElement("input");
		newItem.id = FieldId;
		newItem.name = FieldId;
		newItem.type = "hidden";
		newItem.value = " ";
		document.getElementById(FormId).appendChild(newItem);
	}
};

onOrderLoad = function(){
	$("#fromDate").datepicker({
		changeMonth : true,
		changeYear : true,
		dateFormat : "yy/mm/dd"
	});
	$("#fromDate").val(getSystemData());
	
	$("#toDate").datepicker({
		changeMonth : true,
		changeYear : true,
		dateFormat : "yy/mm/dd"
	});
	$("#toDate").val(getSystemData());
	onOrderSearch();
}

onOrderSearch = function() {
//	$("#partResult").show();
//	var partType = document.getElementById("partType").value;
//	var partCode = document.getElementById("partCode").value;
//	var query = "partCode=" + partCode + "&partType=" + partType;
	var dateType = document.getElementById("dateType").value;
	var fromDate = document.getElementById("fromDate").value;
	var toDate = document.getElementById("toDate").value;
	var orderNum = document.getElementById("orderNum").value;
	var customer = document.getElementById("customer").value;
	var sales = document.getElementById("sales").value;
	var query = "dateType=" + dateType + "&orderNum=" + orderNum
	+"&toDate=" + toDate + "&fromDate=" + fromDate
	+"&customer=" + customer + "&sales=" + sales
	$("#orderTable").jqGrid('GridUnload');
	$("#orderTable").jqGrid({

		onSelectRow: function(id){ 
			   if(id && id!==lastSel){ 
				  jQuery('#orderTable').restoreRow(lastSel); 
				  lastSel=id; 
			   } 
			   var rowData = $("#orderTable").jqGrid("getRowData",id);//
			   var expendCode= rowData.orderNum;
			   onExpend(expendCode);
			},
		url : "ordersearch.action?"+query,
		datatype : "json", // 数据来源，本地数据
		mtype : "POST",// 提交方式
		height : 120,// 高度，表格高度。可为数值、百分比或'auto'
		width : 600,// 这个宽度不能为百分比
		autowidth : true,// 自动宽
		colNames : [ '订单号', '交货时间', '要求完成时间','状态' ],
		colModel : [
		// {name:'id',index:'id', width:'10%', align:'center' },

		{
			name : 'orderNum',
			index : 'orderNum',
			width : '25%',
			align : 'left'
		}, {
			name : 'deliveryDate',
			index : 'deliveryDate',
			width : '30%',
			align : "left"
		}, {
			name : 'dueDate',
			index : 'dueDate',
			width : '15%',
			align : "center"
		},  {
			name : 'planned',
			index : 'planned',
			width : '15%',
			align : "left"
		} ],
		// altRows:true,//设置为交替行表格,默认为false
		// sortname:'createDate',
		// sortorder:'asc',
		viewrecords : true,// 是否在浏览导航栏显示记录总数
		rowNum : 15,// 每页显示记录数
		// rowTotal:2000,
		rowList : [ 15, 20, 25 ],// 用于改变显示行数的下拉列表框的元素数组。
		loadonce : true,
		gridview : true,
		multiselect : true,
		jsonReader : {
			root : "salesOrderList",
			repeatitems : false
		},
		pager : $('orderPager')
	});

};

onExpend = function(partNum) {
	 $("#orderItemTable").jqGrid('GridUnload');
	$("#orderItemTable").jqGrid({
       url:"orderList.action?OrderNum="+partNum,
       datatype:"json", //数据来源，本地数据
       mtype:"POST",//提交方式
       height:420,//高度，表格高度。可为数值、百分比或'auto'
       //width:1000,//这个宽度不能为百分比
       autowidth:true,//自动宽
       colNames:['物料号', '物料名称','零件类型','价格','数量'],
       colModel:[
           //{name:'id',index:'id', width:'10%', align:'center' },
           
           {name:'partCode',index:'partCode', width:'15%',align:'left'},
           {name:'partName',index:'partName', width:'20%', align:"left"},
           {name:'partType',index:'partType', width:'10%',align:"center"},
           {name:'price',index:'partType', width:'20%',align:"center"},
           {name:'quantity',index:'quantity', width:'15%', align:"left", sortable:false}
           
       ],
       rownumbers:true,//添加左侧行号
       //altRows:true,//设置为交替行表格,默认为false
       //sortname:'createDate',
       //sortorder:'asc',
       viewrecords: true,//是否在浏览导航栏显示记录总数
       rowNum:15,//每页显示记录数
//       rowTotal:2000,
       rowList:[15,20,25],//用于改变显示行数的下拉列表框的元素数组。
       loadonce:true,
       gridview: true,  
       viewrecords: true,
       jsonReader:{
           root:"salesOrderItems",
           repeatitems : false
       },
       pager:$('#orderItemPager')
   });
};

mprOrder = function() {
	var grs = jQuery("#orderTable").jqGrid('getGridParam', 'selarrrow');
	var orderNum ="";
	if (grs.length > 0)
		 $.each(grs,function(n,value) {  
				var rowData = $("#orderTable").jqGrid("getRowData",value);
				orderNum=orderNum+rowData.orderNum+","
		 });
	else
		alert("请选择要运算的订单！");
	createNewFieldToForm("data_form", "orderNum");
	document.data_form.orderNum.value = orderNum;
	document.data_form.action = "mprCal";
	document.data_form.method = "post";
	document.data_form.submit();
};
