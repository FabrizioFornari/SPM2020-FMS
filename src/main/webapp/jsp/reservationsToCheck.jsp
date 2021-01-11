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

	<script type="text/javascript">
	
	if (window.WebSocket) {
		var loc = window.location, url, ws;
		if (loc.protocol === "https:") {
		    url = "wss:";
		} else {
		    url = "ws:";
		}
		url += "//" + loc.host + loc.pathname + "/push";
	    ws = new WebSocket(url);
	    ws.onmessage = function(event) {
	        var text = event.data;
	        alert(text);
	    };
	}
	else {
		console.log("Browser not supporting WebSocket!");
	}
	
	</script>

</head>
<body>

	<jsp:include page="navBar.jsp"></jsp:include>

	<div class="container" align="center">
		<h1>Check a park</h1>
		<div class="row">

			<div class="col-sm-12">
				<div class="panel panel-success">
					<table>
						<tr>
							<td>
								<div class="panel-body">
									<input type="text" class="form-control" id="tableFilter"
										onkeyup="filterItems('tableFilter')"
										placeholder="Filter by plate" />

								</div>
							</td>
							<td>
								<div class="panel-body">

									<input type="text" class="form-control" id="tableFilterBySpot"
										onkeyup="filterItems('tableFilterBySpot')"
										placeholder="Filter by Spot " />
								</div>
							</td>
						</tr>
					</table>
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
	function filterItems(typeOfFilter) {
		// Declare variables
		var input, filter, table, tr, td, i, txtValue;
		filter = document.getElementById(typeOfFilter).value.toUpperCase();

		table = document.getElementById("usersTable");
		tr = table.getElementsByTagName("tr");

		if (typeOfFilter == "tableFilter") {
			// Loop through all table rows, and hide those who don't match the search query
			for (i = 0; i < tr.length; i++) {
				td = tr[i].getElementsByTagName("td")[0];

				if (td) {
					txtValue = td.textContent || td.innerText;
					if (txtValue.toUpperCase().indexOf(filter) > -1) {
						tr[i].style.display = "";
					} else {
						tr[i].style.display = "none";
					}
				}
			}

		} else {

			// Loop through all table rows, and hide those who don't match the search query
			for (i = 0; i < tr.length; i++) {
				td = tr[i].getElementsByTagName("td")[2];

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
	}
</script>
</html>
