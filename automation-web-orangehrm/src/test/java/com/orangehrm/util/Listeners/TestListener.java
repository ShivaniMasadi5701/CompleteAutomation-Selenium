package com.orangehrm.util.Listeners;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import com.orangehrm.base.test.BaseTest;

public class TestListener extends TestListenerAdapter {

	private static final Logger logger = Logger.getLogger(TestListener.class.getName());
	

	private static String getTestMethodName(ITestResult iTestResult) {
		return iTestResult.getMethod().getConstructorOrMethod().getName();
	}

	public void captureScreenshot(String methodName, WebDriver driver) throws IOException {

		try {
			String timestamp = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss").format(Calendar.getInstance().getTime());

			/*
			 * String screenshotFolder = System.getProperty("user.dir") + File.separator +
			 * "test-output" + File.separator + "screenshot-reports" + File.separator +
			 * "failure_screenshots";
			 */

			// Define folder path
			String screenshotFolder = "C:\\Users\\DELL\\eclipse-workspace\\eclipse-HONO\\automation-web-orangehrm\\test-output\\screenshot-reports\\failure_screenshots";
			File directory = new File(screenshotFolder);

			// Ensure the directory exists
			if (!directory.exists()) {
				directory.mkdirs();
			}

			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

			// Define screenshot file path with method name and timestamp
			File destFile = new File(screenshotFolder + File.separator + methodName + "_" + timestamp + ".png");

			// Copy file to the destination
			FileUtils.copyFile(scrFile, destFile);
			System.out.println("Screenshot saved: " + destFile.getAbsolutePath());
		} catch (IOException e) {
			logger.error("Error while capturing screenshot: " + e.getMessage(), e);
			e.printStackTrace(); // Properly log the error
		}
	}
	
	
	
	@Override
	public void onStart(ITestContext iTestContext) {
		logger.info("In onStart method " + iTestContext.getName());
		
		
		
		
	}

	@Override
	public void onTestStart(ITestResult iTestResult) {
		logger.info(getTestMethodName(iTestResult) + " test is starting.");
	}

	@Override
	public void onTestSuccess(ITestResult iTestResult) {
		logger.info(getTestMethodName(iTestResult) + " test is succeed.");
	}
	
	@Override
	
	  public void onTestFailure(ITestResult iTestResult) {
	  logger.info(getTestMethodName(iTestResult) + " test is failed.");
	  
	  // Get driver from BaseClassAutomationTest and assign to local webdriver
	  Object testClass = iTestResult.getInstance(); 
	  WebDriver driver= ((BaseTest) testClass).getDriver();
	  
	  // Allure ScreenShotRobot and SaveTestLog if (driver != null) {
	  logger.info("Screenshot captured for test case:" +
	  getTestMethodName(iTestResult));
	  try {
		captureScreenshot(getTestMethodName(iTestResult), driver);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  
	  } 
	  	 
	@Override
	public void onTestSkipped(ITestResult iTestResult) {
		logger.info(getTestMethodName(iTestResult) + " test is skipped.");
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
		logger.info("Test failed but it is in defined success ratio " + getTestMethodName(iTestResult));
	}

	public void onTestFailedWithTimeout(ITestResult iTestResult) {
		logger.info(getTestMethodName(iTestResult) + " test is failed with Timeout.");
		onTestFailure(iTestResult);
	}
}