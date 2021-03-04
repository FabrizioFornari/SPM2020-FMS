

$(function() {
	var select = '';

	for (var hour = 0; hour < 24; hour++) {
		if (hour < 10) {
			select += '<option value="0' + hour + ':00" > 0' + hour + ':00</option>';
			select += '<option value="0' + hour + ':30" > 0' + hour + ':30</option>';
		} else {

			select += '<option value="' + hour + ':00" > ' + hour + ':00</option>';
			select += '<option value="' + hour + ':30" > ' + hour + ':30</option>';
		}


	}



	$('#timeList').html(select);
	$('#timeList2').html(select);
});


function startTimer(duration, display) {
	var timer = duration, minutes, seconds;
	setInterval(function() {
		minutes = parseInt(timer / 60, 10)
		seconds = parseInt(timer % 60, 10);

		minutes = minutes < 10 ? "0" + minutes : minutes;
		seconds = seconds < 10 ? "0" + seconds : seconds;

		display.text(minutes + ":" + seconds);

		if (--timer < 0) {
			return;

		}
	}, 1000);
}

$("#parkingStartDate").change(function() {

	$("#parkingEndDate").val($('#parkingStartDate').val());

});

$("#timeList").change(function() {


	$("#timeList2").val($('#timeList').val());

});





$('#reservationModal').on('show.bs.modal', function(event) {


	var button = $(event.relatedTarget); // Button that triggered the modal
	var city = button.data('city');
	var name = button.data('name');
	var address = button.data('address');
	var idSpace = button.data('idparkingspace');
	imageToShow = button.data('image');




	var modal = $(this);


	modal.find('#messagesList').html("");
	modal.find('.modal-body div#spotAssignment').html("");

	modal.find('#licenseSelect,#paymentSelect, .modal-footer button, .funkyradio').show();



	if (button.attr("id") == "scheduleButton") {
		modal.find('.modal-footer button#reserveNowButton').hide();
		modal.find('.modal-footer button#reserveButton').show();
		modal.find("#dateSelection").show();
	} else {
		modal.find('.modal-footer button#reserveButton').hide();
		modal.find('.modal-footer button#reserveNowButton').show();
		modal.find("#dateSelection").hide();
	}

	modal.find('.modal-title').text('Reservation at ' + city + " - " + name + " - " + address);


	modal.find('.modal-body input#parkingSpaceId').val(idSpace);

});


$('#reservationModal').on('hide.bs.modal', function(event) {

	if (wsParkNow != null) {
		wsParkNow.close();

	}

});


$('#reserveNowButton').on('click', function(event) {

	var modal = $('#reservationModal');
	var spaceId = modal.find('.modal-body input#parkingSpaceId').val();
	var modal = $('#reservationModal');
	var plate = modal.find('.modal-body select#licensePlateNumber').val();
	var payment = modal.find('.modal-body select#paymentType').val();
	if (plate == null) {
		modal.find('#messagesList').html('<li class="list-group-item list-group-item-danger">Select your license plate, if you don\'t have one please add it <a href="myCars">here</a>  </li>');

		return;

	} else if (payment == null) {

		modal.find('#messagesList').html('<li class="list-group-item list-group-item-danger">Select your payment method or check your profile <a href="profile">here</a> to associate one </li>');
		return;
	}

	var reservation = $('#reservationForm').serialize();

	var fiveMinutes = 60 * 5;



	$.ajax({
		type: "POST",
		url: "parkNow",
		data: reservation,
		dataType: "text",
		success: function(data) {

			var spot = parseInt(data) || 0;

			if (spot == 0) {

				modal.find('#messagesList').html('<li class="list-group-item list-group-item-danger">' + data + '</li>');
				modal.find('#licenseSelect,#paymentSelect, .funkyradio, .modal-footer button').hide();

				return;
			}


			if (window.WebSocket) {

				if (locParkNow.protocol === "https:") {
					urlParkNow = "wss:";
				} else {
					urlParkNow = "ws:";
				}
				urlParkNow += "//" + locParkNow.host + locParkNow.pathname + "/push/" + data + "/" + spaceId;
				wsParkNow = new WebSocket(urlParkNow);


				wsParkNow.onmessage = function(event) {
					var text = event.data;
					modal.modal('toggle');
					alert(text);
				};
			}
			else {
				alert("Browser not supporting WebSocket!");
			}



			modal.find('#messagesList').html("<li class='list-group-item list-group-item-success'>Reservation accomplished!</li>");
			modal.find('#licenseSelect,#paymentSelect, .funkyradio, .modal-footer button').hide();

			modal.find('.modal-body div#spotAssignment').html('<img align="center" class="imgCenter" id="parkMap"  src=""></img><br><h5>Your parking spot is:</h5><br><h1 align="center">' + data + '</h1><h6 align="center" style="color:red">Please park within <span id="timer">05:00</span> or try again</h6>');
			$.ajax({
				url: 'getMapSrc',
				type: 'GET',
				data: ({ filename: imageToShow }),
				success: function(data2) {
					document.getElementById("parkMap").src = data2;
				}
			});


			var display = modal.find('#timer');
			startTimer(fiveMinutes, display);

		},
		error: function(jqxhr) {
			modal.find('#messagesList').html('<li class="list-group-item list-group-item-danger">' + jqxhr.responseText + '</li>'); // @text = response error, it is will be errors: 324, 500, 404 or anythings else
		}

	});



});



