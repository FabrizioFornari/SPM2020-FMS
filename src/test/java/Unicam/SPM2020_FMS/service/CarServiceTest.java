package Unicam.SPM2020_FMS.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import Unicam.SPM2020_FMS.model.Car;
import Unicam.SPM2020_FMS.model.Login;
import Unicam.SPM2020_FMS.model.User;

//@ExtendWith(SpringExtension.class)
//@ContextConfiguration({"classpath:/user-beans.xml"})

@SpringJUnitConfig(locations = "classpath:/user-beans.xml")
@TestMethodOrder(OrderAnnotation.class)
public class CarServiceTest {

  @Autowired
  private UserService userService;
  
  @Autowired
  private CarService carService;
  
  private User user;
  
  @BeforeEach
  public void checkExistingUser() {
	Login login = new Login();
	login.setUsername("guido.veloce@hotmail.it");
	login.setPassword("password");
	user = userService.validateUser(login);
	if(user==null) {
	    user = new User();
	    user.setName("Guido");
	    user.setSurname("Veloce");
	    user.setPassword("password");
	    user.setEmail("guido.veloce@hotmail.it");
	    user.setTaxCode("NNNSSS00M12A123B");
	    user.setPhoneNumber("111111");
	    user.setUserType("driver");
	    int id=userService.register(user);
	    user.setIdUser(id);
	}
  }

  @Test
  @Order(1)
  public void testCorrectRegister() {
    Car car = new Car();
    car.setDriver(this.user.getIdUser());
    car.setLicensePlateNumber("MI551LE");
    car.setModel("Fiat Centoventisette");
    int result = carService.register(car);
    Assert.assertEquals(1,result);
  }
  
  @Test
  @Order(2)
  public void testDuplicateRegister() {
    Car car = new Car();
    car.setDriver(user.getIdUser());
    car.setLicensePlateNumber("MI551LE");
    car.setModel("Fiat Ritmo");
    int result = carService.register(car);
    Assert.assertEquals(0,result);
  }
  
  @Test
  @Order(3)
  public void testDeleteExistingCar() {
	Car car = new Car();
	car.setDriver(user.getIdUser());
	car.setLicensePlateNumber("MI551LE");
	int result = carService.deleteCar(car);
    Assert.assertEquals(1,result);
  }
  
  @Test
  @Order(4)
  public void testDeleteNonExistingCar() {
	Car car = new Car();
	car.setDriver(user.getIdUser());
	car.setLicensePlateNumber("MI551LE");
	int result = carService.deleteCar(car);
    Assert.assertEquals(0,result);
  }

}
