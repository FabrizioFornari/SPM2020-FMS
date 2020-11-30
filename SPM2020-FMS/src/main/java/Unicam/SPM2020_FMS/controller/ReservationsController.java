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
import Unicam.SPM2020_FMS.model.PolicemanUsers;
import Unicam.SPM2020_FMS.model.User;
import Unicam.SPM2020_FMS.service.ReservationsService;



@Controller
public class ReservationsController {

	 @Autowired
	  public ReservationsService reservationsService;
	 
	 
	  
	  @RequestMapping(value = "/reservationsToCheck", method = RequestMethod.GET)
	  public ModelAndView showReservationsToCheck(HttpServletRequest request, HttpServletResponse response,HttpSession session) {
		
	    User user = (User) session.getAttribute("user");

	    if (user!=null) {
	    	ModelAndView mav = new ModelAndView("reservationsToCheck");
	    	List<PolicemanUsers> reservationsToCheck = reservationsService.showReservationsToCheck();
	    	mav.addObject("reservationsToCheck",reservationsToCheck);
	    	return mav;
	    } else {
	    	ModelAndView mav=new ModelAndView("login", "login", new Login());
	    	mav.addObject("message", "Please login");		
	    	return new ModelAndView("login", "login", new Login());
	    }
	  }
}
