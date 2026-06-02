package com.orangehrm.util.Reports;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.IReporter;
import org.testng.IResultMap;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.xml.XmlSuite;

import com.orangehrm.base.test.BaseTest;

public class AdvancedCustomReportsScreenshot implements  ITestListener, IReporter {
	private final Map<String, String> failedTestScreenshots = new HashMap<>();

    // Capture screenshot when a test fails
    public void onTestFailure(ITestResult result) {
        String screenshotPath = captureScreenshot(result);
        if (screenshotPath != null) {
            result.setAttribute("screenshotPath", screenshotPath);
            failedTestScreenshots.put(result.getName(), screenshotPath);
        }
    }

    private String captureScreenshot(ITestResult result) {
        Object testInstance = result.getInstance();
        if (testInstance instanceof BaseTest) {
            WebDriver driver = ((BaseTest) testInstance).getDriver();

            if (driver == null) {
                System.err.println("Driver is null for test: " + result.getName());
                return null;
            }

            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String screenshotName = result.getName() + "_" + timestamp + ".png";
            String screenshotFolder = System.getProperty("user.dir") + "/test-output/CustomReports/screenshots/";

            new File(screenshotFolder).mkdirs();

            File screenshotFile = new File(screenshotFolder + screenshotName);

            try {
                File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                FileUtils.copyFile(srcFile, screenshotFile);

                System.out.println("Screenshot saved: " + screenshotFile.getAbsolutePath());

                // Return relative path for embedding in HTML report
                return "screenshots/" + screenshotName;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("Test instance is not of type BaseTest for: " + result.getName());
        }
        return null;
    }
    
    @Override
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
    	
    	 String reportFolder = outputDirectory + "/CustomReports/";
         new File(reportFolder).mkdirs();

         String timestamp = new SimpleDateFormat("yyyyMMdd_HHmm").format(new Date());
         String reportFileName = "AdvanceReport_" + timestamp + ".html";
         String reportFilePath = reportFolder + reportFileName;


        // Create and write to new file
        try (FileWriter writer = new FileWriter(reportFilePath))  {

            writer.write("<html><head><title>Advanced TestNG Report</title>");
            writer.write("<style>");
            writer.write("body {font-family: Arial, sans-serif; margin: 20px;}");
            writer.write("h1 {color: #4CAF50;}");
            writer.write("h2 {color: #2196F3;}");
            writer.write("table {width: 100%; border-collapse: collapse; margin: 20px 0;}");
            writer.write("table, th, td {border: 1px solid #ddd;}");
            writer.write("th {background-color: #f4f4f4; color: #333; padding: 10px;}");
            writer.write("td {padding: 10px;}");
            writer.write(".passed {background-color: #d4edda; color: #155724;}");
            writer.write(".failed {background-color: #f8d7da; color: #721c24;}");
            writer.write(".skipped {background-color: #fff3cd; color: #856404;}");
            writer.write("</style>");
            writer.write("</head><body>");

            writer.write("<h1>Execution Summary</h1>");

            for (ISuite suite : suites) {
                writer.write("<h2>Suite: " + suite.getName() + "</h2>");

                for (ISuiteResult result : suite.getResults().values()) {
                    ITestContext context = result.getTestContext();

                    writer.write("<h3>PASSED TESTS</h3>");
                    writeTestDetails(writer, context.getPassedTests(), "passed");

                    writer.write("<h3>FAILED TESTS</h3>");
                    writeTestDetails(writer, context.getFailedTests(), "failed");

                    writer.write("<h3>SKIPPED TESTS</h3>");
                    writeTestDetails(writer, context.getSkippedTests(), "skipped");
                }
            }

            writer.write("</body></html>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void writeTestDetails(FileWriter writer,IResultMap tests, String cssClass) throws IOException {    	
    	if (tests.getAllResults().isEmpty()) {
            writer.write("<p>None</p>");
        } else {
            writer.write("<table>");
            writer.write("<tr><th>Test Name</th><th>Status</th><th>Exception</th><th>Screenshot</tr>");
            for (ITestResult result : tests.getAllResults()) { 
            	 writer.write("<tr class='" + cssClass + "'>");   
                writer.write("<td>" + result.getName() + "</td>");
                writer.write("<td>" + cssClass.toUpperCase() + "</td>");             
                Throwable throwable = result.getThrowable();
                writer.write("<td>" + (throwable != null ? throwable.getMessage() : "N/A") + "</td>");
                
                String screenshotPath = (String) result.getAttribute("screenshotPath");
                if (screenshotPath != null) {
                    writer.write("<td><a href='" + screenshotPath + "' target='_blank'>View Screenshot</a></td>");
                } else {
                    writer.write("<td>N/A</td>");
                }
                writer.write("</tr>");
            }
            writer.write("</table>");
        }
    }
    
    @Override public void onTestStart(ITestResult result) {}
    @Override public void onTestSuccess(ITestResult result) {}
    @Override public void onTestSkipped(ITestResult result) {}
    @Override public void onTestFailedButWithinSuccessPercentage(ITestResult result) {}
    @Override public void onStart(ITestContext context) {}
    @Override public void onFinish(ITestContext context) {}
}
    
    
