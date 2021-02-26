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

import Unicam.SPM2020_FMS.model.Login;
import Unicam.SPM2020_FMS.model.ParkingSpace;
import Unicam.SPM2020_FMS.model.User;

@SpringJUnitWebConfig(locations = {"classpath:/user-beans.xml","file:src/main/webapp/WEB-INF/spring-mvc-servlet.xml"})
public class NewParkSpaceControllerTest {
	
  MockMvc mockMvc;
  MockHttpSession mockSession;
  User mockUser;
  
  @BeforeEach
  void setup(WebApplicationContext wac) {
      this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
      this.mockSession = new MockHttpSession();
      this.mockUser = new User();
      this.mockUser.setUserType("Municipality");
      this.mockSession.setAttribute("user", mockUser);
  }
  
  @Test
  public void testNewParkNoUserView() throws Exception {
	ModelAndView resultMav=mockMvc.perform(get("/newParkArea")).andExpect(status().isOk()).andReturn().getModelAndView();
	assertViewName(resultMav, "login");
  }

  @Test
  public void testNewParkNoUserModel() throws Exception {
	ModelAndView resultMav=mockMvc.perform(get("/newParkArea")).andExpect(status().isOk()).andReturn().getModelAndView();
	assertAndReturnModelAttributeOfType(resultMav, "login", Login.class);
  }
  
  @Test
  public void testNewParkNoMunicView() throws Exception {
	this.mockSession.removeAttribute("user");
	this.mockUser.setUserType("Policeman");
	this.mockSession.setAttribute("user", this.mockUser);
	ModelAndView resultMav=mockMvc.perform(get("/newParkArea").session(mockSession)).andExpect(status().isOk()).andReturn().getModelAndView();
	assertViewName(resultMav, "welcome");
  }
  
  @Test
  public void testNewParkNoMunicModel() throws Exception {
	this.mockSession.removeAttribute("user");
	this.mockUser.setUserType("Policeman");
	this.mockSession.setAttribute("user", this.mockUser);
	ModelAndView resultMav=mockMvc.perform(get("/newParkArea").session(mockSession)).andExpect(status().isOk()).andReturn().getModelAndView();
	assertAndReturnModelAttributeOfType(resultMav, "user", User.class);
  }
  
  @Test
  public void testNewParkMunicipalView() throws Exception {
	ModelAndView resultMav=mockMvc.perform(get("/newParkArea").session(mockSession)).andExpect(status().isOk()).andReturn().getModelAndView();
	assertViewName(resultMav, "newParkArea");
  }
  
  @Test
  public void testNewParkMunicipalModel() throws Exception {
	ModelAndView resultMav=mockMvc.perform(get("/newParkArea").session(mockSession)).andExpect(status().isOk()).andReturn().getModelAndView();
	assertAndReturnModelAttributeOfType(resultMav, "parkSpace", ParkingSpace.class);
  }
  
  @Test
  public void testAddParkRedirect() throws Exception {
	mockMvc.perform(post("/addParkSpace").session(mockSession).flashAttr("newParkSpace", new ParkingSpace())).andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/newParkArea"));
  }

}
