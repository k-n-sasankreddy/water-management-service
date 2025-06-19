package com.knsr.ingestion.dto.response;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class BillingResponseDTO {
    private Long billingId;
    private Long userId;
    private OffsetDateTime periodStart;
    private OffsetDateTime periodEnd;
    private double amount;
    private String status;
    private OffsetDateTime createdAt;
}

