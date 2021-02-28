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
import Unicam.SPM2020_FMS.model.User;

@SpringJUnitWebConfig(locations = {"classpath:/user-beans.xml","file:src/main/webapp/WEB-INF/spring-mvc-servlet.xml"})
public class StatisticsControllerTest {
	
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
  public void testStatisticsNoUserView() throws Exception {
	ModelAndView resultMav=mockMvc.perform(get("/Statistics")).andExpect(status().isOk()).andReturn().getModelAndView();
	assertViewName(resultMav, "login");
  }

  @Test
  public void testStatisticsNoUserModel() throws Exception {
	ModelAndView resultMav=mockMvc.perform(get("/Statistics")).andExpect(status().isOk()).andReturn().getModelAndView();
	assertAndReturnModelAttributeOfType(resultMav, "login", Login.class);
  }
  
  @Test
  public void testStatisticsNoMunicView() throws Exception {
	this.mockSession.removeAttribute("user");
	this.mockUser.setUserType("Policeman");
	this.mockSession.setAttribute("user", this.mockUser);
	ModelAndView resultMav=mockMvc.perform(get("/Statistics").session(mockSession)).andExpect(status().isOk()).andReturn().getModelAndView();
	assertViewName(resultMav, "welcome");
  }
  
  @Test
  public void testStatisticsNoMunicModel() throws Exception {
	this.mockSession.removeAttribute("user");
	this.mockUser.setUserType("Policeman");
	this.mockSession.setAttribute("user", this.mockUser);
	ModelAndView resultMav=mockMvc.perform(get("/Statistics").session(mockSession)).andExpect(status().isOk()).andReturn().getModelAndView();
	assertAndReturnModelAttributeOfType(resultMav, "user", User.class);
  }
  
  @Test
  public void testStatisticsMunicView() throws Exception {
	ModelAndView resultMav=mockMvc.perform(get("/Statistics").session(mockSession)).andExpect(status().isOk()).andReturn().getModelAndView();
	assertViewName(resultMav, "statistics");
  }
  
  @Test
  public void testStatisticsMunicModel() throws Exception {
	ModelAndView resultMav=mockMvc.perform(get("/Statistics").session(mockSession)).andExpect(status().isOk()).andReturn().getModelAndView();
	assertAndReturnModelAttributeOfType(resultMav, "totalRevenue", Float.class);
	assertAndReturnModelAttributeOfType(resultMav, "totalRevenueFiltered", Float.class);
	assertAndReturnModelAttributeOfType(resultMav, "totalUsers", Integer.class);
	assertAndReturnModelAttributeOfType(resultMav, "revenueBySpace", List.class);
	assertAndReturnModelAttributeOfType(resultMav, "revenueBySpaceFiltered", List.class);
	assertAndReturnModelAttributeOfType(resultMav, "paymentTypeList", List.class);
  }

}
