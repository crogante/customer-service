package com.company.messaging;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerProducer {

    private final AmqpTemplate amqpTemplate;

    @Value("${rabbitmq.queue.customer}")
    private String customerQueue;

    public void sendClientEvent(CustomerCreatedEvent event) {
        amqpTemplate.convertAndSend(customerQueue, event);
        System.out.println("Customer event sent: " + event);
    }
}
