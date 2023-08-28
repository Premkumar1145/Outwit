package com.outwit.reports;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.outwit.base.BasePageActions;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


public class Utility extends BasePageActions {

	public static String captureScreen(String fileName) throws Exception {

		String dest = fileName + ".jpg";
		// String dest = "./ExtentReport/" + fileName + ".jpg";
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Rectangle screenRectangle = new Rectangle(screenSize);
		Robot robot = new Robot();
		BufferedImage image = robot.createScreenCapture(screenRectangle);
		logger.info("The path is " + System.getProperty("user.dir"));
		ImageIO.write(image, "jpg", new File(System.getProperty("user.dir") + "/ExtentReport/" + fileName + ".jpg"));

		logger.info("Screen shot taken");

		return dest;
	}

	public static String getScreenhot(String screenshotName) throws Exception {
		String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		// after execution, you could see a folder "FailedTestsScreenshots"
		// under src folder
		String destination = System.getProperty("user.dir") + "/ExtentReport/" + screenshotName+"_Date_"+dateName
				+ ".png";
		
		File finalDestination = new File(destination);
		FileUtils.copyFile(source, finalDestination);
		return destination;
	}
	
	public static void emailNotification() {
		
		
		Properties props = new Properties();

		// this will set host of server- you can change based on your requirement 
		props.put("mail.smtp.host", "smtp.gmail.com");

		// set the port of socket factory 
		props.put("mail.smtp.socketFactory.port", "465");

		// set socket factory
		props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");

		// set the authentication to true
		props.put("mail.smtp.auth", "true");

		// set the port of SMTP server
		props.put("mail.smtp.port", "465");

		// This will handle the complete authentication
		Session session = Session.getDefaultInstance(props,

				new javax.mail.Authenticator() {

					protected PasswordAuthentication getPasswordAuthentication() {

					return new PasswordAuthentication("u2ukumar@gmail.com", "Gurudatta@1145");

					}

				});

		try {

			// Create object of MimeMessage class
			Message message = new MimeMessage(session);

			// Set the from address
			message.setFrom(new InternetAddress("u2ukumar@gmail.com"));

			// Set the recipient address
			message.setRecipients(Message.RecipientType.TO,InternetAddress.parse("p2mkumar@gmail.com"));
            
                        // Add the subject link
			message.setSubject("ABFRL Automation TestExecution Reports");

			// Create object to add multimedia type content
			BodyPart messageBodyPart1 = new MimeBodyPart();

			// Set the body of email
			messageBodyPart1.setText("Please find the test results in attached document");

			// Create another object to add another content
			MimeBodyPart messageBodyPart2 = new MimeBodyPart();

			// Mention the file which you want to send
			String filename = "D:\\Acuver\\AutomationTesting\\ABFRL\\ABFRLAutomation\\ABFRLAutomation\\ABFRL\\ExtentReport\\Extent.html";

			// Create data source and pass the filename
			DataSource source = new FileDataSource(filename);

			// set the handler
			messageBodyPart2.setDataHandler(new DataHandler(source));

			// set the file
			messageBodyPart2.setFileName(filename);

			// Create object of MimeMultipart class
			Multipart multipart = new MimeMultipart();

			// add body part 1
			multipart.addBodyPart(messageBodyPart2);

			// add body part 2
			multipart.addBodyPart(messageBodyPart1);

			// set the content
			message.setContent(multipart);

			// finally send the email
			Transport.send(message);

			System.out.println("=====Email Sent=====");

		} catch (MessagingException e) {

			throw new RuntimeException(e);

		}
		
	}
}
