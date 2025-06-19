package com.knsr.ingestion.dto.request;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class BillingRequestDTO {
    private Long userId;
    private OffsetDateTime periodStart;
    private OffsetDateTime periodEnd;
}
