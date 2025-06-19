package com.knsr.ingestion.dto.request;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class WaterUsageRequestDTO {
    private Long userId;
    private double consumptionLiters;
    private OffsetDateTime timestamp; // Optional; if null, use current time
}

