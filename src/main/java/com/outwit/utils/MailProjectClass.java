package com.outwit.utils;
import java.io.File;
import java.io.IOException;
import java.util.Properties;
import javax.activation.*;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class MailProjectClass {

public static void main(String args[]) {

   //final String username = "testautomationacuver@gmail.com";
   final String username = "premkumar.uppu@acuverconsulting.com";
   
   //final String Recipient="testautomationacuver@gmail.com";
   
  // final String Recipient="gautham.alva@acuverconsulting.com";
   
   final String Recipient="sheetal.raina@acuverconsulting.com";
    
    
    final String password = "Gurudatta@222321";

    Properties props = new Properties();
/*
* props.put("mail.smtp.auth", true); props.put("mail.smtp.starttls.enable",
* true); props.put("mail.smtp.host", "smtp.gmail.com");
* props.put("mail.smtp.port", "587");
*/
    
    
    
//    properies.put("mail.smtp.host", "smtp.gmail.com");
//    properies.put("mail.smtp.port", "465");
//    properies.put("mail.smtp.auth", "true");
//    properies.put("mail.smtp.starttls.enable", "true");
//    properies.put("mail.smtp.starttls.required", "true");
//    properies.put("mail.smtp.ssl.protocols", "TLSv1.2");
//    properies.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
   
   
    props.setProperty("mail.transport.protocol", "smtp");    
    props.setProperty("mail.host", "smtp.gmail.com");  
    props.put("mail.smtp.auth", "true");  
   // props.put("mail.smtp.port", "465");  
    props.put("mail.smtp.port", "587");
    props.put("mail.debug", "true");  
    props.put("mail.smtp.socketFactory.port", "587");  
    props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");  
    props.put("mail.smtp.socketFactory.fallback", "false");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.starttls.required", "true");
    props.put("mail.smtp.ssl.protocols", "TLSv1.2");

    props.put("mail.smtp.socketFactory.fallback", "true");
   
   

    Session session = Session.getInstance(props,
            new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

    try {

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(Recipient));
        message.setSubject("ABFRL Automation report");
        message.setText("This is an email sent by JavaMail api.");

        MimeBodyPart messageBodyPart = new MimeBodyPart();

        Multipart multipart = new MimeMultipart();
//  D:\Acuver\AutomationTesting\ABFRL\ABFRLAutomation\ABFRLAutomation\ABFRL\ExtentReport\CrossBrowserTesting.pptx
        messageBodyPart = new MimeBodyPart();
        String localDir = System.getProperty("user.dir");
        String file = localDir+"\\ExtentReport";
        
        
        
        
       
       // C:\Users\Admin\Desktop\Sign.jpg
        String fileName = "Extent.html";
        //DataSource source = new FileDataSource(file);
        
        File att = new File(new File(file), fileName);
        
        System.out.println("**************File Date *************************************" +att);
        try {
			messageBodyPart.attachFile(att);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        try {
			//messageBodyPart.setDataHandler(new DataHandler(source));
			//messageBodyPart.setFileName(fileName);
			multipart.addBodyPart(messageBodyPart);

			message.setContent(multipart);
			
      

      // System.out.println("Sending");

			Transport.send(message);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        

      //  System.out.println("Done");

    } catch (MessagingException e) {
        e.printStackTrace();
    }
  }
}