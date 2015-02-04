<%@ page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/WEB-INF/taglib/sitemesh-decorator.tld"
	prefix="decorator"%>
<%@ taglib uri="/WEB-INF/taglib/sitemesh-page.tld" prefix="page"%>
<!DOCTYPE html>
<html>
<head>
<title>数据导入通用接口</title>
</head>
<body>

	<s:if test="hasActionErrors()">
		<s:actionerror id="actionerror"/>
	</s:if>
	<s:if test="hasActionMessages()">
		<s:actionmessage id="actionmessage"/>
	</s:if>
	<div id="loading" class="hidden">
		<img src="css/images/loading.gif" />
	</div>
	<form class="form-horizontal well" action="dataImport!dataImport"
		method="post" accept-charset="utf-8" role="form"
		enctype="multipart/form-data" id="dataImport">
		<div class="form-group">
			<label for="file" class="col-sm-2 control-label">导入文件：</label>
			<div class="col-sm-10">
				<input type="file" name="file" class="form-control"
					placeholder="file..." id="fileupload">
			</div>
		</div>
		<div class="form-group">
			<label for="importType" class="col-sm-2 control-label">文件类型：</label>
			<div class="col-sm-10">
				<label class="radio-inline"> <input type="radio"
					name="importType" id="importType" value="SH" <s:if test="importType=='SH'"> checked</s:if>> 发货数据
				</label> <label class="radio-inline"> <input type="radio"
					name="importType" id="importType" value="PH" <s:if test="importType=='PH'"> checked</s:if> >
					配置数据
				</label> <label class="radio-inline"> <input type="radio"
					name="importType" id="importType" value="DL" <s:if test="importType=='DL'"> checked</s:if>>
					经销商数据
				</label>
			    <label class="radio-inline"> <input type="radio"
					name="importType" id="importType" value="DE" <s:if test="importType=='DE'"> checked</s:if>>
					经销商员工数据
				</label>				
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
			<table cellpadding="0" cellspacing="0" border="0"
				class="datatable table table-striped table-bordered">
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
			<table cellpadding="0" cellspacing="0" border="0"
				class="datatable table table-striped table-bordered">
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
			<table cellpadding="0" cellspacing="0" border="0"
				class="datatable table table-striped table-bordered">
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
			<table cellpadding="0" cellspacing="0" border="0"
				class="datatable table table-striped table-bordered">
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
	</s:iterator>
	<script type='text/javascript'
		src="${pageContext.request.contextPath}/js/dataImport/dataImport.js"></script>
</body>
</html>