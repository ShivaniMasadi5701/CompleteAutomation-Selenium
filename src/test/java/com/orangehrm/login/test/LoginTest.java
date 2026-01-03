package com.orangehrm.login.test;

import static com.orangehrm.util.Constants.*;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.orangehrm.base.test.BaseTest;
import com.orangehrm.login.page.LoginPage;
import com.orangehrm.util.Listeners.TestListener;
import com.orangehrm.util.Reports.ExtentReportsManager;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;


@Listeners({ExtentReportsManager.class,TestListener.class})
@Epic("ORANGEHRM ")
@Feature("Login into ORANGEHRM")
public class LoginTest extends BaseTest {

	public static Logger logger = Logger.getLogger(LoginTest.class);

    @BeforeClass
	@Parameters({ "browser", "siteURL"})
	public void initBrowserAndWebsite(String browser, String siteURL) throws InterruptedException {
		logger.info("Starting initBrowserAndWebsite Method ");

		getBrowserAndWebsite(browser, siteURL);
		loginPage = new LoginPage(driver);


		logger.info("Ending initBrowserAndWebsite Method ");
	}
	
	
	@Test(priority = 1,  description = "Verify Login functionality by leaving User name & password fields Blank")
	@Description("Test Case #1, Verify Login functionality by leaving User name & password fields Blank")
	@Severity(SeverityLevel.CRITICAL)
	@Story("Test Case #1, Verify Login functionality by leaving User name & password fields Blank")
	public void verifyLoginByLeavingAllFieldsBlank() {
		logger.info("Starting verifyLoginByLeavingAllFieldsBlank Method");

		loginPage.clickOnLoginButton();

		Assert.assertEquals(loginPage.getUsernameRequiredText(),
				expectedAssertionsProp.getProperty(REQUIRED_TEXT));		
		
		Assert.assertEquals(loginPage.getPasswordRequiredText(),
				expectedAssertionsProp.getProperty(REQUIRED_TEXT));

		logger.info("***************InvocationCount******************");

		logger.info("Ending verifyLoginByLeavingAllFieldsBlank Method");

	}

	@Test(priority = 2, description = "Verify Login By Entering  Valid Username and Leaving Password Field blank")
	@Description("Test Case #2, Verify Login By Entering  Valid Username and Leaving Password Field blank")
	@Severity(SeverityLevel.CRITICAL)
	@Story("Test Case #2, Verify Login By Entering  Valid Username and Leaving Password Field blank")
	public void verifyLoginByEnteringUsernameFieldLeavingPasswordFieldBlank() {
		logger.info("Starting verifyLoginByEnteringUsernameFieldLeavingPasswordFieldBlank Method");

		driver.navigate().refresh();
		loginPage.setUsername(testDataProp.getProperty(USERNAME_TEXT));
		loginPage.clickOnLoginButton();

		Assert.assertEquals(loginPage.getUsernameRequiredText(),
				expectedAssertionsProp.getProperty(REQUIRED_TEXT));

		logger.info("Ending verifyLoginByEnteringUsernameFieldLeavingPasswordFieldBlank Method");

	}

	@Test(priority = 3, description = "Verify Login By Entering  Invalid Username and Leaving Password Field blank")
	@Description("Test Case #3, Verify Login By Entering  Invalid Username and Leaving Password Field blank")
	@Severity(SeverityLevel.CRITICAL)
	@Story("Test Case #3, Verify Login By Entering  Invalid Username and Leaving Password Field blank")
	public void verifyLoginByEnteringInvalidUsernameFieldLeavingPasswordFieldBlank() {
		logger.info("Starting verifyLoginByEnteringUsernameFieldLeavingPasswordFieldBlank Method");

		driver.navigate().refresh();
		loginPage.setUsername(testDataProp.getProperty(INVALID_USERNAME_TEXT));
		loginPage.clickOnLoginButton();

		Assert.assertEquals(loginPage.getPasswordRequiredText(),
				expectedAssertionsProp.getProperty(REQUIRED_TEXT));

		logger.info("Ending verifyLoginByEnteringUsernameFieldLeavingPasswordFieldBlank Method");

	}

	@Test(priority = 4, description = "Verify Login By Entering Valid Password and Leaving Username Field blank")
	@Description("Test Case #4, Verify Login By Entering Valid Password and Leaving Username Field blank")
	@Severity(SeverityLevel.CRITICAL)
	@Story("Test Case #4, Verify Login By Entering Valid Password and Leaving Username Field blank")
	public void verifyLoginByEnteringPasswordFieldLeavingUsernameFieldBlank() {
		logger.info("Starting verifyLoginByEnteringPasswordFieldLeavingUsernameFieldBlank Method");

		driver.navigate().refresh();
		loginPage.setPassword(testDataProp.getProperty(PASSWORD_TEXT));
		loginPage.clickOnLoginButton();

		Assert.assertEquals(loginPage.getUsernameRequiredText(),
				expectedAssertionsProp.getProperty(REQUIRED_TEXT));

		logger.info("Ending verifyLoginByEnteringPasswordFieldLeavingUsernameFieldBlank Method");

	}

