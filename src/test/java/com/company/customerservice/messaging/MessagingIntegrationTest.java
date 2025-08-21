package com.company.customerservice.messaging;

import com.company.entity.Client;
import com.company.messaging.CustomerConsumer;
import com.company.messaging.CustomerCreatedEvent;
import com.company.messaging.CustomerProducer;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class MessagingIntegrationTest {

	@Mock
	private AmqpTemplate amqpTemplate;

	@InjectMocks
	private CustomerProducer customerProducer;

	@Test
	void testSendClientEvent_sendsMessageToQueue() {
		MockitoAnnotations.openMocks(this);
		CustomerCreatedEvent event = new CustomerCreatedEvent(
				1L, "Juan", "Pérez", 30,
				LocalDate.of(1995, 1, 1), Instant.now()
		);
		String queueName = "customer.queue";
		// Simular el valor de la propiedad
		customerProducer = new CustomerProducer(amqpTemplate);
		// Usar reflexión para setear el valor de la queue
		try {
			var field = CustomerProducer.class.getDeclaredField("customerQueue");
			field.setAccessible(true);
			field.set(customerProducer, queueName);
		} catch (Exception e) {
			fail("No se pudo setear el nombre de la queue");
		}

		customerProducer.sendClientEvent(event);
		verify(amqpTemplate, times(1)).convertAndSend(queueName, event);
	}

	@Test
	void testReceiveMessage_processesClient() {
		Client client = Client.builder()
				.id(1L)
				.firstName("Ana")
				.lastName("Gómez")
				.age(25)
				.birthDate(LocalDate.of(2000, 5, 10))
				.build();
		CustomerConsumer consumer = new CustomerConsumer();
		// Solo verificamos que no lanza excepción y el log se ejecuta
		assertDoesNotThrow(() -> consumer.receiveMessage(client));
	}
}
