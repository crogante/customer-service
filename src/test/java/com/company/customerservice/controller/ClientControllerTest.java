
package com.company.customerservice.controller;

import com.company.controller.ClientController;
import com.company.dto.ClientRequest;
import com.company.dto.ClientResponse;
import com.company.dto.MetricsResponse;
import com.company.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClientControllerTest {

	@Mock
	private ClientService clientService;

	@InjectMocks
	private ClientController clientController;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testCreateClient_success() {
		ClientRequest request = ClientRequest.builder()
				.firstName("Juan")
				.lastName("Pérez")
				.age(30)
				.birthDate(LocalDate.of(1995, 1, 1))
				.build();
		ClientResponse response = ClientResponse.builder()
				.id(1L)
				.firstName("Juan")
				.lastName("Pérez")
				.age(30)
				.birthDate(LocalDate.of(1995, 1, 1))
				.estimatedDeathDate(LocalDate.of(2075, 1, 1))
				.build();
		when(clientService.createClient(request)).thenReturn(response);

		ResponseEntity<ClientResponse> result = clientController.createClient(request);
		assertEquals(200, result.getStatusCodeValue());
		assertEquals(response, result.getBody());
	}

	@Test
	void testGetAllClients_success() {
		ClientResponse c1 = ClientResponse.builder().id(1L).firstName("Ana").lastName("Gómez").age(25).birthDate(LocalDate.of(2000, 5, 10)).estimatedDeathDate(LocalDate.of(2070, 5, 10)).build();
		ClientResponse c2 = ClientResponse.builder().id(2L).firstName("Luis").lastName("Martínez").age(40).birthDate(LocalDate.of(1985, 3, 20)).estimatedDeathDate(LocalDate.of(2060, 3, 20)).build();
		List<ClientResponse> list = Arrays.asList(c1, c2);
		when(clientService.getAllClients()).thenReturn(list);

		ResponseEntity<List<ClientResponse>> result = clientController.getAllClients();
		assertEquals(200, result.getStatusCodeValue());
		assertEquals(list, result.getBody());
	}

	@Test
	void testGetMetrics_success() {
		MetricsResponse metrics = MetricsResponse.builder().averageAge(32.5).standardDeviationAge(7.5).build();
		when(clientService.getMetrics()).thenReturn(metrics);

		ResponseEntity<MetricsResponse> result = clientController.getMetrics();
		assertEquals(200, result.getStatusCodeValue());
		assertEquals(metrics, result.getBody());
	}
}
