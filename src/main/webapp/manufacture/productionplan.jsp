<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/WEB-INF/taglib/sitemesh-decorator.tld"
	prefix="decorator"%>
<%@ taglib uri="/WEB-INF/taglib/sitemesh-page.tld" prefix="page"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查询</title>
<link rel="stylesheet" href="css/jquery-ui.min-jd.css" type="text/css" />
<link rel="stylesheet" href="css/ui.jqgrid.css" type="text/css" />
<link href="css/bootstrap.css" rel="stylesheet">

<script type='text/javascript' src="js/jquery.jqGrid.min.js"></script>
<script type='text/javascript' src="manufacture/js/productionplan.js"></script>
<script type='text/javascript' src="js/jquery-ui.min.js"></script>

<script type='text/javascript' src="js/grid.locale-cn.js"></script>
<script type="text/javascript" src="js/bootstrap-3.0.3.min.js"></script>
<script type="text/javascript" src="js/bootstrap-multiselect.js"></script>

<link rel="stylesheet" href="css/bootstrap-multiselect.css"
	type="text/css" />




<script type="text/javascript" language="javascript">
	//     var selBtn = 0;
	//     var nodeId = 0;
	$(document).ready(function() {
		$('#orderList').multiselect({
		includeSelectAllOption: true,
			        	enableFiltering: true,
			        	maxHeight: 150
			        	});

	});
</script>
</head>
<body>
	<h4>
		生产管理. <small>生产订单</small>
		</h1>


		<form action="" name="data_form" id="data_form" class="form-inline"
			role="form">
			
			
			
			<s:select name="orderList" id="orderList" list="orderNumList" theme="simple" multiple="true" headerKey="00" headerValue="00"></s:select>
			
		
			<div class="form-group">
				<div class="input-group" style="width:200px">

					<div class="input-group" style="width:200px">
						<span class="input-group-addon">订单号码：</span> <input type="text"
							class="form-control" id="orderNum" name="orderNum"
							style="width:100px"> <span class="input-group-btn">

						</span>
					</div>

				</div>
			</div>
			<div class="form-group">
				<div class="input-group" style="width:180px">

					<div class="input-group" style="width:200px">
						<span class="input-group-addon">客戶：</span> <input type="text"
							class="form-control" id="customer" name="customer"
							style="width:100px"> <span class="input-group-btn">

						</span>
					</div>

				</div>
			</div>
			<div class="form-group">
				<div class="input-group" style="width:200px">

					<div class="input-group" style="width:200px">
						<span class="input-group-addon">销售：</span> <input type="text"
							class="form-control" id="sales" name="sales" style="width:100px">
						<span class="input-group-btn"> </span>
					</div>

				</div>
			</div>
			<br> <br>
			<div class="form-group">




				<div class="input-group" style="width:400px">
					<label class="sr-only">日期类型</label> <select id="dateType"
						class="form-control" name="dateType">
						<option value=deliveryDate>要求发货时间</option>
						<option value=dueDate>要求完成时间</option>
					</select> <span class="input-group-addon">从：</span> <input type="text"
						class="form-control" id="fromDate" name="fromDate"
						style="width:100px"> <span class="input-group-addon">到：</span>
					<input type="text" class="form-control" id="toDate" name="toDate"
						style="width:100px">
				</div>


			</div>
			<button type="button" class="btn btn-default"
				onClick="loadPlan() ">查询</button>

			<button type="button" class="btn btn-default" onClick="getOrder()">运算</button>







		</form>


		<table id="planTable" class="table table-striped table-condensed"></table>
		<!-- jqGrid 分页 div gridPager -->
		<div id="planPager"></div>


		<table id="orderItemTable" class="table table-striped table-condensed"></table>
		<!-- jqGrid 分页 div gridPager -->
		<div id="orderPager"></div>
</body>
</html>