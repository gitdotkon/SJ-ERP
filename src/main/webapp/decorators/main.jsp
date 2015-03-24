<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title><decorator:title default="IT Application" /></title>
<meta name="generator" content="Bootply" />
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">

<link rel="stylesheet" href="${ctx}/css/bootstrap.css" >
<link rel="stylesheet" href="${ctx}/css/layout.css" />
<link rel="stylesheet" href="${ctx }/css/jquery-ui.min-jd.css" type="text/css" />
<link rel="stylesheet" href="${ctx }/css/ui.jqgrid.css" type="text/css" />
<link rel="stylesheet" href="${ctx }/css/bootstrap-multiselect.css"	type="text/css" />

<script type='text/javascript' src="${ctx}/js/addon/jquery-1.10.2.js"></script>
<script type='text/javascript' src="${ctx}/js/addon/jquery.jqGrid.min.js"></script>
<script type='text/javascript' src="${ctx}/js/addon/jquery-ui.min.js"></script>
<script type='text/javascript' src="${ctx}/js/addon/grid.locale-cn.js"></script>
<script type="text/javascript" src="${ctx }/js/addon/bootstrap-3.0.3.min.js"></script>
<script type="text/javascript" src="${ctx }/js/addon/bootstrap-multiselect.js"></script>
<script type='text/javascript' src="${ctx}/js/common.js"></script>
<decorator:head />
</head>

<body>

<!-- Header -->
<div id="top-nav" class="navbar navbar-inverse navbar-static-top"><%@ include file="header.jsp"%></div>
<!-- /Header -->

<!-- Main -->
<div class="container">
<div class="row"><!-- Left column -->
<div class="col-md-2"><%@ include file="navigation.jsp"%></div>
<!-- Left column --> <!-- Body -->
<div class="col-md-10"><!-- column 2 --> <a href="#" id="nav"><strong><i class="glyphicon glyphicon-dashboard"></i> 企业信息系统</strong></a>

<hr>
<decorator:body />
<hr>
</div>
<!-- Body --></div>
</div>
<!-- /Main -->

<div class="text-center"><%@ include file="footer.jsp"%></div>
</body>
</html>