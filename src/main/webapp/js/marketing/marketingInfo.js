/**
 * [Data] json 数据
 */
var factoryModel = {"JDTAW":"JD904,JD954,JD1054,JD1104,JD1204,JD1354,JD1404,JD5-750,JD5-754,JD5-800,JD5-804,JD5-850,JD5-854,JD5-900,JD5-904,JD5-950,JD5-1000",
        "JDJW":"C100,C110,C230,L60,L70,W210,W70,W80,Y210,Y215,Y110,Y115,9970",
        "JDNW":"JD504,3B-504,JD500,JD550,JD550-1,JD554,JD554-1,JDX550,JDB550,JDX554,JDB554,JD354,2C-354,JD350,JD280,JD284,JD300,JD304,JD320,JD324,JD400,JD404,JD450,JD454,JD480,JD484,JD350-3,JDB350,JD704,JD704-1,JD700,JD700-1,JD654,JD654-1,JD650,JD650-1,JD604,JD604-1,JD600,JD600-1,CH330,CP20",
        "JDHBW":"1654,1854,2054,2104,S660,2204,2854"
};
var intendsBuyData = "JD904,JD954,JD1054,JD1104,JD1204,JD1354,JD1404,JD5-750,JD5-754,JD5-800,JD5-804,JD5-850,JD5-854,JD5-900,JD5-904,JD5-950,JD5-1000," +
         "C100,C110,C230,L60,L70,W210,W70,W80,Y210,Y215,Y110,Y115,9970," +
         "JD504,3B-504,JD500,JD550,JD550-1,JD554,JD554-1,JDX550,JDB550,JDX554,JDB554,JD354,2C-354,JD350,JD280,JD284,JD300,JD304,JD320,JD324,JD400,JD404,JD450,JD454,JD480,JD484,JD350-3,JDB350,JD704,JD704-1,JD700,JD700-1,JD654,JD654-1,JD650,JD650-1,JD604,JD604-1,JD600,JD600-1,CH330,CP20," +
         "1654,1854,2054,2104,S660,2204,2854";

var addId = 0;

var marketingInfo = {
		/*客分类*/
		cusClassify:"",
		/*基本信息*/
		name:"",
		sex:"",
		phone:"",
		address:"",
		/*详细信息--潜在客户登记*/
		origine:"",
		firstTime:"",
		intendsBuy:"",
		intendsBuyNum:"",
		opinionLeaderName:"",//机头
		/*详细信息--成交客户登记*/
		belongsFactory:"",
		buyModels:"",
		buyNum:"",
		soldDate:"",
		operationType:"",
		updateId:"",
		/*详细信息--丢失客户登记*/
		lostCause:"",
		origIntendModel:"",
		buyBrand:"",
		actualPayment:"",
		experience:""
};
jQuery(function($) {
    $.datepicker.regional['zh-CN'] = {
        clearText: '清除',
        clearStatus: '清除已选日期',
        closeText: '关闭',
        closeStatus: '不改变当前选择',
        prevText: '<上月',
        prevStatus: '显示上月',
        prevBigText: '<<',
        prevBigStatus: '显示上一年',
        nextText: '下月>',
        nextStatus: '显示下月',
        nextBigText: '>>',
        nextBigStatus: '显示下一年',
        currentText: '今天',
        currentStatus: '显示本月',
        monthNames: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
        monthNamesShort: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
        monthStatus: '选择月份',
        yearStatus: '选择年份',
        weekHeader: '周',
        weekStatus: '年内周次',
        dayNames: ['星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六'],
        dayNamesShort: ['周日', '周一', '周二', '周三', '周四', '周五', '周六'],
        dayNamesMin: ['日', '一', '二', '三', '四', '五', '六'],
        dayStatus: '设置 DD 为一周起始',
        dateStatus: '选择 m月 d日, DD',
        dateFormat: 'yy-mm-dd',
        firstDay: 7,
        initStatus: '请选择日期',
        isRTL: false
    };
    $.datepicker.setDefaults($.datepicker.regional['zh-CN']);
});
/**
 * [初始加载] 初始加载的一些方法
 */
initMarket = function () {
	loadTable();
	poshytipShow();
    changeDropListIntendsBuy();
    changeDropListOrigIntendModel();
    changeDropListFactory();
    $("#firstTime").datepicker({changeMonth: true, changeYear: true, dateFormat: "yy/mm/dd"});
    $("#firstTime").val(getSystemData());
//    $("#soldDate").datepicker({minDate: new Date(2014, 11 - 1, 3), maxDate: new Date(2014, 12 - 1, 3), changeMonth: true, changeYear: true, dateFormat: "yy/mm/dd"});
//    $("#soldDate").datepicker({minDate: new Date(getfscMonFirstDay(new Date())), maxDate: new Date(getfscMonLastDay(new Date())), changeMonth: true, changeYear: true, dateFormat: "yy/mm/dd"});
    $("#soldDate").datepicker({minDate: new Date(getfscMonFirstDay(new Date())), maxDate: new Date(), changeMonth: true, changeYear: true, dateFormat: "yy/mm/dd"});
    $("#soldDate").val(getSystemData());
};
poshytipShow = function () {
	 $('#intendsBuyNum').poshytip({
			className: 'tip-yellowsimple',
			alignTo: 'target',
			alignX: 'inner-left',
			offsetX: 0,
			offsetY: 5,
			showTimeout: 100
		});	
	 $('#buyNum').poshytip({
		 className: 'tip-yellowsimple',
		 alignTo: 'target',
		 alignX: 'inner-left',
		 offsetX: 0,
		 offsetY: 5,
		 showTimeout: 100
	 });	
	 $('#actualPayment').poshytip({
		 className: 'tip-yellowsimple',
		 alignTo: 'target',
		 alignX: 'inner-left',
		 offsetX: 0,
		 offsetY: 5,
		 showTimeout: 100
	 });	
	 $('#experience').poshytip({
		 className: 'tip-yellowsimple',
		 alignTo: 'target',
		 alignX: 'inner-left',
		 offsetX: 0,
		 offsetY: 5,
		 showTimeout: 100
	 });	
};
/**
 * [时间] 获取系统当前时间 格式为 yyyy/mm/dd
 */
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
/**
 * [选项卡] 初始化选项卡
 */
