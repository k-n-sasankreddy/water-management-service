package com.knsr.ingestion.dto.request;

import lombok.Data;

@Data
public class BillingPaymentRequestDTO {
    private Long billingId;
    private String status; // e.g., "PAID"
}
