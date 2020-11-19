package Unicam.SPM2020_FMS.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


import Unicam.SPM2020_FMS.model.User;
import Unicam.SPM2020_FMS.service.UserService;

@Controller
public class InformationController {

	
	 @Autowired
	  UserService userService;

	  @RequestMapping(value = "/profile", method = RequestMethod.GET)
	  public ModelAndView showProfile(HttpServletRequest request, HttpServletResponse response,HttpSession session) {
	    ModelAndView mav = new ModelAndView("profilePage");
	    mav.addObject("user", session.getAttribute("user"));

	    return mav;
	  }
	  
	  
	  @RequestMapping(value = "/updateUserProcess", method = RequestMethod.POST)
	  public ModelAndView updateProfile(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("user") User user) {
	    
		  userService.update(user);

	    return new ModelAndView("profilePage", "message",user);
	  }
}
