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
import Unicam.SPM2020_FMS.model.Statistic;
import Unicam.SPM2020_FMS.model.User;
import Unicam.SPM2020_FMS.service.StatisticsService;

@Controller
public class StatisticsController {
  
  @Autowired
  public StatisticsService statService;

  @RequestMapping(value = "/Statistics", method = RequestMethod.GET)
  public ModelAndView showStatistics(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
    
    User user = (User)session.getAttribute("user");
	if (user != null) {
		if (user.getUserType().equals("Municipality")) {
			ModelAndView mav = new ModelAndView("statistics");
		    Object message= session.getAttribute("message");
		    if(message!=null) {
		    	mav.addObject("message", (String) message);
		    	session.removeAttribute("message");
		    }
			List<Statistic> revenueBySpace = statService.revenueByParkSpace(false);
			List<Statistic> revenueBySpaceMonth = statService.revenueByParkSpace(true);
			List<Statistic> usersByPayment = statService.usersByPayment();
			Float totalRevenue = statService.totalRevenue(false);
			Float totalRevenueMonth = statService.totalRevenue(true);
			int totalDrivers = statService.totalDrivers();
			mav.addObject("revenueBySpace",revenueBySpace);
			mav.addObject("revenueBySpaceFiltered",revenueBySpaceMonth);
			mav.addObject("paymentTypeList",usersByPayment);
			mav.addObject("totalRevenue",totalRevenue);
			mav.addObject("totalRevenueFiltered",totalRevenueMonth);
			mav.addObject("totalUsers",totalDrivers);
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
