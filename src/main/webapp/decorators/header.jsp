<!-- Header -->
<div class="container">
	<div class="navbar-header">
		<button type="button" class="navbar-toggle" data-toggle="collapse"
			data-target=".navbar-collapse">
			<span class="icon-toggle"></span>
		</button>
		<a class="navbar-brand" href="dashboard">Dashboard</a>
	</div>
	<div class="navbar-collapse collapse">
		<ul class="nav navbar-nav navbar-right">

			<li class="dropdown">
				<a class="dropdown-toggle" role="button" data-toggle="dropdown" href="#">
					<i class="glyphicon glyphicon-user"></i> 
					<s:property	value="#session.user" /> 
					<span class="caret"></span>
				</a>
				<ul id="g-account-menu" class="dropdown-menu" role="menu">
					<li><a href="#">My Profile</a></li>
				</ul>
			</li>
			<li>
				<a href="https://registration.deere.com/servlet/LogOutServlet">
					<i class="glyphicon glyphicon-lock"></i> Logout</a>
			</li>
		</ul>
	</div>
</div>
<!-- /container -->
<!-- /Header -->