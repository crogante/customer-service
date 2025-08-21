package com.company.messaging;

import java.time.Instant;
import java.time.LocalDate;

public record CustomerCreatedEvent(
        Long clientId,
        String firstName,
        String lastName,
        int age,
        LocalDate birthDate,
        Instant createdAt
) {}
