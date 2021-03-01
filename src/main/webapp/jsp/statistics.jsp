<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link
	href="${pageContext.request.contextPath}/resources/css/statisticsStyle.css"
	rel="stylesheet">
<title>Statistics</title>
</head>
<body>
	<jsp:include page="navBar.jsp"></jsp:include>
	<br>
	<div class='title'>
		<h1>ParkMe statistics</h1>
		<h2 style="color: #ececec;">(Here you can check all the statistics
			for your city)</h2>
	</div>




	<div class='ui'>

		<!-- Trigger revenues modal -->
		<div class='ui_box' id="revenuesStatisticsButton" data-toggle="modal"
			data-target="#revenuesModal">
			<div class='ui_box__inner'>
				<h2>Total revenues</h2>
				<fmt:formatNumber var="n" value="${totalRevenue}"
					maxFractionDigits="2" />
				<div class='stat'>
					<i class="fas fa-money-bill-alt fa-5x" style="float: right;"></i> <span>${n} €</span>
				</div>
				<div class='progress'>
					<div class='progress_bar'></div>
				</div>
				<p>Total money earned since the beginning</p>
			</div>
			<div class='drop'>
				<i align="center" class="fas fa-arrow-down "></i> &ensp; Take a
				closer look
			</div>
		</div>



		<div class='ui_box' id="usersStatisticsButton" data-toggle="modal"
			data-target="#usersModal">
			<div class='ui_box__inner'>
				<fmt:formatNumber var="t" value="${totalUsers}"
					maxFractionDigits="0" />

				<h2>Total Drivers</h2>



				<div class='stat'>
					<i class="fas fa-users fa-5x" style="float: right;"></i> <span>${t}</span>
				</div>
				<div class='progress'>
					<div class='progress_bar'></div>
				</div>


				<p>(Check the information regarding the number of the users and
					their payment preferences)</p>

			</div>
			<div class='drop'>
				<i align="center" class="fas fa-arrow-down "></i> &ensp; Take a
				closer look
			</div>
		</div>

	</div>


	<!-- Start modal of revenues -->
	<div class="modal fade" id="revenuesModal" tabindex="-1" role="dialog"
		aria-labelledby="revenuesModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg modal-dialog-centered"
			role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="revenuesModalLabel">Revenues</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<select class="form-control" id="filterRevenues">
						<option value="0">Since the beginning</option>
						<option value="1">Since last month</option>
					</select>&ensp;
					<table id="revenuesBeginning">
						<thead>
							<tr>
								<th scope="col">#</th>
								<th scope="col">Parking space</th>
								<th scope="col">Revenue</th>
								<th scope="col">Percentage</th>
							</tr>
						</thead>
						<c:forEach var="revenue" items="${revenueBySpace}"
							varStatus="tagStatus">

							<tr>
								<th scope="row">${revenueBySpace.indexOf(revenue)+1}</th>
								<td>${revenue.getDescription() }</td>
								<td>${revenue.getQuantity() }€</td>
								<td>${revenue.getPercentage() }%
									<div class="progress">
										<div class="progress-bar progress-bar-striped"
											role="progressbar"
											style="width: ${revenue.getPercentage() }%"
											aria-valuenow="${revenue.getPercentage() }" aria-valuemin="0"
											aria-valuemax="100"></div>
									</div>
								</td>
							</tr>

						</c:forEach>
					</table>

					<table id="revenuesLastMonth" style="display: none;">
						<thead>
							<tr>
								<th scope="col">#</th>
								<th scope="col">Parking space</th>
								<th scope="col">Revenue</th>
								<th scope="col">Percentage</th>
							</tr>
						</thead>

						<c:forEach var="revenue" items="${revenueBySpaceFiltered}"
							varStatus="tagStatus">

							<tr>
								<th scope="row">${revenueBySpaceFiltered.indexOf(revenue)+1}</th>
								<td>${revenue.getDescription() }</td>
								<td>${revenue.getQuantity() }€</td>
								<td>${revenue.getPercentage() }%
									<div class="progress">
										<div class="progress-bar progress-bar-striped"
											role="progressbar"
											style="width: ${revenue.getPercentage() }%"
											aria-valuenow="${revenue.getPercentage() }" aria-valuemin="0"
											aria-valuemax="100"></div>
									</div>
								</td>
							</tr>

						</c:forEach>

					</table>

				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-dismiss="modal">Close</button>

				</div>
			</div>
		</div>
	</div>
	<!-- End modal of revenues -->

	<!-- Start modal of users -->
	<div class="modal fade" id="usersModal" tabindex="-1" role="dialog"
		aria-labelledby="usersModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg modal-dialog-centered"
			role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="usersModalLabel">Users statistics</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<table>
						<thead>
							<tr>
								<th scope="col">#</th>
								<th scope="col">Payment type</th>
								<th scope="col">Total associations</th>
								<th scope="col">Percentage</th>
							</tr>
						</thead>

						<c:forEach var="paymentType" items="${paymentTypeList}"
							varStatus="tagStatus">
							<fmt:formatNumber var="i" value="${paymentType.quantity}"
								maxFractionDigits="0" />
							<tr>
								<th scope="row">${paymentTypeList.indexOf(paymentType)+1}</th>
								<td>${paymentType.getDescription() }</td>
								<td>${i}</td>
								<td>${paymentType.getPercentage() }%
									<div class="progress">
										<div class="progress-bar progress-bar-striped"
											role="progressbar"
											style="width: ${paymentType.getPercentage() }%"
											aria-valuenow="${paymentType.getPercentage() }"
											aria-valuemin="0" aria-valuemax="100"></div>
									</div>
								</td>
							</tr>

						</c:forEach>
					</table>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-dismiss="modal">Close</button>

				</div>
			</div>
		</div>
	</div>

	<!-- End modal of users -->

</body>
<script type="text/javascript">
	$('#filterRevenues').change(function() {

		if ($('#filterRevenues').val() == '0') {

			$('#revenuesBeginning').show();
			$('#revenuesLastMonth').hide();

		} else {

			$('#revenuesBeginning').hide();
			$('#revenuesLastMonth').show();

		}

	});
</script>
</html>