package com.bridgelabz.bookstore.utils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.bridgelabz.bookstore.response.MailResponse;



@Component
public class RabbitMqSender {
	
	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Value("rmq.rube.exchange")
	private String exchange;

	@Value("rube.key")
	private String routingkey;

	public boolean send(MailResponse message) {
		rabbitTemplate.convertAndSend(exchange, routingkey, message);
		return true;
	}

}