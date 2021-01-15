package Unicam.SPM2020_FMS;

import static org.junit.jupiter.api.Assertions.*;
import Unicam.SPM2020_FMS.model.User;


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

public class UserTest {
	
	public User driver;
	public User policeman;
	public User municipality;
	static String driverIdUser;
	static String driverFirstname;
	static String driverSurname;
	static String driverEmail;
	static String driverPassword;
	static String driverTaxCode;
	static String driverPhoneNumber;
	static String driverUserType = "driver";
	static String policemanIdUser;
	static String policemanFirstname;
	static String policemanSurname;
	static String policemanEmail;
	static String policemanPassword;
	static String policemanTaxCode;
	static String policemanPhoneNumber;
	static String policemanIdNumber;
	static String policemanUserType = "policeman";
	static String municipalityIdUser;
	static String municipalityFirstname;
	static String municipalitySurname;
	static String municipalityEmail;
	static String municipalityPassword;
	static String municipalityTaxCode;
	static String municipalityPhoneNumber;
	static String municipalityAuthNumber;
	static String municipalityUserType = "municipality";	
	static String projectPath;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
				
		//Reading data from a configuration file
		projectPath = System.getProperty("user.dir");
		try (InputStream input = new FileInputStream( projectPath+"/src/main/resources/config.properties")) {
		       Properties prop = new Properties();
		       prop.load(input);
			   
		       //Driver's data
		       driverIdUser = prop.getProperty("newIdUser");
		       driverFirstname = prop.getProperty("newUserFirstname");
		       driverSurname = prop.getProperty("newUserSurname");
		       driverEmail = prop.getProperty("newUserEmail");
		       driverPassword = prop.getProperty("newUserPassword");
		       driverTaxCode = prop.getProperty("newUserTaxCode");
		       driverPhoneNumber = prop.getProperty("newUserPhoneNumber");	
			   
			   //Policeman's data
			   policemanIdUser = prop.getProperty("newPolicemanIdUser");
			   policemanFirstname = prop.getProperty("newPolicemanFirstname");
			   policemanSurname = prop.getProperty("newPolicemanSurname");
			   policemanEmail = prop.getProperty("newPolicemanEmail");
			   policemanPassword = prop.getProperty("newPolicemanPassword");
			   policemanTaxCode = prop.getProperty("newPolicemanTaxCode");
			   policemanPhoneNumber = prop.getProperty("newPolicemanPhoneNumber");	
			   policemanIdNumber = prop.getProperty("newPolicemanIdNumber");
			   
			   //municipality's data
			   municipalityIdUser = prop.getProperty("newMunicipalityIdUser");
			   municipalityFirstname = prop.getProperty("newMunicipalityFirstname");
			   municipalitySurname = prop.getProperty("newMunicipalitySurname");
			   municipalityEmail = prop.getProperty("newMunicipalityEmail");
			   municipalityPassword = prop.getProperty("newMunicipalityPassword");
			   municipalityTaxCode = prop.getProperty("newMunicipalityTaxCode");
			   municipalityPhoneNumber = prop.getProperty("newMunicipalityPhoneNumber");	
			   municipalityAuthNumber = prop.getProperty("newMunicipalityAuthNumber");			   
			     
		} catch (IOException ex) {
		           ex.printStackTrace();
		}
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		driver = new User(Integer.parseInt(driverIdUser), driverFirstname, driverSurname, driverEmail, driverPassword, driverTaxCode, driverPhoneNumber, driverUserType, null, null);
		policeman = new User(Integer.parseInt(policemanIdUser), policemanFirstname, policemanSurname, policemanEmail, policemanPassword, policemanTaxCode, policemanPhoneNumber, policemanUserType, "policemanIdNumber", null);
		municipality = new User(Integer.parseInt(municipalityIdUser), municipalityFirstname, municipalitySurname, municipalityEmail, municipalityPassword, municipalityTaxCode, municipalityPhoneNumber, municipalityUserType, null, "municipalityAuthNumber");
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	@DisplayName(" Driver test")
	void driverTest() {
		assertEquals(driver.getClass(), User.class);
		assertEquals(driver.getIdUser(), Integer.parseInt(driverIdUser));
		assertEquals(driver.getSurname(), driverSurname);
		assertEquals(driver.getEmail(), driverEmail);
		assertEquals(driver.getPassword(), driverPassword);
		assertEquals(driver.getTaxCode(), driverTaxCode);
		assertEquals(driver.getPhoneNumber(), driverPhoneNumber);
		assertEquals(driver.getUserType(), driverUserType);
		assertNull(driver.getIdNumber());
		assertNull(driver.getAuthNumber());	
	}
	
	@Test
	@DisplayName("Policeman test")
	void policemanTest() {
		assertEquals(policeman.getClass(), User.class);
		assertEquals(policeman.getIdUser(), Integer.parseInt(policemanIdUser));
		assertEquals(policeman.getSurname(), policemanSurname);
		assertEquals(policeman.getEmail(), policemanEmail);
		assertEquals(policeman.getPassword(), policemanPassword);
		assertEquals(policeman.getTaxCode(), policemanTaxCode);
		assertEquals(policeman.getPhoneNumber(), policemanPhoneNumber);
		assertEquals(policeman.getUserType(), policemanUserType);
		assertEquals(policeman.getIdNumber(), "policemanIdNumber");
		assertNull(policeman.getAuthNumber());	
	}
	
	@Test
	@DisplayName("Municipality test")
	void municipalityTest() {
		assertEquals(municipality.getClass(), User.class);
		assertEquals(municipality.getIdUser(), Integer.parseInt(municipalityIdUser));
		assertEquals(municipality.getSurname(), municipalitySurname);
		assertEquals(municipality.getEmail(), municipalityEmail);
		assertEquals(municipality.getPassword(), municipalityPassword);
		assertEquals(municipality.getTaxCode(), municipalityTaxCode);
		assertEquals(municipality.getPhoneNumber(), municipalityPhoneNumber);
		assertEquals(municipality.getUserType(), municipalityUserType);
		assertNull(municipality.getIdNumber());
		assertEquals(municipality.getAuthNumber(), "municipalityAuthNumber");	
	}
}