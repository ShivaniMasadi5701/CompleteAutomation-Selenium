package com.orangehrm.util.Reports;
import org.testng.*;
import org.testng.xml.XmlSuite;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AdvancedCustomReporter implements IReporter {

    @Override
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
    	
    	if (!outputDirectory.endsWith("/") && !outputDirectory.endsWith("\\")) {
            outputDirectory += "/";
        }
    	String reportFolder = outputDirectory + "CustomReports/";

        // Generate unique file name using timestamp
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String reportFileName = "AdvancedCustomReport_" + timestamp + ".html";

        // Full path to the new report file
        String reportFilePath = reportFolder + reportFileName;

        // Make sure CustomReports folder exists (in case it's deleted manually)
        new java.io.File(reportFolder).mkdirs();

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

    private void writeTestDetails(FileWriter writer, IResultMap tests, String cssClass) throws IOException {
        if (tests.getAllResults().isEmpty()) {
            writer.write("<p>None</p>");
        } else {
            writer.write("<table>");
            writer.write("<tr><th>Test Name</th><th>Status</th><th>Exception</th></tr>");
            for (ITestResult result : tests.getAllResults()) {
                writer.write("<tr class='" + cssClass + "'>");
                writer.write("<td>" + result.getName() + "</td>");
                writer.write("<td>" + cssClass.toUpperCase() + "</td>");
                Throwable throwable = result.getThrowable();
                writer.write("<td>" + (throwable != null ? throwable.getMessage() : "N/A") + "</td>");
                writer.write("</tr>");
            }
            writer.write("</table>");
        }
    }
}
