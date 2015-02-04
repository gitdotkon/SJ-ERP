$(document).ready(function(){

	//Uploading the file
	$("#dataImport").submit(function(){
		$("actionerror").val("");
		$("actionmessage").val("");
		
	$("#actionerror").html("");
	$("#actionmessage").html("");
	
	var fileupload = $("#fileupload").val();
	var importType = $('input[name="importType"]:checked').val();
	
	if(fileupload==null||fileupload==""){
		alert("请选择上传的文件!");
		return false ;
	}
	if(importType==null||importType==""){
		alert("请选择文件类型!");
		return false;
	}		
	$("#loading").removeClass("hidden").addClass("show");
	});
	setSHDateShowOrHide();
});

setSHDateShowOrHide = function () {
	var operation = $(":radio:checked").val();
	if (operation === "SH") {
		var day = new Date();
		var year = getFiscalYear(day);
		$("#fscYear").val(year);
		var month = getFiscalMonth(day);
		$("#fscMon").val(month);
		$("#div_sh_show").show();
	} else {
		$("#div_sh_show").hide();
	}
}

//getFscYear = function () {
//	var myDate = new Date();
//	var fscYear = myDate.getFullYear() + ((myDate.getMonth()+1)<11?0:1)
//	return fscYear;
//}
