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
	  @Autowired
	  public CarService carService;

	  @RequestMapping(value = "/addCar", method = RequestMethod.GET)
	  public ModelAndView showRegister(HttpServletRequest request, HttpServletResponse response,HttpSession session) {
		
	    User user = (User) session.getAttribute("user");
	    if (user!=null) {
	    	ModelAndView mav = new ModelAndView("addCar");
	    	UserCars userCars = new UserCars();
	    	userCars.setMyCars(carService.showCars(user.getIdUser()));
	    	mav.addObject("userCars", userCars);
	    	mav.addObject("carToAdd", new Car());
	    	mav.addObject("carToTrash", new Car());
	    	return mav;
	    } else {
	    	ModelAndView mav=new ModelAndView("login", "login", new Login());
	    	mav.addObject("message", "Please login");		
	    	return new ModelAndView("login", "login", new Login());
	    }
	  }

	  @RequestMapping(value = "/addCar", method = RequestMethod.POST)
	  public ModelAndView addCar(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			  @ModelAttribute("carToAdd") Car car) {
		User user = (User) session.getAttribute("user");
		car.setDriver(user.getIdUser());
		
	    carService.register(car);
	    ModelAndView mav = new ModelAndView("addCar");
    	UserCars userCars = new UserCars();
    	userCars.setMyCars(carService.showCars(user.getIdUser()));
    	mav.addObject("userCars", userCars);
    	mav.addObject("carToAdd", new Car());
    	mav.addObject("carToTrash", new Car());
	    
	    return mav;
	  }
	  
	  @RequestMapping(value = "/modifyCars", method = RequestMethod.POST)
	  public ModelAndView modifyCar(HttpServletRequest request, HttpServletResponse response, HttpSession session,
	      @ModelAttribute("cars") UserCars cars) {
		  
		List<Car> carsList = cars.getMyCars();
		User user = (User) session.getAttribute("user");
		
	    System.out.println(carService.modifyCars(carsList, carService.showCars(user.getIdUser())));
	    
	    ModelAndView mav = new ModelAndView("addCar");
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
		car.setDriver(user.getIdUser());
		
		System.out.println(car.getLicensePlateNumber()+"-"+car.getDriver());
		
	    System.out.println(carService.deleteCar(car));
	    
	    ModelAndView mav = new ModelAndView("addCar");
    	UserCars userCars = new UserCars();
    	userCars.setMyCars(carService.showCars(user.getIdUser()));
    	mav.addObject("userCars", userCars);
    	mav.addObject("carToAdd", new Car());
    	mav.addObject("carToTrash", new Car());
	    
	    return mav;
	  }

}
