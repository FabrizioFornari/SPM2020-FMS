package Unicam.SPM2020_FMS.service;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import Unicam.SPM2020_FMS.model.Login;
import Unicam.SPM2020_FMS.model.User;

//@ExtendWith(SpringExtension.class)
//@ContextConfiguration({"classpath:/user-beans.xml"})

@SpringJUnitConfig(locations = "classpath:/user-beans.xml")
@TestMethodOrder(OrderAnnotation.class)
public class UserServiceTest {

  @Autowired
  private UserService userService;

  @Test
  @Order(1)
  public void testRegister() {
    User user = new User();
    user.setName("Sebastian");
    user.setSurname("Vettel");
    user.setPassword("password");
    user.setEmail("vettel@ferrari.com");
    user.setTaxCode("SSSNNN00M12A123B");
    user.setPhoneNumber("111111");
    user.setUserType("driver");
    int result = userService.register(user);
    Assert.assertTrue(result>0);
  }
  
  @Test
  @Order(2)
  public void testDuplicateMail() {
    User user = new User();
    user.setName("Charles");
    user.setSurname("Leclerc");
    user.setPassword("password");
    user.setEmail("vettel@ferrari.com");
    user.setTaxCode("CCCLLL01M12A123B");
    user.setPhoneNumber("222222");
    user.setUserType("driver");
    int result = userService.register(user);
    Assert.assertEquals(-1,result);
  }
  
  @Test
  @Order(3)
  public void testDuplicateTaxcode() {
    User user = new User();
    user.setName("Charles");
    user.setSurname("Leclerc");
    user.setPassword("password");
    user.setEmail("leclerc@ferrari.com");
    user.setTaxCode("SSSNNN00M12A123B");
    user.setPhoneNumber("222222");
    user.setUserType("driver");
    int result = userService.register(user);
    Assert.assertEquals(-2,result);
  }

  @Test
  @Order(4)
  public void testValidateCorrectUser() {
    Login login = new Login();
    login.setUsername("vettel@ferrari.com");
    login.setPassword("password");
    User user = userService.validateUser(login);
    Assert.assertEquals("Sebastian", user.getName());
  }
  
  @Test
  public void testValidateWrongUser() {
    Login login = new Login();
    login.setUsername("hamilton@ferrari.com");
    login.setPassword("password");
    Assert.assertNull(userService.validateUser(login));
  }
  
  @Test
  @Order(5)
  public void testUpdate() {
    Login login = new Login();
    login.setUsername("vettel@ferrari.com");
    login.setPassword("password");
    User user = userService.validateUser(login);
    user.setName("Carlos");
    user.setSurname("Sainz");
    user.setEmail("sainz@ferrari.com");
    int result = userService.update(user);
    Assert.assertEquals(result, 1);
  }
  
  @Test
  @Order(6)
  public void testDelete() {
    Login login = new Login();
    login.setUsername("sainz@ferrari.com");
    login.setPassword("password");
    User user = userService.validateUser(login);
    int result = userService.delete(user);
    Assert.assertEquals(result, 1);
  }

}
