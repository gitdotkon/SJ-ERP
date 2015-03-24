lastSel=0;

pageLoad = function(){
	setDatePicker("fromDate");
	setDatePicker("toDate");
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
		url : "salesOrderAction!salesOrderSearch.action?"+query,
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
       url:"salesOrderAction!orderList.action?OrderNum="+partNum,
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
	document.data_form.action = "mrpAction!mrpCal";
	document.data_form.method = "post";
	document.data_form.submit();
};
