var alertText = "<div style='margin:0.3em; background:#FFFFFF; border:1px solid #A6C9E2; padding: 0.5em;' mce_style='margin:0.3em; background:#FFFFFF; border:1px solid #A6C9E2; padding: 0.5em;'>请选择需要操作的数据行!</div>";  
var rowidGlobal = null;
//查询
onSearch = function() {
	var findItem = document.getElementById("findItem").value;
	switch (findItem) {
	case 'dealer':
		$("#div_salesAllTable").hide();
		$("#div_tradingTable").hide();
		$("#div_failureTable").hide();
		$("#div_salesTable").hide();
		$("#div_customerTable").hide();
		$("#div_dealerTable").show();
		loadDealer();
		break;
	case 'salesAll':
		$("#div_tradingTable").hide();
		$("#div_failureTable").hide();
		$("#div_dealerTable").hide();
		$("#div_customerTable").hide();
		$("#div_salesTable").hide();
		$("#div_salesAllTable").show();
		loadSalesAll();
		break;
	case 'sales':
		$("#div_salesAllTable").hide();
		$("#div_tradingTable").hide();
		$("#div_failureTable").hide();
		$("#div_dealerTable").hide();
		$("#div_customerTable").hide();
		$("#div_salesTable").show();
		loadSales();
		break;
	case 'trading':
		$("#div_salesAllTable").hide();
		$("#div_failureTable").hide();
		$("#div_dealerTable").hide();
		$("#div_customerTable").hide();
		$("#div_salesTable").hide();
		$("#div_tradingTable").show();
		loadTrading();
		break;
	case 'failure':
		$("#div_salesAllTable").hide();
		$("#div_tradingTable").hide();
		$("#div_dealerTable").hide();
		$("#div_customerTable").hide();
		$("#div_salesTable").hide();
		$("#div_failureTable").show();
		loadFailure();
		break;
	case 'customer':
		$("#div_salesAllTable").hide();
		$("#div_tradingTable").hide();
		$("#div_failureTable").hide();
		$("#div_salesTable").hide();
		$("#div_dealerTable").hide();
		$("#div_customerTable").show();
		loadCustomer();
		break;

	default:
		break;
	}
};

/****************************************/
/***************Dealer Start*************/
/****************************************/
loadDealer = function () {
	var findItem = document.getElementById("findItem").value;
	var soptString = {sopt:["eq","ne","bw","bn","ew","en","cn","nc"]};
	jQuery("#dealerList").jqGrid({
		url:'findDealerGrid.action',
//		 type : "post",
		height: 222,
		width: 900,
		datatype: "json", 
		colNames:['经销商编码','经销商名称', '经销商email', '部门','负责人','省份','当事人'],
        mtype: "GET",  
        colModel:[
			{name:'dlrCode',index:'dlrCode', width:100, align:"center",searchoptions:soptString},
			{name:'dlrName',index:'dlrName', width:200, align:"center",searchoptions:soptString},
			{name:'dlrEmail',index:'dlrEmail', width:200, align:"center",searchoptions:soptString},
			{name:'division',index:'division', width:100, align:"center",searchoptions:soptString},
			{name:'territory',index:'territory', width:100, align:"center",searchoptions:soptString},		
			{name:'province',index:'province', width:100,align:"center",searchoptions:soptString},		
			{name:'principal',index:'principal', width:100, sortable:false, align:"center",searchoptions:soptString}		
		],
        viewrecords: true,  
        altRows : true,  
        rowNum: 10,  
        rowList: [10,20,30],  
        prmNames: {search: "search"},   //(1)  
        jsonReader: {  
            root:"gridModel",       // (2)  
            records: "record",      // (3)  
            repeatitems : false     // (4)  
        },  
        pager: "#dealerPager",  
        caption: "经销商数据",  
        hidegrid: false  
	});
	
    $("#dealerList").jqGrid("navGrid", "#dealerPager", {  
    	add:false,
//        addfunc : openDialogDealerAdding,    // (1) 点击添加按钮，则调用openDialogDealerAdding方法  
        editfunc : openDialogDealerUpdating, // (2) 点击添加按钮，则调用openDialogDealerUpdating方法  
        delfunc : openDialogDealerDeleting,  // (3) 点击添加按钮，则调用openDialogDealerDeleting方法  
        alerttext : alertText   // (4) 当未选中任何行而点击编辑、删除、查看按钮时，弹出的提示信息  
    },{},{},{},{    // (5) 修改于查询相关的prmSearch参数  
        caption: "查找",  
        Find: "GO",  
        multipleSearch: true,  
        groupOps: [{ op: "AND", text: "全部" }],  
    },{});  
      
    // 配置对话框  
    $("#consoleDealerDlg").dialog({  
        autoOpen: false,      
        modal: true,    // 设置对话框为模态（modal）对话框  
        resizable: true,      
        width: 350,  
        buttons: {  // 为对话框添加按钮  
            "创建": addDealer,  
            "保存": updateDealer,  
            "删除": deleteDealer,  
            "取消": function() {$("#consoleDealerDlg").dialog("close")}  
        }  
    });  

}

