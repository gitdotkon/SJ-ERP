<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查询</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/jquery-ui.min-jd.css" type="text/css" /> 
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/ui.jqgrid.css" type="text/css" /> 
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/js/treegrid/TreeGrid.css" type="text/css" /> 
<script type='text/javascript' src="${pageContext.request.contextPath}/js/jquery.jqGrid.min.js"></script>
<script type='text/javascript' src="${pageContext.request.contextPath}/js/search.js"></script>
<script type='text/javascript' src="${pageContext.request.contextPath}/js/jquery-ui.min.js"></script>
<%-- <script type='text/javascript' src="${pageContext.request.contextPath}/js/jquery-1.3.2.min.js"></script>
 --%><script type='text/javascript' src="${pageContext.request.contextPath}/js/grid.locale-cn.js"></script>
<script type='text/javascript' src="${pageContext.request.contextPath}/js/treegrid/TreeGrid.js"></script>


<script type="text/javascript" language="javascript">
//     var selBtn = 0;
//     var nodeId = 0;
    $(document).ready(function() {


    });
  	
</script>
</head>
<body>
<s:label>零件类型</s:label>
<select id="partType" name="partType"  >
	<option value=0>全部</option>
	<option value=1>外购</option>
	<option value=2>自制</option>
	<option value=3>组焊</option>
	<option value=4>组合</option>
	<option value=6>总成</option>
	<option value=5>整机</option>
</select>
<input type="text" id="partCode" name="partCode" class="conditionInput" value="${partCode}" onChange="onSearch()"/>	
<button id="target" onClick="onSearch()">查询</button> 
	<table id="partTable"  ></table>
			<!-- jqGrid 分页 div gridPager -->
				<div id="partPager"></div>
<table>

<tr >
<td valign="top" style="padding-top:10px" >
<table id="treegrid" style="width:200px; padding-top:0px ">           </table> 
</td>
 
	<!-- jqGrid table list4 -->
			<td>
				<div id="无 CSS 样式" style="padding-left:30px">
			</td>
			<td valign="top" style="padding-top:10px">
	

<table id="bomTable" style="width:750px"></table>
<!-- jqGrid 分页 div gridPager -->
<div id="bomPager"></div>
</td>
</tr>
</table>


</body>
</html>