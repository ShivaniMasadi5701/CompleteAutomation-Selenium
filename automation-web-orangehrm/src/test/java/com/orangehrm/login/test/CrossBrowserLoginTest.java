package com.orangehrm.login.test;

import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import com.orangehrm.base.test.BaseTest;
import com.orangehrm.login.page.LoginPage;
import com.orangehrm.util.DataProvider.BrowserDataProvider;

public class CrossBrowserLoginTest extends BaseTest {
	public static Logger logger = Logger.getLogger(LoginTest.class);	

	@Test(dataProvider = "browserData", dataProviderClass = BrowserDataProvider.class)
    public void verifyLoginOnDifferentBrowsers(String browser) throws InterruptedException { 
		setup(browser);
		System.out.println("**********************browsername"+browser);

        LoginPage loginPage = new LoginPage(driver);
        
        loginPage.setUsername("Admin");
        loginPage.setPassword("admin123");
        loginPage.clickOnLoginButton();   
      
       
    }
}


