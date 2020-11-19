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

import Unicam.SPM2020_FMS.model.Car;

import Unicam.SPM2020_FMS.service.CarService;

@Controller
public class CarRegistrationController {
	  @Autowired
	  public CarService carService;

	  @RequestMapping(value = "/addCar", method = RequestMethod.GET)
	  public ModelAndView showRegister(HttpServletRequest request, HttpServletResponse response,HttpSession session) {
	    ModelAndView mav = new ModelAndView("addCar");
	    mav.addObject("car", new Car());
System.out.println(session.getId());
System.out.println(session.getAttribute("user"));
System.out.println(session.getAttribute("user"));
System.out.println(session.getCreationTime());
	    return mav;
	  }

	  @RequestMapping(value = "/addCarProcess", method = RequestMethod.POST)
	  public ModelAndView addUser(HttpServletRequest request, HttpServletResponse response, HttpSession session,
	      @ModelAttribute("car") Car car) {
	
		car.setDriver((Integer)session.getAttribute("user"));
		
	    carService.register(car);

	    return new ModelAndView("addCar", "message", car.getLicensePlateNumber());
	  }

}
