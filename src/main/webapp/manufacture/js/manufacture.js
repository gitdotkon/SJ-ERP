var stock={
	partCode:"",
	partName:"",
	partType:"",
	quantity:"",
	address:""
};
var addId=0;
var lastSel=1;
checkDuplicate= function(TpartCode){
	var obj = $("#stockTable").jqGrid("getRowData");
	var flag=true;
	if (obj.length > 0) {
		$(obj)
				.each(
						function() {
							var partCode = this["partCode"];
							if(partCode==TpartCode){
								alert("物料号重复");
								flag= false;
							}
						});
	}
	return flag;

};

addStock = function(rowData){
	stock.partCode=rowData.partCode;
	
	stock.partName=rowData.partName;
	stock.partType=rowData.partType;
	stock.address=rowData.address;
	if(checkDuplicate(stock.partCode)){
	
	jQuery("#stockTable").jqGrid('addRowData',addId,stock);
	addId=addId+1;
	if(stock.address=="")
		window.setTimeout(function() {
					$("#stockTable").jqGrid("editCell", addId, 4, true);
				}, 10);
		
	else
		window.setTimeout(function() {
			$("#stockTable").jqGrid("editCell", addId, 5, true);
		}, 10);
	}
	
};




delGrid = function() {
	var grs = jQuery("#stockTable").jqGrid('getGridParam', 'selarrrow');
	if (grs.length > 0)
		jQuery("#stockTable").jqGrid('delGridRow', grs, {
			reloadAfterSubmit : false
		});
	else
		alert("请选择要删除行！");
	// 若点击了全选按钮，重置全选按钮框
	$("#stockTable").jqGrid('resetSelection');
};


onCheckEnter = function(e){
	var keyCode = null;

        if(e.which)
            keyCode = e.which;
        else if(e.keyCode) 
            keyCode = e.keyCode;
            
        if(keyCode == 13) 
			onStockSearch();


};

onStockSearch = function() {
	var partType=document.getElementById("partType").value;
	var partCode=document.getElementById("partCode").value;
	var query="partCode="+partCode +"&partType="+partType;
	 $("#partTable").jqGrid('GridUnload');
		$("#partTable").jqGrid({
			
			onSelectRow: function(id){ 
				   if(id && id!==lastSel){ 
					  jQuery('#partTable').restoreRow(lastSel); 
					  lastSel=id; 
				   } 
				   var rowData = $("#partTable").jqGrid("getRowData",id);//
				   addStock(rowData);
				},
	        url:"searchInventory.action?"+query,
	        datatype:"json", //数据来源，本地数据
	        mtype:"POST",//提交方式
	        height:120,//高度，表格高度。可为数值、百分比或'auto'
	        width:600,//这个宽度不能为百分比
	        autowidth:true,//自动宽
	        colNames:['物料号', '物料名称','零件类型','材料','存放地址'],
	        colModel:[
	            //{name:'id',index:'id', width:'10%', align:'center' },
	            
	            {name:'partCode',index:'partCode', width:'25%',align:'left'},
	            {name:'partName',index:'partName', width:'30%', align:"left"},
	            {name:'partType',index:'partType', width:'15%',align:"center"},
	            {name:'material',index:'material', width:'15%', align:"left"},
	            {name:'address',index:'address', width:'15%', align:"left"}
	        ],
	        rownumbers:true,//添加左侧行号
	        //altRows:true,//设置为交替行表格,默认为false
	        //sortname:'createDate',
	        //sortorder:'asc',
	        viewrecords: true,//是否在浏览导航栏显示记录总数
	        rowNum:15,//每页显示记录数
//	        rowTotal:2000,
	        rowList:[15,20,25],//用于改变显示行数的下拉列表框的元素数组。
	        loadonce:true,
	        gridview: true,   	
	        viewrecords: true,
	        jsonReader:{
	            root:"partList",
	            repeatitems : false
	        },
	        pager:$('#partPager')
	    });

	};
	

	
	getSystemData = function (day) {
	var myDate;
	if(day == undefined) {
		myDate = new Date();
	} else {
		myDate=new Date(day);
	}
	 var strDate = myDate.getFullYear()+"/"+((myDate.getMonth()+1)<10?"0":"")+(myDate.getMonth()+1)+"/"+(myDate.getDate()<10?"0":"")+myDate.getDate();  
	 return strDate;
};


loadTable = function() {
	 $("#warehousingDate").datepicker({changeMonth: true, changeYear: true, dateFormat: "yy/mm/dd"});
	    $("#warehousingDate").val(getSystemData());
	jQuery("#stockTable").jqGrid({
		datatype : "local",
		height : 400,
		//width : 685,
		autowidth:true,
		colNames : [ '物料号','物料名称','零件类型','存放地址', '數量' ],
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
			name : 'address',
			index : 'address',
			sortable : true,
			width : 200,
			align : "center",
			editable : true
			
		} , {
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

doSubmit = function(){
	
	createNewFieldToForm("stock_form", "stockJson");

	var jsonData = $("#stockTable").jqGrid("getRowData");
	//通过cookie获取的sm_session_cookie stocktake!warehouseEntry
	document.stock_form.stockJson.value = JSON.stringify(jsonData);
//	document.stock_form.warehousingDate.value = JSON.stringify(jsonData);
	document.stock_form.action = "stocktake!warehouseEntry";
	document.stock_form.method = "post";
	document.stock_form.submit();
};

function createNewFieldToForm(FormId, FieldId)
{
	if (document.getElementById(FormId)[FieldId] == null)
	{
		var newItem = document.createElement("input");
		newItem.id = FieldId;
		newItem.name = FieldId;
		newItem.type = "hidden";
		newItem.value = " ";
		document.getElementById(FormId).appendChild(newItem);
	}
};