jQuery.jqtab = function(tabtit,tabcon) {
    $(tabcon).hide();
    $(tabtit+" li:first").addClass("thistab").show();
    $(tabcon+":first").show();

    $(tabtit+" li").click(function() {
        $(tabtit+" li").removeClass("thistab");
        $(this).addClass("thistab");
        $(tabcon).hide();
        var activeTab = $(this).find("a").attr("tab");
        $("#tabShowTab").attr("value", activeTab);
        $("#"+activeTab).fadeIn();
        return false;
    });
    
};
/**
 * [Table]使用jqGrid画table
 */
loadTable = function () {
	jQuery("#sourceTable").jqGrid({  
		datatype: "local",
		height: 151,
		width: 600, 
	   	colNames:['客分类','姓名','性别','电话','地址','客户来源','推荐者','首次接触时间','拟购机型','拟购数量','所属工厂','成交日期','购买机型','购买数量','操作类型','变更号','丢失原因','最初拟购机型','购买品牌','实际支付','经验总结'],
	   	colModel:[
            {name:'cusClassify',index:'cusClassify', width:65,align:"center"},
			{name:'name',index:'name',sortable:true, width:120,align:"center",editable:true},
			{name:'sex',index:'sex',sortable:true, width:120,align:"center"},
			{name:'phone',index:'phone',sortable:true, width:120,align:"center",editable:true},
			{name:'address',index:'address',sortable:true, width:120,align:"center",editable:true},
			{name:'origine',index:'origine',sortable:true, width:120,align:"center"},
			{name:'opinionLeaderName',index:'opinionLeaderName',sortable:true, width:120,align:"center"},
			{name:'firstTime',index:'firstTime',sortable:true, width:120,align:"center"},
			{name:'intendsBuy',index:'intendsBuy',sortable:true, width:120,align:"center"},
			{name:'intendsBuyNum',index:'intendsBuyNum',sortable:true, width:120,align:"center",editable: true, editrules:{integer:true, minValue:1}},
			{name:'belongsFactory',index:'belongsFactory',sortable:true, width:120,align:"center"},
			{name:'soldDate',index:'soldDate',sortable:true, width:120,align:"center"},
			{name:'buyModels',index:'buyModels',sortable:true, width:120,align:"center"},
			{name:'buyNum',index:'buyNum',sortable:true, width:120,align:"center",editable: true, editrules:{integer:true, minValue:1}},
			{name:'operationType',index:'operationType',sortable:true, width:120,align:"center"},
			{name:'updateId',index:'updateId',sortable:true, width:120,align:"center"},
			{name:'lostCause',index:'lostCause',sortable:true, width:120,align:"center"},
			{name:'origIntendModel',index:'origIntendModel',sortable:true, width:120,align:"center"},
			{name:'buyBrand',index:'buyBrand',sortable:true, width:120,align:"center"},
			{name:'actualPayment',index:'actualPayment',sortable:true, width:120,align:"center",editable: true, editrules:{integer:true, minValue:1}},
			{name:'experience',index:'experience',sortable:true, width:120,align:"center",editable: true},],
		multiselect: true,
 		multikey: "ctrlKey",
 		cellEdit:true,
		editurl:"http://trirand.com/blog/jqgrid/jqgrid.html",
		cellsubmit: 'clientArray',//单元格提交方法，数据保存在客户端。不需设置url:""
		sortable:true,
		rowNum: 100000,
		shrinkToFit:false,
		caption: ""
	});
	jQuery("#sourceTableHead").jqGrid({  
		datatype: "local",
		height: 134,
		colNames:['客分类','姓名','性别','电话','地址','客户来源','推荐者','首次接触时间','拟购机型','拟购数量','所属工厂','成交日期','购买机型','购买数量','操作类型','变更号','丢失原因','最初拟购机型','购买品牌','实际支付','经验总结'],
	    colModel:[
	            {name:'cusClassify',index:'cusClassify', width:65,align:"center"},
		    	{name:'name',index:'name',sortable:true, width:120, align:"center"},
                {name:'sex',index:'sex',sortable:true, width:120,align:"center"},
                {name:'phone',index:'phone',sortable:true, width:120,align:"center"},
                {name:'address',index:'address',sortable:true, width:120,align:"center"},
                {name:'origine',index:'origine',sortable:true, width:120,align:"center"},
                {name:'opinionLeaderName',index:'opinionLeaderName',sortable:true, width:120,align:"center"},
                {name:'firstTime',index:'firstTime',sortable:true, width:120,align:"center"},
                {name:'intendsBuy',index:'intendsBuy',sortable:true, width:120,align:"center"},
                {name:'intendsBuyNum',index:'intendsBuyNum',sortable:true, width:120,align:"center"},
                {name:'belongsFactory',index:'belongsFactory',sortable:true, width:120,align:"center"},
                {name:'soldDate',index:'soldDate',sortable:true, width:120,align:"center"},
                {name:'buyModels',index:'buyModels',sortable:true, width:120,align:"center"},
                {name:'buyNum',index:'buyNum',sortable:true, width:120,align:"center"},
                {name:'operationType',index:'operationType',sortable:true, width:120,align:"center"},
    			{name:'updateId',index:'updateId',sortable:true, width:120,align:"center"},
                {name:'lostCause',index:'lostCause',sortable:true, width:120,align:"center"},
                {name:'origIntendModel',index:'origIntendModel',sortable:true, width:120,align:"center"},
                {name:'buyBrand',index:'buyBrand',sortable:true, width:120,align:"center"},
                {name:'actualPayment',index:'actualPayment',sortable:true, width:120,align:"center"},
                {name:'experience',index:'experience',sortable:true, width:120,align:"center"}, ],
                multiselect: true,
                multikey: "ctrlKey",
                cellEdit:true,
                editurl:"http://trirand.com/blog/jqgrid/jqgrid.html",
                sortable:true,
                rowNum: 100000,
                shrinkToFit:false,
                caption: ""
	});
};
/**
 * [焦点]焦点移开时判断是否是电话格式，不是重新获取焦点
 */
