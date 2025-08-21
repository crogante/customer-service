package com.company.util;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;

@Service
public class TimeService {

    /**
     * Devuelve el instante actual.
     */
    public Instant getInstant() {
        return Instant.now();
    }

    /**
     * Calcula la fecha estimada de muerte a partir de la fecha de nacimiento
     * y la expectativa de vida en años.
     *
     * @param birthDate Fecha de nacimiento
     * @param lifeExpectancy Expectativa de vida en años
     * @return Fecha estimada de muerte
     */
    public LocalDate calculateEstimatedDeathDate(LocalDate birthDate, int lifeExpectancy) {
        if (birthDate == null) {
            throw new IllegalArgumentException("birthDate no puede ser null");
        }
        if (lifeExpectancy <= 0) {
            throw new IllegalArgumentException("lifeExpectancy debe ser mayor a 0");
        }
        return birthDate.plusYears(lifeExpectancy);
    }
}
