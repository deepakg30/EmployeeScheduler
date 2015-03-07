<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Welcome</title>
<script src="./Pages/js/jquery.min.js"></script>
<script src="./Pages/js/bootstrap.min.js"></script>
<link class="cssdeck" rel="stylesheet"
	href="./Pages/css/bootstrap.min.css">
<link rel="stylesheet" href="./Pages/css/bootstrap-responsive.min.css">
</head>
<body>
	<%-- <h1>Welcome!  <s:textfield value="%{#userId}"/></h1> --%>
	<div class="container">
		<nav role="navigation" class="navbar navbar-inverse">
		<div class="navbar-header">

			<button type="button" data-target="#navbarCollapse"
				data-toggle="collapse" class="navbar-toggle">

				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>

			</button>

			<a href="#" class="navbar-brand">MYAPP</a>

		</div>

		<!-- Collection of nav links, forms, and other content for toggling -->

		<div id="navbarCollapse" class="collapse navbar-collapse">

			<ul class="nav navbar-nav">

				<li class="active"><a href="#">Home</a></li>

				<li><a href="#">Reflow</a></li>
				<li><a href="#">Error Trends</a></li>

			</ul>
			<ul class="nav navbar-nav navbar-right">

				<li class="dropdown"><a data-toggle="dropdown"
					class="dropdown-toggle" href="#">${user.uid}<b class="caret"></b></a>

					<ul role="menu" class="dropdown-menu">

						<li><a href="#">Profile</a></li>

						<li><a href="<s:url action="logout"/>">LogOut</a></li>

					</ul></li>
			</ul>

		</div>

		</nav>



		<div class="row">
			<div class="span2">
				<ul class="nav nav-pills nav-stacked">
					<li class="active"><a href="#">Home</a></li>
					<li><a href="<s:url action="reflow"/>">Reflow</a></li>
					<li><a href="<s:url action="errorTrend"/>">Error Trends</a></li>
					<li><a href="#">Link3</a></li>

				</ul>
			</div>
		</div>

	</div>
</body>
</html>