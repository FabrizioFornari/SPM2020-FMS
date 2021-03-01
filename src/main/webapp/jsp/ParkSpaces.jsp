<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link rel="stylesheet" type="text/css"
	href="https://js.api.here.com/v3/3.1/mapsjs-ui.css" />
<link
	href="${pageContext.request.contextPath}/resources/css/parkSpacesStyle.css"
	rel="stylesheet">
<title>Park spaces</title>

</head>
<body id="markers-on-the-map">



	<jsp:include page="navBar.jsp"></jsp:include>

	<nav>
		<div class="nav nav-tabs nav-fill" id="nav-tab" role="tablist">
			<a class="nav-item nav-link active" id="nav-map-tab"
				data-toggle="tab" href="#nav-map" role="tab" aria-controls="nav-map"
				aria-selected="true"><span class="fas fa-map fa-2x"></span></a> <a
				class="nav-item nav-link" id="nav-list-tab" data-toggle="tab"
				href="#nav-list" role="tab" aria-controls="nav-list"
				aria-selected="false"><span class="fas fa-list-ul fa-2x"></span></a>

		</div>
	</nav>

	<div class="tab-content py-3 px-3 px-sm-0" id="nav-tabContent">

		<div class="tab-pane fade show active" id="nav-map" role="tabpanel"
			aria-labelledby="nav-map-tab">

			<div class="input-group">
				<div class="input-group-prepend">
					<span class="input-group-text"> <i class="fas fa-search "
						onclick="filterMap()" id="searchButton"></i>
					</span>
				</div>


				<input type="text" class="input" id="mapFilter"
					placeholder="Search by location (specify city for better usage)" />
			</div>

			<div id="map" align="center" ontouchstart="blurInput()"></div>

		</div>

		<div class="tab-pane fade" id="nav-list" role="tabpanel"
			aria-labelledby="nav-list-tab">

			<div class="container">
				<div class="row">
					<div class="col-lg-10 mx-auto mb-4">
						<div class="section-title text-center ">
							<h3 class="top-c-sep">Find your parking space</h3>
							<p>You can choose the parking space that is most suitable for
								you. Press "Reserve" if you want to schedule your parking time, "Park now" otherwise.</p>
						</div>
					</div>
				</div>

				<div class="row">
					<div class="col-lg-10 mx-auto">
						<div class="career-search mb-60">

							<div class="form-group input-group">
								<div class="input-group-prepend">
									<span class="input-group-text"> <i
										class="fas fa-search "></i>
									</span>
								</div>


								<input class="input" id="filterInput"
									placeholder="Filter by Address" type="text"
									onkeyup="filterItems()" />
							</div>

							<div class="filter-result" id="parkingSpacesList">
								<c:forEach var="parkSpace" items="${parkSpaceList}"
									varStatus="tagStatus">
									<div
										class="job-box d-md-flex align-items-center justify-content-between mb-30">
										<div
											class="job-left my-4 d-md-flex align-items-center flex-wrap">
											<div
												class="img-holder mr-md-4 mb-md-0 mb-4 mx-auto mx-md-0 d-md-none d-lg-flex">
												P${parkSpaceList.indexOf(parkSpace)+1}</div>
											<div class="job-content">
												<h5 class="text-center text-md-left">
													<strong>${parkSpace.getCity()} </strong> |
													${parkSpace.getName()} <br /> ${parkSpace.getAddress()}
												</h5>
												<ul class="d-md-flex flex-wrap text-capitalize ff-open-sans"
													style="padding: 0;">
													<li class="mr-md-4"><i class="zmdi zmdi-pin mr-2"></i>
														Overall:
														${parkSpace.getFreeAll()}/${parkSpace.getSpotsCapacity()}</li>
													<li class="mr-md-4"><i class="zmdi zmdi-pin mr-2"></i>
														Covered:
														${parkSpace.getFreeCovered()}/${parkSpace.getCoveredSpots()}</li>
													<li class="mr-md-4"><i class="zmdi zmdi-time mr-2"></i>
														Handicap:
														${parkSpace.getFreeHandicap()}/${parkSpace.getHandicapSpots()}</li>
													<c:if test="${parkSpace.isGuarded() == true}">
														<li class="mr-md-4" style="color: #d1ec15;"><i class="zmdi zmdi-time mr-2"></i>
															<strong>Is guarded</strong></li>
													</c:if>
														<li class="mr-md-4" style="color: #d1ec15;"><i class="zmdi zmdi-time mr-2" ></i>
													<strong>	Fee:
														${parkSpace.getParkingFee()} €/hour</strong></li>
												</ul>
											</div>
										</div>

										<div class="job-right my-4 flex-shrink-0">
											<button type="button"
												class="btn d-block w-100 d-sm-inline-block btn-light"
												data-toggle="modal" id="parkNowButton"
												data-image="${parkSpace.getImageName()}"
												data-city="${parkSpace.getCity()}"
												data-name="${parkSpace.getName()}"
												data-address="${parkSpace.getAddress()}"
												data-idparkingspace="${parkSpace.getIdParkingSpace()}"
												data-target="#reservationModal">Park now</button>
											&emsp;
											<button type="button"
												class="btn d-block w-100 d-sm-inline-block btn-light"
												data-toggle="modal" id="scheduleButton"
												data-image="${parkSpace.getImageName()}"
												data-city="${parkSpace.getCity()}"
												data-name="${parkSpace.getName()}"
												data-address="${parkSpace.getAddress()}"
												data-idparkingspace="${parkSpace.getIdParkingSpace()}"
												data-target="#reservationModal">Reserve</button>
										</div>
									</div>
								</c:forEach>
							</div>
						</div>

					</div>
				</div>

			</div>



		</div>

	</div>



	<!-- Modal -->
	<div class="modal fade" id="reservationModal" tabindex="-1"
		role="dialog" aria-labelledby="reservationModalLabel"
		aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="reservationModalLabel">What kind
						of reservation do you want to perform?</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<form:form modelAttribute="reservation" id="reservationForm"
						method="post">

						<div class="form-group">
							<form:input path="parkingSpaceId" class="input" type="hidden"
								readonly="true" />
						</div>
						<div class="form-group" id="spotAssignment"></div>

						<div class="form-group" id="dateSelection">

							From:
					
							<input type="date" id="parkingStartDate" class="input"
								autocomplete /><br> <select id="timeList" class="input">

							</select> To:
					
							<input type="date" id="parkingEndDate" class="input" autocomplete />
							<br> <select id="timeList2" class="input">

							</select>
						</div>
						<!-- form-group// -->
						<div class="form-group" id="licenseSelect">
							<form:select class="input" path="licensePlateNumber"
								required="true">
								<option value="" disabled selected>Choose your license
									plate</option>
								<c:forEach var="userCar" items="${userCars.myCars}"
									varStatus="tagStatus">
									<option>${userCar.getLicensePlateNumber()}</option>

								</c:forEach>


							</form:select>
						</div>
						<!-- form-group// -->
							<div class="form-group input-group">
							<form:select class="input" required="true" path="paymentType">
					
								<c:forEach var="payment" items="${paymentsList}"
									varStatus="tagStatus">
								<c:choose>
								<c:when test="${user.getPaymentTypeId() == 0 && user.getPaymentTypeId() == payment.getId()}">
							<option value="${payment.getId()}" disabled selected>${payment.getType()}</option>
								
								</c:when>
							<c:when test="${user.getPaymentTypeId() == payment.getId()}">
								<option value="${payment.getId()}" selected>${payment.getType()}</option>

							</c:when>
							<c:otherwise>
								<option value="${payment.getId()}">${payment.getType()}</option>

							</c:otherwise>
						</c:choose>
								</c:forEach>
							</form:select>
						</div>
						
						<!-- form-group// -->
						<div class="form-group input-group">


							<div class="funkyradio">
								<div class="funkyradio-default">


									<form:checkbox path="askedCovered" id="coveredSpot"
										name="askedCovered"  />

									<label for="coveredSpot">Covered</label>
								</div>

							</div> &ensp;
							<div class="funkyradio">
								<div class="funkyradio-default">


									<form:checkbox path="askedHandicap" id="handicapSpot"
										name="askedHandicap"  />

									<label for="handicapSpot">Handicap</label>
								</div>

							</div>

						</div>

						  
					</form:form>

				</div>
				<div class="modal-footer">
					<ul id="messagesList">

					</ul>

					<button type="button" id="reserveNowButton" align="center"
						class="btn confirm btn-secondary">Confirm</button>
					<button type="button" id="reserveButton" align="center"
						class="btn confirm btn-secondary">Confirm</button>
					<button type="button" align="center" class="btn btn-secondary"
						data-dismiss="modal">Cancel</button>

				</div>
			</div>
		</div>
	</div>




