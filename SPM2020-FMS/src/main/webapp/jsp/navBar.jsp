<%@page import="Unicam.SPM2020_FMS.model.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<% User user = (User)session.getAttribute("user"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
	integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
	crossorigin="anonymous"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx"
	crossorigin="anonymous"></script>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css"
	integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.15.1/css/all.css">
<script
	src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>


<link
	href="https://cdnjs.cloudflare.com/ajax/libs/javascript.util/0.12.12/javascript.util.min.js">

<script src="https://cdn.jsdelivr.net/npm/vue@2/dist/vue.js"></script>
<style type="text/css">
body {
	background: linear-gradient(rgba(176,176,176,0.5), rgba(255,255,255,.5)), url(${pageContext.request.contextPath}/resources/images/parking.jpg);
}
</style>
</head>
<body>

	<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
		<a class="navbar-brand" href="./"><img
			src="${pageContext.request.contextPath}/resources/images/logo.png"
			alt="" width="40" height="40"></img></a>
		<button class="navbar-toggler" type="button" data-toggle="collapse"
			data-target="#navbarSupportedContent"
			aria-controls="navbarSupportedContent" aria-expanded="false"
			aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>

		<div class="collapse navbar-collapse" id="navbarSupportedContent">
			<ul class="navbar-nav mr-auto">
			
				<li class="nav-item "><a class="nav-link" href="./"><span
						class="fas fa-home"></span> Home </a></li>
					
						<c:if test="${user.getUserType() == 'Driver' }">
						<li class="nav-item"><a class="nav-link" href="myCars"><span
						class="fas fa-id-badge"></span> My Cars</a></li>
						</c:if>
						<c:if test="${user.getUserType() == 'Driver' }">
						<li class="nav-item"><a class="nav-link" href="ParkSpaces"><span
						class="fas fa-parking"></span> Park spaces</a></li>
						</c:if>
						<c:if test="${user.getUserType() == 'Municipality' }">
						<li class="nav-item"><a class="nav-link" href="newParkArea"><span
						class="fas fa-id-badge"></span> Add Park space</a></li>
						</c:if>
						<c:if test="${user.getUserType() == 'Policeman' }">
						<li class="nav-item"><a class="nav-link" href="reservationsToCheck"><span
						class="fas fa-id-badge"></span> Reservations</a></li>
						</c:if>
				
				<li class="nav-item"><a class="nav-link" href="profile"><span
						class="fas fa-user-secret"></span> Profile</a></li>
				<li class="nav-item"><a class="nav-link" href="logout"><span
						class="fas fa-power-off"></span> Logout</a></li>


			</ul>

		</div>
	</nav>
</body>
</html>