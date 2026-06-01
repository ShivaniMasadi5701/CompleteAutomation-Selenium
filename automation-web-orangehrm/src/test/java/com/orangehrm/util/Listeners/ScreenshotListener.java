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
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import com.orangehrm.base.test.BaseTest;

public class ScreenshotListener extends TestListenerAdapter {

	private static final Logger logger = Logger.getLogger(ScreenshotListener.class.getName());

	private static String getTestMethodName(ITestResult iTestResult) {
		return iTestResult.getMethod().getConstructorOrMethod().getName();
	}

	public String captureScreenshot(String methodName, WebDriver driver) throws IOException {
		String screenshotPath = null;
		try {
			String timestamp = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss").format(Calendar.getInstance().getTime());
			String screenshotFolder = "C:\\Users\\DELL\\eclipse-workspace\\eclipse-HONO\\automation-web-orangehrm\\test-output\\screenshot-reports\\failure_screenshots";

			File directory = new File(screenshotFolder);
			if (!directory.exists()) {
				directory.mkdirs();
			}

			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			File destFile = new File(screenshotFolder + File.separator + methodName + "_" + timestamp + ".png");

			FileUtils.copyFile(scrFile, destFile);
			screenshotPath = destFile.getAbsolutePath();

			System.out.println("Screenshot saved: " + screenshotPath);
		} catch (IOException e) {
			logger.error("Error while capturing screenshot: " + e.getMessage(), e);
			throw e;
		}
		return screenshotPath;
	}

	@Override
	public void onTestFailure(ITestResult iTestResult) {
		logger.info(getTestMethodName(iTestResult) + " test is failed.");

		// Get driver from BaseTest
		Object testClass = iTestResult.getInstance();
		WebDriver driver = ((BaseTest) testClass).getDriver();

		if (driver != null) {
			logger.info("Screenshot captured for test case: " + getTestMethodName(iTestResult));
			try {
				String screenshotPath = captureScreenshot(getTestMethodName(iTestResult), driver);
				logger.info("**************Screenshot Listener: " + screenshotPath);
				// Store the path in ITestResult
				iTestResult.setAttribute("screenshotPath", screenshotPath);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