onChangePhone = function (obj) {
	 //电话校验 
	var phoneExpr = /^0\d{2,3}-\d{7,8}?$/;
	var mobilePhoneExpr = /^1[3|4|5|7|8|][0-9]\d{8}$/; 
	var phone = $.trim($(obj).val());
	if (phone == "") {
		$("#errorPhone").show();
	} else {
		if (!phoneExpr.test(phone) && !mobilePhoneExpr.test(phone)) {
			$("#errorPhoneExpr").show();
		} else {
			$("#errorPhoneExpr").hide();
		}
		$("#errorPhone").hide();
	}
};
/**
 * [其他]当dropList选择为其他时，显示隐藏input框
 */
changeBuyBrandOther = function (obj) {
	var brand = $.trim($(obj).val());
	if (brand === "其他") {
		$("#buyBrandInputDiv").show();
	} else {
		$("#buyBrandInputDiv").hide();
	}
};
/**
 * [新增]按钮按下处理（根据输入数据在表里生成一行数据）
 */
addMarketing = function () {
	var flag = true,clearflag = true;
	//数字
	var digitExpr = /^\d+(\.\d+)?$/;
	//正整数
	var integerExpr = /^[0-9]*[1-9][0-9]*$/;
    //电话校验 
	var phoneExpr = /^0\d{2,3}-\d{7,8}?$/;
	var mobilePhoneExpr = /^1[3|4|5|7|8|][0-9]\d{8}$/; 
	/*tab1*/
	var origine='',firstTime='',intendsBuy='',intendsBuyNum='',opinionLeaderName='';
	/*tab2*/
	var belongsFactory='',buyModels='',buyNum='',soldDate='', operationType='', updateId='';
	/*tab3*/
	var lostCause='',origIntendModel='',buyBrandSelect='',buyBrand='',actualPayment='',experience='';
	var objTable = $("#sourceTable").jqGrid("getRowData");
	var name = $.trim($("#name").val());
	var sex = $.trim($("#sex").val());
	var phone = $.trim($("#phone").val());
	var address = $.trim($("#province").val())+$.trim($("#city").val())+$.trim($("#county").val())+$.trim($("#address").val());
	//
	$("#sourceTableHeadDiv").hide();
	$("#sourceTableDiv").show();
	//验证
	if (name == "") {
		$("#errorName").show();
		flag = false;
	} else {
		$("#errorName").hide();
	}
	if (phone == "") {
		$("#errorPhone").show();
		flag = false;
	} else {
		if (!phoneExpr.test(phone) && !mobilePhoneExpr.test(phone)) {
			$("#errorPhoneExpr").show();
			flag = false;
		} else {
			$("#errorPhoneExpr").hide();
		}
		$("#errorPhone").hide();
	}
	
	/*判断选择哪一个tab*/
	var showTab = $("#tabShowTab").val();
	switch (showTab) {
	case "tab1":
		marketingInfo.cusClassify = "潜在";
		origine = $.trim($("#origine").val());
		firstTime = $.trim($("#firstTime").val());
		intendsBuy = $.trim($("#intendsBuy").val());
		intendsBuyNum = $.trim($("#intendsBuyNum").val());
		opinionLeaderName = $.trim($("#opinionLeaderName").val());
		//正则校验是否为正整数
		if (intendsBuyNum != "") {
			if (!integerExpr.test(intendsBuyNum)) {
				$("#errorIntendsBuyNum").show();
				flag = false;
			} else {
				$("#errorIntendsBuyNum").hide();
			}
		}
		if (!flag) {
			return;
		}
		if(objTable.length > 0) {
			$(objTable).each(function () {
				var tab1phone = this["phone"];
				var tab1intendsBuy = this["intendsBuy"];
				if (intendsBuy!= "" && phone === tab1phone && intendsBuy === tab1intendsBuy) {
					clearflag = false;
				} 
			});
		}
		//判断是否重复，不重复数据清空
		if (clearflag) {
			clearTab1();
		} else {
			alert("不可新增重复数据");
			return;
		}
		break;
	case "tab2":
		marketingInfo.cusClassify = "成交";
		belongsFactory = $.trim($("#belongsFactory").val());
		buyModels = $.trim($("#buyModels").val());
		buyNum = $.trim($("#buyNum").val());
		soldDate = $.trim($("#soldDate").val());
		operationType = $(":radio:checked").val();
		updateId = $.trim($("#updateId").val());
		//正则校验是否为正整数
		if (operationType == "变更") {
			if (buyNum != "") {
				$("#errorBuyNum").hide();
			} else {
				$("#errorBuyNum").show();
				flag = false;
			}
			if (updateId == "") {
				$("#errorUpdateId").show();
				flag = false;
			} else {
				$("#errorUpdateId").hide();
			}
		} else {
			if (buyNum != "") {
				if (!integerExpr.test(buyNum)) {
					$("#errorBuyNum").show();
					flag = false;
				} else {
					$("#errorBuyNum").hide();
				}
			} else {
				$("#errorBuyNum").show();
				flag = false;
			}
			
		}
		if (!flag) {
			return;
		}
		if(objTable.length > 0) {
			$(objTable).each(function () {
				var tab2phone = this["phone"];
				var tab2buyModels = this["buyModels"];
				var tab2soldDate = this["soldDate"];
				if (buyModels != "" && phone === tab2phone && buyModels === tab2buyModels && soldDate === tab2soldDate) {
					clearflag = false;
				} 
			});
		}
		//判断是否重复，不重复数据清空
		if (clearflag) {
			clearTab2();
		} else {
			alert("不可新增重复数据");
			return;
		}
		break;
	case "tab3":
		marketingInfo.cusClassify = "丢失";
		lostCause = $.trim($("#lostCause").val());
		origIntendModel = $.trim($("#origIntendModel").val());
		buyBrandSelect = $.trim($("#buyBrandSelect").val());
		//判断其他是否有值
		if (buyBrandSelect === "其他") {
			buyBrand = $.trim($("#buyBrandInput").val());
			if (buyBrand == "" || buyBrand == null) {
				$("#errorBuyBrand").show();
				flag = false;
			} else {
				$("#errorBuyBrand").hide();
			}
		} else {
			buyBrand = $.trim($("#buyBrand").val());
		}
		actualPayment = $.trim($("#actualPayment").val());
		experience = $.trim($("#experience").val());
		//正则校验是否为正整数
		if (actualPayment != ""){
			if (!digitExpr.test(actualPayment)) {
			    $("#errorActualPayment").show();
			    flag = false;
		    } else {
			    $("#errorActualPayment").hide();
		    }
		} else {
			$("#errorActualPayment").hide();
		}
		if (!flag) {
			return;
		}
		if(objTable.length > 0) {
			//循环遍历
			$(objTable).each(function () {
				var tab3phone = this["phone"];
				var tab3origIntendModel = this["origIntendModel"];
				if (origIntendModel != "" & phone === tab3phone && origIntendModel === tab3origIntendModel) {
					clearflag = false;
				} 
			});
		}
		//判断是否重复，不重复数据清空
		if (clearflag) {
			clearTab3();
		} else {
			alert("不可新增重复数据");
			return;
		}
		break;

	default:
		marketingInfo.cusClassify = "潜在";
		origine = $.trim($("#origine").val());
		firstTime = $.trim($("#firstTime").val());
		intendsBuy = $.trim($("#intendsBuy").val());
		intendsBuyNum = $.trim($("#intendsBuyNum").val());
		opinionLeaderName = $.trim($("#opinionLeaderName").val());
		//正则校验是否为正整数
		if (!integerExpr.test(intendsBuyNum)) {
			$("#errorIntendsBuyNum").show();
			flag = false;
		} else {
			$("#errorIntendsBuyNum").hide();
		}
		if (!flag) {
			return;
		}
		if(objTable.length > 0) { 
			$(objTable).each(function () {
				var tab1phone = this["phone"];
				var tab1intendsBuy = this["intendsBuy"];
				if (intendsBuy!= "" && phone === tab1phone && intendsBuy === tab1intendsBuy) {
					clearflag = false;
				} 
			});
		}
		//判断是否重复，不重复数据清空
		if (clearflag) {
			clearTab1();
		} else {
			alert("不可新增重复数据");
			return;
		}
		break;
	}
	
	marketingInfo.name = name;
	marketingInfo.sex = sex;
	marketingInfo.phone = phone;
	marketingInfo.address = address;
	marketingInfo.origine = origine;
	marketingInfo.firstTime = firstTime;
	marketingInfo.intendsBuy = intendsBuy;
	marketingInfo.intendsBuyNum = intendsBuyNum.replace(/\b(0+)/gi, "");
	marketingInfo.opinionLeaderName = opinionLeaderName;
	
	marketingInfo.belongsFactory = belongsFactory;
	marketingInfo.buyModels = buyModels;
	marketingInfo.buyNum = buyNum.replace(/\b/gi, "");
	marketingInfo.soldDate = soldDate;
	marketingInfo.operationType = operationType;
	marketingInfo.updateId = updateId;
	
	
	marketingInfo.lostCause = lostCause;
	marketingInfo.buyBrand = buyBrand;
	var actualStr = actualPayment.replace(/\b(0+)/gi, "").indexOf(".");
	if (actualStr == "0") {
		marketingInfo.actualPayment = actualPayment.replace(/\b(0+)/gi, "").replace(/./, "0.");
	} else {
		marketingInfo.actualPayment = actualPayment.replace(/\b(0+)/gi, "");
	}
	
	marketingInfo.experience = experience;
	marketingInfo.origIntendModel = origIntendModel;
	 
	jQuery("#sourceTable").jqGrid('addRowData',addId,marketingInfo);
	addId++;
	
};
/**
 * [清空tab1]清空tab1中的数据
 */
