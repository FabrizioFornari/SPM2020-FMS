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

import Unicam.SPM2020_FMS.model.Login;
import Unicam.SPM2020_FMS.model.User;
import Unicam.SPM2020_FMS.service.UserService;

@Controller
public class LoginController {

  @Autowired
  UserService userService;

  @RequestMapping(value = "/login", method = RequestMethod.GET)
  public ModelAndView showLogin(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
    
    User user = (User)session.getAttribute("user");
    if (user!=null) {
    	return new ModelAndView("welcome", "user", user);
    } else {    
	    ModelAndView mav = new ModelAndView("login");
	    mav.addObject("login", new Login());	    
	    Object message= session.getAttribute("message");
	    if(message!=null) {
	    	mav.addObject("message", (String) message);
	    	session.removeAttribute("message");
	    }	
	    return mav;
    }
  }

  @RequestMapping(value = "/loginProcess", method = RequestMethod.POST)
  public String loginProcess(HttpServletRequest request, HttpServletResponse response, HttpSession session,
  @ModelAttribute("login") Login login) {

    User user = userService.validateUser(login);

    if (user != null) {
      session.setAttribute("user", user);
      return "redirect:/";
    } else {
      session.setAttribute("message", "Email or Password is wrong!!");
      return "redirect:/login";
    }
    
  }
  
}
