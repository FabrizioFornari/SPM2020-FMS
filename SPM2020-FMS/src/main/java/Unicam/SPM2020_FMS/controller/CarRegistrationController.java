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
import Unicam.SPM2020_FMS.model.Login;
import Unicam.SPM2020_FMS.model.User;
import Unicam.SPM2020_FMS.service.CarService;

@Controller
public class CarRegistrationController {
	  @Autowired
	  public CarService carService;

	  @RequestMapping(value = "/addCar", method = RequestMethod.GET)
	  public ModelAndView showRegister(HttpServletRequest request, HttpServletResponse response,HttpSession session) {
		
	    User user = (User) session.getAttribute("user");
	    if (user!=null) {
	    	ModelAndView mav = new ModelAndView("addCar");
	    	mav.addObject("cars", carService.showCars(user.getIdUser()));
	    	mav.addObject("car", new Car());
	    	return mav;
	    } else {
	    	ModelAndView mav=new ModelAndView("login", "login", new Login());
	    	mav.addObject("message", "Please login");		
	    	return new ModelAndView("login", "login", new Login());
	    }
	  }

	  @RequestMapping(value = "/addCar", method = RequestMethod.POST)
	  public ModelAndView addCar(HttpServletRequest request, HttpServletResponse response, HttpSession session,
	      @ModelAttribute("car") Car car) {
		User user = (User) session.getAttribute("user");
		car.setDriver(user.getIdUser());
		
	    carService.register(car);

	    return new ModelAndView("addCar", "message", "License plate number "+car.getLicensePlateNumber()+" inserted!");
	  }

}