clearTitle= function(){
	$("#name").val("");
	$("#phone").val("");
	$("#province").val("");
	$("#city").val("");
	$("#city").css('display','none'); 
	$("#county").val("");
	$("#county").css('display','none'); 
	$("#address").val("");
	$("#sex").val("男");
};

clearTab1 = function () {
	$("#origineSelect").val("到店咨询（店面宣传资料）");
	$("#origine").val("到店咨询（店面宣传资料）");
	$("#firstTime").val(getSystemData());
	var selectEl=document.getElementById("intendsBuySelect");  
	if (selectEl.length>0) {  
		selectEl.options[0].selected=true;
		$("#intendsBuy").val(selectEl.options[0].text);
	}
	$("#intendsBuyNum").val("");
	$("#opinionLeaderName").val("");
};
/**
 * [清空tab2]清空tab2中的数据
 */
clearTab2 = function () {
	$("#belongsFactorySelect").val("JDNW");
	$("#belongsFactory").val("宁波工厂");
	$("#soldDate").val(getSystemData());
	$("input[name=operationType][value=添加]").attr("checked",true);
	selectOperationType();
	$("#updateId").val("");
	changeDropListFactory();
	$("#buyNum").val("");
};
/**
 * [清空tab3]清空tab3中的数据
 */
clearTab3 = function () {
	$("#lostCauseSelect").val("价格太高");
	$("#lostCause").val("价格太高");
	$("#buyBrandSelect").val("一拖东方红");
	$("#buyBrand").val("一拖东方红");
	$("#buyBrandInput").val("");
	$("#buyBrandInputDiv").hide();
	$("#actualPayment").val("");
	$("#experience").val("");
	var selectEl1=document.getElementById("origIntendModelSelect");  
	if (selectEl1.length>0) {  
		selectEl1.options[0].selected=true;
		$("#origIntendModel").val(selectEl1.options[0].text);
	}
};
/**
 * [删除]按钮按下处理（删除checkbook选中的数据）
 */
