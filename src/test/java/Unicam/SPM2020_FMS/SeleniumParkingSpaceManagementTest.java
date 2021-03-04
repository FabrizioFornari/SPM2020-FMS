package Unicam.SPM2020_FMS;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Assume;
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
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

class SeleniumParkingSpaceManagementTest {
	
	static String projectPath;
	static String pathToDriver;
	static WebDriver driver;
	static String runningOS;
	static String URLbase;
	static String municipality;
	static String municipalityPassword;
	static String city;
	static String name;
	static String address;
	static String coordinates;
	static String capacity;
	static String totCoveredSpots;
	static String totHandicapSpots;
	static String coveredSpotsIds;
	static String handicapSpotsIds;
	static String parkingFee;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		//Checking running OS
		runningOS = (System.getProperty("os.name"));
		
		//Setting up system properties
		projectPath = System.getProperty("user.dir");
		
		//Reading data from a configuration file
		try (InputStream input = new FileInputStream( projectPath+"/src/main/resources/config.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            
            if (runningOS.contains("Linux")) {
            	URLbase = "http://localhost:8080/SPM2020-FMS/";
            	pathToDriver = prop.getProperty("pathToLinuxDriver");
            }
            else if (runningOS.contains("Windows")) {
            	URLbase = "http://localhost:80/SPM2020-FMS/";
            	pathToDriver = prop.getProperty("pathToWindowsDriver");
            }
            		    
		    municipality = prop.getProperty("municipality");
		    municipalityPassword = prop.getProperty("rightPassword");
		    city = prop.getProperty("city");
			name = prop.getProperty("name");
			address = prop.getProperty("address");
			coordinates = prop.getProperty("coordinates");
			capacity = prop.getProperty("capacity");
			totCoveredSpots = prop.getProperty("totCoveredSpots");
			totHandicapSpots = prop.getProperty("totHandicapSpots");
			coveredSpotsIds = prop.getProperty("coveredSpotsIds");
			handicapSpotsIds = prop.getProperty("handicapSpotsIds");
			parkingFee = prop.getProperty("parkingFee");
			
		} catch (IOException ex) {
            ex.printStackTrace();
		}	    
		
		//Setting up WebDriver options
		System.setProperty("webdriver.chrome.driver", projectPath+pathToDriver);
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-dev-shm-usage");
		options.addArguments("--no-sandbox");
		
		options.addArguments("--window-size=1920,1080");
		options.addArguments("--disable-gpu");
		options.addArguments("--disable-extensions");
		//options.setExperimentalOption("useAutomationExtension", false);
		//options.addArguments("--proxy-server='direct://'");
		//options.addArguments("--proxy-bypass-list=*");
		options.addArguments("--start-maximized");
				
		//Remove or add comment prefix on the next line if you want to run test in headless mode or not
		//options.addArguments("--headless");
		
				
		driver = new ChromeDriver(options);
		
		//Logging in
		driver.get(URLbase+"login");
		WebDriverWait wait = new WebDriverWait(driver, 10);
		Assert.assertEquals("Login", driver.getTitle());
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
		element.sendKeys(municipality);
		//Thread.sleep(1500);  //Just for showing purpose
		driver.findElement(By.id("password")).sendKeys(municipalityPassword);
		//Thread.sleep(1500);  //Just for showing purpose
		driver.findElement(By.id("login")).click();
		//Thread.sleep(1500);  //Just for showing purpose
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("jumbotron")));
		
		//Checking whether login has succeeded
		Assume.assumeTrue(driver.getPageSource().contains("Hello"));
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		//Thread.sleep(3000);  //Just for showing purpose
		driver.close();
		driver.quit();
	}

	@BeforeEach
	void setUp() throws Exception {	
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	//@Disabled
	// Waits are properly managed with the WebDriverWait class
	// Every sleep in the following code can be easily removed without compromising the test
	// Sleeps are there just for showing purpose
	@Tag("AcceptanceTest")
	@DisplayName("Check whether adding a new parking space succeeds")
	void AddANewParkingSpace() throws InterruptedException {
		
		WebDriverWait wait = new WebDriverWait(driver, 10);
		
		//Checking whether the parking space has already been inserted into the DB
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='ParksManagement']"))).click();
		if (!driver.getPageSource().contains(name)) {
			// New parking space: proceed to insert all the data required		
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='newParkArea']"))).click();
			driver.findElement(By.id("city")).sendKeys(city);
			//Thread.sleep(1500);  //Just for showing purpose
			driver.findElement(By.id("name")).sendKeys(name);
			//Thread.sleep(1500);  //Just for showing purpose
			driver.findElement(By.id("address")).sendKeys(address);
			//Thread.sleep(1500);  //Just for showing purpose
			driver.findElement(By.id("coordinates")).sendKeys(coordinates);
			//Thread.sleep(1500);  //Just for showing purpose
			driver.findElement(By.id("spotsCapacity")).sendKeys(capacity);
			//Thread.sleep(1500);  //Just for showing purpose
			driver.findElement(By.id("coveredSpots")).sendKeys(totCoveredSpots);
			//Thread.sleep(1500);  //Just for showing purpose
			driver.findElement(By.id("handicapSpots")).sendKeys(totHandicapSpots);
			//Thread.sleep(1500);  //Just for showing purpose
			driver.findElement(By.id("specCovered")).sendKeys(coveredSpotsIds);
			//Thread.sleep(1500);  //Just for showing purpose
			driver.findElement(By.id("specHandicap")).sendKeys(handicapSpotsIds);
			Thread.sleep(1500);  //Just for showing purpose
			driver.findElement(By.id("parkingFee")).clear();
			driver.findElement(By.id("parkingFee")).sendKeys(parkingFee);
			//Thread.sleep(1500);  //Just for showing purpose
			
			driver.findElement(By.id("register")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("newParkingMessage"))).click();
			//Thread.sleep(3000);  //Just for showing purpose
			
			//Checking if a successful message has been displayed
			assertTrue(driver.getPageSource().contains("Park Space correctly created"));
		}
	}
}