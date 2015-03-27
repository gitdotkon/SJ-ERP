<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!-- Left column -->
<a href="#"><strong><i class="glyphicon glyphicon-wrench"></i> Menus</strong></a>

<hr>

<ul class="list-unstyled">
	<li class="nav-header">
<!--<<<<<<< HEAD-->
	<ul class="list-unstyled collapse in">
		<li><a href="${ctx }/salesOrderAction!execute.action"><i class="glyphicon glyphicon-check"></i> 订单管理</a></li>
		<li><a href="${ctx }/productionPlan.action"><i class="glyphicon glyphicon-check"></i> 生产计划</a></li>
		<li><a href="${ctx }/search.action"><i class="glyphicon glyphicon-check"></i> 零件查询</a></li>
		<li><a href="${ctx }/stockManager.action"><i class="glyphicon glyphicon-cog"></i>仓库管理</a></li>
	</ul>
<!--=======
		<ul class="list-unstyled collapse in">
			<li>
				<a href="ordermanagement"><i class="glyphicon glyphicon-check"></i> 订单管理</a>
			</li>
			<li>
				<a href="productionplan"><i class="glyphicon glyphicon-check"></i> 生产计划</a>
			</li>
			<li>
				<a href="search"><i class="glyphicon glyphicon-check"></i> 零件查询</a>
			</li>
			<li>
				<a href="stockmanager"><i class="glyphicon glyphicon-cog"></i>仓库管理</a>
			</li>
		</ul>
>>>>>>> origin/master-->
	</li>
</ul>
<hr>