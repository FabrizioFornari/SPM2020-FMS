

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
    setInterval(function () {
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

$("#parkingStartDate").change(function(){
	
    $("#parkingEndDate").val($('#parkingStartDate').val());

});

$("#timeList").change(function(){
	

$("#timeList2").val($('#timeList').val());

});





$('#reservationModal').on('show.bs.modal', function(event) {


	var button = $(event.relatedTarget); // Button that triggered the modal
	var city = button.data('city');
	var name = button.data('name');
	var address = button.data('address');
	var idSpace = button.data('idparkingspace');
	var img = button.data('image');
	
	
	
	
	var modal = $(this);
	
	
	modal.find('#messagesList').html("");
	modal.find('.modal-body div#spotAssignment').html("");

	modal.find('#licenseSelect').show();
	modal.find('.modal-footer button').show();
	
	
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

	if(ws != null){
		ws.close();
		console.log("Websocket closed");
	}

});


$('#reserveNowButton').on('click',function(event){

var modal = $('#reservationModal');
var spaceId = modal.find('.modal-body input#parkingSpaceId').val();
var modal = $('#reservationModal');
var plate = modal.find('.modal-body select#licensePlateNumber').val();

if (plate == null) {
		modal.find('#messagesList').html('<li class="list-group-item list-group-item-danger">Select your license plate, if you don\'t have one please add it <a href="myCars">here</a>  </li>');

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
		
		if (window.WebSocket) {
		
		if (loc.protocol === "https:") {
		    url = "wss:";
		} else {
		    url = "ws:";
		}
		url += "//" + loc.host + loc.pathname + "/push/"+data+"/"+spaceId;
	    ws = new WebSocket(url);
	    ws.onmessage = function(event) {
	        var text = event.data;
	      
	       alert(text);
	    };
	}
	else {
		console.log("Browser not supporting WebSocket!");
	}
	
	
			modal.find('#messagesList').html("<li class='list-group-item list-group-item-success'>Reservation accomplished!</li>");
			modal.find('#licenseSelect').hide();
			
			modal.find('.modal-body div#spotAssignment').html('<img align="center" class="imgCenter"  src="'+path+'/resources/images/lot-map.png"></img><h5>Your parking spot is:</h5><br><h1 align="center">' + data + '</h1><h6 align="center" style="color:red">(You have <span id="timer">05:00</span> minutes to park, after that you will loose your spot)</h6>');
			modal.find('.modal-footer button').hide();
		
			var display = modal.find('#timer');
			startTimer(fiveMinutes, display);

		},
		error: function(jqxhr) {
			$("#messagesList").text(jqxhr.responseText); // @text = response error, it is will be errors: 324, 500, 404 or anythings else
		}

	});
	
	
	
});



$('#reserveButton').on('click', function(event) {


	var modal = $('#reservationModal');
	var date1 =  modal.find('.modal-body input#parkingStartDate').val();
	var dayFrom = date1.split("-");
	var day1 = parseInt(dayFrom[2]);
	var month1 = parseInt(dayFrom[1]);
	var year1 = parseInt(dayFrom[0]);
	
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

	if (plate == null) {
		modal.find('#messagesList').html('<li class="list-group-item list-group-item-danger">Select your license plate, if you don\'t have one please add it <a href="myCars">here</a>  </li>');

		return;

	}else if(dayFrom == "" || dayTo == ""){
		
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
	//modal.find('#messagesList').html('<img align="center" src="' + path + '/resources/images/loadingCar.gif" id="loadingImg" />')
	 

	$.ajax({
		type: "POST",
		url: "reserve",
		data: reservation,
		dataType: "text",
		success: function(data) {
			modal.find('#messagesList').html("<li class='list-group-item list-group-item-success'>Reservation accomplished for !</li>");
			modal.find('#licenseSelect').hide();
			modal.find('#dateSelection').hide();
			modal.find('.modal-body div#spotAssignment').html('<h5>Your parking spot is:</h5><br><h1 align="center">' + data + '</h1>');
			modal.find('.modal-footer button').hide();
		

		},
		error: function(jqxhr) {
			$("#messagesList").text(jqxhr.responseText); // @text = response error, it is will be errors: 324, 500, 404 or anythings else
		}

	});


});