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
public class RegistrationController {

  @Autowired
  public UserService userService;

  @RequestMapping(value = "/register", method = RequestMethod.GET)
  public ModelAndView showRegister(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
	  
    User user = (User)session.getAttribute("user");
    if (user!=null) {
    	return new ModelAndView("welcome", "user", user);
    } else {
        ModelAndView mav = new ModelAndView("register");
        mav.addObject("user", new User());
        Object message= session.getAttribute("message");
        if(message!=null) {
        	mav.addObject("message", (String) message);
        	session.removeAttribute("message");
        }
        return mav;   	
    }
  }

  @RequestMapping(value = "/registerProcess", method = RequestMethod.POST)
  public String addUser(HttpServletRequest request, HttpServletResponse response, HttpSession session,
      @ModelAttribute("user") User user) {

    int regResult=userService.register(user);
    String[] messages = {
    			"Registration error!",
    			"Mail already used in the system",
    			"Tax code already registered",
    			"ID number already used",
    			"Authorization number already used"
    		};
    
    if (regResult>0) {
        user.setIdUser(regResult);
        session.setAttribute("user", user);
        return "redirect:/welcome";
    } else {
    	regResult*=-1;
    	session.setAttribute("message", messages[regResult]);
    	return "redirect:/register";
    }

  }
  
}
