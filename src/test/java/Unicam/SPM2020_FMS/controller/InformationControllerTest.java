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
import Unicam.SPM2020_FMS.model.User;

@SpringJUnitWebConfig(locations = {"classpath:/user-beans.xml","file:src/main/webapp/WEB-INF/spring-mvc-servlet.xml"})
public class InformationControllerTest {
	
  MockMvc mockMvc;
  MockHttpSession mockSession;
  
  @BeforeEach
  void setup(WebApplicationContext wac) {
      this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
      this.mockSession = new MockHttpSession();
      this.mockSession.setAttribute("user", new User());
  }
  
  @Test
  public void testProfileNoUserView() throws Exception {
	ModelAndView resultMav=mockMvc.perform(get("/profile")).andExpect(status().isOk()).andReturn().getModelAndView();
	assertViewName(resultMav, "login");
  }

  @Test
  public void testProfileNoUserModel() throws Exception {
	ModelAndView resultMav=mockMvc.perform(get("/profile")).andExpect(status().isOk()).andReturn().getModelAndView();
	assertAndReturnModelAttributeOfType(resultMav, "login", Login.class);
  }
  
  @Test
  public void testProfileUserView() throws Exception {
	ModelAndView resultMav=mockMvc.perform(get("/profile").session(mockSession)).andExpect(status().isOk()).andReturn().getModelAndView();
	assertViewName(resultMav, "profilePage");
  }
  
  @Test
  public void testProfileUserModel() throws Exception {
	ModelAndView resultMav=mockMvc.perform(get("/profile").session(mockSession)).andExpect(status().isOk()).andReturn().getModelAndView();
	assertAndReturnModelAttributeOfType(resultMav, "user", User.class);
  }
  
  @Test
  public void testupdateUserProcessRedirect() throws Exception {
	mockMvc.perform(post("/updateUserProcess").session(mockSession).flashAttr("user", new User())).andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/profile"));
  }
  
}
