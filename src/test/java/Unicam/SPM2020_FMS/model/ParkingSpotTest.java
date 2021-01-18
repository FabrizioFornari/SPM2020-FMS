package Unicam.SPM2020_FMS.model;

import static org.junit.jupiter.api.Assertions.*;
import Unicam.SPM2020_FMS.model.ParkingSpot;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ParkingSpotTest {
	
	public ParkingSpot spot;
	static String spotNumber;
	static String parkingSpace;
	static String occupied;
	static String isRestricted;
	static String isCovered;
	static String projectPath;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
				
		//Reading data from a configuration file
		projectPath = System.getProperty("user.dir");
		try (InputStream input = new FileInputStream( projectPath+"/src/main/resources/config.properties")) {
		       Properties prop = new Properties();
		       prop.load(input);
			   
		       spotNumber = prop.getProperty("spotNumber");
		       parkingSpace = prop.getProperty("parkingSpace");
		       occupied = prop.getProperty("occupied");
		       isRestricted = prop.getProperty("isRestricted");
		       isCovered = prop.getProperty("isCovered");
		       
			     
		} catch (IOException ex) {
		           ex.printStackTrace();
		}
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		spot = new ParkingSpot(Integer.parseInt(spotNumber), Integer.parseInt(parkingSpace), Integer.parseInt(occupied), Integer.parseInt(isRestricted), Integer.parseInt(isCovered));;		
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	@DisplayName(" Parking Spot model test")
	void parkingSpotModelTest() {
		assertEquals(spot.getClass(), ParkingSpot.class);
		assertEquals(spot.getSpotNumber(), Integer.parseInt(spotNumber));
		assertEquals(spot.getParkingSpace(), Integer.parseInt(parkingSpace));
		assertEquals(spot.getOccupied(), Integer.parseInt(occupied));
		assertEquals(spot.getIsRestricted(), Integer.parseInt(isRestricted));
		assertEquals(spot.getIsCovered(), Integer.parseInt(isCovered));
	}
}