package com.orangehrm.login.test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.orangehrm.base.test.BaseTest;
import com.orangehrm.login.page.LoginPage;

import io.github.bonigarcia.wdm.WebDriverManager;

public class JavaClass extends BaseTest {

	@BeforeTest
	public static void main(String args[]) {
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
		driver.manage().window().maximize();
		loginPage = new LoginPage(driver);
		loginPage.setUsername("Admin");
		loginPage.setPassword("Admin123");
		loginPage.clickOnLoginButton();
	}


	public void verify_NOPriorityOne() {
		System.out.println("No priority one method");
	}

	@Test(priority = 0)
	public void verify_PriorityZero() {
		System.out.println("Zero priority method");
	}

	@Test
	public void verify_NOPriorityTwo() {
		System.out.println("No priority  two method");
	}

	@Test(priority = 1)
	public void verify_PriorityOne() {
		System.out.println("One priority method");
	}

	@Test(priority = -1)
	public void verify_PriorityMinusOne() {
		System.out.println("Minus One priority method");
	}

}
