
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
		let isGuarded = "";
		if(data[key][12] == "false") isGuarded = "No"; else isGuarded = "Yes";
		  addMarkerToGroup(group, {lat:getCoordinate(data[key][0])[0], lng:getCoordinate(data[key][0])[1]},
				    '<div><a href="http://maps.google.com/maps?q='+data[key][0]+'" target="_blank">'+data[key][2]+'</a>' +
				    '</div><div >Address: '+data[key][3]+'<br>Overall: '+data[key][4]+'/'+data[key][5]+'<br>Covered: '+data[key][6]+'/'+data[key][7]+' <br>Handicap: '+data[key][8]+'/'+data[key][9]+'<br><strong style="color:#d1ec15;">Guarded: '+isGuarded+'</strong><br><strong style="color:#d1ec15;">Fee: '+data[key][11]+' €/h</strong></div><div class="job-right my-4 flex-shrink-0">'+
					'<div class="job-right my-4 flex-shrink-0">'+
					'<button type="button" class="btn d-block  d-sm-inline-block btn-light" data-toggle="modal" id="parkNowButton"  data-image="'+data[key][10]+'" data-city="'+data[key][1]+'" data-name="'+data[key][2]+'" data-address="'+data[key][3]+'" data-idparkingspace="'+key+'" data-target="#reservationModal">Park now</button>&emsp;'+
					'<button type="button" class="btn d-block  d-sm-inline-block btn-light" data-toggle="modal" id="scheduleButton"  data-image="'+data[key][10]+'" data-city="'+data[key][1]+'" data-name="'+data[key][2]+'" data-address="'+data[key][3]+'" data-idparkingspace="'+key+'" data-target="#reservationModal">Reserve</button></div>');
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
	  
	  var customIcon = new H.map.Icon(iconPath,{size: {w: 35, h:40}});
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
