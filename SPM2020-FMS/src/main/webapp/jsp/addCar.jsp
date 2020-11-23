<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri = "http://java.sun.com/jsp/jstl/core"%>

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

		<table class="table">
			<thead class="thead-dark">
				<tr>
					<th scope="col">#</th>
					<th scope="col">Number</th>
					<th scope="col">Model</th>
					<th scope="col">Action</th>
				</tr>
			</thead>
			<tbody>
			 <c:forEach var="item" items="${cars}">
               	<tr>
					<th scope="row">${cars.indexOf(item)+1}</th>
					<td> ${item.licensePlateNumber}</td>
					<td>${item.model}</td>
					<td>Delete or modify</td>
				</tr>
            </c:forEach>
            

	
			</tbody>
		</table>

	
		<h2 class="text-center">
			<strong>Add a new car</strong>
		</h2>
		<div class="row justify-content-center">
			<div class="col-12 col-md-8 col-lg-6 pb-5">


				<!--Form with header-->

				<form:form id="carForm" modelAttribute="car" action="addCar"
					method="post">
					<div class="card border-primary rounded-0">
						<div class="card-header p-0">
							<div class="bg-info text-white text-center py-2">
								<h3>
									<i class="fas fa-parking"></i> Please insert your license plate
									number!
								</h3>

							</div>
						</div>
						<div class="card-body p-3">

							<!--Body-->
							<div class="form-group">
								<div class="input-group mb-2">
									<div class="input-group-prepend">
										<div class="input-group-text">
											<i class="fas fa-minus-square"></i>
										</div>
									</div>
									<form:input path="licensePlateNumber" name="licensePlate"
										id="licensePlate" class="form-control"
										placeholder="License plate number" type="text" />
								</div>
							</div>

							<!-- End of form-group -->
							<div class="form-group">
								<div class="input-group mb-2">
									<div class="input-group-prepend">
										<div class="input-group-text">
											<i class="fas fa-car"></i>
										</div>
									</div>
									<form:input path="model" name="model" id="model"
										class="form-control" placeholder="Car model" type="text" />
								</div>
							</div>



							<div class="text-center">
								<form:button id="addCarButton" name="addCarButton" type="submit"
									class="btn btn-primary btn-block">Add car</form:button>
							</div>
						</div>

					</div>
				</form:form>
				<!--Form with header-->



			</div>
		</div>
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