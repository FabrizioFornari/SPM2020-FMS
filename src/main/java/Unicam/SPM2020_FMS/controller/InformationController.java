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

import Unicam.SPM2020_FMS.model.Login;
import Unicam.SPM2020_FMS.model.Payment;
import Unicam.SPM2020_FMS.model.User;
import Unicam.SPM2020_FMS.service.PaymentService;
import Unicam.SPM2020_FMS.service.UserService;

@Controller
public class InformationController {

	
	  @Autowired
	  public UserService userService;
	  
	  @Autowired 
	  public PaymentService paymentService;

	  @RequestMapping(value = "/profile", method = RequestMethod.GET)
	  public ModelAndView showProfile(HttpServletRequest request, HttpServletResponse response,HttpSession session) {
		  
	    User user = (User)session.getAttribute("user");
	    if (user!=null) {
		    ModelAndView mav = new ModelAndView("profilePage", "user", user);
		    Object message= session.getAttribute("message");	
		    if(message!=null) {
		    	mav.addObject("message", (String) message);
		    	session.removeAttribute("message");
		    }
	    	if(user.getUserType().equals("Driver")) {
		    	List<Payment> paymentsList = paymentService.showPaymentsList();
		    	mav.addObject("paymentsList",paymentsList);
	    	}		    
		    return mav;
	    }else {
	    	ModelAndView mav=new ModelAndView("login", "login", new Login());
	    	mav.addObject("message", "Please login");		
	    	return mav;
	    }
	  }
	  	  
	  @RequestMapping(value = "/updateUserProcess", method = RequestMethod.POST)
	  public String updateProfile(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("user") User user, HttpSession session) {
	    User oldUser = (User) session.getAttribute("user");
		if (oldUser==null) {
			session.setAttribute("message", "Please login");		
	    	return "redirect:/login";
		}
	    user.setIdUser(oldUser.getIdUser());
	    user.setUserType(oldUser.getUserType());
	    if (user.getPaymentTypeId()==null) user.setPaymentTypeId(0);
	    int updated=userService.update(user);
	    String msg;
	    session.removeAttribute("user");
	    session.setAttribute("user", user);
	    if (updated>0) {
	    	msg="Information properly updated";
	    } else {
	    	msg="Information update could not be possible";
	    }
	    session.setAttribute("message", msg);
	    return "redirect:/profile";
	  }
}
