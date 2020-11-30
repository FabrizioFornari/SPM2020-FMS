<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link
	href="${pageContext.request.contextPath}/resources/css/carsPageStyle.css"
	rel="stylesheet">
<title>Cars</title>
</head>
<body>

	<jsp:include page="navBar.jsp"></jsp:include>

	<div class="container" align="center">
		<h1>Find a driver:</h1>
		<div class="row">

			<div class="col-sm-12">
				<div class="panel panel-success">
					<div class="panel-heading"></div>
					<div class="panel-body">
						<input type="text" class="form-control" id="tableFilter"
							onkeyup="filterItems()" placeholder="Filter Tasks" />
					</div>
					<table class="table table-hover" id="usersTable">
						<thead>
							<tr>
								<th>#</th>
								<th>Name</th>
								<th>Email</th>
								<th>Phone</th>
								<th>Plate</th>
								<th>Model</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="car" items="${cars}">


								<tr>
									<td>${cars.indexOf(car) + 1}</td>
									<td>${car.name}&ensp;${car.surname}</td>

									<td>${car.email}</td>
									<td>${car.phoneNumber}</td>


									<td>${car.licensePlateNumber}</td>
									<td>${car.model}</td>
								</tr>


							</c:forEach>

						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>



</body>
<script>
function filterItems() {
  // Declare variables
  var input, filter, table, tr, td, i, txtValue;
  input = document.getElementById("tableFilter");
  filter = input.value.toUpperCase();
  table = document.getElementById("usersTable");
  tr = table.getElementsByTagName("tr");

  // Loop through all table rows, and hide those who don't match the search query
  for (i = 0; i < tr.length; i++) {
    td = tr[i].getElementsByTagName("td")[4];
    if (td) {
      txtValue = td.textContent || td.innerText;
      if (txtValue.toUpperCase().indexOf(filter) > -1) {
        tr[i].style.display = "";
      } else {
        tr[i].style.display = "none";
      }
    }
  }
}
</script>
</html>