var openDialogDealerAdding = function() {  
    var consoleDealerDlg = $("#consoleDealerDlg");  
    var dialogButtonPanel = consoleDealerDlg.siblings(".ui-dialog-buttonpane");  
    consoleDealerDlg.find("input").removeAttr("disabled").val("");  
    dialogButtonPanel.find("button:not(:contains('取消'))").hide();  
    dialogButtonPanel.find("button:contains('创建')").show();  
    consoleDealerDlg.dialog("option", "title", "创建新联系人").dialog("open");  
};  
var openDialogDealerUpdating = function(rowid) { // (6) 接受选中行的id为参数  
    var consoleDealerDlg = $("#consoleDealerDlg");  
    var dialogButtonPanel = consoleDealerDlg.siblings(".ui-dialog-buttonpane");  
      
    consoleDealerDlg.find("input").removeAttr("disabled");
    consoleDealerDlg.find("#dlrCode").attr("disabled", true);    
    dialogButtonPanel.find("button:not(:contains('取消'))").hide();  
    dialogButtonPanel.find("button:contains('保存')").show();  
    consoleDealerDlg.dialog("option", "title", "修改联系人信息");  
    rowidGlobal = rowid;
    var rowData = $("#dealerList").jqGrid("getRowData",rowid); 
    loadSelectedRowDataDealer(rowData.dlrCode); // (7) 将选中行id传入loadSelectedRowDataDealer方法  
}  
var openDialogDealerDeleting = function(rowid) { // (8) 接受选中行的id为参数  
    var consoleDealerDlg = $("#consoleDealerDlg");  
    var dialogButtonPanel = consoleDealerDlg.siblings(".ui-dialog-buttonpane");  
      
    consoleDealerDlg.find("input").attr("disabled", true);  
    dialogButtonPanel.find("button:not(:contains('取消'))").hide();  
    dialogButtonPanel.find("button:contains('删除')").show();  
    consoleDealerDlg.dialog("option", "title", "删除联系人");  
    rowidGlobal = rowid;
    var rowData = $("#dealerList").jqGrid("getRowData",rowid);  
    loadSelectedRowDataDealer(rowData.dlrCode);  
}  
var loadSelectedRowDataDealer = function(selectedRowId) { // (9) 接受选中行的id为参数  
    var params = {  
        "dealer.dlrCode" : selectedRowId  
    };  
    var actionUrl = "findDealerByDlrCode.action"; 
    var fn = function(data, textStatus) {  
        // 如果读取结果成功，则将信息载入到对话框中          
        var rowData = data.dealer;  
        var consoleDealerDlg = $("#consoleDealerDlg");  
        consoleDealerDlg.find("#dlrCode").val(rowData.dlrCode);  
        consoleDealerDlg.find("#dlrName").val(rowData.dlrName);  
        consoleDealerDlg.find("#dlrEmail").val(rowData.dlrEmail);  
        consoleDealerDlg.find("#division").val(rowData.division);  
        consoleDealerDlg.find("#territory").val(rowData.territory);  
        consoleDealerDlg.find("#province").val(rowData.province);  
        consoleDealerDlg.find("#principal").val(rowData.principal);  
          
        // 根据新载入的数据将表格中的对应数据行一并刷新一下  
        var dataRow = {  
        		dlrCode : rowData.dlrCode,  
        		dlrName : rowData.dlrName,  
        		dlrEmail : rowData.dlrEmail,  
        		division : rowData.division,  
        		territory : rowData.territory,  
        		province : rowData.province,  
        		principal : rowData.principal  
            };  
          
        $("#dealerList").jqGrid("setRowData", data.dealer.dlrCode, dataRow);  
              
        // 打开对话框  
        consoleDealerDlg.dialog("open");  
    };
    // 从Server读取对应ID的JSON数据  
    executeAjax(actionUrl, params, fn); 
};  
var addDealer = function() {  
    var consoleDealerDlg = $("#consoleDealerDlg");  
          
    var dlrCode = $.trim(consoleDealerDlg.find("#dlrCode").val());  
    var dlrName = $.trim(consoleDealerDlg.find("#dlrName").val());  
    var dlrEmail = $.trim(consoleDealerDlg.find("#dlrEmail").val());  
    var division = $.trim(consoleDealerDlg.find("#division").val());  
    var territory = $.trim(consoleDealerDlg.find("#territory").val());  
    var province = $.trim(consoleDealerDlg.find("#province").val());  
    var principal = $.trim(consoleDealerDlg.find("#principal").val());  
      
    var params = {  
        "dealer.dlrCode" : dlrCode,  
        "dealer.dlrName" : dlrName,  
        "dealer.dlrEmail" : dlrEmail,  
        "dealer.division" : division,  
        "dealer.territory" : territory,  
        "dealer.province" : province,  
        "dealer.principal" : principal  
    };  
      
    var actionUrl = "createDealer.action" 
	var fn = function(data, textStatus) {  
        if(data.ajaxResult == "success") {  
        	var rowData = data.dealer; 
            var dataRow = {  
        		dlrCode : rowData.dlrCode,  
        		dlrName : rowData.dlrName,  
        		dlrEmail : rowData.dlrEmail,  
        		division : rowData.division,  
        		territory : rowData.territory,  
        		province : rowData.province,  
        		principal : rowData.principal 
            };  
              
            var srcrowid = $("#dealerList").jqGrid("getGridParam", "selrow");  
              
            if(srcrowid) {  
                $("#dealerList").jqGrid("addRowData", rowData.dlrCode, dataRow, "before", srcrowid);  
            } else {  
                $("#dealerList").jqGrid("addRowData", rowData.dlrCode, dataRow, "first");  
            }  
            consoleDealerDlg.dialog("close");  
              
            alert("联系人添加操作成功!");  
              
        } else {  
            alert("添加操作失败!");  
        }  
    };
    executeAjax(actionUrl, params, fn); 
};  
var updateDealer = function() {  
	
    var consoleDealerDlg = $("#consoleDealerDlg");  
      
    var dlrCode = $.trim(consoleDealerDlg.find("#dlrCode").val());  
    var dlrName = $.trim(consoleDealerDlg.find("#dlrName").val());  
    var dlrEmail = $.trim(consoleDealerDlg.find("#dlrEmail").val());  
    var division = $.trim(consoleDealerDlg.find("#division").val());  
    var territory = $.trim(consoleDealerDlg.find("#territory").val());  
    var province = $.trim(consoleDealerDlg.find("#province").val());  
    var principal = $.trim(consoleDealerDlg.find("#principal").val());  
    var params = {  
		"dealer.dlrCode" : dlrCode,  
        "dealer.dlrName" : dlrName,  
        "dealer.dlrEmail" : dlrEmail,  
        "dealer.division" : division,  
        "dealer.territory" : territory,  
        "dealer.province" : province,  
        "dealer.principal" : principal   
    };  
    var actionUrl = "updateDealer.action";  
    var fn = function(data, textStatus) {  
        if (data.ajaxResult == "success") {  
        	var rowData = data.dealer; 
            var dataRow = {  
        		dlrCode : rowData.dlrCode,  
        		dlrName : rowData.dlrName,  
        		dlrEmail : rowData.dlrEmail,  
        		division : rowData.division,  
        		territory : rowData.territory,  
        		province : rowData.province,  
        		principal : rowData.principal 
            };  
            $("#dealerList").jqGrid("setRowData", rowidGlobal, dataRow, {color:"#FF0000"});  
            alert("联系人信息更新成功!");  
            consoleDealerDlg.dialog("close");  
              
        } else {  
            alert("修改操作失败!");  
        }  
    };
    executeAjax(actionUrl, params, fn);
};  
var deleteDealer = function() {  
    var consoleDealerDlg = $("#consoleDealerDlg");  
      
    var dlrCode = $.trim(consoleDealerDlg.find("#dlrCode").val());
    var params = {  
    	"dealer.dlrCode" : dlrCode
    };  
    var actionUrl = "deleteDealer.action"; 
    var fn = function(data, textStatus) {  
        if (data.ajaxResult == "success") {  
            $("#dealerList").jqGrid("delRowData", rowidGlobal);  
            consoleDealerDlg.dialog("close");  
            alert("联系人删除成功!");  
        } else {  
            alert("删除操作失败!");  
        }  
    };
    executeAjax(actionUrl, params, fn);
}; 

