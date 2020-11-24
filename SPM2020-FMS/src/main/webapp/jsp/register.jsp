<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css"
	integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2"
	crossorigin="anonymous">

<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.15.1/css/all.css">
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/javascript.util/0.12.12/javascript.util.min.js">
<script
	src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>

<script src="https://cdn.jsdelivr.net/npm/vue@2/dist/vue.js"></script>
<link
	href="${pageContext.request.contextPath}/resources/css/loginStyle.css"
	rel="stylesheet">
<title>Registration</title>
</head>
<body>

	<div class="login-wrap" id="app">
		<div class="login-html">
			<input id="tab-1" type="radio" name="tab" class="sign-in"><label
				style="display: none" for="tab-1" class="tab">Sign In</label> <input
				id="tab-2" type="radio" name="tab" class="sign-up" checked><label
				for="tab-2" class="tab">Sign Up</label>
			<div class="login-form">
				<form:form id="regForm" modelAttribute="user"
					action="registerProcess" method="post">
					<div class="sign-up-htm">
						<div class="group">
							<label for="firstname" class="label">First Name:</label>
							<form:input path="name" name="name" id="firstname" class="input"
								type="text" required="true" />
						</div>
						<div class="group">
							<label for="surname" class="label">Last Name:</label>
							<form:input path="surname" name="surname" id="surname"
								class="input" type="text" required="true" />
						</div>
						<div class="group">
							<label for="email" class="label">Email Address:</label>
							<form:input path="email" name="email" id="email" class="input"
								type="email" required="true" />
						</div>
						<div class="group">
							<label for="password" class="label">Password:</label>
							<form:password path="password" v-model="password" name="password"
								id="password" class="input" required="true" />
						</div>
						<div class="group">
							<label for="repeatPassword" class="label">Repeat
								Password:</label> <input name="repeatPassword" v-model="repeatPassword"
								v-on:keyup="checkPassword" type="password" id="repeatPassword"
								class="input" required />
						</div>

						<div class="group">
							<label for="userType" class="label">Type:</label>
							<form:select v-model="type" class="input" path="userType"
								id="userType" required="true">
								<option value="" disabled selected>I am a ...</option>
								<option>Driver</option>
								<option>Policeman</option>
								<option>Municipality</option>
							</form:select>
						</div>

						<div class="group">
							<label for="taxCode" class="label">Tax Code:</label>
							<form:input path="taxCode" name="taxCode" id="taxCode"
								class="input" type="text" required="true" />
						</div>

						<div class="group" v-if="type == 'Policeman' ">
							<label for="taxCode" class="label">ID number:</label>
							<form:input path="idNumber" name="idNumber" id="idNumber"
								class="input" type="text" required="true" />
						</div>

						<div class="group" v-if="type == 'Municipality' ">
							<label for="authNumber" class="label">Authorization
								number:</label>
							<form:input path="authNumber" name="authNumber" id="authNumber"
								class="input" type="text" required="true" />
						</div>

						<div class="group">
							<label for="phone" class="label">Phone number:</label>
							<form:input path="phoneNumber" name="phone" id="phone"
								class="input" type="text" required="true" />
						</div>

						<div class="group">
							<form:button id="register" name="register" type="submit"
						class="button">Create Account</form:button>
						</div>
						<table align="center">
							<tr>
								<td style="font-style: italic; color: red;">${message}</td>
								<td v-if="isPasswordValid == false"
									style="font-style: italic; color: red;">The passwords
									don't match!</td>
							</tr>
						</table>
						<div class="hr"></div>
						<div class="foot-lnk">
							<label for="tab-1">Already Member?</a>
						</div>
					</div>
				</form:form>
			</div>
		</div>
	</div>



</body>

<script>
	var app = new Vue({
		el : '#app',
		data : {
			type : '',
			password : '',
			repeatPassword : '',
			isPasswordValid : null
		},
		methods : {
			checkPassword : function() {
				if (this.password.trim() === this.repeatPassword.trim()) {
					this.isPasswordValid = true;
					return;
				} else {
					this.isPasswordValid = false;
					return;
				}
			}
		}
	})
</script>

</html>
