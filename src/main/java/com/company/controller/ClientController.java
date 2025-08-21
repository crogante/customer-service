package com.company.controller;

import com.company.dto.ClientRequest;
import com.company.dto.ClientResponse;
import com.company.dto.MetricsResponse;
import com.company.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    /**
     * Crear un nuevo cliente
     */
    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ClientResponse> createClient(@Valid @RequestBody ClientRequest request) {
        return ResponseEntity.ok(clientService.createClient(request));
    }

    /**
     * Listar todos los clientes
     */
    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<ClientResponse>> getAllClients() {
        return ResponseEntity.ok(clientService.getAllClients());
    }

    /**
     * Obtener métricas: promedio y desviación estándar de las edades
     */
    @GetMapping("/metrics")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<MetricsResponse> getMetrics() {
        return ResponseEntity.ok(clientService.getMetrics());
    }
}