/****************************************/
/***************Dealer End***************/
/****************************************/

/****************************************/
/*************Customer Start*************/
/****************************************/
loadCustomer = function () {
	var soptString = {sopt:["eq","ne","bw","bn","ew","en","cn","nc"]};
	jQuery("#customerList").jqGrid({
		url:'findGridCustomer.action',
//		 type : "post",
		datatype: "json", 
		height: 222,
		width: 900,
		colNames:['姓名','电话', '性别', '地址'],
        mtype: "GET",  
        colModel:[
			{name:'name',index:'name', width:100, align:"center",searchoptions:soptString},
			{name:'phoneNumber',index:'phoneNumber', width:150, align:"center",searchoptions:soptString},
			{name:'gender',index:'gender', width:110, align:"center",searchoptions:soptString},
			{name:'address',index:'address', width:240, align:"center",searchoptions:soptString}	
		],
        viewrecords: true,  
        altRows : true,  
        rowNum: 10,  
        rowList: [10,20,30],  
        prmNames: {search: "search"},   //(1)  
        jsonReader: {  
            root:"gridModel",       // (2)  
            records: "record",      // (3)  
            repeatitems : false     // (4)  
        },  
        pager: "#customerPager",  
        caption: "客户信息",  
        hidegrid: false  
	});
	
    $("#customerList").jqGrid("navGrid", "#customerPager", {  
        addfunc : openDialogCustomerAdding,    // (1) 点击添加按钮，则调用openDialogDealerAdding方法  
        editfunc : openDialogCustomerUpdating, // (2) 点击添加按钮，则调用openDialogDealerUpdating方法  
        delfunc : openDialogCustomerDeleting,  // (3) 点击添加按钮，则调用openDialogDealerDeleting方法  
        alerttext : alertText   // (4) 当未选中任何行而点击编辑、删除、查看按钮时，弹出的提示信息  
    },{},{},{},{    // (5) 修改于查询相关的prmSearch参数  
        caption: "查找",  
        Find: "GO",  
        multipleSearch: true,  
        groupOps: [{ op: "AND", text: "全部" }],  
    },{});  
      
    // 配置对话框  
    $("#consoleCustomerDlg").dialog({  
        autoOpen: false,      
        modal: true,    // 设置对话框为模态（modal）对话框  
        resizable: true,      
        width: 300,  
        buttons: {  // 为对话框添加按钮  
            "创建": addCustomer,  
            "保存": updateCustomer,  
            "删除": deleteCustomer,  
            "取消": function() {$("#consoleCustomerDlg").dialog("close")}  
        }  
    });  

}

