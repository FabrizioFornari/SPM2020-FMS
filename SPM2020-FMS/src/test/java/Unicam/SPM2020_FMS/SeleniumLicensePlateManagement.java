package Unicam.SPM2020_FMS;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

class SeleniumLicensePlateManagement {
	
	static String projectPath;
	static String pathToDriver;
	static WebDriver driver;
	static String runningOS;
	static String URLbase;
	static String user;
	static String password;
	static String licensePlate1;
	static String licensePlate2;
	static String model1;
	static String model2;

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
            
            if (runningOS.contains("Linux")) {
            	pathToDriver = prop.getProperty("pathToLinuxDriver");
            }
            else if (runningOS.contains("Windows")) {
            	pathToDriver = prop.getProperty("pathToWindowsDriver");
            }
            
            user=prop.getProperty("user");
            password=prop.getProperty("rightPassword");
            licensePlate1=prop.getProperty("licensePlate1");
		    model1=prop.getProperty("model1");
		    licensePlate2=prop.getProperty("licensePlate2");
		    model2=prop.getProperty("model2");
		} catch (IOException ex) {
            ex.printStackTrace();
		}	       
		//Setting up WebDriver options
				System.setProperty("webdriver.chrome.driver", projectPath+pathToDriver);
				ChromeOptions options = new ChromeOptions();
				options.addArguments("--disable-dev-shm-usage");
				options.addArguments("--no-sandbox");
						
				//Remove comment prefix on the next line if you want to run test in headless mode
				//options.addArguments("--headless");
						
				driver = new ChromeDriver(options);
				
				// Waits are properly managed with the WebDriverWait class
				// Every sleep in the following code can be easily removed without compromising the test
				// Sleeps are there just for showing purpose
				
				//Logging in
				driver.get(URLbase);
				WebDriverWait wait = new WebDriverWait(driver, 10);
				Assert.assertEquals("Login", driver.getTitle());
				WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
				element.sendKeys(user);
				Thread.sleep(1500);  //Just for showing purpose
				driver.findElement(By.id("password")).sendKeys(password);
				Thread.sleep(1500);  //Just for showing purpose
				driver.findElement(By.id("login")).click();
				Thread.sleep(1500);  //Just for showing purpose
						
				//Waiting for the welcome page and requesting the license plate management page
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='myCars']"))).click();
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("licensePlate")));
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		Thread.sleep(5000);  //Just for showing purpose
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
	@DisplayName("Check whether adding a new car succeeds")
	void checkLicensePlateInsertion() throws InterruptedException {
			
		// Testing whether the car is not yet in the list and adding a new car
		if (!(driver.getPageSource().contains(licensePlate1))) {
			//Compiling the form
			Thread.sleep(1500);  //Just for showing purpose
			driver.findElement(By.id("licensePlate")).sendKeys(licensePlate1);
			Thread.sleep(1500);  //Just for showing purpose
			driver.findElement(By.id("model")).sendKeys(model1);
			Thread.sleep(1500);  //Just for showing purpose
			driver.findElement(By.id("addCarButton")).click();;
			Thread.sleep(1500);  //Just for showing purpose
						
			//Checking if the car has been added to the list
			assertTrue(driver.getPageSource().contains(licensePlate1));
		}
		
		// Testing whether the car is not yet in the list and adding another car
		if (!(driver.getPageSource().contains(licensePlate2))) {
			//Compiling the form
			Thread.sleep(1500);  //Just for showing purpose
			driver.findElement(By.id("licensePlate")).sendKeys(licensePlate2);
			Thread.sleep(1500);  //Just for showing purpose
			driver.findElement(By.id("model")).sendKeys(model2);
			Thread.sleep(1500);  //Just for showing purpose
			driver.findElement(By.id("addCarButton")).click();;
			Thread.sleep(1500);  //Just for showing purpose
			
			//Checking if the car has been added to the list
			assertTrue(driver.getPageSource().contains(licensePlate2));
		}
	}
	
	@Test
	@DisplayName("Check whether deleting a car succeeds")
	void checkLicensePlateDeletion() throws InterruptedException {		
		// Testing whether the car is already in the list and deleting it
		if ((driver.getPageSource().contains(licensePlate1))) {
			Thread.sleep(1500);  //Just for showing purpose
			driver.findElement(By.id("delete[0]")).click();;
			//Thread.sleep(1500);  //Just for showing purpose
			
			//Checking if the car has been deleted from the list
			assertFalse(driver.getPageSource().contains(licensePlate1));
		}
			
		// Testing whether the car is already in the list and deleting it
		if ((driver.getPageSource().contains(licensePlate2))) {
			Thread.sleep(1500);  //Just for showing purpose
			driver.findElement(By.id("delete[0]")).click();;
			//Thread.sleep(1500);  //Just for showing purpose
					
			//Checking if the car has been deleted from the list
			assertFalse(driver.getPageSource().contains(licensePlate2));
		}
	}
}
