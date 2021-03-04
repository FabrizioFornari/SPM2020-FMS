package Unicam.SPM2020_FMS.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.ModelAndViewAssert.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import Unicam.SPM2020_FMS.model.User;
import Unicam.SPM2020_FMS.service.UserService;

@SpringJUnitWebConfig(locations = {"classpath:/user-beans.xml","file:src/main/webapp/WEB-INF/spring-mvc-servlet.xml"})
public class RegistrationControllerTest {
	
  @Autowired
  private UserService userService;
	
  MockMvc mockMvc;
  MockHttpSession mockSession;
  
  @BeforeEach
  void setup(WebApplicationContext wac) {
      this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
      this.mockSession = new MockHttpSession();
      this.mockSession.setAttribute("user", new User());
  }
  
  @Test
  public void testRegisterNoUserView() throws Exception {
	ModelAndView resultMav=mockMvc.perform(get("/register")).andExpect(status().isOk()).andReturn().getModelAndView();
	assertViewName(resultMav, "register");
  }

  @Test
  public void testRegisterNoUserModel() throws Exception {
	ModelAndView resultMav=mockMvc.perform(get("/register")).andExpect(status().isOk()).andReturn().getModelAndView();
	assertAndReturnModelAttributeOfType(resultMav, "user", User.class);
  }
  
  @Test
  public void testRegisterUserView() throws Exception {
	ModelAndView resultMav=mockMvc.perform(get("/register").session(mockSession)).andExpect(status().isOk()).andReturn().getModelAndView();
	assertViewName(resultMav, "welcome");
  }
  
  @Test
  public void testRegisterUserModel() throws Exception {
	ModelAndView resultMav=mockMvc.perform(get("/register").session(mockSession)).andExpect(status().isOk()).andReturn().getModelAndView();
	assertAndReturnModelAttributeOfType(resultMav, "user", User.class);
  }
  
  @Test
  public void testRegisterProcessRedirectBack() throws Exception {
	mockMvc.perform(post("/registerProcess").flashAttr("user", new User())).andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/register"));
  }
  
  @Test
  public void testLoginProcessRedirectHome() throws Exception {
	User mockUser = new User();
	mockUser.setName("Signup");
	mockUser.setSurname("Test");
	mockUser.setPassword("password");
	mockUser.setEmail("signup@test.com");
	mockUser.setTaxCode("SSSTTT00M12A123B");
	mockUser.setPhoneNumber("111111");
	mockUser.setUserType("Driver");
	mockMvc.perform(post("/registerProcess").flashAttr("user", mockUser)).andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/"));
	userService.delete(mockUser);
  }
  
}
