package com.orangehrm.util.Reports;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ExtentReportsManager implements ITestListener {

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
        testMap.put(result.getMethod().getMethodName(), test); // Associate ExtentTest with the method
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
            extent.flush(); // Only flush once at the end of the whole suite
        }
    }
}
