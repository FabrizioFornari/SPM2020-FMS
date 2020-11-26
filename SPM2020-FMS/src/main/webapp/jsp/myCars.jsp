<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">


<title>My Cars</title>
</head>
<body>
	<jsp:include page="navBar.jsp"></jsp:include>
	<div class="container" id="app">

<h2 align="center">My Cars</h2>
<h5 align="center">Here you can see and modify the list of your cars</h5>
		<table class="table table-striped table-dark">
				<thead class="thead-dark">
					<tr>
						<th scope="col">Plate Number</th>
						<th scope="col">Model</th>
						<th scope="col">Action</th>
					</tr>
				</thead>
			<tbody>
				<c:forEach var="item" items="${userCars.myCars}" varStatus="tagStatus">
					<tr>
						<td>
							${item.licensePlateNumber}
						</td>
						<td>
							${item.model}
						</td>
						<td>
							<form:form method="post" action="deleteCar" modelAttribute="carToTrash">
								<form:input type="hidden" path="licensePlateNumber" value="${item.licensePlateNumber}"/>
								<form:button type="submit" id="delete[${tagStatus.index}]" class="btn btn-danger"><span class="fas fa-trash-alt"></span></form:button>
							</form:form>
						</td>	
					</tr>
				</c:forEach>

				<tr>
					<td colspan="5"><form:form id="carForm" modelAttribute="carToAdd"
							action="addCar" method="post">
							<div class="input-group">
								<form:input path="licensePlateNumber" name="licensePlate"
									id="licensePlate" class="form-control"
									placeholder="Plate number" type="text" required ="required" />
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