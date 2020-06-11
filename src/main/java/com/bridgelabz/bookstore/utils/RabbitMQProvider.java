package com.bridgelabz.bookstore.utils;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.bridgelabz.bookstore.constants.Constant;
import com.bridgelabz.bookstore.exception.MailSendingException;
import com.bridgelabz.bookstore.response.MailResponse;



@Component
public class RabbitMQProvider {
	Logger logger = LoggerFactory.getLogger(RabbitMQProvider.class);

	private boolean sendMail(String toEmailId, String subject, String bodyContaint) {
		Authenticator authentication = new Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(Constant.SENDER_EMAIL_ID, Constant.SENDER_PASSWORD);
			}
		};
		Session session = Session.getInstance(mailPropertiesSettings(), authentication);
		try {
			Transport.send(mimeMessageConfiguration(session, toEmailId, subject, bodyContaint));
			return true;
		} catch (MessagingException e) {
			return false;
		}

	}

	private MimeMessage mimeMessageConfiguration(Session session, String toEmail, String subject, String body) {

		MimeMessage mimeMessage = new MimeMessage(session);
		// set message headers
		try {
			mimeMessage.addHeader("Content-type", "text/HTML; charset=UTF-8");
			mimeMessage.addHeader("format", "flowed");
			mimeMessage.addHeader("Content-Transfer-Encoding", "8bit");
			mimeMessage.setFrom(new InternetAddress(Constant.SENDER_EMAIL_ID, " Online BookStore Application"));
			mimeMessage.setReplyTo(InternetAddress.parse(Constant.SENDER_EMAIL_ID, false));
			mimeMessage.setSubject(subject, "UTF-8");
			mimeMessage.setText(body, "UTF-8");
			mimeMessage.setSentDate(new Date());
			mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
		} catch (MessagingException | UnsupportedEncodingException e) {
			logger.info(e.getMessage());
		}
		return mimeMessage;
	}

	
	private Properties mailPropertiesSettings() {
		Properties properties = new Properties();
		properties.put("mail.smtp.host", "smtp.gmail.com"); // SMTP Host
		properties.put("mail.smtp.port", "587"); // TLS Port
		properties.put("mail.smtp.auth", "true"); // enable authentication
		properties.put("mail.smtp.starttls.enable", "true"); // enable STARTTLS
		return properties;
	}

	@RabbitListener(queues = "rmq.rube.queue")
	public void recievedMessage(MailResponse mailObject) throws MailSendingException {

		if (sendMail(mailObject.getEmail(), mailObject.getSubject(), mailObject.getMessage())) {
			return;
		}
		throw new MailSendingException("Opps...Error Sending mail!", 502);
	}
}