delMarketing = function () {
	var grs = jQuery("#sourceTable").jqGrid('getGridParam','selarrrow'); 
	if( grs.length > 0 ) 
		jQuery("#sourceTable").jqGrid('delGridRow',grs,{reloadAfterSubmit:false}); 
	else alert("请选择要删除行！");
	$("#sourceTable").jqGrid('resetSelection'); 
};
/**
 * [删除]按钮按下处理（删除checkbook选中的数据）废弃
 */
delMarketingAbandon = function () {
	var chks = $("input[type='checkbox'][name='chkIDs']");
	$(chks).each(function() {
		if ($(this).is(":checked")) {
			$(this).parents("tr").remove();
		}
	});
	document.getElementById("chkid").checked=false;
};
/**
 * [过滤]过滤其他数据
 */
$(function() { 
	//性别
	$("#sex").autocomplete({ 
 		source: ['男', '女'],
        delay: 100,
        autoFocus: true,
        select: function(event, ui) { 
        	/*设置值相等的为选中*/
        	var val = ui.item.value;
        	/*设置是否存在， 默认为不存在*/
        	var existFlag = false;
            var selectEl=document.getElementById("sexSelect");  
            if(selectEl.length>0){  
	            for(var i=0; i < selectEl.options.length; i++){  
	                if(selectEl.options[i].text==val){  
	                	selectEl.options[i].selected=true;  
	                	existFlag = true;
	                    break;  
	                }  
	            }  
            }
            if (!existFlag) {
				var selectEl=document.getElementById("sexSelect");  
				if(selectEl.length>0){  
					for(var i=0; i<selectEl.options.length; i++){  
						if(selectEl.options[i].text=="男"){  
							selectEl.options[i].selected=true;
							$("#sex").val("男");
							break;  
						}  
					}  
				}
			}
        } 
    }); 
	//客户来源
	$("#origine").autocomplete({ 
		source: ['到店咨询（店面宣传资料）', '亲朋推荐', '座谈会/推介会/培训', '展会/巡展', '演示会', 
		         '当地农机系统提供/客户拜访', '广告宣传(电视, 网站, 杂志, 公交广告等)', '厂家邮寄的宣传品（直邮）', '老客户', '其他'],
		delay: 100,
		autoFocus: true,
		select: function(event, ui) { 
			/*设置值相等的为选中*/
			var val = ui.item.value;
			/*设置是否存在， 默认为不存在*/
        	var existFlag = false;
			var selectEl=document.getElementById("origineSelect");  
			if(selectEl.length>0){  
				for(var i=0; i<selectEl.options.length; i++){  
					if(selectEl.options[i].text==val){  
						selectEl.options[i].selected=true;  
						existFlag = true;
						break;  
					}  
				}  
			}
			if (!existFlag) {
				var selectEl=document.getElementById("origineSelect");  
				if(selectEl.length>0){  
					for(var i=0; i<selectEl.options.length; i++){  
						if(selectEl.options[i].text=="到店咨询（店面宣传资料）"){  
							selectEl.options[i].selected=true;
							$("#origine").val("到店咨询（店面宣传资料）");
							break;  
						}  
					}  
				}
			}
		} 
	}); 
	//拟购机型
	$("#intendsBuy").autocomplete({ 
		source: dataSplit(),
		delay: 100,
		autoFocus: true,
		select: function(event, ui) { 
			/*设置值相等的为选中*/
			var val = ui.item.value;
			/*设置是否存在， 默认为不存在*/
        	var existFlag = false;
			var selectEl=document.getElementById("intendsBuySelect"); 
			if(selectEl.length>0){  
				for(var i=0; i<selectEl.options.length; i++){  
					if(selectEl.options[i].text==val){  
						selectEl.options[i].selected=true; 
						existFlag = true;
						break; 
					}  					
				}  
			}
			if (!existFlag) {
				var selectEl=document.getElementById("intendsBuySelect");  
				if(selectEl.length>0){  
					for(var i=0; i<selectEl.options.length; i++){  
						if(selectEl.options[i].text=="JD904"){  
							selectEl.options[0].selected=true;
							$("#intendsBuy").val("JD904");
							break;  
						}  
					}  
				}
			}
		} 
	}); 
	
	//最初拟购机型
	$("#origIntendModel").autocomplete({ 
		source: dataSplit(),
		delay: 100,
		autoFocus: true,
		select: function(event, ui) { 
			/*设置值相等的为选中*/
			var val = ui.item.value;
			/*设置是否存在， 默认为不存在*/
        	var existFlag = false;
			var selectEl=document.getElementById("origIntendModel"); 
			if(selectEl.length>0){  
				for(var i=0; i<selectEl.options.length; i++){  
					if(selectEl.options[i].text==val){  
						selectEl.options[i].selected=true; 
						existFlag = true;
						break; 
					}  					
				}  
			}
			if (!existFlag) {
				var selectEl=document.getElementById("origIntendModel");  
				if(selectEl.length>0){  
					for(var i=0; i<selectEl.options.length; i++){  
						if(selectEl.options[i].text=="JD904"){  
							selectEl.options[i].selected=true;
							$("#intendsBuy").val("JD904");
							break;  
						}  
					}  
				}
			}
		} 
	}); 
	
	//所属工厂
	$("#belongsFactory").autocomplete({ 
		source: ['宁波工厂', '天津工厂', '哈尔滨工厂', '佳木斯工厂'],
		delay: 100,
		autoFocus: true,
		select: function(event, ui) { 
			/*设置值相等的为选中*/
			var val = ui.item.value;
			/*设置是否存在， 默认为不存在*/
        	var existFlag = false;
			var selectEl=document.getElementById("belongsFactorySelect");  
			if(selectEl.length>0){  
				for(var i=0; i<selectEl.options.length; i++){  
					if(selectEl.options[i].text==val){  
						selectEl.options[i].selected=true;  
						changeDropListFactory();
						existFlag = true;
						break;  
					}  
				}  
			}
			if (!existFlag) {
				var selectEl=document.getElementById("belongsFactorySelect");  
				if(selectEl.length>0){  
					for(var i=0; i<selectEl.options.length; i++){  
						if(selectEl.options[i].text=="宁波工厂"){  
							selectEl.options[i].selected=true;
							changeDropListFactory();
							$("#belongsFactory").val("宁波工厂");
							break;  
						}  
					}  
				}
			}
		} 
	}); 
	
	//丢失原因
	$("#lostCause").autocomplete({ 
		source: ['价格太高', '产品性能不能满足要求', '交货时间不能满足要求', '当地政府补贴还未开始', '零售信贷政策不能满足用户要求', '不到作业季节, 不着急购机','不确定是否能够拿到补贴指标',
		         '处在同类产品的对比阶段','生意模式改变导致不再有购机需求','其它'],
		delay: 100,
		autoFocus: true,
		select: function(event, ui) { 
			/*设置值相等的为选中*/
			var val = ui.item.value;
			/*设置是否存在， 默认为不存在*/
        	var existFlag = false;
			var selectEl=document.getElementById("lostCauseSelect");  
			if(selectEl.length>0){  
				for(var i=0; i<selectEl.options.length; i++){  
					if(selectEl.options[i].text==val){  
						selectEl.options[i].selected=true;  
						existFlag = true;
						break;  
					}  
				}  
			}
			if (!existFlag) {
				var selectEl=document.getElementById("lostCauseSelect");  
				if(selectEl.length>0){  
					for(var i=0; i<selectEl.options.length; i++){  
						if(selectEl.options[i].text=="价格太高"){  
							selectEl.options[i].selected=true;
							$("#lostCause").val("价格太高");
							break;  
						}  
					}  
				}
			}
		} 
	}); 
	
	//购买品牌
	$("#buyBrand").autocomplete({ 
		source: ['一拖东方红', '福田雷沃', '东风', '时风金鹰', '黄海金马', '马恒达', '山拖', '常发', '奇瑞', '江苏清江', '山东巨明', '山东金亿', '洛阳中收', '江苏沃得',
		         '常发佳联', '山东宁联', '中农博远', '湖州星光', '柳林', '爱科', '中机南方', '新疆牧神', '其他'],
		delay: 100,
		autoFocus: true,
		select: function(event, ui) { 
			/*设置值相等的为选中*/
			var val = ui.item.value;
			/*设置是否存在， 默认为不存在*/
			var existFlag = false;
			var selectEl=document.getElementById("buyBrandSelect");  
			if(selectEl.length>0){  
				for(var i=0; i<selectEl.options.length; i++){  
					if(selectEl.options[i].text==val){  
						selectEl.options[i].selected=true;  
						existFlag = true;
						break;  
					}  
				}  
			}
			if (!existFlag) {
				var selectEl=document.getElementById("buyBrandSelect");  
				if(selectEl.length>0){  
					for(var i=0; i<selectEl.options.length; i++){  
						if(selectEl.options[i].text=="一拖东方红"){  
							selectEl.options[i].selected=true;
							$("#lostCause").val("一拖东方红");
							break;  
						}  
					}  
				}
			}
		} 
	}); 
}); 
/**
 * [自动过滤]自动过滤购买机型
 */
