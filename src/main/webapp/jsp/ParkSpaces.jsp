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
								you, once pressed the button "reserve" the system will show you
								your spot.</p>
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
													${parkSpace.getName()}
												<br/>
													${parkSpace.getAddress()}
												</h5>
												<ul class="d-md-flex flex-wrap text-capitalize ff-open-sans"
													style="padding: 0;">
													<li class="mr-md-4"><i class="zmdi zmdi-pin mr-2"></i>
														Overall: ${parkSpace.getFreeAll()}/${parkSpace.getSpotsCapacity()}</li>
													<li class="mr-md-4"><i class="zmdi zmdi-pin mr-2"></i>
														Covered: ${parkSpace.getFreeCovered()}/${parkSpace.getCoveredSpots()}</li>
													<li class="mr-md-4"><i class="zmdi zmdi-time mr-2"></i>
														Handicap: ${parkSpace.getFreeHandicap()}/${parkSpace.getHandicapSpots()}</li>
													<c:if test="${parkSpace.isGuarded() == true}">
														<li class="mr-md-4"><i class="zmdi zmdi-time mr-2"></i>
															Is guarded</li>
													</c:if>
												</ul>
											</div>
										</div>

										<div class="job-right my-4 flex-shrink-0">
											<a href="#"
												class="btn d-block w-100 d-sm-inline-block btn-light">Reserve</a>
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






</body>

<script type="text/javascript"
	src="https://js.api.here.com/v3/3.1/mapsjs-core.js"></script>
<script type="text/javascript"
	src="https://js.api.here.com/v3/3.1/mapsjs-service.js"></script>
<script type="text/javascript"
	src="https://js.api.here.com/v3/3.1/mapsjs-ui.js"></script>
<script type="text/javascript"
	src="https://js.api.here.com/v3/3.1/mapsjs-mapevents.js"></script>
<script>


var data = {
        <c:forEach var="parkSpace" items="${parkSpaceList}"
			varStatus="tagStatus">
            ${parkSpace.getIdParkingSpace()}: ['${parkSpace.getCoordinates()}','${parkSpace.getCity()}','${parkSpace.getName()}', '${parkSpace.getAddress()}','${parkSpace.getSpotsCapacity()}','${parkSpace.getHandicapSpots()}']${!tagStatus.last ? ',' : ''}
        </c:forEach>
    };




var group;
/**
 * Adds markers to the map highlighting the locations of the captials of
 * France, Italy, Germany, Spain and the United Kingdom.
 *
 * @param  {H.Map} map      A HERE Map instance within the application
 */
function addMarkersToMap(map) {
 
	
	  group = new H.map.Group();

	  map.addObject(group);


	  // add 'tap' event listener, that opens info bubble, to the group
	  group.addEventListener('tap', function (evt) {
	    // event target is the marker itself, group is a parent event target
	    // for all objects that it contains
	    var bubble =  new H.ui.InfoBubble(evt.target.getGeometry(), {
	      // read custom data
	      content: evt.target.getData()
	    });
	    // show info bubble
	    ui.addBubble(bubble);
	  }, false);

	  Object.keys(data).forEach(key => {
		
		  addMarkerToGroup(group, {lat:getCoordinate(data[key][0])[0], lng:getCoordinate(data[key][0])[1]},
				    '<div><a href="http://maps.google.com/maps?q='+data[key][0]+'" target="_blank">'+data[key][2]+'</a>' +
				    '</div><div >Address: '+data[key][3]+'<br>Capacity: '+data[key][4]+'<br>Handicap spots: '+data[key][5]+'</div>');
		});
	
	 
	
	// get geo bounding box for the group and set it to the map
	  map.getViewModel().setLookAtData({
	    bounds: group.getBoundingBox()
	  });
}
 


 /**
  * Creates a new marker and adds it to a group
  * @param {H.map.Group} group       The group holding the new marker
  * @param {H.geo.Point} coordinate  The location of the marker
  * @param {String} html             Data associated with the marker
  */
 function addMarkerToGroup(group, coordinate, html) {
	  var path = "${pageContext.request.contextPath}/resources/images/parking-icon-red.png";
	  var customIcon = new H.map.Icon(path,{size: {w: 35, h:40}});
   var marker = new H.map.Marker(coordinate,{icon:customIcon});
   // add custom data to the marker
   marker.setData(html);
   group.addObject(marker);
 }

// Parse the coordinates from a single string to two float values (Latitude and Longitude)
function getCoordinate(coordinates){
	
	
	var arr = [];
	var sub1 = coordinates.substr(0,coordinates.indexOf(","));
	arr[0] = parseFloat(sub1);
	arr[1] = parseFloat(coordinates.substr(sub1.length+1));

	return arr;
}


