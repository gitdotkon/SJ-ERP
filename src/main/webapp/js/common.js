setDatePicker= function(field){
	$(field).datepicker({
		changeMonth : true,
		changeYear : true,
		dateFormat : "yy/mm/dd"
	});
	$(field).val(getSystemData());
}

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

onCheckEnter = function(e,action){
	var keyCode = null;

        if(e.which)
            keyCode = e.which;
        else if(e.keyCode) 
            keyCode = e.keyCode;
            
        if(keyCode == 13) 
			action();


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


delGrid = function(table) {
	var grs = jQuery(table).jqGrid('getGridParam', 'selarrrow');
	if (grs.length > 0)
		jQuery(table).jqGrid('delGridRow', grs, {
			reloadAfterSubmit : false
		});
	else
		alert("请选择要删除行！");
	// 若点击了全选按钮，重置全选按钮框
	$("#proTable").jqGrid('resetSelection');
};

addGrid = function(table,item,key,addId) {
	if (checkDuplicate(table,item,key)) {

		jQuery(table).jqGrid('addRowData', addId, item);
		addId = addId + 1;

		window.setTimeout(function() {
			$(table).jqGrid("editCell", addId, 4, true);
		}, 10);
	}
	return addId;

};

checkDuplicate = function(table,newItem,key) {
	var obj = $(table).jqGrid("getRowData");
	var flag = true;
	if (obj.length > 0) {
		$(obj).each(function() {
			var code = this[key];
			if (code == newItem[key]) {
				alert("主键重复");
				flag = false;
			}
		});
	}
	return flag;

};