package com.knsr.ingestion.dto.response;

import lombok.Data;
import java.time.OffsetDateTime;

@Data
public class UsageResponseDTO {
    private Long meterId;
    private OffsetDateTime timestamp;
    private double consumptionLiters;
    private double costInRupees;
}
