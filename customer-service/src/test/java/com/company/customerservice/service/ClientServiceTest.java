package com.company.customerservice.service;

import com.company.dto.ClientRequest;
import com.company.dto.ClientResponse;
import com.company.dto.MetricsResponse;
import com.company.entity.Client;
import com.company.exception.BadRequestException;
import com.company.exception.NotFoundException;
import com.company.mapper.ClientMapper;
import com.company.messaging.CustomerProducer;
import com.company.repository.ClientRepository;
import com.company.service.MetricService;
import com.company.util.TimeService;
import com.company.service.impl.ClientServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClientServiceTest {

	@Mock
	private ClientRepository clientRepository;
	@Mock
	private ClientMapper clientMapper;
	@Mock
	private CustomerProducer customerProducer;
	@Mock
	private TimeService timeService;
	@Mock
	private MetricService metricService;

	@InjectMocks
	private ClientServiceImpl clientService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		// Setear expectativa de vida por reflexión
		try {
			var field = ClientServiceImpl.class.getDeclaredField("lifeExpectancy");
			field.setAccessible(true);
			field.set(clientService, 80);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	void testCreateClient_success() {
		ClientRequest request = ClientRequest.builder().firstName("Juan").lastName("Pérez").age(30).birthDate(LocalDate.of(1995, 1, 1)).build();
		Client client = Client.builder().id(1L).firstName("Juan").lastName("Pérez").age(30).birthDate(LocalDate.of(1995, 1, 1)).build();
		Client saved = client;
		ClientResponse response = ClientResponse.builder().id(1L).firstName("Juan").lastName("Pérez").age(30).birthDate(LocalDate.of(1995, 1, 1)).estimatedDeathDate(LocalDate.of(2075, 1, 1)).build();
		when(clientMapper.toEntity(request)).thenReturn(client);
		when(clientRepository.save(client)).thenReturn(saved);
		when(timeService.getInstant()).thenReturn(java.time.Instant.now());
		when(clientMapper.toResponse(saved)).thenReturn(response);

		ClientResponse result = clientService.createClient(request);
		assertEquals(response.getId(), result.getId());
		assertEquals(response.getEstimatedDeathDate(), result.getEstimatedDeathDate());
		verify(customerProducer, times(1)).sendClientEvent(any());
	}

	@Test
	void testCreateClient_missingName_throwsException() {
		ClientRequest request = ClientRequest.builder().firstName(null).lastName(null).age(30).birthDate(LocalDate.of(1995, 1, 1)).build();
		assertThrows(BadRequestException.class, () -> clientService.createClient(request));
	}

	@Test
	void testGetAllClients_success() {
		Client c1 = Client.builder().id(1L).firstName("Ana").lastName("Gómez").age(25).birthDate(LocalDate.of(2000, 5, 10)).build();
		Client c2 = Client.builder().id(2L).firstName("Luis").lastName("Martínez").age(40).birthDate(LocalDate.of(1985, 3, 20)).build();
		List<Client> clients = Arrays.asList(c1, c2);
		ClientResponse r1 = ClientResponse.builder().id(1L).firstName("Ana").lastName("Gómez").age(25).birthDate(LocalDate.of(2000, 5, 10)).estimatedDeathDate(LocalDate.of(2080, 5, 10)).build();
		ClientResponse r2 = ClientResponse.builder().id(2L).firstName("Luis").lastName("Martínez").age(40).birthDate(LocalDate.of(1985, 3, 20)).estimatedDeathDate(LocalDate.of(2065, 3, 20)).build();
		when(clientRepository.findAll()).thenReturn(clients);
		when(clientMapper.toResponse(c1)).thenReturn(r1);
		when(clientMapper.toResponse(c2)).thenReturn(r2);

		List<ClientResponse> result = clientService.getAllClients();
		assertEquals(2, result.size());
		assertEquals(r1, result.get(0));
		assertEquals(r2, result.get(1));
	}

	@Test
	void testGetAllClients_empty_throwsException() {
		when(clientRepository.findAll()).thenReturn(List.of());
		assertThrows(NotFoundException.class, () -> clientService.getAllClients());
	}

	@Test
	void testGetMetrics_success() {
		Client c1 = Client.builder().id(1L).firstName("Ana").lastName("Gómez").age(25).birthDate(LocalDate.of(2000, 5, 10)).build();
		Client c2 = Client.builder().id(2L).firstName("Luis").lastName("Martínez").age(40).birthDate(LocalDate.of(1985, 3, 20)).build();
		List<Client> clients = Arrays.asList(c1, c2);
		MetricsResponse metrics = MetricsResponse.builder().averageAge(32.5).standardDeviationAge(7.5).build();
		when(clientRepository.findAll()).thenReturn(clients);
		when(metricService.calculateMetrics(clients)).thenReturn(metrics);

		MetricsResponse result = clientService.getMetrics();
		assertEquals(metrics, result);
	}
}
