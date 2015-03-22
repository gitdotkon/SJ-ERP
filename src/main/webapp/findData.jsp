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
<title>查询1</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/jquery-ui.min-jd.css" type="text/css" /> 
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/ui.jqgrid.css" type="text/css" /> 
<script type='text/javascript' src="${pageContext.request.contextPath}/js/jquery.jqGrid.min.js"></script>
<script type='text/javascript' src="${pageContext.request.contextPath}/js/find/dataFindGrid.js"></script>
<script type='text/javascript' src="${pageContext.request.contextPath}/js/jquery-ui.min.js"></script>
<script type='text/javascript' src="${pageContext.request.contextPath}/js/grid.locale-cn.js"></script>
<script type="text/javascript" language="javascript">
//     var selBtn = 0;
//     var nodeId = 0;
    $(document).ready(function() {
    	onSearch();
    });
  	
</script>
</head>
<body>
<form action="" method="post" id="baseForm"  enctype="multipart/form-data">
    <div>
    <s:label>选择查询类型：</s:label>
	<select type="select" name="findItem" id="findItem" onchange="onSearch()">
	    <option value="dealer">经销商信息</option>
	    <option value="salesAll">销售信息 </option>
	    <option value="sales">潜在客户</option>
	    <option value="trading">成交客户</option>
	    <option value="failure">丢失客户 </option>
	    <option value="customer">客户信息</option>
    </select>
<!-- 	<button type="button" class="btn btn-primary" onclick="onSearch()">查询</button> -->
    </div>
    <br />
	<div id="div_dealerTable" style="display: none;">
		<table id="dealerList"></table>
		<div id="dealerPager"></div>
	</div>
	<div id="div_customerTable" style="display:none;">
		<table id="customerList"></table>
		<div id="customerPager"></div>
	</div>
	<div id="div_salesTable" style="display:none;">
		<table id="salesList"></table>
		<div id="salesPager"></div>
	</div>
	<div id="div_tradingTable" style="display:none;">
		<table id="tradingList"></table>
		<div id="tradingPager"></div>
	</div>
	<div id="div_failureTable" style="display:none;">
		<table id="failureList"></table>
		<div id="failurePager"></div>
	</div>
	<div id="div_salesAllTable" style="display:none;">
		<table id="salesAllList"></table>
		<div id="salesAllPager"></div>
	</div>
<!-- 	dealer -->
	<div id="consoleDealerDlg" style="display: none;"> 
        <div id="formContainer">  
            <form id="consoleForm">  
                <table class="formTable">  
                    <tr>  
                        <th>经销商编码：</th>  
                        <td>  
                            <input type="text" class="textField" id="dlrCode" name="dlrCode" />  
                        </td>  
                    </tr>  
                    <tr>  
                        <th>经销商名称：</th>  
                        <td>  
                            <input type="text" class="textField" id="dlrName" name="dlrName" />  
                        </td>  
                    </tr>  
                    <tr>  
                        <th>经销商email：</th>  
                        <td>  
                            <input type="text" class="textField" id="dlrEmail" name="dlrEmail" />  
                        </td>  
                    </tr>  
                    <tr>  
                        <th>部门：</th>  
                        <td>  
                            <input type="text" class="textField" id="division" name="division" />  
                        </td>  
                    </tr>  
                    <tr>  
                        <th>负责人：</th>  
                        <td>  
                            <input type="text" class="textField" id="territory" name="territory" />  
                        </td>  
                    </tr>  
                    <tr>  
                        <th>省份：</th>  
                        <td>  
                            <input type="text" class="textField" id="province" name="province" />  
                        </td>  
                    </tr>  
                    <tr>  
                        <th>当事人：</th>  
                        <td>  
                            <input type="text" class="textField" id="principal" name="principal" />  
                        </td>  
                    </tr>                       
                </table>  
            </form>  
        </div>  
    </div>
<!-- 	custoemr -->
	<div id="consoleCustomerDlg" style="display: none;"> 
        <div id="formContainer">  
            <form id="consoleForm">  
                <table class="formTable">  
                    <tr>  
                        <th>姓名：</th>  
                        <td>  
                            <input type="text" class="textField" id="name" name="name" />  
                        </td>  
                    </tr>  
                    <tr>  
                        <th>电话：</th>  
                        <td>  
                            <input type="text" class="textField" id="phoneNumber" name="phoneNumber" />  
                        </td>  
                    </tr>  
                    <tr>  
                        <th>性别：</th>  
                        <td>  
                            <input type="text" class="textField" id="gender" name="gender" />  
                        </td>  
                    </tr>  
                    <tr>  
                        <th>地址：</th>  
                        <td>  
                            <input type="text" class="textField" id="address" name="address" />  
                        </td>  
                    </tr>  
                </table>  
            </form>  
        </div>  
    </div>
<!-- 	sales -->
	<div id="consoleSalesDlg" style="display: none;"> 
        <div id="formContainer">  
            <form id="salesForm">  
                <table class="formTable">  
                    <tr>  
                        <th>客户来源：</th>  
                        <td>  
                            <input type="text" class="textField" id="origine" name="origine" />  
                        </td>  
                    </tr>  
                    <tr>  
                        <th>拟购机型：</th>  
                        <td>  
                            <input type="text" class="textField" id="intendsBuy" name="intendsBuy" />  
                        </td>  
                    </tr>  
                    <tr>  
                        <th>首次接触时间：</th>  
                        <td>  
                            <input type="text" class="textField" id="firstTime" name="firstTime" />  
                        </td>  
                    </tr>  
                    <tr>  
                        <th>拟购数量：</th>  
                        <td>  
                            <input type="text" class="textField" id="intendsBuyNum" name="intendsBuyNum" />  
                        </td>  
                    </tr>  
                </table>  
            </form>  
        </div>  
    </div>

</form>	
</body>
</html>