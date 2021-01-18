package Unicam.SPM2020_FMS.model;

import static org.junit.jupiter.api.Assertions.*;
import Unicam.SPM2020_FMS.model.Car;


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

public class CarTest {
	
	public Car car;
	static String licensePlateNumber;
	static String driver;
	static String model;
	static String projectPath;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
				
		//Reading data from a configuration file
		projectPath = System.getProperty("user.dir");
		try (InputStream input = new FileInputStream( projectPath+"/src/main/resources/config.properties")) {
		       Properties prop = new Properties();
		       prop.load(input);
			   
		       licensePlateNumber = prop.getProperty("licensePlate1");
		       driver = prop.getProperty("newIdUser");
		       model = prop.getProperty("model1");
			     
		} catch (IOException ex) {
		           ex.printStackTrace();
		}
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		car = new Car(licensePlateNumber, Integer.parseInt(driver), model);		
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	@DisplayName(" Car field test")
	void carFieldTest() {
		assertEquals(car.getClass(), Car.class);
		assertEquals(car.getLicensePlateNumber(), licensePlateNumber);
		assertEquals(car.getDriver(), Integer.parseInt(driver));
		assertEquals(car.getModel(), model);
	}
}
