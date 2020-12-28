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



@Controller
public class ParkSpaceListController {

	 @Autowired
	  public ParkSpaceService parkSpaceService;
	  
	  @RequestMapping(value = "/ParkSpaces", method = RequestMethod.GET)
	  public ModelAndView showParkSpaces(HttpServletRequest request, HttpServletResponse response,HttpSession session) {
		
	    User user = (User) session.getAttribute("user");

	    if (user!=null) {
	    	if (user.getUserType().equals("Driver")) {
		    	ModelAndView mav = new ModelAndView("ParkSpaces");
		    	List<ParkingSpace> parkSpaceList = parkSpaceService.showParkSpaceList();
		    	
		    	mav.addObject("parkSpaceList", parkSpaceList);
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
