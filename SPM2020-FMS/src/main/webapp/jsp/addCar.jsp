<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">


<title>Registration</title>
</head>
<body>
	<jsp:include page="navBar.jsp"></jsp:include>
	<div class="container" id="app">

<h2 align="center">License Plates</h2>
<h5 align="center">Here you can add a new License plate (add new car) or see the ones already added.</h5>
	<form:form method="post" action="modifyCars" modelAttribute="userCars">
		<table class="table table-striped table-dark">
				<thead class="thead-dark">
					<tr>
						<th scope="col">Number</th>
						<th scope="col">Model</th>
						<th scope="col">Action</th>
					</tr>
				</thead>
			<tbody>
				<c:forEach var="item" items="${userCars.myCars}" varStatus="tagStatus">
					<tr>
						<td>
							<form:input path="myCars[${tagStatus.index}].licensePlateNumber" value="${item.licensePlateNumber}"/>
						</td>
						<td>
							<form:input path="myCars[${tagStatus.index}].model" value="${item.model}"/>
						</td>
						<td>
							<button type="button" class="btn btn-danger"><span class="fas fa-trash-alt"></span></button>
						</td>	
					</tr>
				</c:forEach>
		<input type="submit" value="Save" />
	</form:form>

				<tr>
					<td colspan="5"><form:form id="carForm" modelAttribute="car"
							action="addCar" method="post">
							<div class="input-group">
								<form:input path="licensePlateNumber" name="licensePlate"
									id="licensePlate" class="form-control"
									placeholder="License plate number" type="text" required ="required" />
								<form:input path="model" name="model" id="model"
									class="form-control" placeholder="Car model" type="text" />
								<div class="input-group-append">
									<form:button id="addCarButton" name="addCarButton"
										type="submit" class="btn btn-outline-success btn-block">Add car</form:button>

								</div>
							</div>
						</form:form></td>

				</tr>

			</tbody>
		</table>





	</div>

	<table align="center">
		<tr>
			<td style="font-style: italic; color: red;">${message}</td>
		</tr>
	</table>



</body>


<script>
	
</script>
</html>