package com.orangehrm.util.Reports;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.*;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.orangehrm.base.test.BaseTest;

public class ExtentReportsScreenshot implements ITestListener {

    private static final Logger logger = Logger.getLogger(ExtentReportsScreenshot.class);
    private static ExtentReports extent = new ExtentReports();
    private static ExtentSparkReporter sparkReporter;
    private static Map<String, ExtentTest> testMap = new HashMap<>();

    @Override
    public void onStart(ITestContext context) {
        if (sparkReporter == null) {
            String timeStamp = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date());
            String reportFile = System.getProperty("user.dir") + "/Reports/OrangeHRM_Report_" + timeStamp + ".html";
            sparkReporter = new ExtentSparkReporter(reportFile);

            sparkReporter.config().setDocumentTitle("OrangeHRM Automation Report");
            sparkReporter.config().setReportName("Test Execution Report");
            sparkReporter.config().setTheme(Theme.DARK);

            extent.attachReporter(sparkReporter);
            extent.setSystemInfo("Tester", "Shivani");
            extent.setSystemInfo("Environment", "QA");

            String browser = context.getCurrentXmlTest().getParameter("browser");
            extent.setSystemInfo("Browser", browser);
        }
    }

    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getTestClass().getName() + " - " + result.getMethod().getMethodName();
        ExtentTest test = extent.createTest(testName);
        testMap.put(result.getMethod().getMethodName(), test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentTest test = testMap.get(result.getMethod().getMethodName());
        if (test != null) {
            test.log(Status.PASS, "Test Passed");
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentTest test = testMap.get(result.getMethod().getMethodName());
        if (test != null) {
            test.log(Status.FAIL, "Test Failed: " + result.getThrowable());

            // Capture screenshot and attach to Extent Report
            Object testClass = result.getInstance();
            WebDriver driver = ((BaseTest) testClass).getDriver();

            if (driver != null) {
                logger.info("Capturing screenshot for failed test: " + result.getMethod().getMethodName());
                String screenshotPath = captureScreenshot(result.getMethod().getMethodName(), driver);
                test.addScreenCaptureFromPath(screenshotPath, "Failure Screenshot");

            } else {
                logger.warn("Driver is null, cannot capture screenshot for " + result.getMethod().getMethodName());
            }
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentTest test = testMap.get(result.getMethod().getMethodName());
        if (test != null) {
            test.log(Status.SKIP, "Test Skipped");
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        if (extent != null) {
            extent.flush();
        }
    }

    private String captureScreenshot(String methodName, WebDriver driver) {
        String timeStamp = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss").format(Calendar.getInstance().getTime());

        String projectPath = System.getProperty("user.dir");
        String screenshotFolder = projectPath + "/test-output/screenshot-reports/failure_screenshots";
        File directory = new File(screenshotFolder);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String screenshotPath = screenshotFolder + File.separator + methodName + "_" + timeStamp + ".png";
        try {
            File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(scrFile, new File(screenshotPath));
            logger.info("Screenshot saved at: " + screenshotPath);
        } catch (IOException e) {
            logger.error("Error capturing screenshot: " + e.getMessage(), e);
        }

        // This is for Extent Report attachment - make relative path
        String relativePath = "./../test-output/screenshot-reports/failure_screenshots/" + methodName + "_" + timeStamp + ".png";
        return relativePath;  // return relative path so Extent Report can find it
    }

}
