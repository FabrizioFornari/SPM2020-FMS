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
public class WelcomeControllerTest {
	
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
  public void testWelcomeNoUserView() throws Exception {
	ModelAndView resultMav=mockMvc.perform(get("/")).andExpect(status().isOk()).andReturn().getModelAndView();
	assertViewName(resultMav, "login");
  }

  @Test
  public void testWelcomeNoUserModel() throws Exception {
	ModelAndView resultMav=mockMvc.perform(get("/")).andExpect(status().isOk()).andReturn().getModelAndView();
	assertAndReturnModelAttributeOfType(resultMav, "login", Login.class);
  }
  
  @Test
  public void testWelcomeUserView() throws Exception {
	ModelAndView resultMav=mockMvc.perform(get("/").session(mockSession)).andExpect(status().isOk()).andReturn().getModelAndView();
	assertViewName(resultMav, "welcome");
  }
  
  @Test
  public void testWelcomeUserModel() throws Exception {
	ModelAndView resultMav=mockMvc.perform(get("/").session(mockSession)).andExpect(status().isOk()).andReturn().getModelAndView();
	assertAndReturnModelAttributeOfType(resultMav, "user", User.class);
  }
  
}