var openDialogCustomerAdding = function() {  
    var consoleCustomerDlg = $("#consoleCustomerDlg");  
    var dialogButtonPanel = consoleCustomerDlg.siblings(".ui-dialog-buttonpane");  
    consoleCustomerDlg.find("input").removeAttr("disabled").val("");  
    dialogButtonPanel.find("button:not(:contains('取消'))").hide();  
    dialogButtonPanel.find("button:contains('创建')").show();  
    consoleCustomerDlg.dialog("option", "title", "创建客户信息").dialog("open");  
};  
var openDialogCustomerUpdating = function(rowid) { // (6) 接受选中行的id为参数  
    var consoleCustomerDlg = $("#consoleCustomerDlg");  
    var dialogButtonPanel = consoleCustomerDlg.siblings(".ui-dialog-buttonpane");  
      
    consoleCustomerDlg.find("input").removeAttr("disabled");
    consoleCustomerDlg.find("#phoneNumber").attr("disabled", true);
    dialogButtonPanel.find("button:not(:contains('取消'))").hide();  
    dialogButtonPanel.find("button:contains('保存')").show();  
    consoleCustomerDlg.dialog("option", "title", "修改客户信息");  
    rowidGlobal = rowid;
    var rowData = $("#customerList").jqGrid("getRowData",rowid); 
    loadSelectedRowDataCustomer(rowData.phoneNumber); // (7) 将选中行id传入loadSelectedRowDataCustomer方法  
}  
var openDialogCustomerDeleting = function(rowid) { // (8) 接受选中行的id为参数  
    var consoleCustomerDlg = $("#consoleCustomerDlg");  
    var dialogButtonPanel = consoleCustomerDlg.siblings(".ui-dialog-buttonpane");  
      
    consoleCustomerDlg.find("input").attr("disabled", true);  
    dialogButtonPanel.find("button:not(:contains('取消'))").hide();  
    dialogButtonPanel.find("button:contains('删除')").show();  
    consoleCustomerDlg.dialog("option", "title", "删除客户信息");  
    rowidGlobal = rowid;
    var rowData = $("#customerList").jqGrid("getRowData",rowid);  
    loadSelectedRowDataCustomer(rowData.phoneNumber);  
}  
var loadSelectedRowDataCustomer = function(selectedRowId) { // (9) 接受选中行的id为参数  
    var params = {  
		"customer.phoneNumber" : selectedRowId  
    };  
    var actionUrl = "findCustomerByDlrCode.action"; 
    var fn = function(data, textStatus) {  
        // 如果读取结果成功，则将信息载入到对话框中          
        var rowData = data.customer;  
        var consoleCustomerDlg = $("#consoleCustomerDlg");  
        consoleCustomerDlg.find("#name").val(rowData.name);  
        consoleCustomerDlg.find("#phoneNumber").val(rowData.phoneNumber);  
        consoleCustomerDlg.find("#gender").val(rowData.gender);  
        consoleCustomerDlg.find("#address").val(rowData.address);  
          
        // 根据新载入的数据将表格中的对应数据行一并刷新一下  
        var dataRow = {  
        		name : rowData.name,  
        		phoneNumber : rowData.phoneNumber,  
        		gender : rowData.gender,  
        		address : rowData.address 
            };  
          
        $("#customerList").jqGrid("setRowData", data.customer.phoneNumber, dataRow);  
              
        // 打开对话框  
        consoleCustomerDlg.dialog("open");  
    };
    // 从Server读取对应ID的JSON数据  
    executeAjax(actionUrl, params, fn); 
};  
var addCustomer = function() {  
    var consoleCustomerDlg = $("#consoleCustomerDlg");  
          
    var name = $.trim(consoleCustomerDlg.find("#name").val());  
    var phoneNumber = $.trim(consoleCustomerDlg.find("#phoneNumber").val());  
    var gender = $.trim(consoleCustomerDlg.find("#gender").val());  
    var address = $.trim(consoleCustomerDlg.find("#address").val());  
      
    var params = {  
        "customer.name" : name,  
        "customer.phoneNumber" : phoneNumber,  
        "customer.gender" : gender,  
        "customer.address" : address 
    };  
      
    var actionUrl = "createCustomer.action" 
	var fn = function(data, textStatus) {  
        if(data.ajaxResult == "success") {  
        	var rowData = data.customer; 
            var dataRow = {  
        		name : rowData.name,  
        		phoneNumber : rowData.phoneNumber,  
        		gender : rowData.gender,  
        		address : rowData.address  
            };  
              
            var srcrowid = $("#customerList").jqGrid("getGridParam", "selrow");  
              
            if(srcrowid) {  
                $("#customerList").jqGrid("addRowData", rowData.phoneNumber, dataRow, "before", srcrowid);  
            } else {  
                $("#customerList").jqGrid("addRowData", rowData.phoneNumber, dataRow, "first");  
            }  
            consoleCustomerDlg.dialog("close");  
              
            alert("联系人添加操作成功!");  
              
        } else {  
            alert("添加操作失败!");  
        }  
    };
    executeAjax(actionUrl, params, fn); 
};  
var updateCustomer = function() {  
	
    var consoleCustomerDlg = $("#consoleCustomerDlg");  
      
    var name = $.trim(consoleCustomerDlg.find("#name").val());  
    var phoneNumber = $.trim(consoleCustomerDlg.find("#phoneNumber").val());  
    var gender = $.trim(consoleCustomerDlg.find("#gender").val());  
    var address = $.trim(consoleCustomerDlg.find("#address").val());  
    var params = {  
		"customer.name" : name,  
        "customer.phoneNumber" : phoneNumber,  
        "customer.gender" : gender,  
        "customer.address" : address 
    };  
    var actionUrl = "updateCustomer.action";  
    var fn = function(data, textStatus) {  
        if (data.ajaxResult == "success") {  
        	var rowData = data.customer; 
            var dataRow = {  
        		name : rowData.name,  
        		phoneNumber : rowData.phoneNumber,  
        		gender : rowData.gender,  
        		address : rowData.address 
            };  
            $("#customerList").jqGrid("setRowData", rowidGlobal, dataRow, {color:"#FF0000"});  
            alert("联系人信息更新成功!");  
            consoleCustomerDlg.dialog("close");  
              
        } else {  
            alert("修改操作失败!");  
        }  
    };
    executeAjax(actionUrl, params, fn);
};  
var deleteCustomer = function() {  
    var consoleCustomerDlg = $("#consoleCustomerDlg");  
      
    var phoneNumber = $.trim(consoleCustomerDlg.find("#phoneNumber").val());
    var params = {  
    	"customer.phoneNumber" : phoneNumber
    };  
    var actionUrl = "deleteCustomer.action"; 
    var fn = function(data, textStatus) {  
        if (data.ajaxResult == "success") {  
            $("#customerList").jqGrid("delRowData", rowidGlobal);  
            consoleCustomerDlg.dialog("close");  
            alert("联系人删除成功!");  
        } else {  
            alert("删除操作失败!");  
        }  
    };
    executeAjax(actionUrl, params, fn);
}; 

