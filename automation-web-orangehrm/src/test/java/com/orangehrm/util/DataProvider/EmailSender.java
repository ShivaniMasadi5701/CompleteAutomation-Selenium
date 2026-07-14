	package com.orangehrm.util.DataProvider;

	import java.io.File;
	import java.util.Arrays;
	import java.util.Comparator;
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

	public class EmailSender {

	    public static void sendEmailWithReport() {

	        final String fromEmail = System.getenv("SELENIUM_AUTOMATION_EMAILID");
	        final String password = System.getenv("SELENIUM_AUTOMATION_PASSWORD");
	       	final String toEmail = System.getenv("SELENIUM_AUTOMATION_TO_EMAIL_ID");

	        try {

	            // Reports folder
	            File reportDir = new File(System.getProperty("user.dir") + "/Reports");

	            if (!reportDir.exists()) {
	                System.out.println("Reports folder not found!");
	                return;
	            }

	            // Get latest HTML report
	            File latestReport = Arrays.stream(reportDir.listFiles())
	                    .filter(file -> file.isFile() && file.getName().endsWith(".html"))
	                    .max(Comparator.comparingLong(File::lastModified))
	                    .orElse(null);

	            if (latestReport == null) {
	                System.out.println("No HTML report found in Reports folder!");
	                return;
	            }

	            System.out.println("Latest Report Found: " + latestReport.getAbsolutePath());

	            Properties properties = new Properties();
	            properties.put("mail.smtp.auth", "true");
	            properties.put("mail.smtp.starttls.enable", "true");
	            properties.put("mail.smtp.host", "smtp.gmail.com");
	            properties.put("mail.smtp.port", "587");

	            Session session = Session.getInstance(properties,
	                    new Authenticator() {
	                        @Override
	                        protected PasswordAuthentication getPasswordAuthentication() {
	                            return new PasswordAuthentication(fromEmail, password);
	                        }
	                    });

	            Message message = new MimeMessage(session);
	            message.setFrom(new InternetAddress(fromEmail));
	            message.setRecipients(Message.RecipientType.TO,
	                    InternetAddress.parse(toEmail));
	            message.setSubject("OrangeHRM Automation Execution Report");

	            // Email Body
	            MimeBodyPart bodyPart = new MimeBodyPart();
	            bodyPart.setText(
	                    "Hi,\n\n"
	                    +" Please find the attached OrangeHRM automation execution report.\n\n"
	                    + "This email is auto generated and will receive everytime the USER - "
	                    + "Automation Tester Shivani Masadi runs the code"
	                    +"Thanks.");
	                    		

	            // Attachment
	            MimeBodyPart attachmentPart = new MimeBodyPart();
	            attachmentPart.attachFile(latestReport);

	            Multipart multipart = new MimeMultipart();
	            multipart.addBodyPart(bodyPart);
	            multipart.addBodyPart(attachmentPart);

	            message.setContent(multipart);

	            Transport.send(message);

	            System.out.println("Report Email Sent Successfully!");

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}

