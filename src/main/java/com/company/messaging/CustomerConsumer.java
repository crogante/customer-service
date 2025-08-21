package com.company.messaging;

import com.company.entity.Client;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomerConsumer {

    // Nombre de la queue que configuramos
    public static final String QUEUE_NAME = "customer.queue";

    @RabbitListener(queues = QUEUE_NAME)
    public void receiveMessage(Client client) {
        // Aquí podemos procesar el mensaje: persistir en otra base, enviar notificaciones, etc.
        log.info("Nuevo cliente recibido desde RabbitMQ: {}", client);
    }
}
