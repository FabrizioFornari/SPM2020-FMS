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
		<h1>Check a park</h1>
		<div class="row">

			<div class="col-sm-12">
				<div class="panel panel-success">
					<div class="panel-heading"></div>
					<div class="panel-body">
						<input type="text" class="form-control" id="tableFilter"
							onkeyup="filterItems()" placeholder="Filter by License plate number" />
					</div>
					<br>
					<table class="table " id="usersTable">
						<thead>
							<tr>

				
								<th>Plate</th>
					
								<th>Space</th>
								<th>Spot</th>
								<th>End</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="reservation" items="${reservationsToCheck}">


								<tr>

									
									<td>${reservation.licensePlateNumber}</td>
							
									<td>${reservation.parkingSpace}</td>
									<td>${reservation.parkingSpot}</td>
									<td>${reservation.parkingEnd}</td>

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
			td = tr[i].getElementsByTagName("td")[1];
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