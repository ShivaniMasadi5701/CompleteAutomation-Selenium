package com.orangehrm.admin.test;

import static com.orangehrm.util.Constants.*;

import org.apache.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.orangehrm.admin.page.AddUserPage;
import com.orangehrm.admin.page.UserManagementPage;
import com.orangehrm.base.test.BaseTest;
import com.orangehrm.util.Listeners.TestListener;
import com.orangehrm.util.Reports.ExtentReportsManager;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

public class AddUserTest extends BaseTest {

	public static Logger logger = Logger.getLogger(AddUserTest.class);

	@BeforeClass
	@Parameters({ "browser", "siteURL" })
	public void initBrowserAndWebsite(String browser, String siteURL) throws InterruptedException {
		logger.info("Starting initBrowserAndWebsite Method ");

		getBrowserAndWebsite(browser, siteURL);
		ValidLogin();

		userManagementPage = new UserManagementPage(driver);
		addUserPage = new AddUserPage(driver);

		logger.info("Ending initBrowserAndWebsite Method ");
	}

	@Test(priority = 1, description = "Verify Add User ")
	@Description("Test Case #1,, Verify Add User ")
	@Severity(SeverityLevel.NORMAL)
	@Story("Test Case #1, Verify Add User Page")
	public void VerifyAddUserPage() {
		logger.info("Starting VerifyAddUserPage Method");

		userManagementPage.clickOnMainMenuListItem(testDataProp.getProperty(ADMIN_TEXT));
		addUserPage.clickOnAddButton();
		addUserPage.clickOnUserRoleDropdown();
		addUserPage.setUserRoleStatusDropdown(testDataProp.getProperty(ADMIN_TEXT));
		addUserPage.setEmployeeName("Thomas Kutty Benny");
		addUserPage.clickOnUserRoleDropdown();
		addUserPage.setUserRoleStatusDropdown(testDataProp.getProperty(ENABLED_TEXT));
		addUserPage.setUserName();
		String pw = addUserPage.getEmpName();
		addUserPage.setPassword(pw);
		addUserPage.setConfirmPassword(pw);

		logger.info("Ending VerifyAddUserPage Method");
	}

}
