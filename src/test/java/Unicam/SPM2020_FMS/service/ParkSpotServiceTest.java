package Unicam.SPM2020_FMS.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.jdbc.JdbcTestUtils;

import Unicam.SPM2020_FMS.model.ParkingSpace;
import Unicam.SPM2020_FMS.model.ParkingSpot;
import Unicam.SPM2020_FMS.model.Reservation;
import Unicam.SPM2020_FMS.model.SpotIllegallyOccupied;

@SpringJUnitConfig(locations = "classpath:/user-beans.xml")
public class ParkSpotServiceTest {
  
  @Autowired
  private ParkSpaceService parkService;
  
  @Autowired
  private ParkSpotService spotService;
  
  @Autowired
  JdbcTemplate jdbcTemplate;
  
  private ParkingSpace park;
  private List<ParkingSpot> spots;

  @BeforeEach
  public void createTestCase() {
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
    spots = new ArrayList<ParkingSpot>();
    spots.add(new ParkingSpot(1, park.getIdParkingSpace(), 0, 0, 0));
    spots.add(new ParkingSpot(2, park.getIdParkingSpace(), 0, 0, 1));
    spots.add(new ParkingSpot(3, park.getIdParkingSpace(), 0, 1, 0));
    spots.add(new ParkingSpot(4, park.getIdParkingSpace(), 1, 1, 1));
  }
  
  @AfterEach
  public void deleteTestCase() {
    parkService.deleteParkSpace(park.getIdParkingSpace());
  }
  
  @Test
  public void testGenerateSpotsOk() {
	int result=spotService.generateSpots(spots);
	Assert.assertEquals(4, result);
  }
  
  @Test
  public void testGenerateSpotsKo() {
	for (ParkingSpot spot : spots) {
		spot.setParkingSpace(park.getIdParkingSpace()+1);
	}
	int result=spotService.generateSpots(spots);
	Assert.assertEquals(0, result);
  }
  
  @Test
  public void testGetCovered() {
	spotService.generateSpots(spots);
	List<Integer> result = spotService.getCoveredSpotsNs(park.getIdParkingSpace());
	Integer[] expected = {2,4};
	Assert.assertArrayEquals(expected, result.toArray());
  }
  
  @Test
  public void testGetHandicap() {
	spotService.generateSpots(spots);	  
	List<Integer> result = spotService.getHandicapSpotsNs(park.getIdParkingSpace());
	Integer[] expected = {3,4};
	Assert.assertArrayEquals(expected, result.toArray());
  }
  
  @Test
  public void testGetAvailable() {
	spotService.generateSpots(spots);
	int result = spotService.getAvailable(park.getIdParkingSpace());
	int expected=JdbcTestUtils.countRowsInTableWhere(jdbcTemplate,"parkingspot","ParkingSpace='" +park.getIdParkingSpace()+"' and isOccupied=0");
    Assert.assertEquals(expected,result);
  }
  
  @Test
  public void testGetCoveredAvailable() {
	spotService.generateSpots(spots);
	int result = spotService.getCoveredAvailable(park.getIdParkingSpace());
	int expected=JdbcTestUtils.countRowsInTableWhere(jdbcTemplate,"parkingspot","ParkingSpace='" +park.getIdParkingSpace()+"' and isCovered=1 and isOccupied=0");
    Assert.assertEquals(expected,result);
  }
  
  @Test
  public void testGetHandicapAvailable() {
	spotService.generateSpots(spots);
	int result = spotService.getHandicapAvailable(park.getIdParkingSpace());
	int expected=JdbcTestUtils.countRowsInTableWhere(jdbcTemplate,"parkingspot","ParkingSpace='" +park.getIdParkingSpace()+"' and isRestricted=1 and isOccupied=0");
    Assert.assertEquals(expected,result);
  }
  
  @Test
  public void testGetFreeSpotNumberNowKo() {
	spotService.generateSpots(spots);
	int result=spotService.getFreeSpot(park.getIdParkingSpace(), true, true);
	Assert.assertEquals(0,result);
  }
  
