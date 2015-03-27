
var addId = 0;
var lastSel = 1;


pageLoad = function(){
	setDatePicker("#fromDate");
	setDatePicker("#toDate");
}



addPro = function(part) {
	addId=addGrid(proTable,part,"partCode",addId);
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

onExpend = function(partNum) {
	 $("#orderItemTable").jqGrid('GridUnload');
	$("#orderItemTable").jqGrid({
     url:"productionAction!listOrder.action?partCode="+partNum,
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
//     rowTotal:2000,
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
				url : "${ctx}/main/productionAction!execute.action?selectedOrder="+$("#orderList").val(),
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



function productionPlan(){
	var selObj = new Array();
	var grs = jQuery("#planTable").jqGrid('getGridParam', 'selarrrow');
	
	for(var i=0;i<grs.length;i++){
		selObj[i]=$("#planTable").jqGrid("getRowData", grs[i]);
	}
	//var rowData = $("#partTable").jqGrid("getRowData", grs);//

	createNewFieldToForm("data_form", "jsonData");

	
	
	document.data_form.jsonData.value = JSON.stringify(selObj);
	document.data_form.action = "productionAction!insertProOrder";
	document.data_form.method = "post";
	document.data_form.submit();

};


generatePlan = function() {
	var objList = $("#planTable").jqGrid("getRowData");
	//jsonData = JSON.stringify(jsonData);
	//createNewFieldToForm("data_form", "order");
	createNewFieldToForm("data_form", "jsonData");
	/*
	 * createNewFieldToForm("data_form", "dataJson");
	 * 
	 * var jsonData = $("#proTable").jqGrid("getRowData"); //
	 * 通过cookie获取的sm_session_cookie stocktake!warehouseEntry
	 * document.data_form.dataJson.value = JSON.stringify(jsonData);
	 */
	// document.stock_form.warehousingDate.value = JSON.stringify(jsonData);
	document.data_form.jsonData.value = JSON.stringify(objList);
	document.data_form.action = "productionAction!insertProOrder";
	document.data_form.method = "post";
	document.data_form.submit();
};

