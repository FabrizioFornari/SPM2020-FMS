<%@page import="Unicam.SPM2020_FMS.model.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">


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
									<button class="btn btn-outline-warning btn-lg" style="width: 100%;">Find
										a place</button>
								</a>
								<a href="#">
									<button class="btn btn-outline-warning btn-lg" style="width: 100%;">My
										Reservations</button>
								</a>
								<a href="myCars">
									<button class="btn btn-outline-warning btn-lg"
										style="width: 100%;">My Cars</button>
								</a>
							</c:when>
							<c:when test="${user.userType == 'Policeman'}">
							<a href="reservationsToCheck">
									<button class="btn btn-outline-warning btn-lg">Check Reservations</button>
								</a>
								<a href="#">
									<button class="btn btn-outline-warning btn-lg">Profile</button>
								</a>
							
							
							
							</c:when>
							<c:when test="${user.userType == 'Municipality'}">
							
								<a href="newParkArea">
									<button class="btn btn-outline-warning btn-lg">Add Parking spaces</button>
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
</html>