</body>

<script type="text/javascript"
	src="https://js.api.here.com/v3/3.1/mapsjs-core.js"></script>
<script type="text/javascript"
	src="https://js.api.here.com/v3/3.1/mapsjs-service.js"></script>
<script type="text/javascript"
	src="https://js.api.here.com/v3/3.1/mapsjs-ui.js"></script>
<script type="text/javascript"
	src="https://js.api.here.com/v3/3.1/mapsjs-mapevents.js"></script>
<script type="text/javascript">
	var data = {
	        <c:forEach var="parkSpace" items="${parkSpaceList}"
				varStatus="tagStatus">
	            ${parkSpace.getIdParkingSpace()}: ['${parkSpace.getCoordinates()}',"${parkSpace.getCity()}","${parkSpace.getName()}", "${parkSpace.getAddress()}",'${parkSpace.getFreeAll()}','${parkSpace.getSpotsCapacity()}','${parkSpace.getFreeCovered()}','${parkSpace.getCoveredSpots()}','${parkSpace.getFreeHandicap()}','${parkSpace.getHandicapSpots()}','${parkSpace.getImageName()}','${parkSpace.getParkingFee()}','${parkSpace.isGuarded()}']${!tagStatus.last ? ',' : ''}
	        </c:forEach>
		};
	var locParkNow = window.location, urlParkNow, wsParkNow;
	var path = "${pageContext.request.contextPath}";	
	var imagePath = "${uploadDir}";
	var imageToShow = "";
	var iconPath ="${pageContext.request.contextPath}/resources/images/parking-icon-red.png";
	</script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/reservation.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/mapScript.js">

</script>
</html>