  @Test
  public void testGetFreeSpotNumberNowOk1() {
	spotService.generateSpots(spots);
	int result=spotService.getFreeSpot(park.getIdParkingSpace(), false, false);
	Assert.assertEquals(1,result);
  }
  
  @Test
  public void testGetFreeSpotNumberNowOk2() {
	spotService.generateSpots(spots);
	int result=spotService.getFreeSpot(park.getIdParkingSpace(), true, false);
	Assert.assertEquals(2,result);
  }
  
  @Test
  public void testGetFreeSpotNumberNowOk3() {
	spotService.generateSpots(spots);
	int result=spotService.getFreeSpot(park.getIdParkingSpace(), false, true);
	Assert.assertEquals(3,result);
  }
  
  @Test
  public void testGetFreeSpotNumberLaterKo() {
	spotService.generateSpots(spots);
	Reservation res = new Reservation();
	res.setParkingSpaceId(park.getIdParkingSpace());
	res.setParkingStart("wrong format start");
	res.setParkingEnd("wrong format end");
	res.setAskedCovered(false);
	res.setAskedHandicap(false);
	int result=spotService.getFreeSpot(res);
	Assert.assertEquals(-1,result);
  }
  
  @Test
  public void testGetFreeSpotNumberLaterOk() {
	spotService.generateSpots(spots);
	Reservation res = new Reservation();
	res.setParkingSpaceId(park.getIdParkingSpace());
	res.setParkingStart("2021-02-24 10:00");
	res.setParkingEnd("2021-02-24 10:30");
	res.setAskedCovered(false);
	res.setAskedHandicap(false);
	int result=spotService.getFreeSpot(res);
	Assert.assertEquals(1,result);
  }
  
  @RepeatedTest(5)
  public void testIsBusy() {
	spotService.generateSpots(spots);
	ParkingSpot spot = spots.get((int)(Math.random()*spots.size()));
	boolean expected = (spot.getOccupied()==1);
	boolean result = spotService.isBusy(spot.getSpotNumber(), spot.getParkingSpace());
	Assert.assertEquals(expected,result);
  }
  
  @Test
  public void testUpdateSpotsPlus() {
	spotService.generateSpots(spots);
	spots.add(new ParkingSpot(5, park.getIdParkingSpace(), 0, 0, 0));
	spots.add(new ParkingSpot(6, park.getIdParkingSpace(), 0, 0, 0));
	spots.add(new ParkingSpot(7, park.getIdParkingSpace(), 0, 0, 0));
	int result = spotService.updateSpots(spots);
	int DBres = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate,"parkingspot","ParkingSpace='" +park.getIdParkingSpace()+"'");
	Assert.assertEquals(7,DBres);
	Assert.assertEquals(7,result);
  }
  
  @Test
  public void testUpdateSpotsMinus() {
	spotService.generateSpots(spots);
	spots.remove(3);
	spots.remove(2);
	int result = spotService.updateSpots(spots);
	int DBres = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate,"parkingspot","ParkingSpace='" +park.getIdParkingSpace()+"'");
	Assert.assertEquals(2,DBres);
	Assert.assertEquals(4,result);
  }
  
  @Test
  public void testUpdateSpotsKo() {
	spotService.generateSpots(spots);
	spots.add(new ParkingSpot(5, park.getIdParkingSpace(), 0, 0, 0));
	spots.add(new ParkingSpot(5, park.getIdParkingSpace(), 0, 0, 0));
	int result = spotService.updateSpots(spots);
	int DBres = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate,"parkingspot","ParkingSpace='" +park.getIdParkingSpace()+"'");
	Assert.assertEquals(5,DBres);
	Assert.assertEquals(-1,result);
  }
  
  @Test
  public void testGetIllegallyOccupied() {
	spotService.generateSpots(spots);
	List<SpotIllegallyOccupied> result = spotService.getIllegallyOccupied();
	result.removeIf(elem -> !(elem.getParkingSpaceName().equals(park.getName())));
	int IllegalSpot=result.get(0).getParkingSpot();
	Assert.assertEquals(1,result.size());
	Assert.assertEquals(4,IllegalSpot);
  }

}