/****************************************/
/***************Customer End*************/
/****************************************/
/****************************************/
/*************Sales Start*************/
/****************************************/
loadSales = function () {
	var soptString = {sopt:["eq","ne","bw","bn","ew","en","cn","nc"]};
	var optStatus = 1;
	jQuery("#salesList").jqGrid({
//		url:'findGridSales.action',
		url:'findGridSales.action?optStatus=' + optStatus,
//		 type : "post",
		height: 222,
		width: 900,
		datatype: "json", 
		colNames:['客户来源','拟购机型', '首次接触时间', '拟购数量'],
		mtype: "GET",  
		colModel:[
		          {name:'optSource',index:'optSource', width:100, align:"center",searchoptions:soptString},
		          {name:'intendModel',index:'intendModel', width:160, align:"center",searchoptions:soptString},
		          {name:'visitDate',index:'visitDate', width:160, align:"center",formatter:"date",searchoptions:{  
                      sopt:["eq","ne","lt","le","gt","ge"],  
                      dataInit: function (elem) { // (4)输入框元素将被作为参数传入方法函数中  
                          $(elem).datepicker({changeMonth: true, changeYear: true, dateFormat: "yy-mm-dd"});   // (5)将输入框设置为日期选择框  
                      }  
                  }  },
		          {name:'intendQuantity',index:'intendQuantity', width:140, align:"center",searchoptions:soptString}	
		          ],
		          viewrecords: true,  
		          altRows : true,  
		          rowNum: 10,  
		          rowList: [10,20,30],  
		          prmNames: {search: "search"},   //(1)  
		          jsonReader: {  
		        	  root:"gridModel",       // (2)  
		        	  records: "record",      // (3)  
		        	  repeatitems : false     // (4)  
		          },  
		          pager: "#salesPager",  
		          caption: "潜在客户",  
		          hidegrid: false  
	});
	
//	$("#salesList").jqGrid("navGrid", "#salesPager", {  
//		addfunc : openDialogSalesAdding,    // (1) 点击添加按钮，则调用openDialogDealerAdding方法  
//		editfunc : openDialogSalesUpdating, // (2) 点击添加按钮，则调用openDialogDealerUpdating方法  
//		delfunc : openDialogSalesDeleting,  // (3) 点击添加按钮，则调用openDialogDealerDeleting方法  
//		alerttext : alertText   // (4) 当未选中任何行而点击编辑、删除、查看按钮时，弹出的提示信息  
//	},{},{},{},{    // (5) 修改于查询相关的prmSearch参数  
//		caption: "查找",  
//		Find: "GO",  
//		multipleSearch: true,  
//		groupOps: [{ op: "AND", text: "全部" }],  
//	},{});  
	

	$("#salesList").jqGrid('navGrid','#salesPager',{edit:false,add:false,del:false},{},{},{},{    // (5) 修改于查询相关的prmSearch参数  
		caption: "查找",  
		Find: "GO",  
		multipleSearch: true,  
		groupOps: [{ op: "AND", text: "全部" }],  
	},{});
	// 配置对话框  
	$("#consoleSalesDlg").dialog({  
		autoOpen: false,      
		modal: true,    // 设置对话框为模态（modal）对话框  
		resizable: true,      
		width: 300,  
		buttons: {  // 为对话框添加按钮  
			"创建": addSales,  
			"保存": updateSales,  
			"删除": deleteSales,  
			"取消": function() {$("#consoleSalesDlg").dialog("close")}  
		}  
	}); 
	
	
	$("#salesList").export2Excel('#salesPager', 'export_gridTable');  //添加导出按钮
	
	$("#export_gridTable").attr("title","导出EXCEL");//设置标题
	
	//导出事件，提交到后台 处理导出
	
	$("#export_gridTable").click(function() {
		var postData = $("#salesList").jqGrid("getGridParam", "postData"); 
		if (confirm("确认要导出数据？") == false) return false;
		var actionUrl = objectToURL("exportPotential.action?optStatus=" + optStatus, postData);
		$("#baseForm").attr("action", actionUrl);
		$("#baseForm").submit();
	});
	
}
//导出
$.fn.export2Excel= function(pager, id) {    
	$(this).navButtonAdd(pager,{id: id,caption:"",buttonicon:"ui-icon-arrowthickstop-1-s"});
}
var openDialogSalesAdding = function() {  
	var consoleSalesDlg = $("#consoleSalesDlg");  
	var dialogButtonPanel = consoleSalesDlg.siblings(".ui-dialog-buttonpane");  
	consoleSalesDlg.find("input").removeAttr("disabled").val("");  
	dialogButtonPanel.find("button:not(:contains('取消'))").hide();  
	dialogButtonPanel.find("button:contains('创建')").show();  
	consoleSalesDlg.dialog("option", "title", "创建客户信息").dialog("open");  
};  
var openDialogSalesUpdating = function(rowid) { // (6) 接受选中行的id为参数  
	var consoleSalesDlg = $("#consoleSalesDlg");  
	var dialogButtonPanel = consoleSalesDlg.siblings(".ui-dialog-buttonpane");  
	
	consoleSalesDlg.find("input").removeAttr("disabled");
	consoleSalesDlg.find("#phoneNumber").attr("disabled", true);
	dialogButtonPanel.find("button:not(:contains('取消'))").hide();  
	dialogButtonPanel.find("button:contains('保存')").show();  
	consoleSalesDlg.dialog("option", "title", "修改客户信息");  
	rowidGlobal = rowid;
	var rowData = $("#salesList").jqGrid("getRowData",rowid); 
	loadSelectedRowDataSales(rowData.phoneNumber); // (7) 将选中行id传入loadSelectedRowDataSales方法  
}  
var openDialogSalesDeleting = function(rowid) { // (8) 接受选中行的id为参数  
	var consoleSalesDlg = $("#consoleSalesDlg");  
	var dialogButtonPanel = consoleSalesDlg.siblings(".ui-dialog-buttonpane");  
	
	consoleSalesDlg.find("input").attr("disabled", true);  
	dialogButtonPanel.find("button:not(:contains('取消'))").hide();  
	dialogButtonPanel.find("button:contains('删除')").show();  
	consoleSalesDlg.dialog("option", "title", "删除客户信息");  
	rowidGlobal = rowid;
	var rowData = $("#salesList").jqGrid("getRowData",rowid);  
	loadSelectedRowDataSales(rowData.phoneNumber);  
}  
var loadSelectedRowDataSales = function(selectedRowId) { // (9) 接受选中行的id为参数  
	var params = {  
			"sales.phoneNumber" : selectedRowId  
	};  
	var actionUrl = "findSalesByDlrCode.action"; 
	var fn = function(data, textStatus) {  
		// 如果读取结果成功，则将信息载入到对话框中          
		var rowData = data.sales;  
		var consoleSalesDlg = $("#consoleSalesDlg");  
		consoleSalesDlg.find("#name").val(rowData.name);  
		consoleSalesDlg.find("#phoneNumber").val(rowData.phoneNumber);  
		consoleSalesDlg.find("#gender").val(rowData.gender);  
		consoleSalesDlg.find("#address").val(rowData.address);  
		
		// 根据新载入的数据将表格中的对应数据行一并刷新一下  
		var dataRow = {  
				name : rowData.name,  
				phoneNumber : rowData.phoneNumber,  
				gender : rowData.gender,  
				address : rowData.address 
		};  
		
		$("#salesList").jqGrid("setRowData", data.sales.phoneNumber, dataRow);  
		
		// 打开对话框  
		consoleSalesDlg.dialog("open");  
	};
	// 从Server读取对应ID的JSON数据  
	executeAjax(actionUrl, params, fn); 
};  
var addSales = function() {  
	var consoleSalesDlg = $("#consoleSalesDlg");  
	
	var name = $.trim(consoleSalesDlg.find("#name").val());  
	var phoneNumber = $.trim(consoleSalesDlg.find("#phoneNumber").val());  
	var gender = $.trim(consoleSalesDlg.find("#gender").val());  
	var address = $.trim(consoleSalesDlg.find("#address").val());  
	
	var params = {  
			"sales.name" : name,  
			"sales.phoneNumber" : phoneNumber,  
			"sales.gender" : gender,  
			"sales.address" : address 
	};  
	
	var actionUrl = "createSales.action" 
		var fn = function(data, textStatus) {  
		if(data.ajaxResult == "success") {  
			var rowData = data.sales; 
			var dataRow = {  
					name : rowData.name,  
					phoneNumber : rowData.phoneNumber,  
					gender : rowData.gender,  
					address : rowData.address  
			};  
			
			var srcrowid = $("#salesList").jqGrid("getGridParam", "selrow");  
			
			if(srcrowid) {  
				$("#salesList").jqGrid("addRowData", rowData.phoneNumber, dataRow, "before", srcrowid);  
			} else {  
				$("#salesList").jqGrid("addRowData", rowData.phoneNumber, dataRow, "first");  
			}  
			consoleSalesDlg.dialog("close");  
			
			alert("联系人添加操作成功!");  
			
		} else {  
			alert("添加操作失败!");  
		}  
	};
	executeAjax(actionUrl, params, fn); 
};  
var updateSales = function() {  
	
	var consoleSalesDlg = $("#consoleSalesDlg");  
	
	var name = $.trim(consoleSalesDlg.find("#name").val());  
	var phoneNumber = $.trim(consoleSalesDlg.find("#phoneNumber").val());  
	var gender = $.trim(consoleSalesDlg.find("#gender").val());  
	var address = $.trim(consoleSalesDlg.find("#address").val());  
	var params = {  
			"sales.name" : name,  
			"sales.phoneNumber" : phoneNumber,  
			"sales.gender" : gender,  
			"sales.address" : address 
	};  
	var actionUrl = "updateSales.action";  
	var fn = function(data, textStatus) {  
		if (data.ajaxResult == "success") {  
			var rowData = data.sales; 
			var dataRow = {  
					name : rowData.name,  
					phoneNumber : rowData.phoneNumber,  
					gender : rowData.gender,  
					address : rowData.address 
			};  
			$("#salesList").jqGrid("setRowData", rowidGlobal, dataRow, {color:"#FF0000"});  
			alert("联系人信息更新成功!");  
			consoleSalesDlg.dialog("close");  
			
		} else {  
			alert("修改操作失败!");  
		}  
	};
	executeAjax(actionUrl, params, fn);
};  
var deleteSales = function() {  
	var consoleSalesDlg = $("#consoleSalesDlg");  
	
	var phoneNumber = $.trim(consoleSalesDlg.find("#phoneNumber").val());
	var params = {  
			"sales.phoneNumber" : phoneNumber
	};  
	var actionUrl = "deleteSales.action"; 
	var fn = function(data, textStatus) {  
		if (data.ajaxResult == "success") {  
			$("#salesList").jqGrid("delRowData", rowidGlobal);  
			consoleSalesDlg.dialog("close");  
			alert("联系人删除成功!");  
		} else {  
			alert("删除操作失败!");  
		}  
	};
	executeAjax(actionUrl, params, fn);
}; 

