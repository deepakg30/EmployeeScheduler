<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login</title>
</head>
<script src="./Pages/js/jquery.min.js" type="text/javascript"></script>
<script src="./Pages/js/bootstrap.min.js" type="text/javascript"></script>
<link class="cssdeck" rel="stylesheet"
	href="./Pages/css/bootstrap.min.css">
<link rel="stylesheet" href="./Pages/css/bootstrap-responsive.min.css">
<body>

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

		</nav>
		<div class="row">

			<div class="span6 well">
				<s:form action="loginauth" theme="simple" class="form-horizontal">
					<fieldset>
						<div id="legend">
							<legend class="">Login</legend>
						</div>
						<s:if test="hasActionErrors()">
							<div class="errors">
								<s:actionerror />
							</div>
						</s:if>
						<div class="control-group">
							<!-- Username -->
							<label class="control-label" for="username">Email_Id</label>
							<div class="controls">
								<s:textfield name="user.email" class="input-xlarge" />
							</div>
						</div>

						<div class="control-group">
							<!-- Password-->
							<label class="control-label" for="password">Password</label>
							<div class="controls">
								<s:password type="text" name="user.pass" class="input-xlarge" />
							</div>
						</div>


						<div class="control-group">
							<!-- Button -->
							<div class="controls">
								<input type="submit" name="login" value="Login"
									class="btn btn-success">
							</div>
						</div>
					</fieldset>
				</s:form>
			</div>
		</div>
	</div>


</body>
</html>