<%@page import="Unicam.SPM2020_FMS.model.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<style type="text/css">
.startTime {
	color: #f4fdf4;
}

.endTime {
	color: #fffb00;
}

table tr {
	border-bottom: solid;
}

table tr.reservationRow {
	cursor: pointer;
}

.imgCenter {
	display: block;
	margin-left: auto;
	margin-right: auto;
	width: 100%;
}
</style>
<title>Welcome</title>

</head>
<body>

	<jsp:include page="navBar.jsp"></jsp:include>

	<main>
	<div class="container">
		<div class="row justify-content-center">
			<div class="col=12 mt-3">
				<div class="jumbotron" style="background-color: #826137ab;">
					<h1 class="display-4">Hello ${user.name} !</h1>

					<hr class="my-4">

					<p class="lead">

						<c:choose>

							<c:when test="${user.userType  == 'Driver'}">

								<a href="ParkSpaces">
									<button class="btn btn-outline-warning btn-lg"
										style="width: 100%;">Find a place</button>
								</a>

								<button type="button" class="btn btn-outline-warning btn-lg"
									style="width: 100%;" data-toggle="modal"
									data-target="#reservationsModal">My Reservations</button>

								<a href="myCars">
									<button class="btn btn-outline-warning btn-lg"
										style="width: 100%;">My Cars</button>
								</a>



								<!-- Modal -->
								<div class="modal fade" id="reservationsModal" tabindex="-1"
									role="dialog" aria-labelledby="reservationsModalLabel"
									aria-hidden="true">
									<div class="modal-dialog modal-dialog-centered" role="document">
										<div class="modal-content"
											style="background-color: #b2851cf0;">
											<div class="modal-header">
												<h5 class="modal-title" id="reservationsModalLabel" align="center">Reserved spots</h5>
												<button type="button" class="close" data-dismiss="modal"
													aria-label="Close">
													<span aria-hidden="true">&times;</span>
												</button>
											</div>
											<div class="modal-body">
												<c:if test="${userReservations.size() != 0}">
													<table id="#reservationsList" style="text-align: center;">
														<thead>
															<tr>

																<th scope="col">Plate</th>
																<th scope="col" style="padding-left: 40px;">Spot</th>
																<th scope="col">Space</th>
																<th scope="col">Time</th>

															</tr>
														</thead>

														<c:forEach var="reservation" items="${userReservations}"
															varStatus="tagStatus">
															<tr data-toggle="collapse" class="reservationRow"
																data-target="#${userReservations.indexOf(reservation)+1}image${reservation.parkingSpaceId }">

																<td>${reservation.licensePlateNumber}</td>
																<td style="padding-left: 40px;"><h4
																		style="color: #fffb00;">${reservation.parkingSpot}</h4></td>
																<td>${reservation.parkingSpace}</td>
																<td><p class="startTime">${reservation.parkingStart}
																	</p>-->
																	<p class="endTime">${reservation.parkingEnd}</p></td>

															</tr>
															<tr id="${userReservations.indexOf(reservation)+1}image${reservation.parkingSpaceId }"
																class="collapse">
																<td colspan=5><img class="imgCenter"
																	id="${userReservations.indexOf(reservation)+1}parkMap${reservation.parkingSpaceId}" src=""></img></td>
															</tr>
														</c:forEach>

													</table>
												</c:if>
												<c:if test="${userReservations.size() == 0}">No reservations at the moment</c:if>
											</div>
											<div class="modal-footer">
												<button type="button" class="btn btn-secondary"
													data-dismiss="modal">Close</button>

											</div>
										</div>
									</div>
								</div>



							</c:when>
							<c:when test="${user.userType == 'Policeman'}">
								<a href="reservationsToCheck">
									<button class="btn btn-outline-warning btn-lg">Check
										Reservations</button>
								</a>
								<a href="#">
									<button class="btn btn-outline-warning btn-lg">Profile</button>
								</a>



							</c:when>
							<c:when test="${user.userType == 'Municipality'}">

								<a href="newParkArea">
									<button class="btn btn-outline-warning btn-lg">Add
										Parking spaces</button>
								</a>
								<a href="profile">
									<button class="btn btn-outline-warning btn-lg">Profile</button>
								</a>

							</c:when>
						</c:choose>


					</p>
					<p class="lead"></p>

				</div>
			</div>
		</div>
	</div>

	</main>

</body>
<script type="text/javascript">

var reservations = '${userReservations.size()}';

if(reservations == 0){
	
	$('#reservationsModalLabel').hide();
}


	$('tr').on('click', function(event) {

		var target = $(this).data("target");
		var index = target.substr(1,target.indexOf("i")-1);
		var idParkingSpace = target.substr(target.lastIndexOf("e")+1);
	
		var elementById = $("#"+index+"parkMap" + idParkingSpace).attr('src');

		if (elementById != "")
			return;

		$.ajax({
			url : 'getMapSrcFromId',
			type : 'GET',
			data : ({
				Id : idParkingSpace
			}),
			success : function(data) {
				document.getElementById(index+"parkMap" + idParkingSpace).src = data;
			}
		});

	})
</script>


</html>