/****************************************/
/***************Sales End*************/
/****************************************/
/****************************************/
/*************Trading Start*************/
/****************************************/
loadTrading = function () {
	var soptString = {sopt:["eq","ne","bw","bn","ew","en","cn","nc"]};
	var optStatus = 4;
	jQuery("#tradingList").jqGrid({
		url:'findGridSales.action?optStatus=' + optStatus,
//		 type : "post",
		datatype: "json", 
		height: 222,
		width: 900,
		colNames:['所属工厂','购买机型', '成交日期', '购买数量'],
		mtype: "GET",  
		colModel:[
		          {name:'soldFactory',index:'soldFactory', width:100, align:"center",searchoptions:soptString},
		          {name:'soldModel',index:'soldModel', width:160, align:"center",searchoptions:soptString},
		          {name:'soldDate',index:'soldDate', width:160, align:"center",formatter:"date",searchoptions:{  
                      sopt:["eq","ne","lt","le","gt","ge"],  
                      dataInit: function (elem) { // (4)输入框元素将被作为参数传入方法函数中  
                          $(elem).datepicker({changeMonth: true, changeYear: true, dateFormat: "yy-mm-dd"});   // (5)将输入框设置为日期选择框  
                      }  
                  }  },
		          {name:'soldQuantity',index:'soldQuantity', width:140, align:"center",searchoptions:soptString}	
		          ],
		          viewrecords: true,  
		          altRows : true,  
		          rowNum: 10,  
		          rowList: [10,20,30],  
		          prmNames: {search: "search"},   //(1)  
		          jsonReader: {  
		        	  root:"gridModel",       // (2)  
		        	  records: "record",      // (3)  
		        	  repeatitems : false     // (4)  
		          },  
		          pager: "#tradingPager",  
		          caption: "成交客户",  
		          hidegrid: false  
	});
	
	$("#tradingList").jqGrid('navGrid','#tradingPager',{edit:false,add:false,del:false},{},{},{},{    // (5) 修改于查询相关的prmSearch参数  
		caption: "查找",  
		Find: "GO",  
		multipleSearch: true,  
		groupOps: [{ op: "AND", text: "全部" }],  
	},{});
	
	$("#tradingList").export2Excel('#tradingPager', 'export_tradingGridTable');  //添加导出按钮
	
	$("#export_tradingGridTable").attr("title","导出EXCEL");//设置标题
	
	//导出事件，提交到后台 处理导出
	
	$("#export_tradingGridTable").click(function() {
		var postData = $("#tradingList").jqGrid("getGridParam", "postData"); 
		if (confirm("确认要导出数据？") == false) return false;
		var actionUrl = objectToURL("exportPotential.action?optStatus=" + optStatus, postData);
		$("#baseForm").attr("action", actionUrl);
		$("#baseForm").submit();
	});
}

