package com.outwit.utils;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.outwit.base.BasePageActions;

public class JavaEmail extends BasePageActions {

	static Properties emailProperties;
	static Session mailSession;
	static MimeMessage emailMessage;
	


	
	public static void sendReport() throws AddressException, MessagingException{
		setMailServerProperties();
		createEmailMessage();
	    sendEmail();
		
	}
	
	
	

	public static void setMailServerProperties() {

		String emailPort = "587";//gmail's smtp port

		emailProperties = System.getProperties();
		emailProperties.put("mail.smtp.port", emailPort);
		emailProperties.put("mail.smtp.auth", "true");
		emailProperties.put("mail.smtp.starttls.enable", "true");

	}

	public static void createEmailMessage() throws AddressException,
			MessagingException {
		
		
		
		//String localDirforEmail = System.getProperty("user.dir");
        //String Emailfilename = localDirforEmail +"\\TestData\\email";
        
	

	//	String[] toEmails = {"ananth.k@acuverconsulting.com","shreekant.rs@acuverconsulting.com"};
		String emailSubject = "Hybris Report";
		String emailBody = "This is an email sent by JavaMail api.";

		mailSession = Session.getDefaultInstance(emailProperties, null);
		emailMessage = new MimeMessage(mailSession);

		for (int i = 0; i < EmaiIds.length; i++) {
			emailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(EmaiIds[i]));
		}

		
		 // Create the message part
        BodyPart messageBodyPart = new MimeBodyPart();
        
        // Create a multipar message
        Multipart multipart = new MimeMultipart();
        
    /*    BodyPart messageBodyPart1 = new MimeBodyPart();  
        String s="Hi all,\t"
        		+ "Please find the attached test automation report";
        messageBodyPart1.setText(s);*/ 
        
        BodyPart htmlBodyPart = new MimeBodyPart(); //4
        htmlBodyPart.setContent("<p>Hi All,</p><p>Please find the attached automation report.</p><p>Thanks,</p><p>Automation Team</p>" , "text/html"); //5
      
        
        
		messageBodyPart = new MimeBodyPart();
		String localDir = System.getProperty("user.dir");
        String filename = localDir +"\\Folder.zip";
       // logger.info("Filename :"+filename);
        DataSource source = new FileDataSource(filename);
        messageBodyPart.setDataHandler(new DataHandler(source));
    //  messageBodyPart.setText(emailBody);
        messageBodyPart.setFileName("AutomationReport.zip");
        
        multipart.addBodyPart(messageBodyPart);
        multipart.addBodyPart(htmlBodyPart);
     //   multipart.addBodyPart(messageBodyPart1);
        
		
		
		emailMessage.setSubject(emailSubject);
	//	emailMessage.setDescription(emailBody);
		emailMessage.setContent(multipart);
	//	emailMessage.setContent(emailBody, "text/html");//for a html email
	//    emailMessage.setText(emailBody);// for a text email
		
		
		

	}

	public static void sendEmail() throws AddressException, MessagingException {

		String emailHost = "smtp.gmail.com";
		String fromUser = "lmautomationacuver@gmail.com";//just the id alone without @gmail.com
		String fromUserEmailPassword = "Landmark@31";

		Transport transport = mailSession.getTransport("smtp");
		
		
		transport.connect(emailHost, fromUser, fromUserEmailPassword);
		transport.sendMessage(emailMessage,emailMessage.getAllRecipients());
		transport.close();
		//logger.info("Email sent successfully.");
	}

}