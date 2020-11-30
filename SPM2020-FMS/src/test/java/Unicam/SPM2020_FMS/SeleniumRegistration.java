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
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

class SeleniumRegistration {
	
	static String projectPath;
	static String pathToDriver;
	static WebDriver driver;
	static String URLbase;
	static String runningOS;
	static String newUserFirstname;
	static String newUserSurname;
	static String newUserEmail;
	static String newUserPassword;
	static String newUserTaxCode;
	static String newUserPhone;

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
	           
	           if (runningOS.equals("Linux")) {
	            	pathToDriver = prop.getProperty("pathToLinuxDriver");
	            }
	            else if (runningOS.equals("Windows")) {
	            	pathToDriver = prop.getProperty("pathToWindowsDriver");
	            }

	           newUserFirstname=prop.getProperty("newUserFirstname");
	           newUserSurname=prop.getProperty("newUserSurname");
	           newUserEmail=prop.getProperty("newUserEmail");
	           newUserPassword=prop.getProperty("newUserPassword");
	           newUserTaxCode=prop.getProperty("newUserTaxCode");
	           newUserPhone=prop.getProperty("newUserPhone");
		} catch (IOException ex) {
	           ex.printStackTrace();
		}		
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		//Setting up WebDriver options
		System.setProperty("webdriver.chrome.driver", projectPath+pathToDriver);
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-dev-shm-usage");
		options.addArguments("--no-sandbox");
		
		//Remove comment prefix on the next line if you want to run test in headless mode
		//options.addArguments("--headless");
		
		driver = new ChromeDriver(options);
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
	@DisplayName("Check whether a new driver's registration succeeds")
	void checkDriverRegistration() throws InterruptedException {
		//Connecting to the home page
		driver.get(URLbase);
		WebDriverWait wait = new WebDriverWait(driver, 10);
		Thread.sleep(1500);  //Just for showing purpose
		
		//Connecting to the registration page
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='register']"))).click();
		
		//Compiling form
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("firstname"))).click();
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
		driver.findElement(By.id("phone")).sendKeys(newUserPhone);
		Thread.sleep(1500);  //Just for showing purpose
		driver.findElement(By.id("register")).click();
		
		//Checking if welcome page has been reached
		assertTrue(driver.getPageSource().contains(newUserFirstname));
		
	}
}