// Permits the show off the keyboard when touching outside the input form on mobile
function blurInput(){
	
	let el = document.querySelector( ':focus' );
	if( el ) el.blur();
}


 

 var  searchedGroup;

 
 /**
  *
  *
  * A full list of available request parameters can be found in the Geocoder API documentation.
  * see: http://developer.here.com/rest-apis/documentation/geocoder/topics/resource-geocode.html
  *
  * @param   {H.service.Platform} platform    A stub class to access HERE services
  */
 function geocode(platform,address) {
		 // If the container has been inizialized (so at least one search is made) and the input field is empty
		 // remove the container already present and set the zoom to see all the parking spaces  
	  if(searchedGroup != null && address == ""){
		  
			 map.removeObject(searchedGroup);
			 searchedGroup = null;
			 map.getViewModel().setLookAtData({
				    bounds: group.getBoundingBox()
				  });
			 return;
	  }else if(searchedGroup != null && address != ""){
			 map.removeObject(searchedGroup);
			 searchedGroup = new H.map.Group();
			 
		 }else if(searchedGroup == null && address == "") {
			 return;
		 }else{
			 searchedGroup = new H.map.Group();
			 
		 }
   var geocoder = platform.getSearchService(),
       geocodingParameters = {
         q: address
       };

   geocoder.geocode(
     geocodingParameters,
     onSuccess,
     onError
   );
 }
 /**
  * This function will be called once the Geocoder REST API provides a response
  * @param  {Object} result          A JSONP object representing the  location(s) found.
  *
  * see: http://developer.here.com/rest-apis/documentation/geocoder/topics/resource-type-response-geocode.html
  */
 function onSuccess(result) {
   var locations = result.items;
  /*
   * The styling of the geocoding response on the map is entirely under the developer's control.
   * A representitive styling can be found the full JS + HTML code of this example
   * in the functions below:
   */
   
   if(locations.length == 0){ 
	   
	   return;
	   
	   }
   
   
   
   addLocationsToMap(locations);
  // addLocationsToPanel(locations);
   // ... etc.
 }

 /**
  * This function will be called if a communication error occurs during the JSON-P request
  * @param  {Object} error  The error message received.
  */
 function onError(error) {
   alert('Can\'t reach the remote server');
 }
  
  
  /**
   * Creates a series of H.map.Markers for each location found, and adds it to the map.
   * @param {Object[]} locations An array of locations as received from the
   *                             H.service.GeocodingService
   */
  function addLocationsToMap(locations){
    debugger
    var position,i;



    // Add a marker for each location found
    for (i = 0;  i < locations.length; i += 1) {
      let location = locations[i];
      marker = new H.map.Marker(location.position);
      marker.label = location.address.label;
      searchedGroup.addObject(marker);
      
      var circle  = new H.map.Circle(
			    new H.geo.Point(location.position.lat,location.position.lng), //center
			    500, // Radius proportional to 2.719 million population
			    {style: {fillColor: 'rgba(0, 221, 255, 0.06)'}}
			  );
      
	searchedGroup.addObject(circle);
    }
      
 

    /*group.addEventListener('tap', function (evt) {
      map.setCenter(evt.target.getGeometry());
      openBubble(
         evt.target.getGeometry(), evt.target.label);
    }, false);*/

    // Add the locations group to the map
    map.addObject(searchedGroup);
 // get geo bounding box for the group and set it to the map
	  map.getViewModel().setLookAtData({
	    bounds: searchedGroup.getBoundingBox()
	  });
  }
 
   document.getElementById("mapFilter")
   .addEventListener("keyup", function(event) {
   event.preventDefault();
   if (event.keyCode === 13) {
       document.getElementById("searchButton").click();
   }
});
   
 // Filter the markers on the map. According to the address inserted, the map will show all the parking spaces
 // in a radius of one kilometer of that address.
 function filterMap(){
		
		// Declare variables
		var input, filter;
		input = document.getElementById("mapFilter");
		
		
		filter = input.value;
		 // create container for objects
		  
		geocode(platform,filter);
		 
	}

 
//Filter the parking spaces by city, address or name (for parking spaces list)
 function filterItems() {
 	// Declare variables
 	var input, filter, spacesList, listOfDiv, element, i, txtValue;
 	input = document.getElementById("filterInput");
 	filter = input.value.toUpperCase();
 	spacesList = document.getElementById("parkingSpacesList");
 	listOfDiv = spacesList.querySelectorAll("div.job-box");

 	// Loop through all divs, and hide those who don't match the search query
 	for (i = 0; i < listOfDiv.length; i++) {
 		element = listOfDiv[i].getElementsByTagName("h5")[0];
 		if (element) {
 			txtValue = element.textContent || element.innerText;
 			if (txtValue.toUpperCase().indexOf(filter) > -1) {
 				listOfDiv[i].style.display = "";
 			} else {
 				listOfDiv[i].setAttribute("style","display:none !important");
 			}
 		}
 	}
 }
 
 
 /**
 * Boilerplate map initialization code starts below:
 */

//Step 1: initialize communication with the platform
// In your own code, replace variable window.apikey with your own apikey
var platform = new H.service.Platform({
  apikey: 't1psY7r-xlszEgaCi4-VBTittgbS5_FwBqEh_TqrzD0'
});
var defaultLayers = platform.createDefaultLayers();

//Step 2: initialize a map - this map is centered over Europe
var map = new H.Map(document.getElementById('map'),
  defaultLayers.vector.normal.map,{
  pixelRatio: window.devicePixelRatio || 1
});
// add a resize listener to make sure that the map occupies the whole container
window.addEventListener('resize', () => map.getViewPort().resize());

//Step 3: make the map interactive
// MapEvents enables the event system
// Behavior implements default interactions for pan/zoom (also on mobile touch environments)
var behavior = new H.mapevents.Behavior(new H.mapevents.MapEvents(map));

// Create the default UI components
var ui = H.ui.UI.createDefault(map, defaultLayers);

// Now use the map as required...
window.onload = function () {
  addMarkersToMap(map);

}


</script>
</html>