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
  public ModelAndView showRegister(HttpServletRequest request, HttpServletResponse response) {
    ModelAndView mav = new ModelAndView("register");
    mav.addObject("user", new User());

    return mav;
  }

  @RequestMapping(value = "/_welcome", method = RequestMethod.POST)
  public ModelAndView addUser(HttpServletRequest request, HttpServletResponse response, HttpSession session,
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
        return new ModelAndView("welcome", "name", user.getName());
    } else {
    	regResult*=-1;
        return new ModelAndView("register", "message", messages[regResult]);
    }

  }
  
}
