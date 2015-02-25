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
<link href="../css/bootstrap.css" rel="stylesheet">
<script type='text/javascript' src="js/jquery.jqGrid.min.js"></script>
<script type='text/javascript' src="manufacture/js/salesorder.js"></script>
<script type='text/javascript' src="js/jquery-ui.min.js"></script>

<script type='text/javascript' src="js/grid.locale-cn.js"></script>

<script type="text/javascript" language="javascript">
	//     var selBtn = 0;
	//     var nodeId = 0;
	$(document).ready(function() {
		onOrderSearch();

	});
</script>
</head>
<body>
	<h4>
		订单管理. <small>查询订单</small>
		</h4>


		<table id="orderTable" name="orderTable" class="table table-striped table-condensed"></table>
				<!-- jqGrid 分页 div gridPager -->
				<div id="orderPager"></div>
			<table id="orderItemTable" name="orderItemTable" class="table table-striped table-condensed"></table>
				<!-- jqGrid 分页 div gridPager -->
				<div id="orderItemPager"></div>

</body>
</html>