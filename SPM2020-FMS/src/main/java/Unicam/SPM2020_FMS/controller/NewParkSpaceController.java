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
import Unicam.SPM2020_FMS.model.Login;
import Unicam.SPM2020_FMS.model.ParkingSpace;
import Unicam.SPM2020_FMS.service.ParkSpaceService;

@Controller
public class NewParkSpaceController {
	  @Autowired
	  public ParkSpaceService parkService;

	  @RequestMapping(value = "/newParkArea", method = RequestMethod.GET)
	  public ModelAndView newParkSpace(HttpServletRequest request, HttpServletResponse response,HttpSession session) {
		
	    User user = (User) session.getAttribute("user");
	    if (user!=null) {
	    	if (user.getUserType().equals("Municipality")) {
		    	ModelAndView mav = new ModelAndView("newParkArea");
		  
		    	mav.addObject("parkSpace", new ParkingSpace());
		    	return mav;
	    	} else {
	    		return new ModelAndView("welcome", "user", user);
	    	}
	    } else {
	    	ModelAndView mav=new ModelAndView("login", "login", new Login());
	    	mav.addObject("message", "Please login");		
	    	return new ModelAndView("login", "login", new Login());
	    }
	  }

	  @RequestMapping(value = "/addParkSpace", method = RequestMethod.POST)
	  public String addCar(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			  @ModelAttribute("newParkSpace") ParkingSpace newParkSpace) {
		
		int addResult=parkService.add(newParkSpace);

	    String[] messages = {
    			"Registration error!",
    			"Position specified has been already used"
	    };

	    if (addResult>0) {
	    	newParkSpace.setIdParkingSpace(addResult);
	    	session.setAttribute("message", "Park Space correctly added");
	    } else {
	    	addResult*=-1;
	    	session.setAttribute("message", messages[addResult]);
	    }
	    
    	return "redirect:/newParkArea";
	  }

}
