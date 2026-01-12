package com.orangehrm.base.test;

import static com.orangehrm.util.Constants.PASSWORD_TEXT;
import static com.orangehrm.util.Constants.USERNAME_TEXT;

import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.orangehrm.admin.page.AddUserPage;
import com.orangehrm.admin.page.UserManagementPage;
import com.orangehrm.login.page.ForgotYourPasswordPage;
import com.orangehrm.login.page.LoginPage;
import com.orangehrm.util.DataProvider.EmailReportSender;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {

	public WebDriver driver;
	public static LoginPage loginPage;
	public ForgotYourPasswordPage forgotYourPasswordPage;
	public UserManagementPage userManagementPage;
	public AddUserPage addUserPage;
	public WebDriverWait wait;

	private static final Logger logger = Logger.getLogger(BaseTest.class.getName());

	protected static Properties testDataProp = null;
	protected static Properties expectedAssertionsProp = null;

	@BeforeSuite
	public static void initTestdata() {
		FileReader testDataReader = null;
		FileReader assertionReader = null;
		try {
			testDataReader = new FileReader("src/main/resources/testdata.properties");
			assertionReader = new FileReader("src/main/resources/expectedassertions.properties");

			testDataProp = new Properties();
			testDataProp.load(testDataReader);

			expectedAssertionsProp = new Properties();
			expectedAssertionsProp.load(assertionReader);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void getBrowserAndWebsite(String browser, String siteURL) throws InterruptedException {
		logger.info("Starting getBrowserAndWebsite method");

		if (browser.equalsIgnoreCase("chrome")) {
		ChromeOptions options = new ChromeOptions();

// 👉 Tell Selenium where Chrome is
options.setBinary("C:\\chrome\\chrome-win64\\chrome.exe");

// 👉 Required for Jenkins
options.addArguments("--headless=new");
options.addArguments("--no-sandbox");
options.addArguments("--disable-dev-shm-usage");

System.setProperty("webdriver.chrome.driver",
        "C:\\chromedriver\\chromedriver.exe");

driver = new ChromeDriver(options);

		} else if (browser.equalsIgnoreCase("edge")) {
			driver = new EdgeDriver();
		} else {
			throw new IllegalArgumentException("Invalid browser name: " + browser);
		}

		driver.manage().window().maximize();
		driver.get(siteURL);

		logger.info("Ending getBrowserAndWebsite method");

	}

	@DataProvider(name = "browserProvider", parallel = false)
	public Object[][] provideBrowsers() {
		return new Object[][] { { "chrome" }, { "edge" } };
	}

	public void ValidLogin() {
		logger.info("Starting verifyLoginByLeavingAllFieldsBlank Method");

		loginPage = new LoginPage(driver);
		loginPage.setUsername(testDataProp.getProperty(USERNAME_TEXT));
		loginPage.setPassword(testDataProp.getProperty(PASSWORD_TEXT));
		loginPage.clickOnLoginButton();

		Assert.assertTrue(loginPage.isDisplayedOrangeHRMLogo());

		logger.info("Ending verifyLoginByLeavingAllFieldsBlank Method");

	}

	public WebDriver getDriver() {
		return driver;
	}

	// @BeforeMethod
	public void VerifyBeforeMethod() {
		System.out.println("Starting VerifyBeforeMethod Method");
		logger.info("Starting VerifyBeforeMethod Method");
	}

	// @AfterMethod
	public void VerifyAfterMethod() {
		System.out.println("Ending VerifyAfterMethod Method");
		logger.info("Ending VerifyAfterMethod Method");
	}

	// @BeforeClass
	public void verifyBeforeClass() {
		System.out.println("Starting verifyBeforeClass Method");
		logger.info("Starting verifyBeforeClass Method");
	}

	@AfterClass
	public void verifyAfterClass() {
		System.out.println("Ending verifyAfterClass Method");
		logger.info("Ending verifyAfterClass Method");
	}

	@BeforeTest
	public void verifyBeforeTest() {
		System.out.println("Starting verifyBeforeTest Method");
		logger.info("Starting verifyBeforeTest Method");
	}

	@AfterTest
	public void verifyAfterTest() {
		System.out.println("Ending verifyAfterTest Method");
		logger.info("Ending verifyAfterTest Method");
	}

	@BeforeSuite
	public void verifyBeforeSuite() {
		System.out.println("Starting verifyBeforeSuite Method");
		logger.info("Starting verifyBeforeSuite Method");
	}

	@AfterSuite
	public void verifyAfterSuite() {
		System.out.println("Ending verifyAfterSuite Method");
		logger.info("Ending verifyAfterSuite Method");
	}

	public void setup(String browser) {
		if (browser.equalsIgnoreCase("chrome")) {
			driver = new ChromeDriver();
		} else if (browser.equalsIgnoreCase("edge")) {
			driver = new EdgeDriver();
		} else {
			throw new IllegalArgumentException("Invalid browser: " + browser);
		}

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get("https://opensource-demo.orangehrmlive.com/");
	}

	// @BeforeClass
	@Parameters({ "browser", "siteURL" })
	public void initBrowserAndWebsite(String browser, String siteURL) throws InterruptedException {
		logger.info("Starting initBrowserAndWebsite Method ");

		getBrowserAndWebsite(browser, siteURL);
		loginPage = new LoginPage(driver);
		forgotYourPasswordPage = new ForgotYourPasswordPage(driver);

		logger.info("Ending initBrowserAndWebsite Method ");
	}

	@Parameters({ "siteURL" })
	public void setUp(@Optional("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login") String siteURL) {
		if (driver == null) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			driver.manage().window().maximize();
			driver.get(siteURL);
			System.out.println("Browser launched and navigated to: " + siteURL);

			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

			wait = new WebDriverWait(driver, Duration.ofSeconds(30));

		}
	}

	// @AfterSuite
	public void tearDown() {
		if (driver != null) {
			// driver.quit();
			System.out.println("Browser closed after all tests.");
			driver = null;
		}
	}

	@AfterSuite
	public void afterSuite() {
		EmailReportSender.sendEmailWithReport();
	}

}
