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
		<table class="table table-striped table-dark">
			<c:if test="${cars.size() > 0 }">
				<thead class="thead-dark">
					<tr>
						<th scope="col">#</th>
						<th scope="col">Number</th>
						<th scope="col">Model</th>
						<th scope="col"  >Action</th>
					</tr>
				</thead>
			</c:if>
			<tbody>
				<c:forEach var="item" items="${cars}">
					<tr>
						<th scope="row">${cars.indexOf(item)+1}</th>
						<td>${item.licensePlateNumber}</td>
						<td>${item.model}</td>
						<td><button type="button" class="btn btn-danger">
								<span class="fas fa-trash-alt"></span>
							</button>
							</td>	
					</tr>
				</c:forEach>

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