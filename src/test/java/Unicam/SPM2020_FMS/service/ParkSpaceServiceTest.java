package Unicam.SPM2020_FMS.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.jdbc.JdbcTestUtils;

import Unicam.SPM2020_FMS.model.ParkingSpace;

@SpringJUnitConfig(locations = "classpath:/user-beans.xml")
public class ParkSpaceServiceTest {
  
  @Autowired
  private ParkSpaceService parkService;
  
  @Autowired
  JdbcTemplate jdbcTemplate;
  
  private ParkingSpace park;

  @BeforeEach
  public void addTestPark() {
	park = new ParkingSpace();
    park.setCity("Milano");
    park.setName("MilanoCentrale");
    park.setAddress("Stazione Centrale");
    park.setCoordinates("45.48707898817148, 9.20698256249191");
    park.setSpotsCapacity(100);
    park.setCoveredSpots(100);
    park.setHandicapSpots(20);
    park.setGuarded(true);
    park.setParkingFee((float) 1.50);
    park.setImageName("image.jpg");
    park.setIdParkingSpace(parkService.add(park));
  }
  
  @AfterEach
  public void deleteTestPark() {
    parkService.deleteParkSpace(park.getIdParkingSpace());
  }
  
  @Test
  public void testShowParkSpaces() {
	List<ParkingSpace> result = parkService.showParkSpaceList();
	int tableRows=JdbcTestUtils.countRowsInTable(jdbcTemplate,"parkingspace");
    Assert.assertEquals(tableRows,result.size());
  }
  
  @Test
  public void testAdditionOk() {
    Assert.assertTrue(park.getIdParkingSpace()>0);
  }
  
  @Test
  public void testDuplicateCordinates() {
	int result = parkService.add(park);
    Assert.assertEquals(-1,result);
  }
  
  @Test
  public void testEditImage() {
	park.setImageName("newImage.jpg");
	park.setName("MilanoCentraleNew");
	int result = parkService.edit(park);
    Assert.assertEquals(1,result);
  }
  
  @Test
  public void testEditNoImage() {
	park.setImageName(null);
	park.setName("MilanoCentraleNew");
	int result = parkService.edit(park);
	Assert.assertEquals(1,result);
  }
  
  @Test
  public void testEditNotFound() {
	park.setIdParkingSpace(park.getIdParkingSpace()+1);
	int result = parkService.edit(park);
	park.setIdParkingSpace(park.getIdParkingSpace()-1);
	Assert.assertEquals(0,result);
  }
  
  @Test
  public void testDeleteOk() {
	int result = parkService.deleteParkSpace(park.getIdParkingSpace());
	Assert.assertEquals(1,result);
  }
  
  @Test
  public void testAlreadyDeleted() {
	parkService.deleteParkSpace(park.getIdParkingSpace());
	int result = parkService.deleteParkSpace(park.getIdParkingSpace());
	Assert.assertEquals(0,result);
  }

}