buyModelsAutocomplete = function (buyModelsArray) {
	//购买机型
	$("#buyModels").autocomplete({ 
		source: buyModelsArray,
		delay: 100,
		autoFocus: true,
		select: function(event, ui) { 
			/*设置值相等的为选中*/
			var val = ui.item.value;
			/*设置是否存在， 默认为不存在*/
			var existFlag = false;
			var selectEl=document.getElementById("buyModelsSelect");  
			if(selectEl.length>0){  
				for(var i=0; i<selectEl.options.length; i++){  
					if(selectEl.options[i].text==val){  
						selectEl.options[i].selected=true;  
						existFlag = true;
						break;  
					}  
				}  
			}
			if (!existFlag) {
				var selectEl=document.getElementById("buyModelsSelect");  
				if(selectEl.length>0){  
					selectEl.options[0].selected=true;
					$("#buyModels").val(selectEl.options[0].text);
				}
			}
		} 
	}); 
};
/**
 * [性别失焦]当自动过滤不满足，失去焦点是调用
 */
onblurSex = function (obj) {
	var val = $(obj).val();
	var existFlag = false;
	var selectEl=document.getElementById("sexSelect");  
	if(selectEl.length>0){  
		for(var i=0; i<selectEl.options.length; i++){  
			if(selectEl.options[i].text==val){  
				selectEl.options[i].selected=true;  
				existFlag = true;
				break;  
			}  
		}  
	}
	if (!existFlag) {
		var selectEl=document.getElementById("sexSelect");  
		if(selectEl.length>0){  
			for(var i=0; i<selectEl.options.length; i++){  
				if(selectEl.options[i].text=="男"){  
					selectEl.options[i].selected=true;  
					$("#sex").val("男");
					break;  
				}  
			}  
		}
	}
};
/**
 * [客户来源失焦]当自动过滤不满足，失去焦点是调用
 */
onblurOrigine = function (obj) {
	var val = $(obj).val();
	var existFlag = false;
	var selectEl=document.getElementById("origineSelect");  
	if(selectEl.length>0){  
		for(var i=0; i<selectEl.options.length; i++){  
			if(selectEl.options[i].text==val){  
				selectEl.options[i].selected=true;  
				existFlag = true;
				break;  
			}  
		}  
	}
	if (!existFlag) {
		var selectEl=document.getElementById("origineSelect");  
		if(selectEl.length>0){  
			for(var i=0; i<selectEl.options.length; i++){  
				if(selectEl.options[i].text=="到店咨询（店面宣传资料）"){  
					selectEl.options[i].selected=true;  
					$("#origine").val("到店咨询（店面宣传资料）");
					break;  
				}  
			}  
		}
	}
};
/**
 * [拟购机型失焦]当自动过滤不满足，失去焦点是调用
 */
