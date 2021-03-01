package Unicam.SPM2020_FMS.controller;

import java.util.List;

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
import Unicam.SPM2020_FMS.model.UserCars;
import Unicam.SPM2020_FMS.service.CarService;

@Controller
public class CarRegistrationController {
	
	  //In this controller also handling for POST will return directly MAV instead to redirect in order to avoid refreshing
	
	  @Autowired
	  public CarService carService;

	  @RequestMapping(value = "/myCars", method = RequestMethod.GET)
	  public ModelAndView showRegister(HttpServletRequest request, HttpServletResponse response,HttpSession session) {
		
	    User user = (User) session.getAttribute("user");
	    if (user!=null) {
	    	if (user.getUserType().equals("Driver")) {
		    	ModelAndView mav = new ModelAndView("myCars");
		    	UserCars userCars = new UserCars();
		    	userCars.setMyCars(carService.showCars(user.getIdUser()));
		    	mav.addObject("userCars", userCars);
		    	mav.addObject("carToAdd", new Car());
		    	mav.addObject("carToTrash", new Car());
		    	return mav;
	    	} else {
	    		return new ModelAndView("welcome", "user", user);
	    	}
	    } else {
	    	ModelAndView mav=new ModelAndView("login", "login", new Login());
	    	mav.addObject("message", "Please login");		
	    	return mav;
	    }
	  }

	  @RequestMapping(value = "/addCar", method = RequestMethod.POST)
	  public ModelAndView addCar(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			  @ModelAttribute("carToAdd") Car car) {
		User user = (User) session.getAttribute("user");
		if (user==null) {
	    	ModelAndView mav=new ModelAndView("login", "login", new Login());
	    	mav.addObject("message", "Please login");		
	    	return mav;
		}
		
		car.setDriver(user.getIdUser());
	    int addResult=carService.register(car);
	    ModelAndView mav = new ModelAndView("myCars");
	    if (addResult==0) {
	    	mav.addObject("message", "Car already registered!");
	    } else if (addResult==-1) {
	    	mav.addObject("message", "Car not registered. Please try again!");
	    }
    	UserCars userCars = new UserCars();
    	userCars.setMyCars(carService.showCars(user.getIdUser()));
    	mav.addObject("userCars", userCars);
    	mav.addObject("carToAdd", new Car());
    	mav.addObject("carToTrash", new Car());
	    
	    return mav;
	  }
	  
	  //NOT USED
	  @RequestMapping(value = "/modifyCars", method = RequestMethod.POST)
	  public ModelAndView modifyCar(HttpServletRequest request, HttpServletResponse response, HttpSession session,
	      @ModelAttribute("cars") UserCars cars) {
		  
		List<Car> carsList = cars.getMyCars();
		User user = (User) session.getAttribute("user");
		if (user==null) {
	    	ModelAndView mav=new ModelAndView("login", "login", new Login());
	    	mav.addObject("message", "Please login");		
	    	return mav;
		}
		
	    carService.modifyCars(carsList, carService.showCars(user.getIdUser()));   
	    ModelAndView mav = new ModelAndView("myCars");
    	UserCars userCars = new UserCars();
    	userCars.setMyCars(carService.showCars(user.getIdUser()));
    	mav.addObject("userCars", userCars);
    	mav.addObject("carToAdd", new Car());
    	mav.addObject("carToTrash", new Car());
	    
	    return mav;
	  }
	  
	  @RequestMapping(value = "/deleteCar", method = RequestMethod.POST)
	  public ModelAndView deleteCar(HttpServletRequest request, HttpServletResponse response, HttpSession session,
	      @ModelAttribute("carToTrash") Car car) {
		  
		User user = (User) session.getAttribute("user");
		if (user==null) {
	    	ModelAndView mav=new ModelAndView("login", "login", new Login());
	    	mav.addObject("message", "Please login");		
	    	return mav;
		}
		car.setDriver(user.getIdUser());
		
	    carService.deleteCar(car);
	    
	    ModelAndView mav = new ModelAndView("myCars");
    	UserCars userCars = new UserCars();
    	userCars.setMyCars(carService.showCars(user.getIdUser()));
    	mav.addObject("userCars", userCars);
    	mav.addObject("carToAdd", new Car());
    	mav.addObject("carToTrash", new Car());
	    
	    return mav;
	  }
	  
	  
	

}
