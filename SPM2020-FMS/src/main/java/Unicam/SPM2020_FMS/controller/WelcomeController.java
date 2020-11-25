package Unicam.SPM2020_FMS.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import Unicam.SPM2020_FMS.model.Login;
import Unicam.SPM2020_FMS.model.User;

@Controller
public class WelcomeController {

  @RequestMapping(value = "/", method = RequestMethod.GET)
  public ModelAndView showWelcome(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
    
    User user = (User)session.getAttribute("user");
    if (user!=null) {
    	return new ModelAndView("welcome", "user", user);
    } else {
    	ModelAndView mav=new ModelAndView("login", "login", new Login());
    	mav.addObject("message", "Please login");		
    	return new ModelAndView("login", "login", new Login());
    }
  }


}
