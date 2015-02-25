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
<link rel="stylesheet"
	href="css/jquery-ui.min-jd.css"
	type="text/css" />
<link rel="stylesheet"
	href="css/ui.jqgrid.css"
	type="text/css" />
	<link href="../css/bootstrap.css" rel="stylesheet">
<script type='text/javascript'
	src="js/jquery.jqGrid.min.js"></script>
<script type='text/javascript'
	src="manufacture/js/production.js"></script>
<script type='text/javascript'
	src="js/jquery-ui.min.js"></script>

<script type='text/javascript'
	src="js/grid.locale-cn.js"></script>

<script type="text/javascript" language="javascript">
	//     var selBtn = 0;
	//     var nodeId = 0;
	$(document).ready(function() {
		loadPlan();

	});
</script>
</head>
<body>
<h4>生产管理. <small>生产订单</small></h1> 
	

		
			
               
			<table id="planTable" class="table table-striped table-condensed"></table>
			<!-- jqGrid 分页 div gridPager -->
			<div id="planPager"></div>
			

				

			
</body>
</html>