$('#reserveButton').on('click', function(event) {


	var modal = $('#reservationModal');
	var currentDate = new Date();
	var date1 = modal.find('.modal-body input#parkingStartDate').val();
	var inputHour1 = modal.find('.modal-body select#timeList').val();

	var dateFrom = new Date(date1 + " " + inputHour1);


	var date2 = modal.find('.modal-body input#parkingEndDate').val();
	var inputHour2 = modal.find('.modal-body select#timeList2').val();

	var dateTo = new Date(date2 + " " + inputHour2);


	var plate = modal.find('.modal-body select#licensePlateNumber').val();
	var payment = modal.find('.modal-body select#paymentType').val();
	if (plate == null) {
		modal.find('#messagesList').html('<li class="list-group-item list-group-item-danger">Select your license plate, if you don\'t have one please add it <a href="myCars">here</a>  </li>');

		return;

		// Check if the user has inserted the date for the reservation
	} else if (payment == null) {

		modal.find('#messagesList').html('<li class="list-group-item list-group-item-danger">Select your payment method or check your profile <a href="profile">here</a> to associate one </li>');
		return;
		
	} else if (date1 == "" || date2 == "") {

		modal.find('#messagesList').html("<li class='list-group-item list-group-item-danger'>Please fill all the fields!</li>");
		return;


	} else if (dateFrom < currentDate || dateFrom > dateTo) {

		modal.find('#messagesList').html("<li class='list-group-item list-group-item-danger'>Please insert a valid period of reservation!</li>");
		return;

	}


	var reservation = $('#reservationForm').serializeArray();
	reservation.push({ name: "parkingStart", value: date1 + ' ' + inputHour1 });
	reservation.push({ name: "parkingEnd", value: date2 + ' ' + inputHour2 });


	$.ajax({
		type: "POST",
		url: "reserve",
		data: $.param(reservation),
		dataType: "text",
		success: function(data) {

			var spot = parseInt(data) || 0;

			if (spot == 0) {

				modal.find('#messagesList').html('<li class="list-group-item list-group-item-danger">' + data + '</li>');
				modal.find('#licenseSelect,#paymentSelect, #dateSelection, .funkyradio, .modal-footer button').hide();

				return;
			}

			modal.find('#messagesList').html("<li class='list-group-item list-group-item-success'>Reservation accomplished! (Check your list for more details)</li>");
			modal.find('#licenseSelect,#paymentSelect, #dateSelection, .funkyradio, .modal-footer button').hide();
			modal.find('.modal-body div#spotAssignment').html('<h5>Your parking spot is:</h5><br><h1 align="center">' + data + '</h1>');



		},
		error: function(jqxhr) {
			modal.find('#messagesList').html('<li class="list-group-item list-group-item-danger">' + jqxhr.responseText + '</li>'); // @text = response error, it is will be errors: 324, 500, 404 or anythings else
		}

	});





});