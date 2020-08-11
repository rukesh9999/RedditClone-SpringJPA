package com.ReditClone.services;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.ReditClone.Exception.SpringRedditException;
import com.ReditClone.dto.NotificationEmail;

@Service
public class MailService {

	 private final  JavaMailSender javaMailSender;
	 private final MailContentBuilder mailContentBuilder;
	
	 private Logger mylogger=Logger.getLogger(getClass().getName());	

	 @Autowired
	 public MailService(JavaMailSender javaMailSender, MailContentBuilder mailContentBuilder) {	
		this.javaMailSender = javaMailSender;
		this.mailContentBuilder = mailContentBuilder;
	}
	 
	 @Async
	 void sendMail(NotificationEmail notificationEmail)
	 {
			
			
			   MimeMessagePreparator messagePreparator = MimeMessage  ->{
				 
				 MimeMessageHelper messageHelper = new MimeMessageHelper(MimeMessage);
				 
				 messageHelper.setFrom("rukesh235@gmail.com");
				 messageHelper.setTo(notificationEmail.getRecipient());
				 messageHelper.setSubject(notificationEmail.getSubject());
				 messageHelper.setText(mailContentBuilder.build(notificationEmail.getBody()));;
				 
			 };
		
			 try {
			 mylogger.info(" Trying to send  Mail...!!");

			 javaMailSender.send(messagePreparator);

			 mylogger.info("Action Mail Sent!!");
			 }
			 catch (MailException e) {
				// TODO: handle exception
				 
				 throw new SpringRedditException("Exception occured while sending a mail to ...."+notificationEmail.getRecipient()); 
			}
			 
	 }
	 
	 
	 
	 
	 
}
