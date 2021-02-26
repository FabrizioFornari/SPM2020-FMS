package Unicam.SPM2020_FMS.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.jdbc.JdbcTestUtils;

import Unicam.SPM2020_FMS.model.Car;
import Unicam.SPM2020_FMS.model.Login;
import Unicam.SPM2020_FMS.model.ParkingSpace;
import Unicam.SPM2020_FMS.model.ParkingSpot;
import Unicam.SPM2020_FMS.model.Reservation;
import Unicam.SPM2020_FMS.model.User;

@SpringJUnitConfig(locations = "classpath:/user-beans.xml")
public class ReservationServiceTest {

  @Autowired
  private UserService userService;
  
  @Autowired
  private CarService carService;
  
  @Autowired
  private ParkSpaceService parkService;
  
  @Autowired
  private ParkSpotService spotService;
  
  @Autowired
  private ReservationService reservationService;
  
  @Autowired
  JdbcTemplate jdbcTemplate;
  
  private User user;
  private ParkingSpace park;
  private Reservation res;
  private Car car;
  
  @BeforeEach
  public void setupTestCase() {
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
	    user.setUserType("Driver");
	    int id=userService.register(user);
	    user.setIdUser(id);
	}
    car = new Car();
    car.setDriver(this.user.getIdUser());
    car.setLicensePlateNumber("MI551LE");
    carService.register(car);
	park = new ParkingSpace();
    park.setCity("Milano");
    park.setName("MilanoCentrale");
    park.setAddress("Stazione Centrale");
    park.setCoordinates("45.48707898817148, 9.20698256249191");
    park.setSpotsCapacity(4);
    park.setCoveredSpots(2);
    park.setHandicapSpots(2);
    park.setGuarded(true);
    park.setParkingFee((float) 1.50);
    park.setImageName("image.jpg");
    park.setIdParkingSpace(parkService.add(park));
    List<ParkingSpot> spots = new ArrayList<ParkingSpot>();
    spots.add(new ParkingSpot(1, park.getIdParkingSpace(), 0, 0, 0));
    spots.add(new ParkingSpot(2, park.getIdParkingSpace(), 0, 0, 0));
    spotService.generateSpots(spots);
    res = new Reservation();
    res.setDriver(user.getIdUser());
    res.setLicensePlateNumber("MI551LE");
    res.setParkingSpot(1);
	res.setParkingSpaceId(park.getIdParkingSpace());
	res.setParkingStart(null);
	res.setParkingEnd(null);
	res.setAskedCovered(false);
	res.setAskedHandicap(false);
	res.setId(reservationService.addReservation(res));
  }
  
  @AfterEach
  public void deleteTestCase() {
    parkService.deleteParkSpace(park.getIdParkingSpace());
    carService.deleteCar(car);
  }
  
  @Test
  public void testShowReservationToCheck() {
	List<Reservation> result = reservationService.showReservationsToCheck();
	int tableRows=JdbcTestUtils.countRowsInTableWhere(jdbcTemplate,"reservation","Parking_start <= NOW() and Parking_end is null");
    Assert.assertEquals(tableRows,result.size());
  }

  @Test
  public void testAddReservationImmediateOk() {
    Assert.assertTrue(res.getId()>0);
  }
  
  @Test
  public void testDuplicateReservation() {
    int result = reservationService.addReservation(res);
    Assert.assertEquals(-2,result);
  }
  
  @Test
  public void testAddReservationKoDate() {
    Reservation res = new Reservation();
    res.setDriver(user.getIdUser());
    res.setLicensePlateNumber("MI551LE");
    res.setParkingSpot(1);
	res.setParkingSpaceId(park.getIdParkingSpace());
	res.setParkingStart("wrong format start");
	res.setParkingEnd("wrong format end");
    int result = reservationService.addReservation(res);
    Assert.assertEquals(-1,result);
  }
  
  @Test
  public void testAddReservationLaterOk() {
    Reservation res = new Reservation();
    res.setDriver(user.getIdUser());
    res.setLicensePlateNumber("MI551LE");
    res.setParkingSpot(2);
	res.setParkingSpaceId(park.getIdParkingSpace());
	res.setParkingStart("2021-02-24 10:00");
	res.setParkingEnd("2021-02-24 10:30");
    int result = reservationService.addReservation(res);
    Assert.assertTrue(result>0);
  }
  
  @Test
  public void testDeleteReservationOk() {
    int result = reservationService.deleteReservation(res.getId());
    Assert.assertEquals(1, result);
  }
  
  @Test
  public void testDeleteReservationNotExisting() {
    int result = reservationService.deleteReservation(res.getId()+1);
    Assert.assertEquals(0, result);
  }
  
  @Test
  public void testChangeSpotOk() {
    int result = reservationService.changeSpot(res.getId(),2);
    Assert.assertEquals(1, result);
  }
  
  @Test
  public void testChangeSpotKo() {
    int result = reservationService.changeSpot(res.getId(),3);
    Assert.assertEquals(-1, result);
  }
  
  @Test
  public void testChangeSpotNotExisting() {
    int result = reservationService.changeSpot(res.getId()+1,2);
    Assert.assertEquals(0, result);
  }
  
  @Test
  public void testCloseReservationOk() {
    int result = reservationService.closeReservation(res.getId());
    Assert.assertEquals(1, result);
  }
  
  @Test
  public void testCloseReservationNotExisting() {
    int result = reservationService.closeReservation(res.getId()+1);
    Assert.assertEquals(0, result);
  }
  
}
