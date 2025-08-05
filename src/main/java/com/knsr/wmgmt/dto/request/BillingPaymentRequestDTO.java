package com.knsr.wmgmt.dto.request;

import lombok.Data;

@Data
public class BillingPaymentRequestDTO {
    private Long billingId;
    private String status; // e.g., "PAID"

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getBillingId() {
        return billingId;
    }

    public void setBillingId(Long billingId) {
        this.billingId = billingId;
    }
}
