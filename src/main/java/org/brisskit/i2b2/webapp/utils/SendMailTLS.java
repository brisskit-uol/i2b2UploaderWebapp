package org.brisskit.i2b2.webapp.utils;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
 
/** SendMailTLS
*
* @author Shajid Issa
* @version 1.0 
* @date 23/03/2015
*/

public class SendMailTLS {
 
	final static Logger logger = LogManager.getLogger(SendMailTLS.class);	
	
	final static String mailserver = "smtp.gmail.com";
	final static String port = "587";
	final static String sendmail = "brisskit.i2b2@gmail.com"; // see wiki
	final static String email_password = "<Password>";
	final static String auth = "true";
	final static String starttls_enable = "true";
	
	public static void sendMail(String subject, String destination_email, String content) {
	
		logger.info("Trying to send email");
		logger.info("mailserver " + mailserver);
		logger.info("port " + port);
		logger.info("sendmail " + sendmail);
		logger.info("email_password " + email_password);
		logger.info("auth " + auth);
		logger.info("starttls_enable " + starttls_enable);
		logger.info("subject " + subject);
		logger.info("destination_email " + destination_email);
		logger.info("content " + content);
		
		final String username = sendmail;
		final String password = email_password;
 
		Properties props = new Properties();
		props.put("mail.smtp.auth", auth);
		props.put("mail.smtp.starttls.enable", starttls_enable);
		props.put("mail.smtp.host", mailserver);
		props.put("mail.smtp.port", port);
 
		logger.info("1 ");
		
		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });
		 		
		try {
 
			Message message = new MimeMessage(session);
			logger.info("3 ");
			message.setFrom(new InternetAddress(sendmail));
			logger.info("4 ");
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(destination_email));
			message.setSubject(subject);
			message.setText(content);
 
			logger.info("5 ");
			
			Transport.send(message);
 
			logger.info("Done");
 
		} catch (MessagingException e) {
			logger.info("MessagingException " + e.getStackTrace());
			logger.info("MessagingException " + e);
			logger.info("MessagingException " + e.toString());
			logger.info("MessagingException " + e.getMessage());
			logger.info("MessagingException " + e.getLocalizedMessage());
			
			throw new RuntimeException(e);
		}
	}
		
}