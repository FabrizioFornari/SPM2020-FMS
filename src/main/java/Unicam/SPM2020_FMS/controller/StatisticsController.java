package Unicam.SPM2020_FMS.controller;

import java.util.List;

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
public class StatisticsController {

  @RequestMapping(value = "/Statistics", method = RequestMethod.GET)
  public ModelAndView showStatistics(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
    
    User user = (User)session.getAttribute("user");
	if (user != null) {
		if (user.getUserType().equals("Municipality")) {
			ModelAndView mav = new ModelAndView("statistics");
	
			return mav;
		} else {
			return new ModelAndView("welcome", "user", user);
		}
	} else {
		ModelAndView mav = new ModelAndView("login", "login", new Login());
		mav.addObject("message", "Please login");
		return mav;
	}
  }


}
