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
import Unicam.SPM2020_FMS.model.User;
import Unicam.SPM2020_FMS.service.ReservationService;

@Controller
public class WelcomeController {
	
  @Autowired
  public ReservationService reservationService;

  @RequestMapping(value = "/", method = RequestMethod.GET)
  public ModelAndView showWelcome(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
    
    User user = (User)session.getAttribute("user");
    if (user!=null) {
    	if (user.getUserType().equals("Driver")) {
	    	ModelAndView mav = new ModelAndView("welcome");
	    	List<Reservation> userReservations = reservationService.showUserReservation(user.getIdUser());
	    	mav.addObject("user", user);
	    	mav.addObject("userReservations", userReservations);
	    	return mav;
    	} else {
    		return new ModelAndView("welcome", "user", user);
    	}
    } else {	
    	return new ModelAndView("login", "login", new Login());
    }
  }


}
