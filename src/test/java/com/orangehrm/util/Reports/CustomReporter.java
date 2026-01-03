package com.orangehrm.util.Reports;

import org.testng.*;
import org.testng.xml.XmlSuite;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CustomReporter implements IReporter {

 
	 @Override
	    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {

     	if (!outputDirectory.endsWith("/") && !outputDirectory.endsWith("\\")) {
            outputDirectory += "/";
        }
    	String reportFolder = outputDirectory + "CustomReports/";

        // Generate unique file name using timestamp
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String reportFileName = "CustomReport_" + timestamp + ".html";

        // Full path to the new report file
        String reportFilePath = reportFolder + reportFileName;

        // Make sure CustomReports folder exists (in case it's deleted manually)
        new java.io.File(reportFolder).mkdirs();

        // Create and write to new file
        try (FileWriter writer = new FileWriter(reportFilePath))  {

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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeTestDetails(FileWriter writer, String status, IResultMap tests) throws IOException {
        writer.write("<h3>" + status + "</h3>");
        if (tests.getAllResults().isEmpty()) {
            writer.write("<p>None</p>");
        } else {
            writer.write("<table border='1'><tr><th>Test Name</th><th>Status</th><th>Exception</th></tr>");
            for (ITestResult result : tests.getAllResults()) {
                writer.write("<tr>");
                writer.write("<td>" + result.getName() + "</td>");
                writer.write("<td>" + status + "</td>");
                Throwable throwable = result.getThrowable();
                writer.write("<td>" + (throwable != null ? throwable.getMessage() : "N/A") + "</td>");
                writer.write("</tr>");
            }
            writer.write("</table>");
        }
    }
}

