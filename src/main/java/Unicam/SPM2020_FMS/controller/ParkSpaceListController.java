package Unicam.SPM2020_FMS.controller;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Base64;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import Unicam.SPM2020_FMS.model.Login;
import Unicam.SPM2020_FMS.model.ParkingSpace;
import Unicam.SPM2020_FMS.model.Reservation;
import Unicam.SPM2020_FMS.model.User;
import Unicam.SPM2020_FMS.model.UserCars;
import Unicam.SPM2020_FMS.service.CarService;
import Unicam.SPM2020_FMS.service.ParkSpaceService;
import Unicam.SPM2020_FMS.service.ParkSpotService;
import Unicam.SPM2020_FMS.service.ReservationService;
import Unicam.SPM2020_FMS.service.SchedulerService;
import Unicam.SPM2020_FMS.service.StorageService;

@Controller
public class ParkSpaceListController {

	@Autowired
	public ParkSpaceService parkService;

	@Autowired
	public ParkSpotService spotService;

	@Autowired
	public StorageService storageService;

	@Autowired
	public CarService carService;
	
	@Autowired
	public ReservationService reservationService;
	
	@Autowired
	public SchedulerService schedulerService;
	
	//DRIVER
	/** Retrieve the list of the parking spaces from the database (Driver) */
	@RequestMapping(value = "/ParkSpaces", method = RequestMethod.GET)
	public ModelAndView showParkSpaces(HttpServletRequest request, HttpServletResponse response, HttpSession session) {

		User user = (User) session.getAttribute("user");
		
		if (user != null) {
			if (user.getUserType().equals("Driver")) {
				ModelAndView mav = new ModelAndView("ParkSpaces");
			    Object message= session.getAttribute("message");
			    if(message!=null) {
			    	mav.addObject("message", (String) message);
			    	session.removeAttribute("message");
			    }
				List<ParkingSpace> parkSpaceList = parkService.showParkSpaceList();
				for (ParkingSpace parkingSpace : parkSpaceList) {
					parkingSpace.setFreeAll(spotService.getAvailable(parkingSpace.getIdParkingSpace()));		
					parkingSpace.setFreeCovered(spotService.getCoveredAvailable(parkingSpace.getIdParkingSpace()));
					parkingSpace.setFreeHandicap(spotService.getHandicapAvailable(parkingSpace.getIdParkingSpace()));
				}
				mav.addObject("parkSpaceList", parkSpaceList);
				UserCars userCars = new UserCars();
		    	userCars.setMyCars(carService.showCars(user.getIdUser()));
		    	mav.addObject("userCars", userCars);
				mav.addObject("reservation", new Reservation());
				return mav;
			} else {
				return new ModelAndView("welcome", "user", user);
			}
		} else {
			ModelAndView mav = new ModelAndView("login", "login", new Login());
			mav.addObject("message", "Please login");
			return mav;
		}
	}

	@RequestMapping(value = "/parkNow", method = RequestMethod.POST)
	@ResponseBody
	public String reserveSpotNow (HttpServletRequest request, HttpServletResponse response, HttpSession session,
				@ModelAttribute("reservartion") Reservation reservation) {
		
		String[] bookMessages = {
				  "We are sorry, it seems that there are no more available spots",
				  "Error: dates are not correctly specified",
				  "Error: reservation has not been possible"
				};		
		if (reservation.isAskedCovered() || reservation.isAskedHandicap()) {
			bookMessages[0]="We are sorry, no available spot match your requests";
		}

		User user = (User) session.getAttribute("user");	
		reservation.setDriver(user.getIdUser());
		reservation.setParkingSpot(spotService.getFreeSpot(reservation.getParkingSpaceId(),reservation.isAskedCovered(), reservation.isAskedHandicap()));
		reservation.setParkingStart(null);
		
		if (reservation.getParkingSpot()==0) {
			return bookMessages[0];
		}
		
		int result=reservationService.addReservation(reservation);			

		if (result<=0) {
			result*=-1;
			return bookMessages[result];
		} 
		reservation.setId(result);
		schedulerService.scheduleReservationExpiring(reservation);
		
		return reservation.getParkingSpot().toString();
	}
	
