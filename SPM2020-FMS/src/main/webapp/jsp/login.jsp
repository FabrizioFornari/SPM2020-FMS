<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<link
	href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
	rel="stylesheet" id="bootstrap-css">
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.0.8/css/all.css">

<script
	src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
<title>Login</title>
</head>
<body>
	<div class="card bg-light">
		<article class="card-body mx-auto" style="max-width: 400px;">
		<h4 class="card-title mt-3 text-center">Login</h4>
		<form:form id="loginForm" modelAttribute="login" action="loginProcess" method="post">

			<div class="form-group input-group">
				<div class="input-group-prepend">
					<span class="input-group-text"> <i class="fas fa-user"></i>
					</span>
				</div>

				<form:input path="username" name="username" id="username" class="form-control" placeholder="Email" type="email" />

			</div>
			<!-- form-group// -->
			
				<div class="form-group input-group">
				<div class="input-group-prepend">
					<span class="input-group-text"> <i class="fas fa-key"></i>
					</span>
				</div>

				<form:password path="password" name="password" id="password" class="form-control" placeholder="Password" />

			</div>
			<!-- form-group// -->
			
				<div class="form-group">
				<form:button id="login" name="login" type="submit" class="btn btn-primary btn-block">Login</form:button>
				
				</div>
				<!-- form-group// -->
				<p class="text-center">
					Don't have an account? <a href="register">Register</a>
				</p>
		</form:form> </article>
	</div>



	<table align="center">
		<tr>
			<td style="font-style: italic; color: red;">${message}</td>
		</tr>
	</table>

</body>
</html>