/****************************************/
/***************Trading End*************/
/****************************************/
/****************************************/
/*************failure Start*************/
/****************************************/
loadFailure = function () {
	var soptString = {sopt:["eq","ne","bw","bn","ew","en","cn","nc"]};
	var optStatus = 3;
	jQuery("#failureList").jqGrid({
		url:'findGridSales.action?optStatus=' + optStatus,
//		 type : "post",
		datatype: "json",
		height: 222,
		width: 900,
		colNames:['丢失原因','最初拟购机型', '购买品牌', '实际支付', '经验总结'],
		mtype: "GET",  
		colModel:[
		          {name:'failureReason',index:'failureReason', width:100, align:"center",searchoptions:soptString},
		          {name:'failureModel',index:'failureModel', width:120, align:"center",searchoptions:soptString},
		          {name:'competitiveBrand',index:'competitiveBrand', width:100, align:"center",searchoptions:soptString},
		          {name:'competitivePrice',index:'competitivePrice', width:100, align:"center",searchoptions:soptString},
		          {name:'comments',index:'comments', width:140, align:"center",searchoptions:soptString}	
		          ],
		          viewrecords: true,  
		          altRows : true,  
		          rowNum: 10,  
		          rowList: [10,20,30],  
		          prmNames: {search: "search"},   //(1)  
		          jsonReader: {  
		        	  root:"gridModel",       // (2)  
		        	  records: "record",      // (3)  
		        	  repeatitems : false     // (4)  
		          },  
		          pager: "#failurePager",  
		          caption: "丢失客户",  
		          hidegrid: false  
	});
	
	$("#failureList").jqGrid('navGrid','#failurePager',{edit:false,add:false,del:false},{},{},{},{    // (5) 修改于查询相关的prmSearch参数  
		caption: "查找",  
		Find: "GO",  
		multipleSearch: true,  
		groupOps: [{ op: "AND", text: "全部" }],  
	},{});
	
	$("#failureList").export2Excel('#failurePager', 'export_failure');  //添加导出按钮
	
	$("#export_failureGridTable").attr("title","导出EXCEL");//设置标题
	
	//导出事件，提交到后台 处理导出
	
	$("#export_failure").click(function() {
		var postData = $("#failureList").jqGrid("getGridParam", "postData"); 
		if (confirm("确认要导出数据？") == false) return false;
		var actionUrl = objectToURL("exportPotential.action?optStatus=" + optStatus, postData);
		$("#baseForm").attr("action", actionUrl);
		$("#baseForm").submit();
	});
}