	@RequestMapping(value = "/getMapSrc", method = RequestMethod.GET, produces = MediaType.ALL_VALUE)
	@ResponseBody
	public String getMapSrc (HttpServletRequest request, HttpServletResponse response, HttpSession session,
			@RequestParam("filename") String filename) throws IOException {

		byte[] payload={};		
		try {
			payload = IOUtils.toByteArray(storageService.loadAsResource(filename).getInputStream());
		} catch (UncheckedIOException e) {
			return "";
		}
		String extension=FilenameUtils.getExtension(filename).toLowerCase();
		String prefix="data:image/"+extension+";base64,";		
		return  prefix+Base64.getEncoder().encodeToString(payload);		
	}
	
	@RequestMapping(value = "/getMapSrcFromId", method = RequestMethod.GET, produces = MediaType.ALL_VALUE)
	@ResponseBody
	public String getMapSrcFromId (HttpServletRequest request, HttpServletResponse response, HttpSession session,
			@RequestParam("Id") String parkId) throws IOException {
		
		List<ParkingSpace> parkSpaceList = parkService.showParkSpaceList();
		String filename="";
		for (ParkingSpace park : parkSpaceList) { if(park.getIdParkingSpace()==Integer.parseInt(parkId)) filename=park.getImageName();}

		byte[] payload={};		
		try {
			payload = IOUtils.toByteArray(storageService.loadAsResource(filename).getInputStream());
		} catch (UncheckedIOException e) {
			return "";
		}
		String extension=FilenameUtils.getExtension(filename).toLowerCase();
		String prefix="data:image/"+extension+";base64,";		
		return  prefix+Base64.getEncoder().encodeToString(payload);		
	}
	
	@RequestMapping(value = "/reserve", method = RequestMethod.POST)
	@ResponseBody
	public String reserveSpotForLater (HttpServletRequest request, HttpServletResponse response, HttpSession session,
				@ModelAttribute("reservartion") Reservation reservation) {
		
		String[] bookMessages = {
				  "We are sorry, it seems that there are no more available spots",
				  "Error: dates are not correctly specified",
				  "Error: reservation has not been possible"
				};		
		if (reservation.isAskedCovered() || reservation.isAskedHandicap()) {
			bookMessages[0]="We are sorry, no available spot match your requests";
		}

		User user = (User) session.getAttribute("user");	
		reservation.setDriver(user.getIdUser());
		reservation.setParkingSpot(spotService.getFreeSpot(reservation));
		
		if (reservation.getParkingSpot()<=0) {
			int result=reservation.getParkingSpot()*-1;
			return bookMessages[result];
		}
		
		int result=reservationService.addReservation(reservation);			

		if (result<=0) {
			result*=-1;
			return bookMessages[result];
		} 
		reservation.setId(result);
		schedulerService.scheduleReservationCheck(reservation);
		schedulerService.scheduleReservationClosing(reservation);
		
		return reservation.getParkingSpot().toString();
	}
	
