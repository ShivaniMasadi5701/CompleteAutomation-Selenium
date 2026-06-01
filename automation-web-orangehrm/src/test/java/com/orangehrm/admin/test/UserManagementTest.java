package com.orangehrm.admin.test;

import static com.orangehrm.util.Constants.*;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.orangehrm.admin.page.UserManagementPage;
import com.orangehrm.base.test.BaseTest;
import com.orangehrm.util.Listeners.TestListener;
import com.orangehrm.util.Reports.ExtentReportsManager;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Listeners({ExtentReportsManager.class,TestListener.class})
public class UserManagementTest extends BaseTest {
	
	public static Logger logger = Logger.getLogger(UserManagementTest.class);


	@BeforeClass
	@Parameters({ "browser", "siteURL" })
	public void initBrowserAndWebsite(String browser, String siteURL) throws InterruptedException {
		logger.info("Starting initBrowserAndWebsite Method ");

		getBrowserAndWebsite(browser, siteURL);
		userManagementPage = new UserManagementPage(driver);
	     ValidLogin();
 
		logger.info("Ending initBrowserAndWebsite Method ");
	}
	
	
	
	@Test(priority = 1,  groups={"smoke"},description = "Verify Admin Menu Item")
	@Description("Test Case #1, Verify Admin Menu Item")
	@Severity(SeverityLevel.NORMAL)
	@Story("Test Case #1, Verify Admin Menu Item")
	public void VerifyAdminMenuItem() {
		logger.info("Starting VerifyAdminMenuItem Method");

		userManagementPage.clickOnMainMenuListItem(testDataProp.getProperty(ADMIN_TEXT));
		
		Assert.assertEquals(userManagementPage.getAdminText(), expectedAssertionsProp.getProperty(ADMIN_TEXT));
		Assert.assertEquals(userManagementPage.getUserManagementText(), expectedAssertionsProp.getProperty(USERMANAGEMENT_TEXT));
	

		logger.info("Ending VerifyAdminMenuItem Method");

	}
	
	@Test(priority = 2, description = "Verify Search System Users  by all fields blank")
	@Description("Test Case #2, Verify Search System Users  by all fields blank")
	@Severity(SeverityLevel.NORMAL)
	@Story("Test Case #2, Verify Search System Users  by all fields blank")	
	public void VerifySearchSystemUsersByAllFieldsBlank()
	{
		logger.info("Starting VerifySearchSystemUsersByAllFieldsBlank Method");
		
		userManagementPage.clickOnSearchButton();
		//int size=userManagementPage.getRecordsSize();
		
		//Assert.assertEquals(userManagementPage.getRecordsFoundText(),"("+size+") Records Found");
	

		logger.info("Ending VerifySearchSystemUsersByAllFieldsBlank Method");
	}
	
	
	@Test(priority = 3, description = "Verify Search System Users by Entering an Invalid Username and leaving User Role, Employee Name, and Status blank")
	@Description("Test Case #3, Verify Search System Users by Entering an Invalid Username and leaving User Role, Employee Name, and Status blank")
	@Severity(SeverityLevel.NORMAL)
	@Story("Test Case #3, Verify Search System Users by Entering an Invalid Username and leaving User Role, Employee Name, and Status blank")	
	public void VerifySearchSystemUsersByEnteringInvalidUsernameOnly()
	{
		logger.info("Starting VerifySearchSystemUsersByAllFieldsBlank Method");
		
		driver.navigate().refresh();
		userManagementPage.setUsername("hello");
		userManagementPage.clickOnSearchButton();
	

		logger.info("Ending VerifySearchSystemUsersByAllFieldsBlank Method");
	}
	

	@Test(priority = 4, groups={"smoke"},description = "Verify Search System Users by Entering an Valid Username and leaving User Role, Employee Name, and Status blank")
	@Description("Test Case #4, Verify Search System Users by Entering an Valid Username and leaving User Role, Employee Name, and Status blank")
	@Severity(SeverityLevel.NORMAL)
	@Story("Test Case #4, Verify Search System Users by Entering an Valid Username and leaving User Role, Employee Name, and Status blank")	
	public void VerifySearchSystemUsersByEnteringValidUsernameOnly()
	{
		logger.info("Starting VerifySearchSystemUsersByAllFieldsBlank Method");
		
		driver.navigate().refresh();
		userManagementPage.setUsername("admin");
		userManagementPage.clickOnSearchButton();
	

		logger.info("Ending VerifySearchSystemUsersByAllFieldsBlank Method");
	}
	
}
