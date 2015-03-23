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


selectOrder = function() {
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
		height : 600,
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

onExpend = function(partNum) {
	 $("#orderItemTable").jqGrid('GridUnload');
	$("#orderItemTable").jqGrid({
      url:"/main/productionAction!listOrder.action?partCode="+partNum,
      datatype:"json", //数据来源，本地数据
      mtype:"POST",//提交方式
      height:420,//高度，表格高度。可为数值、百分比或'auto'
      //width:1000,//这个宽度不能为百分比
      autowidth:true,//自动宽
      colNames:['订单号', '物料号','数量','要求完成时间','发货时间'],
      colModel:[
          //{name:'id',index:'id', width:'10%', align:'center' },
          
          {name:'salesOrder',index:'salesOrder', width:'15%',align:'left'},
          {name:'partCode',index:'partCode', width:'20%', align:"left"},
          {name:'quantity',index:'quantity', width:'15%', align:"left", sortable:false},
          {name:'dueDate',index:'dueDate', width:'20%', formatter:'date', formatoptions:{newformat: 'Y年m月d日'},align:"left"},
          {name:'deliveryDate',index:'deliveryDate',formatter:'date', formatoptions:{newformat: 'Y年m月d日'},width:'20%', align:"left"}
          
      ],
      rownumbers:true,//添加左侧行号
      //altRows:true,//设置为交替行表格,默认为false
      //sortname:'createDate',
      //sortorder:'asc',
      viewrecords: true,//是否在浏览导航栏显示记录总数
      rowNum:15,//每页显示记录数
//      rowTotal:2000,
      rowList:[15,20,25],//用于改变显示行数的下拉列表框的元素数组。
      loadonce:true,
      gridview: true,  
      multiselect : true,
      viewrecords: true,
      jsonReader:{
          root:"orderList",
          repeatitems : false
      },
      pager:$('#orderPager')
  });
};


loadPlan = function() {
	
	$("#planTable").jqGrid('GridUnload');
	$("#planTable").jqGrid(
			{
				onSelectRow: function(id){ 
					   if(id && id!==lastSel){ 
						  jQuery('#planTable').restoreRow(lastSel); 
						  lastSel=id; 
					   } 
					   var rowData = $("#planTable").jqGrid("getRowData",id);//
					   var expendCode= rowData.partCode;
					   onExpend(expendCode);
					},
				url : "/main/productionAction!productionList.action?selectedOrder="+$("#orderList").val(),
				datatype : "json", // 数据来源，本地数据
				mtype : "POST",// 提交方式
				height : 420,// 高度，表格高度。可为数值、百分比或'auto'
				width : 600,// 这个宽度不能为百分比
				autowidth : true,// 自动宽
				colNames : [ '物料号', '物料名称', '零件类型', '需求数量', '库存数量', '建议数量',
						'投产数量', '自制/外协' ],
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
					width : 120,
					align : "center"
				}, {
					name : 'partType',
					index : 'partType',
					sortable : true,
					width : 120,
					align : "center"
				}, {
					name : 'requiredQty',
					index : 'requiredQty',
					sortable : true,
					width : 120,
					align : "center"
				}, {
					name : 'inventoryQty',
					index : 'inventoryQty',
					sortable : true,
					width : 120,
					align : "center"
				}, {
					name : 'recommandedQty',
					index : 'recommandedQty',
					sortable : true,
					width : 120,
					align : "center"
				}, {
					name : 'actualQty',
					index : 'actualQty',
					sortable : true,
					width : 120,
					align : "center",
					editable : true,
					editrules : {
						integer : true,
						minValue : 1
					}
				}, {
					name : 'proType',
					index : 'proType',
					sortable : true,
					
					formatter: 'checkbox',
					width : 120,
					align : 'center',
					edittype: 'checkbox',
					editable : true,
					editoptions: {value: 'true:false', defaultValue: 'false'}
				} ],
				rownumbers : true,// 添加左侧行号
				altRows:true,//设置为交替行表格,默认为false
				// sortname:'createDate',
				// sortorder:'asc',
				viewrecords : true,// 是否在浏览导航栏显示记录总数
				rowNum : 500,// 每页显示记录数
				// rowTotal:2000,
				rowList : [ 15, 20, 25,500 ],// 用于改变显示行数的下拉列表框的元素数组。
				loadonce : true,
				multiselect : true,
				gridview : true,
				cellEdit : true,
				viewrecords : true,		
				// 定义了单元格内容保存位置
				cellsubmit : 'clientArray',// 单元格提交方法，数据保存在客户端。不需设置url:""
				jsonReader : {
					root : "productionList",
					repeatitems : false
				},
				pager : $('#planPager')
			});

};

generatePlan = function() {
	var jsonData = $("#planTable").jqGrid("getRowData");
	//jsonData = JSON.stringify(jsonData);
	createNewFieldToForm("data_form", "order");
	/*
	 * createNewFieldToForm("data_form", "dataJson");
	 * 
	 * var jsonData = $("#proTable").jqGrid("getRowData"); //
	 * 通过cookie获取的sm_session_cookie stocktake!warehouseEntry
	 * document.data_form.dataJson.value = JSON.stringify(jsonData);
	 */
	// document.stock_form.warehousingDate.value = JSON.stringify(jsonData);
	document.data_form.order.value = document.getElementById("orderNum").value;
	document.data_form.dataJson.value = JSON.stringify(jsonData);
	document.data_form.action = "productionList!insertProOrder";
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

function getOrder(){
	var a= $("#orderList").val();
	$("#orderNum").val=a;
};

