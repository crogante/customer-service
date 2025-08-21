package com.company.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MetricsResponse {
    private Double averageAge;
    private Double standardDeviationAge;
}
