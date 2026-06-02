package com.orangehrm.util.DataProvider;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.Multipart;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

public class EmailReportSender {

    public static void sendEmailWithReport() {
        // Email Config
        // App password (or actual password if less secure apps allowed)
        final String fromEmail = System.getenv("SELENIUM_AUTOMATION_EMAILID");
        final String password = System.getenv("SELENIUM_AUTOMATION_PASSWORD");
        final String toEmail=System.getenv("SELENIUM_AUTOMATION_TO_EMAIL_ID");// Recipient
        
       
       // String reportFilePath = System.getProperty("user.dir") + "/Reports/OrangeHRM_Report_.html";
        
    	String reportFolder = "/Reports/";

        // Generate unique file name using timestamp
        String timestamp = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date());
        String reportFileName = "OrangeHRM_Report_" + timestamp + ".html";

        // Full path to the new report file
        String reportFilePath = reportFolder + reportFileName;

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");  // Force TLS 1.2

        // Create Session
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        try {
            // Create email message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("TestNG Execution Report");

            // Create email body
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(" hai  Please find the attached TestNG emailable report.");

            // Attach file
            MimeBodyPart attachmentPart = new MimeBodyPart();
            File file = new File(reportFilePath);
            attachmentPart.attachFile(file);

            // Combine parts
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            multipart.addBodyPart(attachmentPart);

            message.setContent(multipart);

            // Send email
            Transport.send(message);

            System.out.println("Report Email Sent Successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
