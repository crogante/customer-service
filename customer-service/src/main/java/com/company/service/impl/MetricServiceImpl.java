package com.company.service.impl;

import com.company.dto.MetricsResponse;
import com.company.entity.Client;
import com.company.service.MetricService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MetricServiceImpl implements MetricService {

    @Override
    public MetricsResponse calculateMetrics(List<Client> clients) {
        if (clients == null || clients.isEmpty()) {
            return MetricsResponse.builder()
                    .averageAge(0.0)
                    .standardDeviationAge(0.0)
                    .build();
        }

        double avg = clients.stream()
                .mapToInt(Client::getAge)
                .average()
                .orElse(0.0);

        double variance = clients.stream()
                .mapToDouble(c -> Math.pow(c.getAge() - avg, 2))
                .average()
                .orElse(0.0);

        double stdDev = Math.sqrt(variance);

        return MetricsResponse.builder()
                .averageAge(avg)
                .standardDeviationAge(stdDev)
                .build();
    }
}