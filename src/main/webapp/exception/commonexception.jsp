<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>出错拉</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link rel="stylesheet" href="styles/bootstrap.css">

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
        <script src="http://cdn.bootcss.com/html5shiv/3.7.0/html5shiv.min.js"></script>
        <script src="http://cdn.bootcss.com/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->
</head>
<body>
	<div class="container">
		<div class="jumbotron">
			<h1>出错拉:-(</h1>
			<p>请点击"返回"按钮回到上一页面重试,如果问题仍然存在,请联系管理员! Email:ZhengSuiyao@JohnDeere.com,RenHonglin@JohnDeere.com</p>
			<p><div style="color: red;"><s:property value="exception.printStackTrace()"/></div></p>
			<p>
			<!-- TODO -->
				<a class="btn btn-lg btn-primary" href="javascript:history.go(-1)"
					role="button">返回 &raquo;</a>
			</p>
		</div>

	</div>
	<!-- /container -->

</body>
</html>
