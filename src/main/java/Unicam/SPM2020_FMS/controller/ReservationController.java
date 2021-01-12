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
import Unicam.SPM2020_FMS.model.ParkingSpot;
import Unicam.SPM2020_FMS.model.Reservation;
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
	  public ModelAndView showReservationsToCheck(HttpServletRequest request, HttpServletResponse response,HttpSession session) {
		
	    User user = (User) session.getAttribute("user");

	    if (user!=null) {
	    	if (user.getUserType().equals("Policeman")) {
		    	ModelAndView mav = new ModelAndView("reservationsToCheck");
		    	List<Reservation> reservationsToCheck = reservationService.showReservationsToCheck();
		    	mav.addObject("reservationsToCheck",reservationsToCheck);
				List<ParkingSpot> wronglyOccupied = spotService.getWronglyOccupied();
				String wronglyOccupiedMsg="";
				for (ParkingSpot spot : wronglyOccupied) {
					wronglyOccupiedMsg=wronglyOccupiedMsg.concat(spot.getParkingSpace()+":"+spot.getSpotNumber()+";");
				}
				mav.addObject("wronglyOccupiedMsg",wronglyOccupiedMsg);
				System.out.println("Load page check: " + wronglyOccupiedMsg);
		    	return mav;
	    	} else {
	    		return new ModelAndView("welcome", "user", user);
	    	}
	    } else {
	    	ModelAndView mav=new ModelAndView("login", "login", new Login());
	    	mav.addObject("message", "Please login");		
	    	return mav;
	    }
	  }
}
