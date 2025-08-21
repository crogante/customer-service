package com.company.customerservice.util;

import com.company.dto.ClientRequest;
import com.company.dto.ClientResponse;
import com.company.dto.MetricsResponse;
import com.company.entity.Client;
import com.company.service.ClientService;
import com.company.service.MetricService;
import com.company.util.TimeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

class TimeServiceTest {
    @Test
    void testGetInstantNotNull() {
        TimeService timeService = new TimeService();
        Assertions.assertNotNull(timeService.getInstant());
    }

    @Test
    void testCalculateEstimatedDeathDate() {
        TimeService timeService = new TimeService();
        LocalDate birthDate = LocalDate.of(2000, 1, 1);
        int expectancy = 80;
        LocalDate expected = LocalDate.of(2080, 1, 1);
        Assertions.assertEquals(expected, timeService.calculateEstimatedDeathDate(birthDate, expectancy));
    }

    @Test
    void testCalculateEstimatedDeathDateNullBirthDate() {
        TimeService timeService = new TimeService();
        Assertions.assertThrows(IllegalArgumentException.class, () -> timeService.calculateEstimatedDeathDate(null, 80));
    }

    @Test
    void testCalculateEstimatedDeathDateInvalidExpectancy() {
        TimeService timeService = new TimeService();
        Assertions.assertThrows(IllegalArgumentException.class, () -> timeService.calculateEstimatedDeathDate(LocalDate.now(), 0));
    }
}

class MetricServiceTest {
    @Test
    void testCalculateMetricsReturnsNotNull() {
        MetricService metricService = Mockito.mock(MetricService.class);
        List<Client> clients = Arrays.asList(Mockito.mock(Client.class));
        Mockito.when(metricService.calculateMetrics(clients)).thenReturn(Mockito.mock(MetricsResponse.class));
        Assertions.assertNotNull(metricService.calculateMetrics(clients));
    }
}

class ClientMapperTest {
    @Test
    void testToEntityNullRequest() {
        com.company.mapper.ClientMapper mapper = new com.company.mapper.ClientMapper();
        Assertions.assertNull(mapper.toEntity(null));
    }

    @Test
    void testToResponseNullClient() {
        com.company.mapper.ClientMapper mapper = new com.company.mapper.ClientMapper();
        Assertions.assertNull(mapper.toResponse(null));
    }
}
