<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link
	href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
	rel="stylesheet" id="bootstrap-css">
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.0.8/css/all.css">

<script
	src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
<title>Registration</title>
</head>
<body>

	<div class="card bg-light">
		<article class="card-body mx-auto" style="max-width: 400px;">
			<h4 class="card-title mt-3 text-center">Create Account</h4>

			<form:form id="regForm" modelAttribute="user"
				action="registerProcess" method="post">
				<div class="form-group input-group">
					<div class="input-group-prepend">
						<span class="input-group-text"> <i class="fas fa-user"></i>
						</span>
					</div>

					<form:input path="name" name="name" id="firstname"
						class="form-control" placeholder="First name" type="text" />

				</div>
				<!-- form-group// -->

				<div class="form-group input-group">
					<div class="input-group-prepend">
						<span class="input-group-text"> <i class="fas fa-user"></i>
						</span>
					</div>


					<form:input path="surname" name="surname" id="surname"
						class="form-control" placeholder="Last name" type="text" />
				</div>
				<!-- form-group// -->
				<div class="form-group input-group">
					<div class="input-group-prepend">
						<span class="input-group-text"> <i class="fas fa-envelope"></i>
						</span>
					</div>
					<form:input path="email" name="email" id="email"
						class="form-control" placeholder="Email address" type="email" />

				</div>

				<!-- form-group// -->
				<div class="form-group input-group">
					<div class="input-group-prepend">
						<span class="input-group-text"> <i class="fas fa-key"></i>
						</span>
					</div>
					<form:password path="password" name="password" id="password"
						class="form-control" placeholder="Password" />
				</div>
				<!-- form-group// -->
				<div class="form-group input-group">
					<div class="input-group-prepend">
						<span class="input-group-text"> <i class="fas fa-id-card"></i>
						</span>
					</div>
					<form:input path="taxCode" name="taxCode" id="taxCode"
						class="form-control" placeholder="Tax code" type="text" />

				</div>

				<!-- form-group// -->
				<div class="form-group input-group">
					<div class="input-group-prepend">
						<span class="input-group-text"> <i class="fas fa-phone"></i>
						</span>
					</div>
					<form:input path="phoneNumber" name="phone" id="phone"
						class="form-control" placeholder="Phone number" type="text" />

				</div>
				<!-- form-group// -->
				<!-- 	<div class="form-group input-group">
					<div class="input-group-prepend">
						<span class="input-group-text"> <i class="fa fa-building"></i>
						</span>
					</div>
					<select class="form-control">
						<option selected="">Select type of user</option>
						<option>Driver</option>
						<option>Municipality</option>
						<option>Policeman</option>
					</select>
				</div>-->
				<!-- form-group end.// -->
				<!--  	<div class="form-group input-group">
					<div class="input-group-prepend">
						<span class="input-group-text"> <i class="fa fa-lock"></i>
						</span>
					</div>
					<input class="form-control" placeholder="Create password"
						type="password">
				</div>-->
				<!-- form-group// -->
				<!--  	<div class="form-group input-group">
					<div class="input-group-prepend">
						<span class="input-group-text"> <i class="fa fa-lock"></i>
						</span>
					</div>
					<input class="form-control" placeholder="Repeat password"
						type="password">
				</div>-->
				<!-- form-group// -->
				<div class="form-group">
					<form:button id="register" name="register" type="submit"
						class="btn btn-primary btn-block">Create Account</form:button>

				</div>
				<!-- form-group// -->
				<p class="text-center">
					Have an account? <a href="login">Log In</a>
				</p>
			</form:form>
		</article>
	</div>
	<!-- card.// -->




</body>
</html>