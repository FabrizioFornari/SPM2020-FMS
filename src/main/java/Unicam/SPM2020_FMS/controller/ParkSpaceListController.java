package Unicam.SPM2020_FMS.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import Unicam.SPM2020_FMS.model.Login;
import Unicam.SPM2020_FMS.model.ParkingSpace;
import Unicam.SPM2020_FMS.model.User;
import Unicam.SPM2020_FMS.service.ParkSpaceService;
import Unicam.SPM2020_FMS.service.ParkSpotService;
import Unicam.SPM2020_FMS.service.StorageService;

@Controller
public class ParkSpaceListController {

	@Autowired
	public ParkSpaceService parkService;

	@Autowired
	public ParkSpotService spotService;

	@Autowired
	public StorageService storageService;

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
				errMsg = bindingResult.getFieldError().getDefaultMessage().split(":")[1];
			} else {
				errMsg = "Operation not completed: invalid information!";
			}
			session.setAttribute("oldSpace", parkingSpace);

		} else {
			String filename="";
			if (!parkingSpace.getImageFile().isEmpty()) {
				filename = System.currentTimeMillis() + parkingSpace.getImageFile().getOriginalFilename();
				parkingSpace.setImageName(filename);
			}
			int editResult = parkService.edit(parkingSpace);
			String[] spaceMessages = { 
					"Updating Park Space has not been possible!",
					"Position specified has been already used"
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
						errMsg = "Operation not completed: Park spots and Park map are not updated";
					} else {
						errMsg = "Operation not completed: Park spots are not updated";
					}
				} else {
					if (fileNotUploaded) {
						errMsg = "Operation not completed: Park map is not updated";
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
			msg = "Cannot delete the selected parking space!";
		}
		
		
		session.setAttribute("message", msg);
		return "redirect:/ParksManagement";
	}
	
	
	

}
