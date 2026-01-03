package com.orangehrm.login.test;

import static com.orangehrm.util.Constants.INVALID_USERNAME_TEXT;
import static com.orangehrm.util.Constants.RESET_PASSWORD_LINK_SENT_SUCCESSFULLY_TEXT;
import static com.orangehrm.util.Constants.RESET_PASSWORD_TEXT;
import static com.orangehrm.util.Constants.USERNAME_TEXT;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.orangehrm.base.test.BaseTest;
import com.orangehrm.login.page.ForgotYourPasswordPage;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;


public class ForgotYourPasswordTest extends BaseTest {

	public static Logger logger = Logger.getLogger(ForgotYourPasswordTest.class);

	@BeforeClass
	@Parameters({ "browser", "siteURL" })
	public void initBrowserAndWebsite(String browser, String siteURL) throws InterruptedException {
		logger.info("Starting initBrowserAndWebsite Method ");

		getBrowserAndWebsite(browser, siteURL);
		forgotYourPasswordPage = new ForgotYourPasswordPage(driver);

		logger.info("Ending initBrowserAndWebsite Method ");
	}

	@Test(priority = 1, description = "Verify Forgot Your Password by Entering Invalid Username")
	@Description("Test Case #1,Verify Forgot Your Password by Entering Invalid Username")
	@Severity(SeverityLevel.CRITICAL)
	@Story("Test Case #1, Verify Forgot Your Password by Entering Invalid Username")
	public void verifyLoginByEnteringInvalidUsername() {
		logger.info("Starting verifyLoginByEnteringInvalidUsername Method");

		forgotYourPasswordPage.clickOnForgotYourPassword();

		Assert.assertEquals(forgotYourPasswordPage.getResetPasswordText(),
				expectedAssertionsProp.get(RESET_PASSWORD_TEXT));

		forgotYourPasswordPage.setUsername(testDataProp.getProperty(INVALID_USERNAME_TEXT));
		forgotYourPasswordPage.clickOnResetPasswordButton();

		Assert.assertEquals(forgotYourPasswordPage.getResetPasswordLinkSentSuccessfullyText(),
				expectedAssertionsProp.get(RESET_PASSWORD_LINK_SENT_SUCCESSFULLY_TEXT));

		logger.info("Ending verifyLoginByEnteringInvalidUsername Method");

	}
	
	@Test(priority = 2, description = "Verify Forgot Your Password by Entering Valid Username")
	@Description("Test Case #2,Verify Forgot Your Password by Entering Valid Username")
	@Severity(SeverityLevel.CRITICAL)
	@Story("Test Case #2, Verify Forgot Your Password by Entering Valid Username")
	public void verifyLoginByEnteringValidUsername() {
		logger.info("Starting verifyLoginByEnteringValidUsername Method");

		driver.navigate().back();
		driver.navigate().refresh();
		
		forgotYourPasswordPage.setUsername(testDataProp.getProperty(USERNAME_TEXT));
		forgotYourPasswordPage.clickOnResetPasswordButton();

		Assert.assertEquals(forgotYourPasswordPage.getResetPasswordLinkSentSuccessfullyText(),
				expectedAssertionsProp.get(RESET_PASSWORD_LINK_SENT_SUCCESSFULLY_TEXT));

		logger.info("Ending verifyLoginByEnteringValidUsername Method");

	}
	
	
	
	@Test(priority = 3)
	public void verifySwitchingWindows()
	{
		  System.out.println("Main Window: " + driver.getTitle());
		
		 WebDriver newWindow = driver.switchTo().newWindow(WindowType.WINDOW);
	        newWindow.get("https://www.instagram.com");

	      
	        System.out.println("New Window: " + newWindow.getTitle());
	        
			/*
			 * driver.switchTo().newWindow(WindowType.TAB);
			 * driver.get("https://www.facebook.com");
			 * 
			 * driver.switchTo().newWindow(WindowType.TAB);
			 * driver.get("https://www.instagram.com");
			 */
		
		String target_Title="Instagram";
		for (String windowHandle : driver.getWindowHandles()) 
		{
		    driver.switchTo().window(windowHandle);
		    System.out.println("Switched to window: " + driver.getTitle());
		    if(driver.getTitle().equals(target_Title)) {
		    	break;
		    }
		}
		
		 System.out.println("Current URL: " + driver.getCurrentUrl());
		
	


		

	}

}
