onPreview = function() {
	var partType=document.getElementById("partType").value;
	 $("#invTable").jqGrid('GridUnload');
	$("#invTable").jqGrid({
        url:"loadPreview.action!preview",
        datatype:"json", //数据来源，本地数据
        mtype:"POST",//提交方式
        height:420,//高度，表格高度。可为数值、百分比或'auto'
        //width:1000,//这个宽度不能为百分比
        autowidth:true,//自动宽
        colNames:['物料号', '物料名称','数量','存放地址'],
        colModel:[
            //{name:'id',index:'id', width:'10%', align:'center' },
            
            {name:'partCode',index:'partCode', width:'25%',align:'left'},
            {name:'partName',index:'partName', width:'30%', align:"left"},
            {name:'quantity',index:'quantity', width:'15%', align:"left", sortable:false},
            {name:'address',index:'address', width:'30%',align:'center'}
            
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
            root:"invList",
            repeatitems : false
        },
        pager:$('#invPager')
    });

};

$(document).ready(function(){

	//Uploading the file
	$("#dataImport").submit(function(){
		$("actionerror").val("");
		$("actionmessage").val("");
		
	$("#actionerror").html("");
	$("#actionmessage").html("");
	
	var fileName = $("#fileupload").val();
	var importType = $('input[name="importType"]:checked').val();
	
	if(fileName==null||fileName==""){
		alert("请选择上传的文件!");
		return false ;
	}
	if(importType==null||importType==""){
		alert("请选择文件类型!");
		return false;
	}		
	$("#loading").removeClass("hidden").addClass("show");
	onPreview();
	});
});

