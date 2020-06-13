package com.bridgelabz.bookstore.utils;

import java.io.IOException;
import java.time.ZoneId;
import java.util.Date;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bridgelabz.bookstore.model.Mail;
import com.bridgelabz.bookstore.model.User;

@Component
public class MailTempletService {

	@Autowired
	private RabbitMQSender rabbitMQSender;

	@Autowired
	private Mail mail;

	private String RegistrationTemplate = "";

	public void getTemplate(User request) throws IOException {
		if (RegistrationTemplate.equals("")) {
			RegistrationTemplate = Template.readContentFromTemplet();
		}
		RegistrationTemplate = RegistrationTemplate.replaceAll(Pattern.quote("$%name%"), request.getName());
		mail.setTo(request.getEmail());
		mail.setFrom(EmailService.SENDER_EMAIL_ID);
		mail.setSubject(request.getName() + ", Registration Successful");
		mail.setContent(RegistrationTemplate);
		mail.setSentDate(Date.from(DateUtility.today().atZone(ZoneId.systemDefault()).toInstant()));
		rabbitMQSender.send(mail);
	}

}