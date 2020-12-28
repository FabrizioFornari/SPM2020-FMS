package Unicam.SPM2020_FMS;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Assume;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SeleniumFindingBrokenLinksPolicemanTest {
	
	static String projectPath;
	static WebDriver driver;
	static String URLbase;
	static String policeman;
	static String rightPassword;
	static String runningOS;
	static String pathToDriver;
	
	public static void checkLink(String link) throws IOException
	{
           URL url = new URL(link);
           HttpURLConnection httpURLConnect = (HttpURLConnection)url.openConnection();
           httpURLConnect.setConnectTimeout(3000);
           httpURLConnect.connect();
           Assert.assertEquals(200, httpURLConnect.getResponseCode());
    } 

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
            
            if (runningOS.contains("Linux")) {
            	pathToDriver = prop.getProperty("pathToLinuxDriver");
            }
            else if (runningOS.contains("Windows")) {
            	pathToDriver = prop.getProperty("pathToWindowsDriver");
            }

            policeman = prop.getProperty("policeman");
            rightPassword = prop.getProperty("rightPassword");
		} catch (IOException ex) {
            ex.printStackTrace();
		}
		
		//Setting up WebDriver options
		System.setProperty("webdriver.chrome.driver", projectPath+pathToDriver);
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-dev-shm-usage");
		options.addArguments("--no-sandbox");
		options.addArguments("--headless");
		driver = new ChromeDriver(options);

		//Logging in
		driver.get(URLbase+"login");
		WebDriverWait wait = new WebDriverWait(driver, 10);
		Assert.assertEquals("Login", driver.getTitle());
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
		element.sendKeys(policeman);
		driver.findElement(By.id("password")).sendKeys(rightPassword);
		driver.findElement(By.id("login")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("jumbotron")));
			
		//Checking whether login has succeeded
		Assume.assumeTrue(driver.getPageSource().contains("Hello"));
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		driver.close();
		driver.quit();
	}

	@BeforeEach
	void setUp() throws Exception {

	}

	@AfterEach
	void tearDown() throws Exception {
	}

	//The following tests will be executed based on the @Order annotation
	
	@Test
	@Tag("AcceptanceTest")
	@DisplayName("Check broken links in welcome page")
	@Order(1)
	void checkWelcomePage() throws IOException {
		//Getting the welcome page
		driver.get(URLbase);
		assertTrue(driver.getPageSource().contains("Hello"));
		
		//Finding all anchor tags
		List<WebElement> links = driver.findElements(By.tagName("a"));
		
		//Checking every link
		for(int i=0; i<links.size(); i++)
		{	
			WebElement ele = links.get(i);
			String url = ele.getAttribute("href");
			checkLink(url);
		}
	}
	
	@Test
	@Tag("AcceptanceTest")
	@DisplayName("Check broken links in reservations page")
	@Order(2)
	void checkMyCarsPage() throws IOException, InterruptedException {
		//Getting the reservations page
		driver.get(URLbase+"reservationsToCheck");
		assertTrue(driver.getPageSource().contains("Check a park"));
		
		//Finding all anchor tags
		List<WebElement> links = driver.findElements(By.tagName("a"));
		
		//Checking every link
		for(int i=0; i<links.size(); i++)
		{	
			WebElement ele = links.get(i);
			String url = ele.getAttribute("href");
			checkLink(url);
		}
	}
	
	@Test
	@Tag("AcceptanceTest")
	@DisplayName("Check broken links in profile page")
	@Order(3)
	void checkProfilePage() throws IOException {
		//Getting the profile page
		driver.get(URLbase+"profile");
		assertTrue(driver.getPageSource().contains("Update your information"));
		
		//Finding all anchor tags
		List<WebElement> links = driver.findElements(By.tagName("a"));
		
		//Checking every link
		for(int i=0; i<links.size(); i++)
		{	
			WebElement ele = links.get(i);
			String url = ele.getAttribute("href");
			checkLink(url);
		}
	}
}