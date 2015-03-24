<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>报表</title>
</head>
<body>
<table class="table table-bordered table-striped">
     <thead>
         <tr>
            <th>报表名称</th>
            <th>描述</th>
          </tr>
        </thead>
        <tbody>
          <s:if test="">
          <tr>
            <td><a href="https://tableau.deere.com/t/China/views/RetailManagementbyDivision/DealerSalesManagementSystem#1" target="_blank">Retail Management by Division</a></td>
            <td>Employees in following can only see</td>
          </tr>
          <tr>
            <td><a href="https://tableau.deere.com/t/China/views/CustomerConversionRatebyDivision/CCRDash#1" target="_blank">Customer Conversion Rate by Division</a></td>
            <td>Employees in following can only see</td>
          </tr>
          </s:if>
          <s:if test="">
          <tr>
            <td><a href="https://tableau.deere.com/t/China/views/RetailManagement/DealerSalesManagementSystem#3" target="_blank">Retail Management</a></td>
            <td>Employees in following group can only see another two reports as following</td>
          </tr>
          <tr>
            <td><a href="https://tableau.deere.com/t/China/views/customerconversionrate/CCRDash#4" target="_blank">Customer Conversion Rate</a></td>
            <td>Employees in following group can only see another two reports as following</td>
          </tr>
          </s:if>
        </tbody>
    </table>
</body>
</html>