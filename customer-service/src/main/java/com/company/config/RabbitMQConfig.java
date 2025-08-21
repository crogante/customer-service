package com.company.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "customer.exchange";
    public static final String QUEUE_NAME = "customer.queue";
    public static final String ROUTING_KEY = "customer.created";

    // Declaración de la queue
    @Bean
    public Queue customerQueue() {
        return new Queue(QUEUE_NAME, true); // durable = true
    }

    // Declaración del exchange
    @Bean
    public TopicExchange customerExchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    // Binding entre queue y exchange con routing key
    @Bean
    public Binding bindingCustomerQueue(Queue customerQueue, TopicExchange customerExchange) {
        return BindingBuilder.bind(customerQueue).to(customerExchange).with(ROUTING_KEY);
    }
}
