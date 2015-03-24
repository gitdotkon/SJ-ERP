<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>数据导入通用接口</title>
</head>
<body>
<s:if test="hasActionErrors()">
	<s:actionerror id="actionerror" />
</s:if>
<s:if test="hasActionMessages()">
	<s:actionmessage id="actionmessage" />
</s:if>
<div id="loading" class="hidden"><img src="css/images/loading.gif" /></div>
<form class="form-horizontal well" action="dataImport!dataImport" method="post" accept-charset="utf-8" role="form" enctype="multipart/form-data" id="dataImport">
<div class="form-group"><label for="file" class="col-sm-2 control-label">导入文件：</label>
<div class="col-sm-10"><input type="file" name="file" class="form-control" placeholder="file..." id="fileupload"></div>
</div>
<div class="form-group"><label for="importType" class="col-sm-2 control-label">文件类型：</label>
<div class="col-sm-10"><label class="radio-inline"> <input type="radio" onclick="setSHDateShowOrHide()" name="importType" id="importType"
	value="SH" <s:if test="importType=='SH'"> checked</s:if>> 发货数据 </label> <label class="radio-inline"> <input type="radio"
	onclick="setSHDateShowOrHide()" name="importType" id="importType" value="PH" <s:if test="importType=='PH'"> checked</s:if>> 配置数据 </label> <label
	class="radio-inline"> <input type="radio" onclick="setSHDateShowOrHide()" name="importType" id="importType" value="DL"
	<s:if test="importType=='DL'"> checked</s:if>> 经销商数据 </label> <label class="radio-inline"> <input type="radio" onclick="setSHDateShowOrHide()"
	name="importType" id="importType" value="DE" <s:if test="importType=='DE'"> checked</s:if>> 经销商员工数据 </label> <!-- 			    <label class="radio-inline"> <input type="radio" onclick="setSHDateShowOrHide()" -->
<!-- 					name="importType" id="importType" value="IV" <s:if test="importType=='IV'"> checked</s:if>> --> <!-- 					进销存数据 --> <!-- 				</label>				 -->
<div class="" id="div_sh_show" style="display: none;">
<div style="float: left; margin-top: 5px;">财年：<input type="text" id="fscYear" name="fscYear" style="width: 50px; height: 20px;"
	onkeypress="return IsNum(event)" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^0-9]/g,''))">&nbsp;&nbsp;</div>
<div style="float: left; margin-top: 5px;">财月： <select type="select" name="fscMon" id="fscMon">
	<option value="1">1月</option>
	<option value="2">2月</option>
	<option value="3">3月</option>
	<option value="4">4月</option>
	<option value="5">5月</option>
	<option value="6">6月</option>
	<option value="7">7月</option>
	<option value="8">8月</option>
	<option value="9">9月</option>
	<option value="10">10月</option>
	<option value="11">11月</option>
	<option value="12">12月</option>
</select></div>
</div>
</div>
<div class="form-group">
<div class="col-sm-offset-2 col-sm-10">
<button type="submit" class="btn btn-default">导入</button>
</div>
</div>
<s:token />
</form>
<hr />

<!-- Table -->
<s:iterator value="invalidDatas" id="dataList">

	<s:if test="#dataList.key=='SH'">
		<table cellpadding="0" cellspacing="0" border="0" class="datatable table table-striped table-bordered">
			<thead>
				<tr>
					<th>#</th>
					<th>序列号</th>
					<th>物料号</th>
					<th>3B代码</th>
					<th>交付类型</th>
					<th>交付日期</th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="#dataList.value" status="shipmentList">
					<tr>
						<td><s:property value="#shipmentList.count" /></td>
						<td><s:property value="serialNum" /></td>
						<td><s:property value="material" /></td>
						<td><s:property value="dlrCode" /></td>
						<td><s:property value="dlvType" /></td>
						<td><s:property value="dlvDate" /></td>
					</tr>
				</s:iterator>
			</tbody>
		</table>
	</s:if>

	<s:elseif test="#dataList.key=='PH'">
		<table cellpadding="0" cellspacing="0" border="0" class="datatable table table-striped table-bordered">
			<thead>
				<tr>
					<th>#</th>
					<th>工厂</th>
					<th>系列</th>
					<th>机型</th>
					<th>排放类型</th>
					<th>马力</th>
					<th>平台</th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="#dataList.value" status="ph">
					<tr>
						<td><s:property value="#ph.count" /></td>
						<td><s:property value="proHierarchy.factory" /></td>
						<td><s:property value="proHierarchy.series" /></td>
						<td><s:property value="proHierarchy.model" /></td>
						<td><s:property value="emisType" /></td>
						<td><s:property value="proHierarchy.horsepower" /></td>
						<td><s:property value="proHierarchy.platform" /></td>
					</tr>
				</s:iterator>
			</tbody>
		</table>
	</s:elseif>

	<s:elseif test="#dataList.key=='DL'">
		<table cellpadding="0" cellspacing="0" border="0" class="datatable table table-striped table-bordered">
			<thead>
				<tr>
					<th>#</th>
					<th>3B代码</th>
					<th>经销商名称</th>
					<th>邮箱地址</th>
					<th>区域</th>
					<th>区域经理</th>
					<th>省份</th>
					<th>联系人</th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="#dataList.value" status="dl">
					<tr>
						<td><s:property value="#dl.count" /></td>
						<td><s:property value="dlrCode" /></td>
						<td><s:property value="dlrName" /></td>
						<td><s:property value="dlrEmail" /></td>
						<td><s:property value="division" /></td>
						<td><s:property value="territory" /></td>
						<td><s:property value="province" /></td>
						<td><s:property value="principal" /></td>
					</tr>
				</s:iterator>
			</tbody>
		</table>
	</s:elseif>

	<s:elseif test="#dataList.key=='DE'">
		<table cellpadding="0" cellspacing="0" border="0" class="datatable table table-striped table-bordered">
			<thead>
				<tr>
					<th>#</th>
					<th>员工ID</th>
					<th>员工姓名</th>
					<th>邮箱地址</th>
					<th>职位</th>
					<th>经销商</th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="#dataList.value" status="de">
					<tr>
						<td><s:property value="#de.count" /></td>
						<td><s:property value="empId" /></td>
						<td><s:property value="name" /></td>
						<td><s:property value="email" /></td>
						<td><s:property value="title" /></td>
						<td><s:property value="dealer.dlrCode" /></td>
					</tr>
				</s:iterator>
			</tbody>
		</table>
	</s:elseif>
	<!-- 		new add inventory -->
	<s:elseif test="#dataList.key=='IV'">
		<table cellpadding="0" cellspacing="0" border="0" class="datatable table table-striped table-bordered">
			<thead>
				<tr>
					<th>#</th>
					<th>经销商</th>
					<th>模型</th>
					<th>数量</th>
					<th>类型</th>
					<th>时间</th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="#dataList.value" status="iv">
					<tr>
						<td><s:property value="#iv.count" /></td>
						<td><s:property value="dlrCode" /></td>
						<td><s:property value="model" /></td>
						<td><s:property value="quantity" /></td>
						<td><s:property value="indicator" /></td>
						<td><s:property value="date" /></td>
					</tr>
				</s:iterator>
			</tbody>
		</table>
	</s:elseif>
</s:iterator>
<script type='text/javascript' src="${pageContext.request.contextPath}/js/dataImport/dataImport.js"></script>
</body>
</html>