/****************************************/
/***************failure End*************/
/****************************************/
/****************************************/
/*************salesAll Start*************/
/****************************************/
loadSalesAll = function () {
	var soptString = {sopt:["eq","ne","bw","bn","ew","en","cn","nc"]};
//	var optStatus = 0;
	jQuery("#salesAllList").jqGrid({
		url:'findGridSales.action',
//		 type : "post",
		height: 222,
		width: 900,
		datatype: "json", 
		colNames:['客户来源','拟购机型', '首次接触时间', '拟购数量','所属工厂','购买机型', '成交日期', '购买数量','丢失原因','最初拟购机型', '购买品牌', '实际支付', '经验总结'],
		mtype: "GET",  
		colModel:[
				  {name:'optSource',index:'optSource', width:100, align:"center",searchoptions:soptString},
				  {name:'intendModel',index:'intendModel', width:160, align:"center",searchoptions:soptString},
				  {name:'visitDate',index:'visitDate', width:160, align:"center",formatter:"date",searchoptions:{  
				      sopt:["eq","ne","lt","le","gt","ge"],  
				      dataInit: function (elem) { // (4)输入框元素将被作为参数传入方法函数中  
				          $(elem).datepicker({changeMonth: true, changeYear: true, dateFormat: "yy-mm-dd"});   // (5)将输入框设置为日期选择框  
				      }  
				  }  },
				  {name:'intendQuantity',index:'intendQuantity', width:140, align:"center",searchoptions:soptString},
				
				  {name:'soldFactory',index:'soldFactory', width:100, align:"center",searchoptions:soptString},
		          {name:'soldModel',index:'soldModel', width:160, align:"center",searchoptions:soptString},
		          {name:'soldDate',index:'soldDate', width:160, align:"center",formatter:"date",searchoptions:{  
                    sopt:["eq","ne","lt","le","gt","ge"],  
                    dataInit: function (elem) { // (4)输入框元素将被作为参数传入方法函数中  
                        $(elem).datepicker({changeMonth: true, changeYear: true, dateFormat: "yy-mm-dd"});   // (5)将输入框设置为日期选择框  
                    }  
                  }  },
		          {name:'soldQuantity',index:'soldQuantity', width:140, align:"center",searchoptions:soptString},
				
		          {name:'failureReason',index:'failureReason', width:100, align:"center",searchoptions:soptString},
		          {name:'failureModel',index:'failureModel', width:120, align:"center",searchoptions:soptString},
		          {name:'competitiveBrand',index:'competitiveBrand', width:100, align:"center",searchoptions:soptString},
		          {name:'competitivePrice',index:'competitivePrice', width:100, align:"center",searchoptions:soptString},
		          {name:'comments',index:'comments', width:140, align:"center",searchoptions:soptString}	
		          ],
		          viewrecords: true,  
		          altRows : true,  
		          rowNum: 10,  
		          rowList: [10,20,30],  
		          prmNames: {search: "search"},   //(1)  
		          jsonReader: {  
		        	  root:"gridModel",       // (2)  
		        	  records: "record",      // (3)  
		        	  repeatitems : false     // (4)  
		          },  
		          pager: "#salesAllPager",  
		          caption: "销售信息",  
		          hidegrid: false  
	});
	
	$("#salesAllList").jqGrid('navGrid','#salesAllPager',{edit:false,add:false,del:false},{},{},{},{    // (5) 修改于查询相关的prmSearch参数  
		caption: "查找",  
		Find: "GO",  
		multipleSearch: true,  
		groupOps: [{ op: "AND", text: "全部" }],  
	},{});
	
	$("#salesAllList").export2Excel('#salesAllPager', 'export_salesAll');  //添加导出按钮
	
	$("#export_allGridTable").attr("title","导出EXCEL");//设置标题
	
	//导出事件，提交到后台 处理导出
	
	$("#export_salesAll").click(function() {
		var postData = $("#salesAllList").jqGrid("getGridParam", "postData"); 
		if (confirm("确认要导出数据？") == false) return false;
		var actionUrl = objectToURL("exportPotential.action?", postData);
		$("#baseForm").attr("action", actionUrl);
		$("#baseForm").submit();
	});
}

/****************************************/
/***************salesAll End*************/
/****************************************/
executeAjax = function (actionUrl, params, fn) {
	 $.ajax({  
	        url : actionUrl,  
	        type : "post",
	        data : params,  
	        dataType : "json",  
	        cache : false,  
	        error : function(textStatus, errorThrown) {  
	            alert("系统ajax交互错误: " + textStatus);  
	        },  
	        success : fn
	    });  
}
/**
 * 转换JS对象为url形式
 * @param obj
 * @returns  返回 {KEY:VALUE}格式的键/值对
 */
function objectToURL(action, obj) {
	var url = action;

	if (typeof(obj) != "object") {
		return url;
	}
	
	url = url+ '&nd='+ obj.nd +'&search='+ obj.search +'&sidx='+obj.sidx +'&sord='+obj.sord 
	+' &filters='+obj.filters +'&searchField='+obj.searchField +'&searchOper='+obj.searchOper +'&searchString='+obj.searchString;
	return url;
}
