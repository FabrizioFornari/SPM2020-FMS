<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css"
	integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2"
	crossorigin="anonymous">


<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.0.8/css/all.css">

<script
	src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
<link
	href="${pageContext.request.contextPath}/resources/css/loginStyle.css"
	rel="stylesheet">
<title>Login</title>
</head>
<body>
	<div class="login-wrap">
		<div class="login-html">
			<input id="tab-1" type="radio" name="tab" class="sign-in" checked><label
				for="tab-1" class="tab">Sign In</label> <input id="tab-2"
				type="radio" name="tab" class="sign-up"><label
				style="display: none" for="tab-2" class="tab">Sign Up</label>
			<div class="login-form">
				<form:form id="loginForm" modelAttribute="login"
					action="loginProcess" method="post">
					<div class="sign-in-htm">
						<div class="group">
							<label for="username" class="label">Username</label>
							<form:input path="username" name="username" id="username"
								class="input" placeholder="Email" type="email" />
						</div>
						<div class="group">
							<label for="password" class="label">Password</label>
							<form:password path="password" name="password" id="password"
								class="input" placeholder="Password" />
						</div>
						<!--  <div class="group">
							<input id="check" type="checkbox" class="check" checked>
							<label for="check"><span class="icon"></span> Keep me
								Signed in</label>
						</div>-->
						<div class="group">
							<form:button id="login" name="login" type="submit" class="button">Login</form:button>
						</div>
						<table align="center">
							<tr>
								<td style="font-style: italic; color: red;">${message}</td>
							</tr>
						</table>
						<div class="hr"></div>
						<div class="foot-lnk">
							<a href="register">Don't have an account?</a>
						</div>
					</div>
				</form:form>
			</div>
		</div>
	</div>
</body>
</html>