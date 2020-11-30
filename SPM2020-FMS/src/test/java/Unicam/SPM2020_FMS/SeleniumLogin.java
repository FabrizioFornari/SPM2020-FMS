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

class SeleniumLogin {
	
	static String projectPath;
	static WebDriver driver;
	static String URLbase;
	static String user;
	static String rightPassword;
	static String wrongPassword;
	static String runningOS;
	static String pathToDriver;;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		//Checking running OS
		runningOS = (System.getProperty("os.name"));
		
		//Setting up system properties
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

            user=prop.getProperty("user");
            wrongPassword=prop.getProperty("wrongPassword");
            rightPassword=prop.getProperty("rightPassword");
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
	@DisplayName("User login should succeed only with the right credentials")
	void checkUserLogin() throws InterruptedException {
		//Connecting to the login page and trying to login with a wrong password
		driver.get(URLbase+"login");
		WebDriverWait wait = new WebDriverWait(driver, 10);
		Assert.assertEquals("Login", driver.getTitle());
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
		element.sendKeys(user);
		Thread.sleep(1500);  //Just for showing purpose
		driver.findElement(By.id("password")).sendKeys(wrongPassword);
		Thread.sleep(1500);  //Just for showing purpose
		driver.findElement(By.id("login")).click();
		element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
		
		assertTrue(driver.getPageSource().contains("Email or Password is wrong!!"));
		Thread.sleep(1500);  //Just for showing purpose
		
		//Logging with the right credentials
		element.clear();
		element.sendKeys(user);
		Thread.sleep(1500);  //Just for showing purpose
		driver.findElement(By.id("password")).sendKeys(rightPassword);
		Thread.sleep(1500);  //Just for showing purpose
		driver.findElement(By.id("login")).click();
		
		//Checking whether login has succeeded
		assertTrue(driver.getPageSource().contains("Hello"));
	}

}