package com.company.service;

import com.company.dto.ClientRequest;
import com.company.dto.ClientResponse;
import com.company.dto.MetricsResponse;

import java.util.List;

public interface ClientService {

    ClientResponse createClient(ClientRequest request);

    List<ClientResponse> getAllClients();

    MetricsResponse getMetrics();
}
