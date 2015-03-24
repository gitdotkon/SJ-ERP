<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>其他</title>
<script type="text/javascript">
 function formSubmit(actionName) {
    var f = document.createElement("form");
	document.body.appendChild(f);
	f.action = actionName;
	if (actionName == 'inventoryForcastData!inventoryForcastData') {
		var fscYear = document.getElementById("fscYear1").value;
		var fscMon = document.getElementById("fscMon1").value;
		var newItemYear = document.createElement("input");
		newItemYear.id = "fscYear";
		newItemYear.name = "fscYear";
		newItemYear.type = "hidden";
		newItemYear.value = fscYear;
		var newItemMon = document.createElement("input");
		newItemMon.id = "fscMon";
		newItemMon.name = "fscMon";
		newItemMon.type = "hidden";
		newItemMon.value = fscMon;
		f.appendChild(newItemYear);
		f.appendChild(newItemMon);
	} else if (actionName == 'computeRate!computeRate') {
		var fscYear = document.getElementById("fscYear2").value;
		var fscMon = document.getElementById("fscMon2").value;
		var newItemYear = document.createElement("input");
		newItemYear.id = "fscYear";
		newItemYear.name = "fscYear";
		newItemYear.type = "hidden";
		newItemYear.value = fscYear;
		var newItemMon = document.createElement("input");
		newItemMon.id = "fscMon";
		newItemMon.name = "fscMon";
		newItemMon.type = "hidden";
		newItemMon.value = fscMon;
		f.appendChild(newItemYear);
		f.appendChild(newItemMon);
	}
	f.submit();
 }
 getFscYear = function () {
	var myDate = new Date();
	var fscYear = myDate.getFullYear() + ((myDate.getMonth()+1)<11?0:1)
	return fscYear;
}
$(document).ready(function() {
	var year = getFscYear();
	$("#fscYear1").val(year);
	$("#fscYear2").val(year);
});
</script>
</head>
<body>
<s:if test="hasActionErrors()">
	<s:actionerror id="actionerror"/>
</s:if>
<s:if test="hasActionMessages()">
	<s:actionmessage id="actionmessage"/>
</s:if>
<s:form id="otherPage">
<table class="table table-bordered table-striped">
     <thead>
         <tr>
            <th>描述</th>
            <th>动作</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td>重新导入邮件</td>
            <td><button type="button" class="btn" onclick="formSubmit('reimportMail!reimportMail')">执行</button></td>
          </tr>
          <tr>
            <td>计算I/R Rate
            	<div class="" id="div_sh_show" style="display: ;">
					<div style="float: left; margin-top: 5px;">财年：<input type="text" id ="fscYear2" name ="fscYear" style="width: 50px; height: 20px;"
					onkeypress="return IsNum(event)" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^0-9]/g,''))">&nbsp;&nbsp;</div>
					<div style="float: left; margin-top: 5px;">财月：
					<select type="select" name="fscMon" id="fscMon2">
					    <option value="1">1月</option> <option value="2">2月</option> <option value="3">3月</option>
					    <option value="4">4月</option> <option value="5">5月</option> <option value="6">6月</option>
					    <option value="7">7月</option> <option value="8">8月</option> <option value="9">9月</option>
					    <option value="10">10月</option> <option value="11">11月</option> <option value="12">12月</option>
				    </select>
				    </div>
			   </div>
            </td>
            <td><button type="button" class="btn" onclick="formSubmit('computeRate!computeRate')">执行</button></td>
<!--             <td><button type="button" class="btn" onclick="formSubmit('computeRate!computeRate')">执行</button></td> -->
          </tr>
          <tr>
            <td>重新发送I/R 邮件
            	<div class="" id="div_sh_show" style="display: ;">
					<div style="float: left; margin-top: 5px;">财年：<input type="text" id ="fscYear1" name ="fscYear" style="width: 50px; height: 20px;"
					onkeypress="return IsNum(event)" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^0-9]/g,''))">&nbsp;&nbsp;</div>
					<div style="float: left; margin-top: 5px;">财月：
					<select type="select" name="fscMon" id="fscMon1">
					    <option value="1">1月</option> <option value="2">2月</option> <option value="3">3月</option>
					    <option value="4">4月</option> <option value="5">5月</option> <option value="6">6月</option>
					    <option value="7">7月</option> <option value="8">8月</option> <option value="9">9月</option>
					    <option value="10">10月</option> <option value="11">11月</option> <option value="12">12月</option>
				    </select>
				    </div>
			   </div>
			</td>
            <td><button type="button" class="btn" onclick="formSubmit('inventoryForcastData!inventoryForcastData')">执行</button></td>
          </tr>
        </tbody>
    </table>
</s:form>
</body>
</html>