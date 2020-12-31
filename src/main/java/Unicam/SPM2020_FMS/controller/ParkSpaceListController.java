package Unicam.SPM2020_FMS.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
				for (ParkingSpace parkingSpace : parkSpaceList) {
					parkingSpace.setFreeAll(spotService.getAvailable(parkingSpace.getIdParkingSpace()));		
					parkingSpace.setFreeCovered(spotService.getCoveredAvailable(parkingSpace.getIdParkingSpace()));
					parkingSpace.setFreeHandicap(spotService.getHandicapAvailable(parkingSpace.getIdParkingSpace()));
				}
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

}
