<%@ page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/WEB-INF/taglib/sitemesh-decorator.tld"
	prefix="decorator"%>
<%@ taglib uri="/WEB-INF/taglib/sitemesh-page.tld" prefix="page"%>
<!DOCTYPE html>
<html>
<head>
<title>数据导入通用接口</title>
</head>
<body>

	<s:if test="hasActionErrors()">
		<s:actionerror id="actionerror"/>
	</s:if>
	<s:if test="hasActionMessages()">
		<s:actionmessage id="actionmessage"/>
	</s:if>
	<div id="loading" class="hidden">
		<img src="css/images/loading.gif" />
	</div>
	<form class="form-horizontal well" 
		method="post" accept-charset="utf-8" role="form"
		enctype="multipart/form-data" id="dataImport">
		<div class="form-group">
			<label for="file" class="col-sm-2 control-label">导入文件：</label>
			<div class="col-sm-10">
				<input type="file" name="file" class="form-control"
					placeholder="file..." id="fileupload">
			</div>
		</div>
		
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<button type="submit" class="btn btn-default">导入</button>
			</div>
		</div>
		<s:token />
	</form>
	<hr />

	<table id="invTable"></table>
	<!-- jqGrid 分页 div gridPager -->
	<div id="invPager"></div>
	<script type='text/javascript'
		src="${pageContext.request.contextPath}/js/loadPreview.js"></script>
</body>
</html>