	//MUNICIPALITY
	/** Retrieve the list of the parking spaces from the database (Municipality) */
	@RequestMapping(value = "/ParksManagement", method = RequestMethod.GET)
	public ModelAndView showParkSpacesToManage(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {

		User user = (User) session.getAttribute("user");

		if (user != null) {
			if (user.getUserType().equals("Municipality")) {
				
				ModelAndView mav = new ModelAndView("ParksManagement");
			    Object message= session.getAttribute("message");
			    if(message!=null) {
			    	mav.addObject("message", (String) message);
			    	session.removeAttribute("message");
			    }
			    
				List<ParkingSpace> parkSpaceList = parkService.showParkSpaceList();
				for (ParkingSpace parkingSpace : parkSpaceList) {
					try {
						parkingSpace.setSpecCovered(
								parkingSpace.getSpecFromList(
										spotService.getCoveredSpotsNs(parkingSpace.getIdParkingSpace())
							));		
					} catch (IllegalArgumentException e) {
						parkingSpace.setCoveredSpots(0);
						parkingSpace.setSpecCovered("");
					}
					try {
						parkingSpace.setSpecHandicap(
								parkingSpace.getSpecFromList(
										spotService.getHandicapSpotsNs(parkingSpace.getIdParkingSpace())
							));
					} catch (IllegalArgumentException e) {
						parkingSpace.setHandicapSpots(0);
						parkingSpace.setSpecHandicap("");
					}
				}
				
				mav.addObject("parkSpaceToEdit", new ParkingSpace());
				mav.addObject("parkSpaceToDelete", new ParkingSpace());
				mav.addObject("parkSpaceList", parkSpaceList);
				return mav;
			} else {
				return new ModelAndView("welcome", "user", user);
			}
		} else {
			ModelAndView mav = new ModelAndView("login", "login", new Login());
			mav.addObject("message", "Please login");
			return mav;
		}
	}
	
	@RequestMapping(value = "/ParksManagement", method = RequestMethod.POST)
	public String editParkSpace(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			@ModelAttribute("parkSpaceToEdit") ParkingSpace parkingSpace, BindingResult bindingResult) {

		String errMsg = "";
		Boolean fileNotUploaded = false;

		if (bindingResult.hasFieldErrors()) {

			String field = bindingResult.getFieldError().getField();
			if (field.contains("Covered") || field.contains("Handicap")) {
				errMsg = "Not Updated! " + bindingResult.getFieldError().getDefaultMessage().split(":")[1];
			} else {
				errMsg = "Not Updated! invalid information!";
			}
			session.setAttribute("oldSpace", parkingSpace);

		} else {
			String filename="";
			if (parkingSpace.getImageFile()!=null && !parkingSpace.getImageFile().isEmpty()) {
				filename = System.currentTimeMillis() + parkingSpace.getImageFile().getOriginalFilename();
				parkingSpace.setImageName(filename);
			}
			int editResult = parkService.edit(parkingSpace);
			String[] spaceMessages = { 
					"Not Updated! Operation has not been possible!",
					"Not Updated! Position specified has been already used"
			};

			if (editResult <= 0) {
				editResult *= -1;
				errMsg = spaceMessages[editResult];
				session.setAttribute("oldSpace", parkingSpace);

			} else {

				//try to store uploaded file
				if (!parkingSpace.getImageFile().isEmpty()) {
					try {
						storageService.store(parkingSpace.getImageFile(), filename);
					} catch (Exception e) {
						fileNotUploaded = true;
					}
				}

				// try to generate spots
				int genResult = spotService.updateSpots(parkingSpace.getSpots());
				if (genResult < 0) {
					if (fileNotUploaded) {
						errMsg = "Warning: park spots and park map are not updated";
					} else {
						errMsg = "Warning: park spots are not updated";
					}
				} else {
					if (fileNotUploaded) {
						errMsg = "Warning: park map is not updated";
					} else {
						errMsg = "Park Space correctly updated";
					}
				}
			}
		}

		session.setAttribute("message", errMsg);
		return "redirect:/ParksManagement";
	}

	@RequestMapping(value = "/DeleteParkSpace", method = RequestMethod.POST)
	public String deleteParkSpace(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			@ModelAttribute("parkSpaceToDelete") ParkingSpace parkingSpace) {

		int res = parkService.deleteParkSpace(parkingSpace.getIdParkingSpace());
		
		String msg = "";
		if (res > 0) {
			msg = "Parking space successfully deleted";
		}else {
			msg = "Not Deleted! Operation has not been possible!";
		}
		
		session.setAttribute("message", msg);
		return "redirect:/ParksManagement";
	}
	
}