	@Test(priority = 5, description = "Verify Login with Entering Invalid Password and Leaving Username Field blank")
	@Description("Test Case #5, Verify Login with Entering Invalid Password and Leaving Username Field blank")
	@Severity(SeverityLevel.CRITICAL)
	@Story("Test Case #5, Verify Login with Entering Invalid Password and Leaving Username Field blank")
	public void verifyLoginByEnteringInvalidPasswordFieldLeavingUsernameFieldBlank() {
		logger.info("Starting verifyLoginByEnteringPasswordFieldLeavingUsernameFieldBlank Method");

		driver.navigate().refresh();
		loginPage.setPassword(testDataProp.getProperty(INVALID_PASSWORD_TEXT));
		loginPage.clickOnLoginButton();

		Assert.assertEquals(loginPage.getUsernameRequiredText(),
				expectedAssertionsProp.getProperty(REQUIRED_TEXT));

		logger.info("Ending verifyLoginByEnteringPasswordFieldLeavingUsernameFieldBlank Method");

	}

	@Test(priority = 6, description = "Verify Login by Entering Invalid Username and Valid Password")
	@Description("Test Case #6, Verify Login by Entering Invalid Username and Valid Password")
	@Severity(SeverityLevel.CRITICAL)
	@Story("Test Case #6, Verify Login by Entering Invalid Username and Valid Password")
	public void verifyLoginByEnteringInvalidUsernameValidPasswordFields() {
		logger.info("Starting verifyLoginByLeavingAllFieldsBlank Method");

		driver.navigate().refresh();
		loginPage.setUsername(testDataProp.getProperty(INVALID_USERNAME_TEXT));
		loginPage.setPassword(testDataProp.getProperty(PASSWORD_TEXT));
		loginPage.clickOnLoginButton();

		Assert.assertEquals(loginPage.getInvalidCredentialsText(),
				expectedAssertionsProp.getProperty(INVALIDCREDENTIALS_TEXT));

		logger.info("Ending verifyLoginByLeavingAllFieldsBlank Method");

	}

	@Test(priority = 7, description = "Verify Login by Entering Valid Username and Invalid Password")
	@Description("Test Case #7, Verify Login by Entering Valid Username and Invalid Password")
	@Severity(SeverityLevel.CRITICAL)
	@Story("Test Case #7, Verify Login by Entering Valid Username and Invalid Password")
	public void verifyLoginByEnteringValidUsernameInvalidPasswordFields() {
		logger.info("Starting verifyLoginByLeavingAllFieldsBlank Method");

		driver.navigate().refresh();
		loginPage.setUsername(testDataProp.getProperty(USERNAME_TEXT));
		loginPage.setPassword(testDataProp.getProperty(INVALID_PASSWORD_TEXT));
		loginPage.clickOnLoginButton();

		Assert.assertEquals(loginPage.getInvalidCredentialsText(),
				expectedAssertionsProp.getProperty(INVALIDCREDENTIALS_TEXT));

		logger.info("Ending verifyLoginByLeavingAllFieldsBlank Method");

	}

	@Test(priority = 8, description = "Verify Login by Entering Invalid Username and Invalid Password")
	@Description("Test Case #8, Verify Login by Entering Invalid Username and Invalid Password")
	@Severity(SeverityLevel.CRITICAL)
	@Story("Test Case #8, Verify Login by Entering Invalid Username and Invalid Password")
	public void verifyLoginByEnteringInvalidUsernameInvalidPasswordFields() {
		logger.info("Starting verifyLoginByLeavingAllFieldsBlank Method");

		driver.navigate().refresh();
		loginPage.setUsername(testDataProp.getProperty(INVALID_USERNAME_TEXT));
		loginPage.setPassword(testDataProp.getProperty(INVALID_PASSWORD_TEXT));
		loginPage.clickOnLoginButton();

		Assert.assertEquals(loginPage.getInvalidCredentialsText(),
				expectedAssertionsProp.getProperty(INVALIDCREDENTIALS_TEXT));

		logger.info("Ending verifyLoginByLeavingAllFieldsBlank Method");

	}

	@Test(priority = 9, groups={"smoke"},description = "Verify Login by Entering Valid Username and Valid Password")
	@Description("Test Case #9, Verify Login by Entering Valid Username and Valid Password")
	@Severity(SeverityLevel.CRITICAL)
	@Story("Test Case #9, Verify Login by Entering Valid Username and Valid Password")
	public void verifyLoginByValidUsernamePasswordFields() throws InterruptedException {
		logger.info("Starting verifyLoginByLeavingAllFieldsBlank Method");
		

		  loginPage.setUsername(testDataProp.getProperty(USERNAME_TEXT));
		  loginPage.setPassword(testDataProp.getProperty(PASSWORD_TEXT)); 
		  loginPage.clickOnLoginButton();
		  Thread.sleep(3000);

		  Assert.assertTrue(loginPage.isDisplayedOrangeHRMLogo());

		logger.info("Ending verifyLoginByLeavingAllFieldsBlank Method");

	}
	 
	    

}