onblurIntendsBuy = function (obj) {
	var val = $(obj).val();
	var existFlag = false;
	var selectEl=document.getElementById("intendsBuySelect");  
	if (selectEl.length>0) {  
		for(var i=0; i<selectEl.options.length; i++){  
			if(selectEl.options[i].text==val){  
				selectEl.options[i].selected=true;  
				existFlag = true;
				break;  
			}  
		}  
	}
	if (!existFlag) {
		var selectEl=document.getElementById("intendsBuySelect");  
		if(selectEl.length>0){  
			selectEl.options[0].selected=true;  
			$("#intendsBuy").val(selectEl.options[0].text);
		}
	}
};

/**
 * [最初拟购机型]当自动过滤不满足，失去焦点是调用
 */
onblurOrigIntendModel = function (obj) {
	var val = $(obj).val();
	var existFlag = false;
	var selectEl=document.getElementById("origIntendModelSelect");  
	if (selectEl.length>0) {  
		for(var i=0; i<selectEl.options.length; i++){  
			if(selectEl.options[i].text==val){  
				selectEl.options[i].selected=true;  
				existFlag = true;
				break;  
			}  
		}  
	}
	if (!existFlag) {
		var selectEl=document.getElementById("origIntendModelSelect");  
		if(selectEl.length>0){  
			selectEl.options[0].selected=true;  
			$("#origIntendModel").val(selectEl.options[0].text);
		}
	}
};

/**
 * [所属工厂失焦]当自动过滤不满足，失去焦点是调用
 */
onblurBelongsFactory = function (obj) {
	var val = $(obj).val();
	var existFlag = false;
	var selectEl=document.getElementById("belongsFactorySelect");  
	if(selectEl.length>0){  
		for(var i=0; i<selectEl.options.length; i++){  
			if(selectEl.options[i].text==val){  
				selectEl.options[i].selected=true;  
				changeDropListFactory();
				existFlag = true;
				break;  
			}  
		}  
	}
	if (!existFlag) {
		var selectEl=document.getElementById("belongsFactorySelect");  
		if(selectEl.length>0){  
			for(var i=0; i<selectEl.options.length; i++){  
				if(selectEl.options[i].text=="宁波工厂"){  
					selectEl.options[i].selected=true;  
					changeDropListFactory();
					$("#belongsFactory").val("宁波工厂");
					break;  
				}  
			}  
		}
	}
};
/**
 * [购买机型]当自动过滤不满足，失去焦点是调用
 */
onblurBuyModels = function (obj) {
	var val = $(obj).val();
	var existFlag = false;
	var selectEl=document.getElementById("buyModelsSelect");  
	if(selectEl.length>0){  
		for(var i=0; i<selectEl.options.length; i++){  
			if(selectEl.options[i].text==val){  
				selectEl.options[i].selected=true;  
				existFlag = true;
				break;  
			}  
		}  
	}
	if (!existFlag) {
		var selectEl=document.getElementById("buyModelsSelect");  
		if(selectEl.length>0){  
			selectEl.options[0].selected=true;  
			$("#buyModels").val(selectEl.options[0].text);
		}
	}
};
/**
 * [丢失原因失焦]当自动过滤不满足，失去焦点是调用
 */
onblurLostCause = function (obj) {
	var val = $(obj).val();
	var existFlag = false;
	var selectEl=document.getElementById("lostCauseSelect");  
	if(selectEl.length>0){  
		for(var i=0; i<selectEl.options.length; i++){  
			if(selectEl.options[i].text==val){  
				selectEl.options[i].selected=true;  
				existFlag = true;
				break;  
			}  
		}  
	}
	if (!existFlag) {
		var selectEl=document.getElementById("lostCauseSelect");  
		if(selectEl.length>0){  
			for(var i=0; i<selectEl.options.length; i++){  
				if(selectEl.options[i].text=="价格太高"){  
					selectEl.options[i].selected=true;  
					$("#lostCause").val("价格太高");
					break;  
				}  
			}  
		}
	}
};
/**
 * [购买品牌]当自动过滤不满足，失去焦点是调用
 */
onblurBuyBrand = function (obj) {
	var val = $(obj).val();
	if (val === "其他") {
		$("#buyBrandInputDiv").show();
	} else {
		$("#buyBrandInputDiv").hide();
	}
		
	var existFlag = false;
	var selectEl=document.getElementById("buyBrandSelect");  
	if(selectEl.length>0){  
		for(var i=0; i<selectEl.options.length; i++){  
			if(selectEl.options[i].text==val){  
				selectEl.options[i].selected=true;  
				existFlag = true;
				break;  
			}  
		}  
	}
	if (!existFlag) {
		var selectEl=document.getElementById("buyBrandSelect");  
		if(selectEl.length>0){  
			for(var i=0; i<selectEl.options.length; i++){  
				if(selectEl.options[i].text == "一拖东方红"){  
					selectEl.options[i].selected=true;  
					$("#buyBrand").val("一拖东方红");
					break;  
				}  
			}  
		}
	}
};

/**
 * [显示] dropList拟购机型
 */
changeDropListIntendsBuy = function () {
	var factoryModelKeys = dataSplit();
	var intendsBuySelect = $("#intendsBuySelect");
	for(var i = 0; i < factoryModelKeys.length; i++) {
		intendsBuySelect.append("<option value='"+ factoryModelKeys[i] +"' name=''>"+factoryModelKeys[i]+"</option>");
	}
	$("#intendsBuy").val(factoryModelKeys[0]);
};

/**
 * [显示] dropList最初拟购机型
 */
changeDropListOrigIntendModel = function () {
	var factoryModelKeys = dataSplit();
	var intendsBuySelect = $("#origIntendModelSelect");
	for(var i = 0; i < factoryModelKeys.length; i++) {
		intendsBuySelect.append("<option value='"+ factoryModelKeys[i] +"' name=''>"+factoryModelKeys[i]+"</option>");
	}
	$("#origIntendModel").val(factoryModelKeys[0]);
};

/**
 * [显示] dropList
 */
