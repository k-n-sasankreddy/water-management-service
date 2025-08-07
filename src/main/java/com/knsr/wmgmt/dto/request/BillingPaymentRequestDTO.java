package com.knsr.wmgmt.dto.request;

import lombok.Data;

@Data
public class BillingPaymentRequestDTO {
    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
