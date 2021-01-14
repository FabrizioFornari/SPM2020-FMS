package Unicam.SPM2020_FMS.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;



import Unicam.SPM2020_FMS.model.Login;
import Unicam.SPM2020_FMS.service.UserService;

@Controller
public class LogoutController {

	 @Autowired
	 public UserService userService;
	
	 @RequestMapping(value = "/logout", method = RequestMethod.GET)
	  public ModelAndView showLogin(HttpServletRequest request, HttpServletResponse response,HttpSession session) {
	    ModelAndView mav = new ModelAndView("login");
	    mav.addObject("login", new Login());
	    session.invalidate();
	    return mav;
	  }
}
