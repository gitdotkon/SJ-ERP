<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查询</title>

<script type='text/javascript' src="${ctx }/js/mrpRun.js"></script>


<script type="text/javascript" language="javascript">
	//     var selBtn = 0;
	//     var nodeId = 0;
	$(document).ready(function() {
		pageLoad();

	});
</script>
</head>
<body>
	<h4>订单管理. <small>MRP运算</small></h4>
<hr>	
<div align="right" style="float: right;width: 50%">
      	<span style=" margin-left: 5px;" ><a href="mrpCal">MRP运算</a></span>|
    	<span style=" margin-left: 5px;"><a href="salesOrderAction!execute">创建订单</a></span>|
    	<span style=" margin-left: 5px;"><a href="reportInventory.html">当月库存报告</a></span> 
    	</div> 
<br>

	<form action="" name="data_form" id="data_form" class="form-inline"
			role="form">
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
							class="form-control" id="sales" name="sales"
							style="width:100px"> <span class="input-group-btn">

						</span>
					</div>

				</div>
			</div>
			<br><br>
			<div class="form-group">
				
			
				

				<div class="input-group" style="width:400px">
				<label class="sr-only">日期类型</label> <select id="dateType"
					class="form-control" name="dateType">
					<option value=deliveryDate>要求发货时间</option>
					<option value=dueDate>要求完成时间</option>
				</select>
					<span class="input-group-addon">从：</span> <input type="text"
						class="form-control" id="fromDate" name="fromDate"
						style="width:100px"> 
					<span class="input-group-addon">到：</span> 
					<input type="text"
						class="form-control" id="toDate" name="toDate"
						style="width:100px">
						
						
				</div>


			</div>
			<button type="button" class="btn btn-default"
							onClick="onOrderSearch() ">查询</button>
							
							<button type="button" class="btn btn-default"
							onClick="mprOrder() ">运算</button>
			
			
			
			
							
			
			
		</form>
		<table id="orderTable" name="orderTable" class="table table-striped table-condensed"></table>
				<!-- jqGrid 分页 div gridPager -->
				<div id="orderPager"></div>
		<table id="orderItemTable" name="orderItemTable" class="table table-striped table-condensed"></table>
				<!-- jqGrid 分页 div gridPager -->
				<div id="orderItemPager"></div>

</body>
</html>