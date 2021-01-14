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

import Unicam.SPM2020_FMS.model.Reservation;
import Unicam.SPM2020_FMS.model.SpotIllegallyOccupied;
import Unicam.SPM2020_FMS.model.User;
import Unicam.SPM2020_FMS.service.ParkSpotService;
import Unicam.SPM2020_FMS.service.ReservationService;

@Controller
public class ReservationController {

	@Autowired
	public ReservationService reservationService;

	@Autowired
	public ParkSpotService spotService;

	@RequestMapping(value = "/reservationsToCheck", method = RequestMethod.GET)
	public ModelAndView showReservationsToCheck(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {

		User user = (User) session.getAttribute("user");

		if (user != null) {
			if (user.getUserType().equals("Policeman")) {
				ModelAndView mav = new ModelAndView("reservationsToCheck");
				List<Reservation> reservationsToCheck = reservationService.showReservationsToCheck();
				mav.addObject("reservationsToCheck", reservationsToCheck);
				List<SpotIllegallyOccupied> illegallyOccupiedList = spotService.getIllegallyOccupied();
				String illegallyOccupiedString = "";
				for (SpotIllegallyOccupied spotIllegallyOccupied : illegallyOccupiedList) {
					illegallyOccupiedString = illegallyOccupiedString.concat("Parking space: "+spotIllegallyOccupied.getParkingSpaceName()
							+ " - Address: "+spotIllegallyOccupied.getParkingSpaceAddress() + " - Spot: " + spotIllegallyOccupied.getParkingSpot()+";");
				}
				
				mav.addObject("illegallyOccupiedString", illegallyOccupiedString);
		
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
