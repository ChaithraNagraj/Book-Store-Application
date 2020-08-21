package com.bridgelabz.bookstore.utils;

import java.io.IOException;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bridgelabz.bookstore.model.Address;
import com.bridgelabz.bookstore.model.Book;
import com.bridgelabz.bookstore.model.Mail;
import com.bridgelabz.bookstore.model.MyOrderList;
import com.bridgelabz.bookstore.model.Order;
import com.bridgelabz.bookstore.model.User;

@Component
public class OrderTemplateService {

	@Autowired
	private RabbitMQSender rabbitMQSender;

	@Autowired
	private Mail mail;

	private String templateMSG = "";

	public void getTemplate(User request, String token, String path, Address address, Order order, String orders) throws IOException {

		if (templateMSG.equals("")) {
			templateMSG = Template.readContentFromTemplet(path);
		}

		String userAddress = address.getAddress() +", "+ address.getLocality() +", " + address.getCity() +", " + address.getPincode();
		

		
		templateMSG = templateMSG.replaceAll(Pattern.quote("$%name%$"), request.getName());
		templateMSG = templateMSG.replaceAll(Pattern.quote("$%token%$"), token);
		templateMSG = templateMSG.replaceAll(Pattern.quote("$%address%$"), userAddress);
		templateMSG = templateMSG.replaceAll(Pattern.quote("$%orderId%$"), order.getOrderId().toString());
		templateMSG = templateMSG.replaceAll(Pattern.quote("$%orderPrice%$"), order.getOrderPrice().toString());
		templateMSG = templateMSG.replaceAll(Pattern.quote("$%myOrder%$"), orders);
		
		mail.setTo(request.getEmail());
		mail.setFrom(EmailService.SENDER_EMAIL_ID);
		mail.setSubject(request.getName());
		mail.setContent(templateMSG);
		mail.setSentDate(Date.from(DateUtility.today().atZone(ZoneId.systemDefault()).toInstant()));

		rabbitMQSender.send(mail);

		templateMSG = "";
	}
}
