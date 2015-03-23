<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查询</title>
<link rel="stylesheet" href="${ctx }/css/jquery-ui.min-jd.css" type="text/css" />
<link rel="stylesheet" href="${ctx }/css/ui.jqgrid.css" type="text/css" />
<link href="${ctx }/css/bootstrap.css" rel="stylesheet">
<script type='text/javascript' src="${ctx }/js/jquery.jqGrid.min.js"></script>
<script type='text/javascript' src="${ctx }/manufacture/js/manufacture.js"></script>
<script type='text/javascript' src="${ctx }/js/jquery-ui.min.js"></script>

<script type='text/javascript' src="${ctx }/js/grid.locale-cn.js"></script>

<script type="text/javascript" language="javascript">
	//     var selBtn = 0;
	//     var nodeId = 0;
	$(document).ready(function() {
		loadTable();

	});
</script>
</head>
<body>
<h4>仓库管理. <small>入库</small>



<div class="form-inline" role="form">
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
	onKeyPress="onCheckEnter(event)"> <span class="input-group-btn">
<button class="btn btn-default" type="button" onClick="onStockSearch()">查询</button>
</span></div>
</div>
</div>



<table id="partTable" class="table table-striped table-condensed"></table>
<!-- jqGrid 分页 div gridPager -->
<div id="partPager"></div>


<form action="" name="stock_form" id="stock_form" class="form-inline" role="form">

<div class="form-group">


<div class="input-group" style="width: 200px"><span class="input-group-addon">入库时间：</span> <input type="text" class="form-control" id="warehousingDate"
	name="warehousingDate" style="width: 100px"> <span class="input-group-btn">
<button type="button" class="btn btn-default" onClick="doSubmit() ">入库</button>

</span></div>


</div>
<button type="button" class="btn btn-default" onClick="delGrid()">删除</button>
<div class="form-group"></div>
</form>




<table id="stockTable" class="table table-striped"></table>
</body>
</html>