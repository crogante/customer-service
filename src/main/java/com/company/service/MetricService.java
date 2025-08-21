package com.company.service;

import com.company.dto.MetricsResponse;
import com.company.entity.Client;
import java.util.List;

public interface MetricService {
    MetricsResponse calculateMetrics(List<Client> clients);
}
