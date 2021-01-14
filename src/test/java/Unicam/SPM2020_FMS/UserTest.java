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
	
	public User user;
	static String idUser;
	static String firstname;
	static String surname;
	static String email;
	static String password;
	static String taxCode;
	static String phoneNumber;
	static String userType;
	static String projectPath;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
				
		//Reading data from a configuration file
		projectPath = System.getProperty("user.dir");
		try (InputStream input = new FileInputStream( projectPath+"/src/main/resources/config.properties")) {
		       Properties prop = new Properties();
		       prop.load(input);
			   
			   idUser = prop.getProperty("newIdUser");
			   firstname = prop.getProperty("newUserFirstname");
			   surname = prop.getProperty("newUserSurname");
			   email = prop.getProperty("newUserEmail");
			   password = prop.getProperty("newUserPassword");
			   taxCode = prop.getProperty("newUserTaxCode");
			   phoneNumber = prop.getProperty("newUserPhoneNumber");	
			   userType = prop.getProperty("newUserType");
  
		} catch (IOException ex) {
		           ex.printStackTrace();
		}
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		user = new User(Integer.parseInt(idUser), firstname, surname, email, password, taxCode, phoneNumber, userType, "", "");
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	@DisplayName("User model test")
	void UserModelTest() {
		assertEquals(user.getClass(), User.class);
		assertEquals(user.getIdUser(), Integer.parseInt(idUser));
		assertEquals(user.getSurname(), surname);
		assertEquals(user.getEmail(), email);
		assertEquals(user.getPassword(), password);
		assertEquals(user.getTaxCode(), taxCode);
		assertEquals(user.getPhoneNumber(), phoneNumber);
		assertEquals(user.getUserType(), userType);
		assertEquals(user.getIdNumber(), "");
		assertEquals(user.getAuthNumber(), "");	
	}
}
