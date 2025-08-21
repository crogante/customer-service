package com.company.service.impl;

import com.company.dto.ClientRequest;
import com.company.dto.ClientResponse;
import com.company.dto.MetricsResponse;
import com.company.entity.Client;
import com.company.exception.BadRequestException;
import com.company.exception.NotFoundException;
import com.company.messaging.CustomerCreatedEvent;
import com.company.mapper.ClientMapper;
import com.company.messaging.CustomerProducer;
import com.company.repository.ClientRepository;
import com.company.service.ClientService;
import com.company.service.MetricService;
import com.company.util.TimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final CustomerProducer customerProducer;
    private final TimeService timeService;
    private final MetricService metricService;

    @Value("${client.life-expectancy}")
    private int lifeExpectancy;

    @Override
    public ClientResponse createClient(ClientRequest request) {
        // Validación de negocio (no repetimos validaciones de formato)
        if (request.getFirstName() == null || request.getLastName() == null) {
            throw new BadRequestException("Nombre y apellido son obligatorios");
        }

        // Convertimos el request a entidad
        Client client = clientMapper.toEntity(request);
        Client saved = clientRepository.save(client);

        // Enviamos evento a RabbitMQ
        CustomerCreatedEvent event = new CustomerCreatedEvent(
                saved.getId(),
                saved.getFirstName(),
                saved.getLastName(),
                saved.getAge(),
                saved.getBirthDate(),
                timeService.getInstant() // Timestamp para auditoría
        );
        customerProducer.sendClientEvent(event);

        // Devolvemos la respuesta enriquecida
        return enrichWithDerivedData(saved);
    }

    @Override
    public List<ClientResponse> getAllClients() {
        List<Client> clients = clientRepository.findAll();
        if (clients.isEmpty()) {
            throw new NotFoundException("No se encontraron clientes");
        }

        return clients.stream()
                .map(this::enrichWithDerivedData)
                .collect(Collectors.toList());
    }

    @Override
    public MetricsResponse getMetrics() {
        List<Client> clients = clientRepository.findAll();
        return metricService.calculateMetrics(clients);
    }

    private ClientResponse enrichWithDerivedData(Client client) {
        ClientResponse response = clientMapper.toResponse(client);
        response.setEstimatedDeathDate(client.getBirthDate().plusYears(lifeExpectancy));
        return response;
    }
}
