package Unicam.SPM2020_FMS;

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

class SeleniumInformationManagement {
	
	static String projectPath;
	static String pathToDriver;
	static WebDriver driver;
	static String runningOS;
	static String URLbase;
	static String user;
	static String userPassword;
	static String updatedUserPhoneNumber;
	static String updatedUserTaxCode;
	static String policeman;
	static String policemanPassword;
	static String updatedPolicemanPhoneNumber;
	static String updatedPolicemanTaxCode;
	static String municipality;
	static String municipalityPassword;
	static String updatedMunicipalityPhoneNumber;
	static String updatedMunicipalityTaxCode;
	
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
            
            // User's data
            user = prop.getProperty("user");
		    userPassword = prop.getProperty("rightPassword");
		    updatedUserTaxCode = prop.getProperty("updatedUserTaxCode");
		    updatedUserPhoneNumber = prop.getProperty("updatedUserPhoneNumber");
		    
		    //Policeman's data
		    policeman = prop.getProperty("policeman");
		    policemanPassword = prop.getProperty("rightPassword");
		    updatedPolicemanTaxCode = prop.getProperty("updatedPolicemanTaxCode");
		    updatedPolicemanPhoneNumber = prop.getProperty("updatedPolicemanPhoneNumber");
		    
		    //Municipality's data
		    municipality = prop.getProperty("municipality");
		    municipalityPassword = prop.getProperty("rightPassword");
		    updatedMunicipalityTaxCode = prop.getProperty("updatedMunicipalityTaxCode");
		    updatedMunicipalityPhoneNumber = prop.getProperty("updatedMunicipalityPhoneNumber");
		    
		} catch (IOException ex) {
            ex.printStackTrace();
		}	    
		
		//Setting up WebDriver options
		System.setProperty("webdriver.chrome.driver", projectPath+pathToDriver);
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-dev-shm-usage");
		options.addArguments("--no-sandbox");
				
		//Remove or add comment prefix on the next line if you want to run test in headless mode or not
		//options.addArguments("--headless");
				
		driver = new ChromeDriver(options);
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		Thread.sleep(3000);  //Just for showing purpose
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
	// Waits are properly managed with the WebDriverWait class
	// Every sleep in the following code can be easily removed without compromising the test
	// Sleeps are there just for showing purpose
	@DisplayName("Check whether changing user's information succeeds")
	void ChangeUserInformation() throws InterruptedException {
		//Logging in
		driver.get(URLbase);
		WebDriverWait wait = new WebDriverWait(driver, 10);
		Assert.assertEquals("Login", driver.getTitle());
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
		element.sendKeys(user);
		Thread.sleep(1500);  //Just for showing purpose
		driver.findElement(By.id("password")).sendKeys(userPassword);
		Thread.sleep(1500);  //Just for showing purpose
		driver.findElement(By.id("login")).click();
		Thread.sleep(1500);  //Just for showing purpose
		
		//Waiting for the Welcome Page and requesting the Information Management Page
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='profile']"))).click();
				
		//Updating tax code and phone number

		element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("taxCode")));
		Thread.sleep(1500);  //Just for showing purpose
		element.clear();
		Thread.sleep(1500);  //Just for showing purpose
		element.sendKeys(updatedUserTaxCode);
		Thread.sleep(1500);  //Just for showing purpose
		driver.findElement(By.id("phone")).clear();
		Thread.sleep(1500);  //Just for showing purpose
		driver.findElement(By.id("phone")).sendKeys(updatedUserPhoneNumber);
		Thread.sleep(1500);  //Just for showing purpose
		driver.findElement(By.id("register")).click();
		
		//Reloading the page and verifying the changes
		Thread.sleep(1500);  //Just for showing purpose
		driver.get(URLbase+"profile");
		element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("phone")));
		Assert.assertEquals(updatedUserPhoneNumber, element.getAttribute("value"));
		element = driver.findElement(By.id("taxCode"));
		Assert.assertEquals(updatedUserTaxCode, element.getAttribute("value"));
		Thread.sleep(1500);  //Just for showing purpose
		driver.findElement(By.xpath("//a[@href='logout']")).click();
		Thread.sleep(1500);  //Just for showing purpose
	}

	@Test
	@DisplayName("Check whether changing policeman's information succeeds")
	void ChangePolicemanInformation() throws InterruptedException {
		//Logging in
		driver.get(URLbase);
		WebDriverWait wait = new WebDriverWait(driver, 10);
		Assert.assertEquals("Login", driver.getTitle());
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
		element.sendKeys(policeman);
		Thread.sleep(1500);  //Just for showing purpose
		driver.findElement(By.id("password")).sendKeys(policemanPassword);
		Thread.sleep(1500);  //Just for showing purpose
		driver.findElement(By.id("login")).click();
		Thread.sleep(1500);  //Just for showing purpose
		
		//Waiting for the Welcome Page and requesting the Information Management Page
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='profile']"))).click();
				
		//Updating tax code and phone number

		element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("taxCode")));
		Thread.sleep(1500);  //Just for showing purpose
		element.clear();
		Thread.sleep(1500);  //Just for showing purpose
		element.sendKeys(updatedPolicemanTaxCode);
		Thread.sleep(1500);  //Just for showing purpose
		driver.findElement(By.id("phone")).clear();
		Thread.sleep(1500);  //Just for showing purpose
		driver.findElement(By.id("phone")).sendKeys(updatedPolicemanPhoneNumber);
		Thread.sleep(1500);  //Just for showing purpose
		driver.findElement(By.id("register")).click();
		
		//Reloading the page and verifying the changes
		Thread.sleep(1500);  //Just for showing purpose
		driver.get(URLbase+"profile");
		element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("phone")));
		Assert.assertEquals(updatedPolicemanPhoneNumber, element.getAttribute("value"));
		element = driver.findElement(By.id("taxCode"));;
		Assert.assertEquals(updatedPolicemanTaxCode, element.getAttribute("value"));
		Thread.sleep(1500);  //Just for showing purpose
		driver.findElement(By.xpath("//a[@href='logout']")).click();
		Thread.sleep(1500);  //Just for showing purpose
	}
	
	@Test
	@DisplayName("Check whether changing municipality's information succeeds")
	void ChangeMunicipalityInformation() throws InterruptedException {
		//Logging in
		driver.get(URLbase);
		WebDriverWait wait = new WebDriverWait(driver, 10);
		Assert.assertEquals("Login", driver.getTitle());
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
		element.sendKeys(municipality);
		Thread.sleep(1500);  //Just for showing purpose
		driver.findElement(By.id("password")).sendKeys(municipalityPassword);
		Thread.sleep(1500);  //Just for showing purpose
		driver.findElement(By.id("login")).click();
		Thread.sleep(1500);  //Just for showing purpose
		
		//Waiting for the Welcome Page and requesting the Information Management Page
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='profile']"))).click();
				
		//Updating tax code and phone number

		element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("taxCode")));
		Thread.sleep(1500);  //Just for showing purpose
		element.clear();
		Thread.sleep(1500);  //Just for showing purpose
		element.sendKeys(updatedMunicipalityTaxCode);
		Thread.sleep(1500);  //Just for showing purpose
		driver.findElement(By.id("phone")).clear();
		Thread.sleep(1500);  //Just for showing purpose
		driver.findElement(By.id("phone")).sendKeys(updatedMunicipalityPhoneNumber);
		Thread.sleep(1500);  //Just for showing purpose
		driver.findElement(By.id("register")).click();
		
		//Reloading the page and verifying the changes
		Thread.sleep(1500);  //Just for showing purpose
		driver.get(URLbase+"profile");
		element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("phone")));
		Assert.assertEquals(updatedMunicipalityPhoneNumber, element.getAttribute("value"));
		element = driver.findElement(By.id("taxCode"));;
		Assert.assertEquals(updatedMunicipalityTaxCode, element.getAttribute("value"));
		Thread.sleep(1500);  //Just for showing purpose
		driver.findElement(By.xpath("//a[@href='logout']")).click();
		Thread.sleep(1500);  //Just for showing purpose
	}
}
