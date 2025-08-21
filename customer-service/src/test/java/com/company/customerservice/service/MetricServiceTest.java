package com.company.customerservice.service;

import com.company.dto.MetricsResponse;
import com.company.entity.Client;
import com.company.service.impl.MetricServiceImpl;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MetricServiceTest {

    @Test
    void testCalculateMetrics_withClients() {
        Client c1 = Client.builder().age(20).build();
        Client c2 = Client.builder().age(30).build();
        Client c3 = Client.builder().age(40).build();
        List<Client> clients = Arrays.asList(c1, c2, c3);
        MetricServiceImpl metricService = new MetricServiceImpl();

        MetricsResponse result = metricService.calculateMetrics(clients);
        assertNotNull(result);
        assertEquals(30.0, result.getAverageAge());
        assertEquals(8.16, Math.round(result.getStandardDeviationAge() * 100.0) / 100.0); // Redondeo a 2 decimales
    }

    @Test
    void testCalculateMetrics_emptyList() {
        MetricServiceImpl metricService = new MetricServiceImpl();
        MetricsResponse result = metricService.calculateMetrics(List.of());
        assertNotNull(result);
        assertEquals(0.0, result.getAverageAge());
        assertEquals(0.0, result.getStandardDeviationAge());
    }
}
