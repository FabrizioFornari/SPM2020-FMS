package Unicam.SPM2020_FMS;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

class SeleniumRegistrationTest {
	
	static String projectPath;
	static String pathToDriver;
	static WebDriver driver;
	static String URLbase;
	static String runningOS;
	//New Driver
	static String newUserFirstname;
	static String newUserSurname;
	static String newUserEmail;
	static String newUserPassword;
	static String newUserTaxCode;
	static String newUserPhoneNumber;
	//New Policeman
	static String newPolicemanFirstname;
	static String newPolicemanSurname;
	static String newPolicemanEmail;
	static String newPolicemanPassword;
	static String newPolicemanTaxCode;
	static String newPolicemanIdNumber;
	static String newPolicemanPhoneNumber;
	//New Municipality
	static String newMunicipalityFirstname;
	static String newMunicipalitySurname;
	static String newMunicipalityEmail;
	static String newMunicipalityPassword;
	static String newMunicipalityTaxCode;
	static String newMunicipalityAuthNumber;
	static String newMunicipalityPhoneNumber;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		//Checking running OS
		runningOS = (System.getProperty("os.name"));
		
		//Setting up properties
		projectPath = System.getProperty("user.dir");
		URLbase = "http://localhost:8080/SPM2020-FMS/";
				
