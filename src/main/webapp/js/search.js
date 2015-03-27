onSearch = function() {
	
	var partType=document.getElementById("partType").value;
	var partCode=document.getElementById("partCode").value;
	var query="partCode="+partCode +"&partType="+partType;
	var lastSel;
	
	

	
	 $("#partTable").jqGrid('GridUnload');
	$("#partTable").jqGrid({
		
		onSelectRow: function(id){ 
		   if(id && id!==lastSel){ 
			  jQuery('#partTable').restoreRow(lastSel); 
			  lastSel=id; 
		   } 
		   var rowData = $("#partTable").jqGrid("getRowData",id);//
		   var parentCode= rowData.partCode;
		   onClick(parentCode);
		},
        url:"searchMachine.action?"+query,
        datatype:"json", //数据来源，本地数据
        mtype:"POST",//提交方式
        height:120,//高度，表格高度。可为数值、百分比或'auto'
        width:600,//这个宽度不能为百分比
        autowidth:true,//自动宽
        colNames:['物料号', '物料名称','零件类型','材料'],
        colModel:[
            //{name:'id',index:'id', width:'10%', align:'center' },
            
            {name:'partCode',index:'partCode', width:'25%',align:'left'},
            {name:'partName',index:'partName', width:'30%', align:"left"},
            {name:'partType',index:'partType', width:'15%',align:"center"},
            {name:'material',index:'remark', width:'30%', align:"left"}
        ],
        rownumbers:true,//添加左侧行号
        //altRows:true,//设置为交替行表格,默认为false
        //sortname:'createDate',
        //sortorder:'asc',
        viewrecords: true,//是否在浏览导航栏显示记录总数
        rowNum:15,//每页显示记录数
//        rowTotal:2000,
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
onClick = function(parent) {
	var lastSel;
	 $("#treegrid").jqGrid('GridUnload');
	$("#treegrid").jqGrid({ 
		onSelectRow: function(id){ 
		   if(id && id!==lastSel){ 
			  jQuery('#treegrid').restoreRow(lastSel); 
			  lastSel=id; 
		   } 
		   var rowData = $("#treegrid").jqGrid("getRowData",id);//
		   var expendCode= rowData.name;
		   onExpend(expendCode);
		},
	    treeGrid: true, 
	    treeGridModel: 'adjacency', 
	    ExpandColumn: 'name', 
	    ExpandColClick: true, 
	    url: "bomList.action?machineCode="+parent, 
	    datatype: 'json', 
	    colNames: ["机器列表"], 
	    colModel: [{ 
	        name: 'name', 
	        index: 'name' 

	   }], 
	    pager: "false", 
	    height: 'auto', 
	    width: '200', 
	    viewrecords: true, 
	    //   caption: 'none', 
	    jsonReader: { 
	        root: "rows", 
	        total: "total", 
	        repeatitems: true 
	    } 

	});



};

onExpend = function(parent) {
	 $("#bomTable").jqGrid('GridUnload');
	$("#bomTable").jqGrid({
        url:"/bomAction!bomExpend.action?parentCode="+parent,
        datatype:"json", //数据来源，本地数据
        mtype:"POST",//提交方式
        height:420,//高度，表格高度。可为数值、百分比或'auto'
        //width:1000,//这个宽度不能为百分比
        autowidth:true,//自动宽
        colNames:['物料号', '物料名称','零件类型','数量'],
        colModel:[
            //{name:'id',index:'id', width:'10%', align:'center' },
            
            {name:'partCode',index:'partCode', width:'15%',align:'left'},
            {name:'partName',index:'partName', width:'20%', align:"left"},
            {name:'partType',index:'partType', width:'10%',align:"center"},
            {name:'dosage',index:'dosage', width:'35%', align:"left", sortable:false}
            
        ],
        rownumbers:true,//添加左侧行号
        //altRows:true,//设置为交替行表格,默认为false
        //sortname:'createDate',
        //sortorder:'asc',
        viewrecords: true,//是否在浏览导航栏显示记录总数
        rowNum:15,//每页显示记录数
//        rowTotal:2000,
        rowList:[15,20,25],//用于改变显示行数的下拉列表框的元素数组。
        loadonce:true,
        gridview: true,   	
        viewrecords: true,
        jsonReader:{
            root:"bomTreeList",
            repeatitems : false
        },
        pager:$('#bomPager')
    });
};

onStockSearch = function() {
	
//	var partType=document.getElementById("partType").value;
	var partCode=document.getElementById("partCode").value;
//	var query="partCode="+partCode +"&partType="+partType;
	var lastSel;
	
	

	
	 $("#invTable").jqGrid('GridUnload');
	$("#invTable").jqGrid({
		
	
        url:"/searchInv.action?partCode="+partCode,
        datatype:"json", //数据来源，本地数据
        mtype:"POST",//提交方式
        height:420,//高度，表格高度。可为数值、百分比或'auto'
        width:600,//这个宽度不能为百分比
        autowidth:true,//自动宽
        colNames:['物料号','物料名称','零件类型', '數量','存放地址','更新時間'],
        colModel:[
            //{name:'id',index:'id', width:'10%', align:'center' },
            
            {name:'partCode',index:'part.partCode', width:'15%',align:'left'},
			{name:'partName',index:'partName', width:'15%', align:"left"},
            {name:'partType',index:'partType', width:'15%',align:"center"},
            {name:'quantity',index:'quantity', width:'15%', align:"left"},
            {name:'address',index:'address', width:'15%',align:"center"},
            {name:'modifiedDate',index:'modifiedDate', width:'15%', align:"left"}
        ],
        rownumbers:true,//添加左侧行号
        //altRows:true,//设置为交替行表格,默认为false
        //sortname:'createDate',
        //sortorder:'asc',
        viewrecords: true,//是否在浏览导航栏显示记录总数
        rowNum:20,//每页显示记录数
//        rowTotal:2000,
        rowList:[15,20,25],//用于改变显示行数的下拉列表框的元素数组。
        loadonce:true,
        gridview: true,   	
        viewrecords: true,
        jsonReader:{
            root:"invList",
            repeatitems : false
        },
        pager:$('#invPager')
    });

};