changeDropListFactory = function () {
	
	var factoryModelKeys = new Array();
	var selectElement = $("#buyModelsSelect");
	selectElement.empty();
	var factoryId = $("#belongsFactorySelect").val();
	var modelsStr = factoryModel[factoryId];
	if (modelsStr != "") {
		factoryModelKeys = modelsStr.split(",");
		for(var i = 0; i < factoryModelKeys.length; i++) {
			selectElement.append("<option value='"+factoryModelKeys[i]+"' name=''>"+factoryModelKeys[i]+"</option>");
		}
		$("#buyModels").val(factoryModelKeys[0]);
	}
	buyModelsAutocomplete(factoryModelKeys);
};
/**
 * [切割] 把字符串以“，”切割成数组
 */
dataSplit = function () {
	var dataArr = new Array();
	if (intendsBuyData != "") {
		dataArr = intendsBuyData.split(",");
	}
	return dataArr;
};
/**
 * [提交] 点击[提交]按钮，将表格中的数据转换成JSON字符串，提交给UFO服务器
 */
function doSubmit(){
	if(confirmForm()){
		//确认提交后验证数量是否为正整数等等check
        if (!(chkBeforeSub())) {return;}
        //为form_generated_code表单创建4个隐藏字段
		createNewFieldToForm("form_generated_code", "JsonData");
		createNewFieldToForm("form_generated_code", "EmailSendTo");
		createNewFieldToForm("form_generated_code", "UUID");
		createNewFieldToForm("form_generated_code", "ReportType");
		createNewFieldToForm("form_generated_code", "USERID");
		createNewFieldToForm("form_generated_code", "ufoReturnURL");
		document.form_generated_code.method = "post";
		//取当前日期的毫秒数
		var d = new Date();
		strUID = d.getTime();

		//json格式的表数据
		var jsonData = $("#sourceTable").jqGrid("getRowData");
		//通过cookie获取的sm_session_cookie
		var  smCookie = "sm_session_cookie";
		var userId = getCookie(smCookie);
		if(typeof(userId) == "undefined" || userId == ""){
			userId = "testuser";
		}
		document.form_generated_code.JsonData.value = JSON.stringify(jsonData);
		document.form_generated_code.EmailSendTo.value = "honglin.ren@hand-china.com";
		document.form_generated_code.ReportType.value = "SL";
		document.form_generated_code.UUID.value = strUID; 
		document.form_generated_code.USERID.value = userId;
		document.form_generated_code.ufoReturnURL.value="http://dlrdoc.deere.com/china/forms/jddsm/html/returnPage.html";
		document.form_generated_code.action = "https://ufo.deere.com/servlet/smSecureUFO?12199=1";
		document.form_generated_code.submit();
		
	}
}

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
}


function confirmForm(){
   	var _ret = true;
   	var msg;
   	msg = "确认提交?";
   	if (!confirm(msg))  //if user chooses not to submit their form yet, set _ret to false
		_ret = false;
	else  				//else, set _ret to true (user wants to submit their form)
		_ret = true;
	return _ret;		//return the result
}
/**
 * [提交check]点击提交按钮并确认提交后进行为空的check
 */
chkBeforeSub = function () {
    var obj = $("#sourceTable").jqGrid("getRowData");

    if (obj.length == 0) {
        alert("没有新增记录！");
        return false;
    }

    return true;
};
/**
 * 从cookie中获取sm_session_cookie，赋值给自定义的变量
 */
function getCookie(cookiename){
	/* 获取浏览器所有cookie将其拆分成数组 */ 
	var arrstr = document.cookie.split("; ");
	for(var i = 0; i < arrstr.length; i ++) {
		/* 将cookie名称和值拆分进行判断 */
	   var temp = arrstr[i].split("=");
	   if(temp[0] == cookiename) 
		  return unescape(temp[1]);
	}
}

/**
 * 鼠标从当前行移开时，焦点定位到当前行的第二列
 */
moveFocus = function () {
	var obj = $("#sourceTable").jqGrid("getRowData");
	if (obj.length == 0) {
    	return ;
    }
	for (var i = 0; i < obj.length; i++) {
		window.setTimeout(function () { $("#sourceTable").jqGrid("editCell", i + 1, 1, true); }, 100);
		return;
    }
};

selectOperationType = function () {
	var operation = $(":radio:checked").val();
	if (operation === "变更") {
		$("#updateDiv").show();
	} else {
		$("#updateDiv").hide();
	}
}

isShowOpinionLeader =  function (obj) {
	var selected = obj.options[obj.selectedIndex].text;
	switch(selected) {
	case "亲朋推荐" :
	case "座谈会/推介会/培训" :
	case "展会/巡展" :
	case "演示会" :
	case "当地农机系统提供/客户拜访" :
		var opinionLeaderDlg = $("#opinionLeaderDlg");  
	    var dialogButtonPanel = opinionLeaderDlg.siblings(".ui-dialog-buttonpane");  
	    opinionLeaderDlg.find("input").removeAttr("disabled").val("");  
	    dialogButtonPanel.find("button:not(:contains('取消'))").hide();  
	    dialogButtonPanel.find("button:contains('确定')").show();  
	    opinionLeaderDlg.dialog("option", "title", "如有推荐/介绍者，请填写").dialog("open"); 
		break;
	}
}

jQuery(function($) {
	// 配置对话框  
    $("#opinionLeaderDlg").dialog({  
        autoOpen: false,      
        modal: true,    // 设置对话框为模态（modal）对话框  
        resizable: true,      
        width: 350,  
        buttons: {  // 为对话框添加按钮  opinion leader
        	"确定": addOpinionLeader, 
            "取消": function() {$("#opinionLeaderDlg").dialog("close")} 
        }  
    });
});

var addOpinionLeader = function () {
    var opinionLeaderDlg = $("#opinionLeaderDlg");  
          
    var opinionLeader = $.trim(opinionLeaderDlg.find("#opinionLeader").val());  
    opinionLeaderDlg.find("#opinionLeader").val("");
    $("#opinionLeaderName").val(opinionLeader);
    opinionLeaderDlg.dialog("close");
    
}

