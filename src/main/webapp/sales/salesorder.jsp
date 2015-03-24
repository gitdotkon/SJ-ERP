<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查询</title>

<script type='text/javascript' src="${ctx}/js/salesorder.js"></script>

<script type="text/javascript" language="javascript">
	//     var selBtn = 0;
	//     var nodeId = 0;
	$(document).ready(function() {
		pageLoad();

	});
</script>
</head>
<body>
<h4>订单管理. <small>生成订单</small></h4><hr>

<form action="" name="data_form" id="data_form" class="form-inline" role="form">
<div class="form-group">
<div class="input-group" style="width: 200px">

<div class="input-group" style="width: 200px"><span class="input-group-addon">订单号码：</span> <input type="text" class="form-control" id="orderNum"
	name="orderNum" style="width: 100px"> <span class="input-group-btn"> </span></div>

</div>
</div>
<div class="form-group">
<div class="input-group" style="width: 180px">

<div class="input-group" style="width: 200px"><span class="input-group-addon">客戶：</span> <input type="text" class="form-control" id="customer"
	name="customer" style="width: 100px"> <span class="input-group-btn"> </span></div>

</div>
</div>
<div class="form-group">
<div class="input-group" style="width: 200px">

<div class="input-group" style="width: 200px"><span class="input-group-addon">销售：</span> <input type="text" class="form-control" id="sales" name="sales"
	style="width: 100px"> <span class="input-group-btn"> </span></div>

</div>
</div>
<br>
<br>
<div class="form-group">


<div class="input-group" style="width: 400px"><span class="input-group-addon">要求发货时间：</span> <input type="text" class="form-control" id="deliveryDate"
	name="deliveryDate" style="width: 100px"> <span class="input-group-btn"> </span> <span class="input-group-addon">要求完成时间：</span> <input type="text"
	class="form-control" id="dueDate" name="dueDate" style="width: 100px"> <span class="input-group-btn"> </span></div>


</div>
<div class="form-group">
<button type="button" class="btn btn-default" onClick="placeOrder() ">下单</button>

</div>






</form>
<hr>
<div class="form-inline" name="search" role="form">
<div class="form-group"><label class="sr-only">零件类型</label> <select id="partType" class="form-control" name="partType">
	<option value=0>全部</option>
	<option value=1>外购</option>
	<option value=2>自制</option>
	<option value=3>组焊</option>
	<option value=4>组合</option>
	<option value=6>总成</option>
	<option value=5>整机</option>
</select></div>
<div class="form-group">
<div class="input-group" style="width: 200px"><input type="text" class="form-control" placeholder="零件图号" id="partCode" name="partCode" value="${partCode}"
	onKeyPress="onCheckEnter(event,onPartSearch)"> <span class="input-group-btn">
<button class="btn btn-default" type="button" onClick="onPartSearch()">查询</button>
<button type="button" class="btn btn-default" onClick="delGrid(proTable)">删除</button>
</span></div>
</div>
<div id="partResult">
<table id="partTable" class="table table-striped table-condensed"></table>
<!-- jqGrid 分页 div gridPager -->
<div id="partPager"></div>
</div>

</div>


<span class="input-group-addon">订单详情：</span>
<table id="proTable" class="table table-striped"></table>
</body>
</html>