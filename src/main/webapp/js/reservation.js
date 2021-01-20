

$(function() {
	var select = '';

	for (var hour = 0; hour < 24; hour++) {
		if (hour < 10) {
			select += '<option> 0' + hour + ':00</option>';
			select += '<option> 0' + hour + ':30</option>';
		} else {

			select += '<option> ' + hour + ':00</option>';
			select += '<option> ' + hour + ':30</option>';
		}


	}



	$('#timeList').html(select);
	$('#timeList2').html(select);
});




$('#reservationModal').on('show.bs.modal', function(event) {


	var button = $(event.relatedTarget); // Button that triggered the modal
	var recipient = button.data('whatever');

	var elements = recipient.split(",")

	var modal = $(this);
	modal.find('#messagesList').html("");



	if (button.attr("id") == "scheduleButton") {

		modal.find("#dateSelection").show();
	} else {

		modal.find("#dateSelection").hide();
	}

	modal.find('.modal-title').text('Reservation at ' + elements[0] + " - " + elements[1] + " - " + elements[2]);


	modal.find('.modal-body input#parkingSpace').val(elements[3]);

});

$('#reserveButton').on('click', function(event) {


	

	var modal = $('#reservationModal');
	var date1 =  modal.find('.modal-body input#parkingStartDate').val();
	var dayFrom = date1.split("-");
	var day1 = parseInt(dayFrom[2]);
	var month1 = parseInt(dayFrom[1]);
	var year = parseInt(dayFrom[0]);
	
	var inputHour1 = modal.find('.modal-body select#timeList').val();
	var hourFrom = inputHour1.split(":");
	var hour1 = parseInt(hourFrom[0]);
	
	var date2 = modal.find('.modal-body input#parkingEndDate').val();
	var dayTo = date2.split("-");
	var day2 = parseInt(dayTo[2]);
	var month2 = parseInt(dayTo[1]);
	var year2 = parseInt(dayTo[0]);
	
	var inputHour2 = modal.find('.modal-body select#timeList2').val();
	var hourTo = inputHour2.split(":");
	var hour2 = parseInt(hourTo[0]);
	
	var plate = modal.find('.modal-body select#licensePlateNumber').val();

	if (plate == null || dayFrom == "" || dayTo == "") {
		modal.find('#messagesList').html("<li class='list-group-item list-group-item-danger'>Please fill all the fields!</li>");

		return;

	} else if ((day1 > day2 && month1 == month2) || (month1 > month2 && year1 == year2) || (day1 == day2 && hour1 > hour2)) {
		modal.find('#messagesList').html("<li class='list-group-item list-group-item-danger'>Please insert a valid period of reservation!</li>");
		return;
	}



	var startDate = date1+' '+inputHour1;
	var dayFrom = modal.find('.modal-body input#parkingStart').val(startDate);
	var endDate = date2+' '+inputHour2;
	var dayTo = modal.find('.modal-body input#parkingEnd').val(endDate);
	
	var reservation = $('#reservationForm').serialize();
	modal.find('#messagesList').html('<img align="center" src="' + path + '/resources/images/loadingCar.gif" id="loadingImg" />')


	$.ajax({
		type: "POST",
		url: "reserve",
		data: reservation,
		dataType: "text",
		success: function(data) {
			modal.find('#messagesList').html("<li class='list-group-item list-group-item-success'>Reservation accomplished!</li>");
			modal.find('.modal-body').html('<h5>Your parking spot is:</h5><br><h1 align="center">' + data + '</h1><h6 align="center" style="color:red">(You have five minutes to park, after that you will loose your spot)</h6>');
			modal.find('.modal-footer button').hide();

		},
		error: function(jqxhr) {
			$("#messagesList").text(jqxhr.responseText); // @text = response error, it is will be errors: 324, 500, 404 or anythings else
		}

	});


});