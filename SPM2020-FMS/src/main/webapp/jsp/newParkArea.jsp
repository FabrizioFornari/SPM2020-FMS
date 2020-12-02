<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link
	href="${pageContext.request.contextPath}/resources/css/profileStyle.css"
	rel="stylesheet">
<title>Park areas</title>
</head>
<body>
	<jsp:include page="navBar.jsp"></jsp:include>
	<div id="app">

		<article class="card-body mx-auto" style="max-width: 400px;">
			<h4 class="card-title mt-3 text-center">Insert a new park space:</h4>

			<form:form id="addForm" modelAttribute="parkSpace"
				action="addParkSpace" method="post">
				<div class="form-group input-group">
					<div class="input-group-prepend">
						<span class="input-group-text"> <i class="fas fa-user"></i>
						</span>
					</div>

					<form:input path="name" name="name" id="name" class="input"
						placeholder="Name" type="text" required="true" />

				</div>
				<!-- form-group// -->

				<div class="form-group input-group">
					<div class="input-group-prepend">
						<span class="input-group-text"> <i class="fas fa-user "></i>
						</span>
					</div>


					<form:input path="address" name="address" id="address"
						class="input" placeholder="Address" type="text" required="true" />
				</div>
				<!-- form-group// -->
				<div class="form-group input-group">
					<div class="input-group-prepend">
						<span class="input-group-text"> <i class="fas fa-envelope"></i>
						</span>
					</div>
					<form:input path="coordinates" name="coordinates" id="coordinates"
						class="input" placeholder="Coordinates" type="text"
						required="true" />

				</div>



				<!-- form-group// -->
				<div class="form-group input-group">
					<div class="input-group-prepend">
						<span class="input-group-text"> <i class="fas fa-id-card"></i>
						</span>
					</div>
					<form:input path="spotsCapacity" name="spotsCapacity"
						id="spotsCapacity" class="input" placeholder="Capacity"
						type="text" required="true" />


				</div>

				<!-- form-group// -->
				<div class="form-group input-group">
					<div class="input-group-prepend">
						<span class="input-group-text"> <i class="fas fa-id-card"></i>
						</span>
					</div>
					<form:input path="coveredSpots" name="coveredSpots"
						id="coveredSpots" class="input"
						placeholder="Number of covered spots" type="text" required="true" />


				</div>

				<!-- form-group// -->
				<div class="form-group input-group">
					<div class="input-group-prepend">
						<span class="input-group-text"> <i class="fas fa-id-card "></i>
						</span>
					</div>
					<form:input path="handicapSpots" name="handicapSpots"
						id="handicapSpots" class="input"
						placeholder="Number of handicap spots" type="text" required="true" />

				</div>

				<!-- form-group// -->
				<div class=" form-check">


					<form:checkbox path="guarded" name="guarded" id="guarded" />
				</div>

				<div class="hr"></div>
				<!-- form-group// -->
				<div class="form-group">
					<table align="center">
						<tr>
							<td style="font-style: italic; color: green;">${message}</td>

						</tr>
					</table>

					<form:button id="addParkingSpace" name="addParkingSpace"
						type="submit" class="btn btn-primary btn-block">Add</form:button>

				</div>

			</form:form>
		</article>
	</div>
	<!-- card.// -->
</body>
</html>