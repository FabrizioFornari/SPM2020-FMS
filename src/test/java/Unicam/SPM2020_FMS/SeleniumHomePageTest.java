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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

class SeleniumHomePageTest {
	
	static String projectPath;
	static WebDriver driver;
	static String URLbase;
	static String runningOS;
	static String pathToDriver;;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		//Checking running OS
		runningOS = (System.getProperty("os.name"));
				
		//Setting up properties
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
		} catch (IOException ex) {
            ex.printStackTrace();
		}
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		System.setProperty("webdriver.chrome.driver", projectPath+pathToDriver);
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-dev-shm-usage");
		options.addArguments("--no-sandbox");
		options.addArguments("--start-maximized");
		
		//Remove comment prefix on the next line if you want to run test in headless mode
		options.addArguments("--headless");
		
		driver = new ChromeDriver(options);
	}

	@AfterEach
	void tearDown() throws Exception {
		//Thread.sleep(3000);  //Just for showing purpose
		driver.close();
		driver.quit();
	}

	@Test
	@Tag("AcceptanceTest")
	@DisplayName("Home page should be reachable")
	void checkHomePage() throws InterruptedException {
		driver.get(URLbase);
		assertTrue(driver.getPageSource().contains("sign-in"));
	}
}
