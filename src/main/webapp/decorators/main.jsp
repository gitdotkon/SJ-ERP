<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/WEB-INF/taglib/sitemesh-decorator.tld"
	prefix="decorator"%>
<%@ taglib uri="/WEB-INF/taglib/sitemesh-page.tld" prefix="page"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title><decorator:title default="IT Application" /></title>
<meta name="generator" content="Bootply" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<link href="${pageContext.request.contextPath}/css/bootstrap.css"
	rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/layout.css"
	rel="stylesheet" />
<!-- Just for debugging purposes. Don't actually copy this line! -->
<!--[if lt IE 9]><script src="../../docs-assets/js/ie8-responsive-file-warning.js"></script><![endif]-->

<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
      <script src="http://cdn.bootcss.com/html5shiv/3.7.0/html5shiv.min.js"></script>
      <script src="http://cdn.bootcss.com/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->
<script type='text/javascript'
	src="${pageContext.request.contextPath}/js/jquery-1.10.2.js"></script>   
<script type='text/javascript'
	src="${pageContext.request.contextPath}/js/common.js"></script> 
<decorator:head />
</head>

<body>

	<!-- Header -->
	<div id="top-nav" class="navbar navbar-inverse navbar-static-top">
		<%@ include file="header.jsp"%>
	</div>
	<!-- /Header -->

	<!-- Main -->
	<div class="container">
		<div class="row">
			<!-- Left column -->
			<div class="col-md-2">
				<%@ include file="navigation.jsp"%>
			</div>
			<!-- Left column -->
			<!-- Body -->
			<div class="col-md-10">
				<!-- column 2 -->
				<a href="#"><strong><i
						class="glyphicon glyphicon-dashboard"></i> My Dashboard</strong></a>

				<hr>
				<decorator:body />
				<hr>
			</div>
			<!-- Body -->
		</div>
	</div>
	<!-- /Main -->

	<div class="text-center">
		<%@ include file="footer.jsp"%>
	</div>
</body>
</html>