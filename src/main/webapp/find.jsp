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
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/Pager.css" type="text/css" /> 	
<script type='text/javascript' src="${pageContext.request.contextPath}/js/find/commonpager.js"></script>
<script type='text/javascript' src="${pageContext.request.contextPath}/js/find/common.tableData.js"></script>
<script type='text/javascript' src="${pageContext.request.contextPath}/js/find/dataFind.js"></script>
<script type="text/javascript" language="javascript">
    var selBtn = 0;
    var nodeId = 0;
    $(document).ready(function() {
    	pageCallBack = getData;
    	// 分页器加载
        $("#pager").pager({ pagenumber: 1, pagecount: 8, pagesize: 10, buttonClickCallback: pageClick });
    });
  	
</script>
</head>
<body>
    <div>
    <s:label>选择查询类型：</s:label>
	<select type="select" name="findItem" id="findItem">
	    <option value="dealer">经销商信息</option>
	    <option value="sales">销售机会</option>
	    <option value="trading">成交机会</option>
	    <option value="failure">失败机会 </option>
	    <option value="customer">客户信息</option>
    </select>
	<button type="button" class="btn btn-primary" onclick="onSearch()">查询</button>
    </div>
    <br />
	<div id="div_dealerTable" style="display: none;">
		<table id="dealerTable" border='1px solid #F00' width="100%">
			<thead>
				<tr class="title">
					<th id="dlrCode" style="text-align: center;">经销商编码</th>
					<th id="dlrName" style="text-align: center;">经销商名称</th>
					<th id="dlrEmail" style="text-align: center;">经销商email</th>
					<th id="division" style="text-align: center;">部门</th>
					<th id="territory" style="text-align: center;">负责人</th>
					<th id="province" style="text-align: center;">省份</th>
					<th id="principal" style="text-align: center;">当事人</th>
				</tr>
			</thead>
			<tbody></tbody>
		</table>
	</div>
	<br />
	<ul id="pager" class="page_position"></ul>
	

	
</body>
</html>