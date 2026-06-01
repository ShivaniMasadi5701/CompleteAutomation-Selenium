package com.orangehrm.login.test;

import org.apache.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.orangehrm.base.test.BaseTest;
import com.orangehrm.login.page.LoginPage;
import com.orangehrm.util.DataProvider.DataProviderExcelUtil;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

public class ParameterizedTesting extends BaseTest{

		public static Logger logger = Logger.getLogger(LoginTest.class);

	    @BeforeClass
		@Parameters({ "browser", "siteURL"})
		public void initBrowserAndWebsite(String browser, String siteURL) throws InterruptedException {
			logger.info("Starting initBrowserAndWebsite Method ");

			getBrowserAndWebsite(browser, siteURL);
			loginPage = new LoginPage(driver);


			logger.info("Ending initBrowserAndWebsite Method ");
		}		

	    @Test(priority = 1,dataProvider = "loginData",dataProviderClass = DataProviderExcelUtil.class)
	   // @Test(priority = 1,dataProvider = "excelDataProvider", dataProviderClass = DataProviderExcelUtil.class)
	   	    public void DataProviderLogin(String username, String password) throws InterruptedException {
	        logger.info("Starting DataProviderLogin Test");	       

	        driver.navigate().refresh();

	        
	        loginPage.setUsername(username);
	        loginPage.setPassword(password);
	        loginPage.clickOnLoginButton();	         
	
	        
	        System.out.println("Logging in with Username: " + username + " and Password: " + password);

	        logger.info("Ending DataProviderLogin Test");
	    }

	}