		//Reading data from a configuration file
		try (InputStream input = new FileInputStream( projectPath+"/src/main/resources/config.properties")) {
	           Properties prop = new Properties();
	           prop.load(input);
	           
	           //Setting proper driver
	           if (runningOS.contains("Linux")) {
	            	pathToDriver = prop.getProperty("pathToLinuxDriver");
	            }
	            else if (runningOS.contains("Windows")) {
	            	pathToDriver = prop.getProperty("pathToWindowsDriver");
	            }

	           //Reading new Driver's data
	           newUserFirstname = prop.getProperty("newUserFirstname");
	           newUserSurname = prop.getProperty("newUserSurname");
	           newUserEmail = prop.getProperty("newUserEmail");
	           newUserPassword = prop.getProperty("newUserPassword");
	           newUserTaxCode = prop.getProperty("newUserTaxCode");
	           newUserPhoneNumber = prop.getProperty("newUserPhoneNumber");
	           
	           //Reading new Policeman's data
	           newPolicemanFirstname = prop.getProperty("newPolicemanFirstname");
	           newPolicemanSurname = prop.getProperty("newPolicemanSurname");
	           newPolicemanEmail = prop.getProperty("newPolicemanEmail");
	           newPolicemanPassword = prop.getProperty("newPolicemanPassword");
	           newPolicemanTaxCode = prop.getProperty("newPolicemanTaxCode");
	           newPolicemanIdNumber = prop.getProperty("newPolicemanIdNumber");
	           newPolicemanPhoneNumber = prop.getProperty("newPolicemanPhoneNumber");
	           
	           //Reading new Municipality's data
	           newMunicipalityFirstname = prop.getProperty("newMunicipalityFirstname");
	           newMunicipalitySurname = prop.getProperty("newMunicipalitySurname");
	           newMunicipalityEmail = prop.getProperty("newMunicipalityEmail");
	           newMunicipalityPassword = prop.getProperty("newMunicipalityPassword");
	           newMunicipalityTaxCode = prop.getProperty("newMunicipalityTaxCode");
	           newMunicipalityAuthNumber = prop.getProperty("newMunicipalityAuthNumber");
	           newMunicipalityPhoneNumber = prop.getProperty("newMunicipalityPhoneNumber");   
		} catch (IOException ex) {
	           ex.printStackTrace();
		}		
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	@Disabled
	void setUp() throws Exception {
		//Setting up WebDriver options
		System.setProperty("webdriver.chrome.driver", projectPath+pathToDriver);
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-dev-shm-usage");
		options.addArguments("--no-sandbox");
		options.addArguments("--start-maximized");
		
		//Remove comment prefix on the next line if you want to run test in headless mode
		//options.addArguments("--headless");
		
		driver = new ChromeDriver(options);
		
		//Connecting to the home page
				driver.get(URLbase);
				WebDriverWait wait = new WebDriverWait(driver, 10);
				Thread.sleep(1500);  //Just for showing purpose
				
		//Connecting to the registration page
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='register']"))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("firstname")));
	}

	@AfterEach
	void tearDown() throws Exception {
		Thread.sleep(5000);  //Just for showing purpose
		driver.close();
		driver.quit();
	}

	@Test
	// Waits are properly managed with the WebDriverWait class
	// Every sleep in the following code can be easily removed without compromising the test
	// Sleeps are there just for showing purpose
	@Disabled
	@Tag("AcceptanceTest")
	@DisplayName("Check whether a new Driver's registration succeeds")
	void checkDriverRegistration() throws InterruptedException {
		//Compiling form
		driver.findElement(By.id("firstname")).sendKeys(newUserFirstname);
		Thread.sleep(1500);  //Just for showing purpose
		driver.findElement(By.id("surname")).sendKeys(newUserSurname);
		Thread.sleep(1500);  //Just for showing purpose
		driver.findElement(By.id("email")).sendKeys(newUserEmail);
		Thread.sleep(1500);  //Just for showing purpose
		driver.findElement(By.id("password")).sendKeys(newUserPassword);
		Thread.sleep(1500);  //Just for showing purpose
		driver.findElement(By.id("repeatPassword")).sendKeys(newUserPassword);
		Thread.sleep(1500);  //Just for showing purpose
		Select selectElement = new Select(driver.findElement(By.id("userType")));
		selectElement.selectByVisibleText("Driver");
		Thread.sleep(1500);  //Just for showing purpose
		driver.findElement(By.id("taxCode")).sendKeys(newUserTaxCode);
		Thread.sleep(1500);  //Just for showing purpose
		driver.findElement(By.id("phone")).sendKeys(newUserPhoneNumber);
		Thread.sleep(1500);  //Just for showing purpose
		driver.findElement(By.id("register")).click();
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("jumbotron")));
		
		//Checking if welcome page has been reached
		assertTrue(driver.getPageSource().contains(newUserFirstname));
		
	}
	
	@Test
	@Disabled
	@DisplayName("Check whether a new Policeman's registration succeeds")
	void checkPolicemanRegistration() throws InterruptedException {
		//Compiling form
		driver.findElement(By.id("firstname")).sendKeys(newPolicemanFirstname);
		Thread.sleep(1500);  //Just for showing purpose
		driver.findElement(By.id("surname")).sendKeys(newPolicemanSurname);
		Thread.sleep(1500);  //Just for showing purpose
		driver.findElement(By.id("email")).sendKeys(newPolicemanEmail);
		Thread.sleep(1500);  //Just for showing purpose
		driver.findElement(By.id("password")).sendKeys(newPolicemanPassword);
		Thread.sleep(1500);  //Just for showing purpose
		driver.findElement(By.id("repeatPassword")).sendKeys(newPolicemanPassword);
		Thread.sleep(1500);  //Just for showing purpose
		Select selectElement = new Select(driver.findElement(By.id("userType")));
		selectElement.selectByVisibleText("Policeman");
		Thread.sleep(1500);  //Just for showing purpose
		driver.findElement(By.id("taxCode")).sendKeys(newPolicemanTaxCode);
		Thread.sleep(1500);  //Just for showing purpose
		driver.findElement(By.id("idNumber")).sendKeys(newPolicemanIdNumber);
		Thread.sleep(1500);  //Just for showing purpose
		driver.findElement(By.id("phone")).sendKeys(newPolicemanPhoneNumber);
		Thread.sleep(1500);  //Just for showing purpose
		driver.findElement(By.id("register")).click();
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("jumbotron")));
		
		//Checking if welcome page has been reached
		assertTrue(driver.getPageSource().contains(newPolicemanFirstname));
	}
	
	@Test
	@DisplayName("Check whether a new Municipality's registration succeeds")
	void checkMunicipalityRegistration() throws InterruptedException {
		//Compiling form
		driver.findElement(By.id("firstname")).sendKeys(newMunicipalityFirstname);
		Thread.sleep(1500);  //Just for showing purpose
		driver.findElement(By.id("surname")).sendKeys(newMunicipalitySurname);
		Thread.sleep(1500);  //Just for showing purpose
		driver.findElement(By.id("email")).sendKeys(newMunicipalityEmail);
		Thread.sleep(1500);  //Just for showing purpose
		driver.findElement(By.id("password")).sendKeys(newMunicipalityPassword);
		Thread.sleep(1500);  //Just for showing purpose
		driver.findElement(By.id("repeatPassword")).sendKeys(newMunicipalityPassword);
		Thread.sleep(1500);  //Just for showing purpose
		Select selectElement = new Select(driver.findElement(By.id("userType")));
		selectElement.selectByVisibleText("Municipality");
		Thread.sleep(1500);  //Just for showing purpose
		driver.findElement(By.id("taxCode")).sendKeys(newMunicipalityTaxCode);
		Thread.sleep(1500);  //Just for showing purpose
		driver.findElement(By.id("authNumber")).sendKeys(newMunicipalityAuthNumber);
		Thread.sleep(1500);  //Just for showing purpose
		driver.findElement(By.id("phone")).sendKeys(newMunicipalityPhoneNumber);
		Thread.sleep(1500);  //Just for showing purpose
		driver.findElement(By.id("register")).click();
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("jumbotron")));
		
		//Checking if welcome page has been reached
		assertTrue(driver.getPageSource().contains(newMunicipalityFirstname));		
	}
}
