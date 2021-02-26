package Unicam.SPM2020_FMS.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.ModelAndViewAssert.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import Unicam.SPM2020_FMS.model.Car;
import Unicam.SPM2020_FMS.model.Login;
import Unicam.SPM2020_FMS.model.User;
import Unicam.SPM2020_FMS.model.UserCars;

@SpringJUnitWebConfig(locations = {"classpath:/user-beans.xml","file:src/main/webapp/WEB-INF/spring-mvc-servlet.xml"})
public class CarRegistrationControllerTest {
	
  MockMvc mockMvc;
  MockHttpSession mockSession;
  User mockUser;
  
  @BeforeEach
  void setup(WebApplicationContext wac) {
      this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
      this.mockSession = new MockHttpSession();
      this.mockUser = new User();
      this.mockUser.setUserType("Driver");
      this.mockSession.setAttribute("user", mockUser);
  }
  
  @Test
  public void testCarsNoUserView() throws Exception {
	ModelAndView resultMav=mockMvc.perform(get("/myCars")).andExpect(status().isOk()).andReturn().getModelAndView();
	assertViewName(resultMav, "login");
  }

  @Test
  public void testCarsNoUserModel() throws Exception {
	ModelAndView resultMav=mockMvc.perform(get("/myCars")).andExpect(status().isOk()).andReturn().getModelAndView();
	assertAndReturnModelAttributeOfType(resultMav, "login", Login.class);
  }
  
  @Test
  public void testCarsNoDriverView() throws Exception {
	this.mockSession.removeAttribute("user");
	this.mockUser.setUserType("Policeman");
	this.mockSession.setAttribute("user", this.mockUser);
	ModelAndView resultMav=mockMvc.perform(get("/myCars").session(mockSession)).andExpect(status().isOk()).andReturn().getModelAndView();
	assertViewName(resultMav, "welcome");
  }
  
  @Test
  public void testCarsNoDriverModel() throws Exception {
	this.mockSession.removeAttribute("user");
	this.mockUser.setUserType("Policeman");
	this.mockSession.setAttribute("user", this.mockUser);
	ModelAndView resultMav=mockMvc.perform(get("/myCars").session(mockSession)).andExpect(status().isOk()).andReturn().getModelAndView();
	assertAndReturnModelAttributeOfType(resultMav, "user", User.class);
  }
  
  @Test
  public void testCarsDriverView() throws Exception {
	ModelAndView resultMav=mockMvc.perform(get("/myCars").session(mockSession)).andExpect(status().isOk()).andReturn().getModelAndView();
	assertViewName(resultMav, "myCars");
  }
  
  @Test
  public void testCarsDriverModel() throws Exception {
	ModelAndView resultMav=mockMvc.perform(get("/myCars").session(mockSession)).andExpect(status().isOk()).andReturn().getModelAndView();
	assertAndReturnModelAttributeOfType(resultMav, "carToAdd", Car.class);
	assertAndReturnModelAttributeOfType(resultMav, "carToTrash", Car.class);
	assertAndReturnModelAttributeOfType(resultMav, "userCars", UserCars.class);
  }
  
  @Test
  public void testAddCarView() throws Exception {
	ModelAndView resultMav=mockMvc.perform(post("/addCar").session(mockSession).flashAttr("carToAdd", new Car())).andExpect(status().isOk()).andReturn().getModelAndView();
	assertViewName(resultMav, "myCars");
  }
  
  @Test
  public void testAddCarModel() throws Exception {
	ModelAndView resultMav=mockMvc.perform(post("/addCar").session(mockSession).flashAttr("carToAdd", new Car())).andExpect(status().isOk()).andReturn().getModelAndView();
	assertAndReturnModelAttributeOfType(resultMav, "carToAdd", Car.class);
	assertAndReturnModelAttributeOfType(resultMav, "carToTrash", Car.class);
	assertAndReturnModelAttributeOfType(resultMav, "userCars", UserCars.class);
  }
  
  @Test
  public void testDeleteCarView() throws Exception {
	ModelAndView resultMav=mockMvc.perform(post("/deleteCar").session(mockSession).flashAttr("carToTrash", new Car())).andExpect(status().isOk()).andReturn().getModelAndView();
	assertViewName(resultMav, "myCars");
  }
  
  @Test
  public void testDeleteCarModel() throws Exception {
	ModelAndView resultMav=mockMvc.perform(post("/deleteCar").session(mockSession).flashAttr("carToTrash", new Car())).andExpect(status().isOk()).andReturn().getModelAndView();
	assertAndReturnModelAttributeOfType(resultMav, "carToAdd", Car.class);
	assertAndReturnModelAttributeOfType(resultMav, "carToTrash", Car.class);
	assertAndReturnModelAttributeOfType(resultMav, "userCars", UserCars.class);
  }
}
