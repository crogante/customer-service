package com.company.mapper;

import com.company.entity.Client;
import com.company.dto.ClientRequest;
import com.company.dto.ClientResponse;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper {

    public Client toEntity(ClientRequest request) {
        if (request == null) {
            return null;
        }
        return Client.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .age(request.getAge())
                .birthDate(request.getBirthDate())
                .build();
    }

    public ClientResponse toResponse(Client client) {
        if (client == null) {
            return null;
        }

        return ClientResponse.builder()
                .id(client.getId())
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .age(client.getAge())
                .birthDate(client.getBirthDate())
                .build();
    }
}
