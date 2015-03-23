<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登陆</title>
</head>
<body>
	<form class="form-horizontal well" action="loginAction!login.action" method="post">
		用户名：<input type="text" id="userName" name="user.userName" class="conditionInput" /></br>
		密码：<input type="password" id="password" name="user.password" class="conditionInput" /></br>
		<button type="submit" class="btn btn-default">登陆</button>
	</form>
</body>
</html>