package com.orangehrm.util.Reports;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.testng.*;
import org.testng.xml.XmlSuite;

import com.orangehrm.base.test.BaseTest;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CustomReportScreenshotListener implements ITestListener, IReporter {

    private final Map<String, String> failedTestScreenshots = new HashMap<>();

    // Capture screenshot when a test fails
    @Override
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

    // Final report generation
    @Override
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
        String reportFolder = outputDirectory + "/CustomReports/";
        new File(reportFolder).mkdirs();

        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String reportFileName = "CustomReport_" + timestamp + ".html";
        String reportFilePath = reportFolder + reportFileName;

        try (FileWriter writer = new FileWriter(reportFilePath)) {
            writer.write("<html><head><title>Custom TestNG Report</title></head><body>");
            writer.write("<h1>Execution Summary</h1>");

            for (ISuite suite : suites) {
                writer.write("<h2>Suite: " + suite.getName() + "</h2>");

                for (ISuiteResult result : suite.getResults().values()) {
                    ITestContext context = result.getTestContext();

                    writeTestDetails(writer, "PASSED TESTS", context.getPassedTests());
                    writeTestDetails(writer, "FAILED TESTS", context.getFailedTests());
                    writeTestDetails(writer, "SKIPPED TESTS", context.getSkippedTests());
                }
            }

            writer.write("</body></html>");
            System.out.println("Custom report generated: " + reportFilePath);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeTestDetails(FileWriter writer, String status, IResultMap tests) throws IOException {
        writer.write("<h3>" + status + "</h3>");
        if (tests.getAllResults().isEmpty()) {
            writer.write("<p>None</p>");
        } else {
            writer.write("<table border='1'><tr><th>Test Name</th><th>Status</th><th>Exception</th><th>Screenshot</th></tr>");
            for (ITestResult result : tests.getAllResults()) {
                writer.write("<tr>");
                writer.write("<td>" + result.getName() + "</td>");
                writer.write("<td>" + status + "</td>");

                Throwable throwable = result.getThrowable();
                writer.write("<td>" + (throwable != null ? throwable.getMessage() : "N/A") + "</td>");

                String screenshotPath = (String) result.getAttribute("screenshotPath");
                System.out.println("*********Path"+screenshotPath);
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

    // Unused ITestListener methods (optional)
    @Override public void onTestStart(ITestResult result) {}
    @Override public void onTestSuccess(ITestResult result) {}
    @Override public void onTestSkipped(ITestResult result) {}
    @Override public void onTestFailedButWithinSuccessPercentage(ITestResult result) {}
    @Override public void onStart(ITestContext context) {}
    @Override public void onFinish(ITestContext context) {}
}
