<%@page import="Unicam.SPM2020_FMS.model.ParkingSpace"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<link
	href="${pageContext.request.contextPath}/resources/css/parksManagementStyle.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/resources/css/profileStyle.css"
	rel="stylesheet">
<title>Parks Management</title>
</head>
<body>

	<jsp:include page="navBar.jsp"></jsp:include>
	<br>
	<h3 align="center">Manage your parking spaces</h3>
	<br>
	<table align="center"
		class="table table-striped table-hover table-dark">
		<thead>
			<tr>
				<th scope="col">#</th>
				<th scope="col">City</th>
				<th scope="col">Name</th>
				<th scope="col">Address</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="parkSpace" items="${parkSpaceList}"
				varStatus="tagStatus">
				<tr>
					<th scope="row">${parkSpaceList.indexOf(parkSpace)+1}</th>
					<td>${parkSpace.city}</td>
					<td>${parkSpace.name}</td>
					<td>${parkSpace.address}</td>
					<td>
						<!-- Button trigger modal for editing -->
						<button type="button" class="btn btn-warning" data-toggle="modal"
							data-target="#parkSpace${parkSpaceList.indexOf(parkSpace)+1}">
							Edit</button> <!-- Button trigger modal for delete -->
						<button type="button" class="btn btn-danger" data-toggle="modal"
							data-target="#deleteModal${parkSpaceList.indexOf(parkSpace)+1}">Delete</button> <!-- Modal for delete -->
						<div class="modal fade" id="deleteModal${parkSpaceList.indexOf(parkSpace)+1}" tabindex="-1"
							role="dialog" aria-labelledby="deleteModalLabel${parkSpaceList.indexOf(parkSpace)+1}"
							aria-hidden="true">
							<div class="modal-dialog modal-dialog-centered" role="document">
								<div class="modal-content">
									
									<div class="modal-body" style="color:black;">
										<h4>Are you
											sure to delete this parking space?</h4>( This action will be permanent! )
									</div>
									<div class="modal-footer">
										<button type="button" class="btn btn-secondary"
											data-dismiss="modal">Cancel</button>
										<form:form modelAttribute="parkSpaceToDelete"
											action="DeleteParkSpace" method="post">
											<form:input path="idParkingSpace" name="idParkingSpace"
													value="${parkSpace.idParkingSpace}" class="input"
													type="hidden" />
											<form:button id="delete${parkSpaceList.indexOf(parkSpace)+1}"
												type="submit" class="btn btn-danger">Delete</form:button>
										</form:form>
									</div>
								</div>
							</div>
						</div> <!-- Modal for editing -->
						<div class="modal fade"
							id="parkSpace${parkSpaceList.indexOf(parkSpace)+1}" tabindex="-1"
							role="dialog" aria-labelledby="exampleModalCenterTitle"
							aria-hidden="true">
							<div class="modal-dialog modal-dialog-centered" role="document">
								<div class="modal-content">
									<div class="modal-header">
										<h5 class="modal-title" id="exampleModalLongTitle">Park
											space manager</h5>
										<button type="button" class="close" data-dismiss="modal"
											aria-label="Close">
											<span aria-hidden="true">&times;</span>
										</button>
									</div>
									<div class="modal-body">
										<form:form modelAttribute="parkSpaceToEdit"
											action="ParksManagement" method="post"
											enctype="multipart/form-data">
											<!--  form-group -->
											<div class="form-group input-group">
												<div class="input-group-prepend">
													<span class="input-group-text"> <i
														class="fas fa-map "></i>
													</span>
												</div>

												<form:input path="idParkingSpace" name="idParkingSpace"
													value="${parkSpace.idParkingSpace}" class="input"
													type="hidden" />
												<form:input path="city" name="city"
													value="${parkSpace.city}" class="input" placeholder="City"
													type="text" required="true" />
											</div>
											<!--  form-group -->
											<div class="form-group input-group">
												<div class="input-group-prepend">
													<span class="input-group-text"> <i
														class="fas fa-signature"></i>
													</span>
												</div>

												<form:input path="name" name="name"
													value="${parkSpace.name}" class="input" placeholder="Name"
													type="text" required="true" />

											</div>
											<!-- form-group// -->

											<div class="form-group input-group">
												<div class="input-group-prepend">
													<span class="input-group-text"> <i
														class="fas fa-map "></i>
													</span>
												</div>


												<form:input path="address" name="address"
													value="${parkSpace.address}" class="input"
													placeholder="Address" type="text" required="true" />
											</div>
											<!-- form-group// -->
											<div class="form-group input-group">
												<div class="input-group-prepend">
													<span class="input-group-text"> <i
														class="fas fa-map-marker-alt"></i>
													</span>
												</div>
												<form:input path="coordinates" name="coordinates"
													value="${parkSpace.coordinates}" class="input"
													placeholder="Coordinates" type="text" required="true" />

											</div>



											<!-- form-group// -->
											<div class="form-group input-group">
												<div class="input-group-prepend">
													<span class="input-group-text"> <i
														class="fas fa-list-ol"></i>
													</span>
												</div>
												<form:input path="spotsCapacity" name="spotsCapacity"
													value="${parkSpace.spotsCapacity}" class="input"
													placeholder="Capacity" type="number" required="true" />


											</div>

											<!-- form-group// -->
											<div class="form-group input-group">
												<div class="input-group-prepend">
													<span class="input-group-text"> <i
														class="fas fa-warehouse"></i>
													</span>
												</div>
												<form:input path="coveredSpots" name="coveredSpots"
													value="${parkSpace.coveredSpots}" class="input"
													placeholder="Number of covered spots" type="number"
													required="true" />


											</div>

											<!-- form-group// -->
											<div class="form-group input-group">
												<div class="input-group-prepend">
													<span class="input-group-text"> <i
														class="fas fa-wheelchair "></i>
													</span>
												</div>
												<form:input path="handicapSpots" name="handicapSpots"
													value="${parkSpace.handicapSpots}" class="input"
													placeholder="Number of handicap spots" type="number"
													required="true" />

											</div>

											<!-- form-group// -->
											<div class="form-group input-group">
												<div class="input-group-prepend">
													<span class="input-group-text"> <i
														class="fas fa-warehouse"></i>
													</span>
												</div>
												<form:input path="specCovered" name="specCovered"
													pattern="^(\d+(-\d+)?)(,\d+(-\d+)?)*$"
													value="${parkSpace.specCovered}" class="input"
													placeholder="Specify spots separated by , (use - for ranges)"
													type="text" />


											</div>
											<!-- form-group// -->
											<div class="form-group input-group">
												<div class="input-group-prepend">
													<span class="input-group-text"> <i
														class="fas fa-wheelchair "></i>
													</span>
												</div>
												<form:input path="specHandicap" name="specHandicap"
													pattern="^(\d+(-\d+)?)(,\d+(-\d+)?)*$"
													value="${parkSpace.specHandicap}" class="input"
													placeholder="Specify spots separated by , (use - for ranges)"
													type="text" />


											</div>

											<!-- form-group// -->
											<div class="form-group input-group">
												<div class="input-group-prepend">
													<span class="input-group-text"> <i
														class="fas fa-file"></i>
													</span>
												</div>
												<form:input path="imageFile" name="imageFile" class="input"
													placeholder="Upload map of the park" type="file" />


											</div>

											<!-- form-group// -->
											<div class="form-group input-group">


												<div class="funkyradio">
													<div class="funkyradio-default">

														<c:choose>
															<c:when test="${parkSpace.guarded}">
																<form:checkbox path="guarded"
																	id="checkbox${parkSpaceList.indexOf(parkSpace)+1}"
																	name="guarded" checked="checked" />
															</c:when>
															<c:otherwise>
																<form:checkbox path="guarded"
																	id="checkbox${parkSpaceList.indexOf(parkSpace)+1}"
																	name="guarded" />
															</c:otherwise>
														</c:choose>

														<label for="checkbox${parkSpaceList.indexOf(parkSpace)+1}">Is
															guarded? (Check the box)</label>
													</div>

												</div>

											</div>
											<!-- form-group// -->


											<div class="hr"></div>

											<!-- form-group// -->
											<div class="form-group">

												<form:button id="edit${parkSpaceList.indexOf(parkSpace)+1}"
													name="edit${parkSpaceList.indexOf(parkSpace)+1}"
													type="submit" class="button">Update</form:button>

											</div>

										</form:form>
									</div>

								</div>
							</div>
						</div>
					</td>
				</tr>

			</c:forEach>
		</tbody>
	</table>

	<!-- Modal for message -->
	<div class="modal fade" id="messageModal" tabindex="-1" role="dialog"
		aria-labelledby="messageModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content">
	
				<div class="modal-body">
				
					<div id="messageAlert" class="alert " role="alert">
						<h3>${message}</h3>
					</div>

				</div>
			
			</div>
		</div>
	</div>



</body>


<script type="text/javascript">
	var message = "${message}";
	if (message.startsWith("Park")) {
		$("#messageAlert").addClass("alert-success");
		$("#messageModal").modal('show');

	} else if (message != "" && !message.startsWith("Park")) {

		$("#messageAlert").addClass("alert-danger");
		$("#messageModal").modal('show');
	}
	
	
</script>



</html>