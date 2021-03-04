package Unicam.SPM2020_FMS.model;

import static org.junit.jupiter.api.Assertions.*;

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

class ParkingSpaceTest {
	
	public ParkingSpace parking;
	static String idParkingSpace;
	static String city;
	static String name;
	static String address;
	static String coordinates;
	static String spotsCapacity;
	static String coveredSpots;
	static String wrongCoveredSpots;
	static String handicapSpots;
	static String wrongHandicapSpots;
	static String guarded;
	static String parkingFee;
	static String specCovered;
	static String specHandicap;
	static String imageName;
	static String projectPath;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
				
		//Reading data from a configuration file
		projectPath = System.getProperty("user.dir");
		try (InputStream input = new FileInputStream( projectPath+"/src/main/resources/config.properties")) {
		       Properties prop = new Properties();
		       prop.load(input);
			   
		       idParkingSpace = prop.getProperty("idParkingSpace");
		       city = prop.getProperty("city");
		       name = prop.getProperty("name");
		       address = prop.getProperty("address");
		       coordinates = prop.getProperty("coordinates");
		       spotsCapacity = prop.getProperty("capacity");
		       coveredSpots = prop.getProperty("totCoveredSpots");
		       wrongCoveredSpots = prop.getProperty("wrongTotCoveredSpots");
		       handicapSpots = prop.getProperty("totHandicapSpots");
		       wrongHandicapSpots = prop.getProperty("wrongTotHandicapSpots");
		       guarded = prop.getProperty("guarded");
		       parkingFee = prop.getProperty("parkingFee");
		       specCovered = prop.getProperty("coveredSpotsIds");
		       specHandicap = prop.getProperty("handicapSpotsIds");
		       imageName = null;
		       			     
		} catch (IOException ex) {
		           ex.printStackTrace();
		}
	}
	
	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	@Test
	@DisplayName("Parking space model test")
	void parkingSpaceModelTest() {
		parking = new ParkingSpace(Integer.parseInt(idParkingSpace), city, name, address, coordinates, Integer.parseInt(spotsCapacity),
				Integer.parseInt(coveredSpots), Integer.parseInt(handicapSpots), Boolean.parseBoolean(guarded), Float.parseFloat(parkingFee), 
				specCovered, specHandicap, imageName); 	
		
		assertEquals(parking.getClass(), ParkingSpace.class);
		assertEquals(parking.getIdParkingSpace(), Integer.parseInt(idParkingSpace));
		assertEquals(parking.getCity(), city);
		assertEquals(parking.getName(), name);
		assertEquals(parking.getAddress(), address);
		assertEquals(parking.getCoordinates(), coordinates);
		assertEquals(parking.getSpotsCapacity(), Integer.parseInt(spotsCapacity));
		assertEquals(parking.getCoveredSpots(), Integer.parseInt(coveredSpots));
		assertEquals(parking.getHandicapSpots(), Integer.parseInt(handicapSpots));
		assertEquals(parking.isGuarded(), Boolean.parseBoolean(guarded));
		assertEquals(parking.getParkingFee(), Float.parseFloat(parkingFee));
		assertEquals(parking.getSpecCovered(), specCovered);
		assertEquals(parking.getSpecHandicap(), specHandicap);
		assertEquals(parking.getImageName(), imageName);
	}

	@Test
	@DisplayName("Incoherent total number and specification of covered spots should throw an IllegalArgumentException")
	void coveredSpotsTest() {
		assertThrows(IllegalArgumentException.class,()-> 
		new ParkingSpace(Integer.parseInt(idParkingSpace), city, name, address, coordinates, Integer.parseInt(spotsCapacity),
				Integer.parseInt(wrongCoveredSpots), Integer.parseInt(handicapSpots), Boolean.parseBoolean(guarded), Float.parseFloat(parkingFee), 
				specCovered, specHandicap, imageName)); 	
	}

	@Test
	@DisplayName("Incoherent total number and specification of handicap spots should throw an IllegalArgumentException")
	void handicapSpotsTest() {
		assertThrows(IllegalArgumentException.class,()-> 
		new ParkingSpace(Integer.parseInt(idParkingSpace), city, name, address, coordinates, Integer.parseInt(spotsCapacity),
				Integer.parseInt(coveredSpots), Integer.parseInt(wrongHandicapSpots), Boolean.parseBoolean(guarded), Float.parseFloat(parkingFee), 
				specCovered, specHandicap, imageName)); 	
	}
	
	@Test
	@DisplayName("Covered spots number should match covered spots specification")
	void coveredSpotsShouldMatchCoveredSpec() {
		parking = new ParkingSpace(Integer.parseInt(idParkingSpace), city, name, address, coordinates, Integer.parseInt(spotsCapacity),
				10, Integer.parseInt(handicapSpots), Boolean.parseBoolean(guarded), Float.parseFloat(parkingFee), 
				"1-5,11-15", specHandicap, imageName); 	
		assertEquals(parking.getCoveredSpotsNumbers().count(), 10);
		
		parking = new ParkingSpace(Integer.parseInt(idParkingSpace), city, name, address, coordinates, Integer.parseInt(spotsCapacity),
				10, Integer.parseInt(handicapSpots), Boolean.parseBoolean(guarded), Float.parseFloat(parkingFee), 
				"1-10", specHandicap, imageName); 	
		assertEquals(parking.getCoveredSpotsNumbers().count(), 10);
		
		parking = new ParkingSpace(Integer.parseInt(idParkingSpace), city, name, address, coordinates, Integer.parseInt(spotsCapacity),
				10, Integer.parseInt(handicapSpots), Boolean.parseBoolean(guarded), Float.parseFloat(parkingFee), 
				"1,2,3,4,5,11,12,13,14,15", specHandicap, imageName); 	
		assertEquals(parking.getCoveredSpotsNumbers().count(), 10);
		
		parking = new ParkingSpace(Integer.parseInt(idParkingSpace), city, name, address, coordinates, Integer.parseInt(spotsCapacity),
				10, Integer.parseInt(handicapSpots), Boolean.parseBoolean(guarded), Float.parseFloat(parkingFee), 
				"1-8,14,20", specHandicap, imageName); 	
		assertEquals(parking.getCoveredSpotsNumbers().count(), 10);
	}
	
	@Test
	@DisplayName("Handicap spots number should match handicap spots specification")
	void handicapSpotsShouldMatchHandicapSpec() {
		parking = new ParkingSpace(Integer.parseInt(idParkingSpace), city, name, address, coordinates, Integer.parseInt(spotsCapacity),
				Integer.parseInt(coveredSpots), 10, Boolean.parseBoolean(guarded), Float.parseFloat(parkingFee), 
				specCovered, "21-30", imageName); 	
		assertEquals(parking.getHandicapSpotsNumbers().count(), 10);
		
		parking = new ParkingSpace(Integer.parseInt(idParkingSpace), city, name, address, coordinates, Integer.parseInt(spotsCapacity),
				Integer.parseInt(coveredSpots), 10, Boolean.parseBoolean(guarded), Float.parseFloat(parkingFee), 
				specCovered, "1-5,21-25", imageName); 	
		assertEquals(parking.getHandicapSpotsNumbers().count(), 10);
		
		parking = new ParkingSpace(Integer.parseInt(idParkingSpace), city, name, address, coordinates, Integer.parseInt(spotsCapacity),
				Integer.parseInt(coveredSpots), 10, Boolean.parseBoolean(guarded), Float.parseFloat(parkingFee), 
				specCovered, "1-5,7,19,23,34,73", imageName); 	
		assertEquals(parking.getHandicapSpotsNumbers().count(), 10);
		
		parking = new ParkingSpace(Integer.parseInt(idParkingSpace), city, name, address, coordinates, Integer.parseInt(spotsCapacity),
				Integer.parseInt(coveredSpots), 10, Boolean.parseBoolean(guarded), Float.parseFloat(parkingFee), 
				specCovered, "1,3,5,7,9,11,13,15,17,19", imageName); 	
		assertEquals(parking.getHandicapSpotsNumbers().count(), 10);
	}
		
}
