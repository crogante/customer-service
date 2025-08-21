package com.company.customerservice.repository;

import com.company.entity.Client;
import com.company.repository.ClientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ClientRepositoryTest {

	@Autowired
	private ClientRepository clientRepository;

	@Test
	void testSaveAndFindById() {
		Client client = Client.builder()
				.firstName("Carlos")
				.lastName("Ramírez")
				.age(28)
				.birthDate(LocalDate.of(1997, 2, 15))
				.build();
		Client saved = clientRepository.save(client);
		Optional<Client> found = clientRepository.findById(saved.getId());
		assertTrue(found.isPresent());
		assertEquals("Carlos", found.get().getFirstName());
	}

	@Test
	void testFindAll() {
		Client c1 = Client.builder().firstName("Ana").lastName("Gómez").age(25).birthDate(LocalDate.of(2000, 5, 10)).build();
		Client c2 = Client.builder().firstName("Luis").lastName("Martínez").age(40).birthDate(LocalDate.of(1985, 3, 20)).build();
		clientRepository.save(c1);
		clientRepository.save(c2);
		List<Client> clients = clientRepository.findAll();
		assertEquals(2, clients.size());
	}

	@Test
	void testDelete() {
		Client client = Client.builder().firstName("Pedro").lastName("López").age(35).birthDate(LocalDate.of(1990, 7, 7)).build();
		Client saved = clientRepository.save(client);
		clientRepository.deleteById(saved.getId());
		Optional<Client> found = clientRepository.findById(saved.getId());
		assertFalse(found.isPresent());
	}
}
