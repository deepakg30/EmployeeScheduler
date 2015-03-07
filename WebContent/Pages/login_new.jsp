<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <title>Admin|Login</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="description" content="Expand, contract, animate forms with jQuery wihtout leaving the page" />
        <meta name="keywords" content="expand, form, css3, jquery, animate, width, height, adapt, unobtrusive javascript"/>
		<link rel="shortcut icon" href="../favicon.ico" type="image/x-icon"/>
        <link rel="stylesheet" href="./Pages/css/style.css" type="text/css" />
        <link href="website/css/style.css" rel="stylesheet" type="text/css" />
		<script src="./Pages/js/cufon-yui.js" type="text/javascript"></script>
		<script src="./Pages/js/ChunkFive_400.font.js" type="text/javascript"></script>
		<script type="text/javascript">
			Cufon.replace('h1',{ textShadow: '1px 1px #fff'});
			Cufon.replace('h2',{ textShadow: '1px 1px #fff'});
			Cufon.replace('h3',{ textShadow: '1px 1px #000'});
			Cufon.replace('.back');
		</script>
    </head>
    <body>
		<div class="wrapper">
			<h1>Employee Scheduling</h1>
			<br/>
			<br/>
			<div class="content">
				<div id="form_wrapper" class="form_wrapper">
					<s:form action="loginauth" theme="simple" class="show form-horizontal">
						<h3>Login</h3>
						<s:if test="hasActionErrors()">
							<div class="errors">
								<s:actionerror />
							</div>
						</s:if>
						<div>
							<label>Email Id:</label>
							
							<s:textfield name="user.email" class="input-xlarge" />
							<span class="error">This is an error</span>
						</div>
						<div>
							<label>Password: <a href="#" rel="forgot_password" class="forgot linkform">Forgot your password?</a></label>
							<s:password type="text" name="user.pass" class="input-xlarge" />
							<span class="error">This is an error</span>
						</div>
						<div class="bottom">
							<div class="remember"><input type="checkbox" /><span>Keep me logged in</span></div>
							<input type="submit" value="Login"></input>
							<a href="./Pages/register.html" rel="register" class="linkform">You don't have an account yet? Register here</a>
							<div class="clear"></div>
						</div>
					</s:form>
				
				</div>
				<div class="clear"></div>
			</div>
		</div>
		  </body>
</html>