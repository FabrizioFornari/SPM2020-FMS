package Unicam.SPM2020_FMS.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import static org.springframework.test.web.ModelAndViewAssert.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import Unicam.SPM2020_FMS.model.Login;
import Unicam.SPM2020_FMS.model.ParkingSpace;
import Unicam.SPM2020_FMS.model.Reservation;
import Unicam.SPM2020_FMS.model.User;
import Unicam.SPM2020_FMS.model.UserCars;

@SpringJUnitWebConfig(locations = {"classpath:/user-beans.xml","file:src/main/webapp/WEB-INF/spring-mvc-servlet.xml"})
public class ParkSpaceListControllerTest {
	
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
  public void testParksBookingNoUserView() throws Exception {
	ModelAndView resultMav=mockMvc.perform(get("/ParkSpaces")).andExpect(status().isOk()).andReturn().getModelAndView();
	assertViewName(resultMav, "login");
  }

  @Test
  public void testParksBookingNoUserModel() throws Exception {
	ModelAndView resultMav=mockMvc.perform(get("/ParkSpaces")).andExpect(status().isOk()).andReturn().getModelAndView();
	assertAndReturnModelAttributeOfType(resultMav, "login", Login.class);
  }
  
  @Test
  public void testParksBookingNoDriverView() throws Exception {
	this.mockSession.removeAttribute("user");
	this.mockUser.setUserType("Policeman");
	this.mockSession.setAttribute("user", this.mockUser);
	ModelAndView resultMav=mockMvc.perform(get("/ParkSpaces").session(mockSession)).andExpect(status().isOk()).andReturn().getModelAndView();
	assertViewName(resultMav, "welcome");
  }
  
  @Test
  public void testParksBookingNoDriverModel() throws Exception {
	this.mockSession.removeAttribute("user");
	this.mockUser.setUserType("Policeman");
	this.mockSession.setAttribute("user", this.mockUser);
	ModelAndView resultMav=mockMvc.perform(get("/ParkSpaces").session(mockSession)).andExpect(status().isOk()).andReturn().getModelAndView();
	assertAndReturnModelAttributeOfType(resultMav, "user", User.class);
  }
  
  @Test
  public void testParksBookingDriverView() throws Exception {
	ModelAndView resultMav=mockMvc.perform(get("/ParkSpaces").session(mockSession)).andExpect(status().isOk()).andReturn().getModelAndView();
	assertViewName(resultMav, "ParkSpaces");
  }
  
  @Test
  public void testParksBookingDriverModel() throws Exception {
	ModelAndView resultMav=mockMvc.perform(get("/ParkSpaces").session(mockSession)).andExpect(status().isOk()).andReturn().getModelAndView();
	assertAndReturnModelAttributeOfType(resultMav, "parkSpaceList", List.class);
	assertAndReturnModelAttributeOfType(resultMav, "userCars", UserCars.class);
	assertAndReturnModelAttributeOfType(resultMav, "reservation", Reservation.class);
	assertAndReturnModelAttributeOfType(resultMav, "paymentsList", List.class);
  }
   
  @Test
  public void testParkNow() throws Exception {
	mockMvc.perform(post("/parkNow").session(mockSession).flashAttr("reservation", new Reservation())).andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith("text/plain"));
  }
  
  @Test
  public void testParkNowNoSession() throws Exception {
	mockMvc.perform(post("/parkNow")).andExpect(status().isOk()).andExpect(content().string("Error: reservation has not been possible"));
  }
  
  @Test
  public void testReserve() throws Exception {
	mockMvc.perform(post("/reserve").session(mockSession).flashAttr("reservation", new Reservation())).andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith("text/plain"));
  }
  
  @Test
  public void testReserveNoSession() throws Exception {
	mockMvc.perform(post("/reserve")).andExpect(status().isOk()).andExpect(content().string("Error: reservation has not been possible"));
  }
  
  @Test
  public void testGetMapFromName() throws Exception {
	mockMvc.perform(get("/getMapSrc").session(mockSession).param("filename", "")).andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith("text/plain"));
  }
  
  @Test
  public void testParksManagementNoUserView() throws Exception {
	ModelAndView resultMav=mockMvc.perform(get("/ParksManagement")).andExpect(status().isOk()).andReturn().getModelAndView();
	assertViewName(resultMav, "login");
  }
  
  @Test
  public void testParksManagementNoUserModel() throws Exception {
	ModelAndView resultMav=mockMvc.perform(get("/ParksManagement")).andExpect(status().isOk()).andReturn().getModelAndView();
	assertAndReturnModelAttributeOfType(resultMav, "login", Login.class);
  }  
  
  @Test
  public void testParksManagementNoMunicView() throws Exception {
	ModelAndView resultMav=mockMvc.perform(get("/ParksManagement").session(mockSession)).andExpect(status().isOk()).andReturn().getModelAndView();
	assertViewName(resultMav, "welcome");
  }
   
  @Test
  public void testParksManagementNoMunicModel() throws Exception {
	ModelAndView resultMav=mockMvc.perform(get("/ParksManagement").session(mockSession)).andExpect(status().isOk()).andReturn().getModelAndView();
	assertAndReturnModelAttributeOfType(resultMav, "user", User.class);
  }
  
  @Test
  public void testParksManagementMunicView() throws Exception {
	this.mockSession.removeAttribute("user");
	this.mockUser.setUserType("Municipality");
	this.mockSession.setAttribute("user", this.mockUser);
	ModelAndView resultMav=mockMvc.perform(get("/ParksManagement").session(mockSession)).andExpect(status().isOk()).andReturn().getModelAndView();
	assertViewName(resultMav, "ParksManagement");
  }
  
  @Test
  public void testParksManagementMunicModel() throws Exception {
	this.mockSession.removeAttribute("user");
	this.mockUser.setUserType("Municipality");
	this.mockSession.setAttribute("user", this.mockUser);
	ModelAndView resultMav=mockMvc.perform(get("/ParksManagement").session(mockSession)).andExpect(status().isOk()).andReturn().getModelAndView();
	assertAndReturnModelAttributeOfType(resultMav, "parkSpaceToEdit", ParkingSpace.class);
	assertAndReturnModelAttributeOfType(resultMav, "parkSpaceToDelete", ParkingSpace.class);
	assertAndReturnModelAttributeOfType(resultMav, "parkSpaceList", List.class);
  }
  
  @Test
  public void testEditPark() throws Exception {
	this.mockSession.removeAttribute("user");
	this.mockUser.setUserType("Municipality");
	this.mockSession.setAttribute("user", this.mockUser);
	mockMvc.perform(post("/ParksManagement").session(mockSession).flashAttr("parkSpaceToEdit", new ParkingSpace())).andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/ParksManagement"));
  }
  
  @Test
  public void testEditParkNoSession() throws Exception {
	mockMvc.perform(post("/ParksManagement")).andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/login"));
  }
  
  @Test
  public void testDeletePark() throws Exception {
	this.mockSession.removeAttribute("user");
	this.mockUser.setUserType("Municipality");
	this.mockSession.setAttribute("user", this.mockUser);
	mockMvc.perform(post("/DeleteParkSpace").session(mockSession).flashAttr("parkSpaceToDelete", new ParkingSpace())).andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/ParksManagement"));
  }

  @Test
  public void testDeleteParkNoSession() throws Exception {
	mockMvc.perform(post("/DeleteParkSpace")).andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/login"));
  }
 
}
