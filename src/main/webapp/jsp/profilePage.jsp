

<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">


<link
	href="${pageContext.request.contextPath}/resources/css/profileStyle.css"
	rel="stylesheet">
<title>Profile</title>
</head>
<body>

	<jsp:include page="navBar.jsp"></jsp:include>
	<div id="app">

		<article class="card-body mx-auto" style="max-width: 400px;">
			<h4 class="card-title mt-3 text-center">Update your information:</h4>

			<form:form id="updateForm" modelAttribute="user"
				action="updateUserProcess" method="post">
				<div class="form-group input-group">
					<div class="input-group-prepend">
						<span class="input-group-text"> <i class="fas fa-user"></i>
						</span>
					</div>

					<form:input path="name" name="name" id="firstname" class="input"
						placeholder="First name" type="text" required="true" />

				</div>
				<!-- form-group// -->

				<div class="form-group input-group">
					<div class="input-group-prepend">
						<span class="input-group-text"> <i class="fas fa-user "></i>
						</span>
					</div>


					<form:input path="surname" name="surname" id="surname"
						class="input" placeholder="Last name" type="text" required="true" />
				</div>
				<!-- form-group// -->
				<div class="form-group input-group">
					<div class="input-group-prepend">
						<span class="input-group-text"> <i class="fas fa-envelope"></i>
						</span>
					</div>
					<form:input path="email" name="email" id="email" class="input"
						placeholder="Email address" type="email" required="true" />

				</div>



				<!-- form-group// -->
				<div class="form-group input-group">
					<div class="input-group-prepend">
						<span class="input-group-text"> <i class="fas fa-id-card"></i>
						</span>
					</div>
					<form:input path="taxCode" name="taxCode" id="taxCode"
						class="input" placeholder="Tax code" type="text"
						pattern="^[A-Za-z]{6}\d{2}[A-Za-z]\d{2}[A-Za-z]\d{3}[A-Za-z]$"
						required="true" />


				</div>

				<!-- form-group// -->
				<div v-if="type == 'Policeman' " class="form-group input-group">
					<div class="input-group-prepend">
						<span class="input-group-text"> <i class="fas fa-id-card"></i>
						</span>
					</div>
					<form:input path="idNumber" name="idNumber" id="idNumber"
						class="input" placeholder="ID number" type="text"
						pattern="\w{6,20}" required="true" />


				</div>

				<!-- form-group// -->
				<div v-if="type == 'Municipality'" class="form-group input-group">
					<div class="input-group-prepend">
						<span class="input-group-text"> <i class="fas fa-id-card "></i>
						</span>
					</div>
					<form:input path="authNumber" name="authNumber" id="authNumber"
						class="input" placeholder="Authorization number" type="text"
						pattern="\w{6,20}" required="true" />

				</div>

				<!-- form-group// -->
				<div class="form-group input-group">
					<div class="input-group-prepend">
						<span class="input-group-text"> <i class="fas fa-phone"></i>
						</span>
					</div>
					<form:input path="phoneNumber" name="phone" id="phone"
						class="input" placeholder="Phone number" type="tel"
						pattern="\d{6,20}" required="true" />

				</div>

				<!-- form-group// -->
				<div class="form-group input-group">
				<div class="input-group-prepend">
						<span class="input-group-text"> <i class="fas fa-money-check-alt"></i>
						</span>
					</div>
					<select class="input" required>
			
						<c:forEach var="payment" items="${paymentsList}"
				varStatus="tagStatus">
					

						<c:choose>
							<c:when test="${user.getPaymentTypeId() == payment.getId() } ">
								<option value="${payment.getId()}" selected>${payment.getType()}</option>

							</c:when>
							<c:otherwise>
								<option value="${payment.getId()}">${payment.getType()}</option>

							</c:otherwise>
						</c:choose>


</c:forEach>
					</select>
				</div>

				<div class="hr"></div>
				<!-- form-group// -->
				<div class="form-group">
					<table align="center">
						<tr>
							<td style="font-style: italic; color: green;">${message}</td>

						</tr>
					</table>

					<form:button id="register" name="register" type="submit"
						class="btn btn-primary btn-block">Update information</form:button>

				</div>

			</form:form>
		</article>
	</div>
	<!-- card.// -->




</body>

<script>
	var app = new Vue({
		el : '#app',
		data : {
			type : ''
		}
	})
</script>

</html>
