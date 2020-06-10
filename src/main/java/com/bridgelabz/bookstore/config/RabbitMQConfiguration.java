package com.bridgelabz.bookstore.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

	@Autowired
	private ConnectionFactory rabbitConnectionFactory;

	@Bean
	public DirectExchange rubeExchange() {
		return new DirectExchange("rmq.rube.exchange", true, false);
	}

	@Bean
	public Queue rubeQueue() {
		return new Queue("rmq.rube.queue", true);
	}

	@Bean
	public Binding rubeExchangeBinding(DirectExchange rubeExchange, Queue rubeQueue) {
		return BindingBuilder.bind(rubeQueue).to(rubeExchange).with("rube.key");
	}

	@Bean
	public RabbitTemplate rubeExchangeTemplate() {
		RabbitTemplate rabbitTemplet = new RabbitTemplate(rabbitConnectionFactory);
		rabbitTemplet.setConnectionFactory(rabbitConnectionFactory);
		rabbitTemplet.setExchange("rmq.rube.exchange");
		rabbitTemplet.setRoutingKey("rube.key");
		return rabbitTemplet;
	}

}