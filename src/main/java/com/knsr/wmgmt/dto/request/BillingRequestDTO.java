package com.knsr.wmgmt.dto.request;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class BillingRequestDTO {
    private Long userId;
    private OffsetDateTime periodStart;
    private OffsetDateTime periodEnd;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public OffsetDateTime getPeriodEnd() {
        return periodEnd;
    }

    public void setPeriodEnd(OffsetDateTime periodEnd) {
        this.periodEnd = periodEnd;
    }

    public OffsetDateTime getPeriodStart() {
        return periodStart;
    }

    public void setPeriodStart(OffsetDateTime periodStart) {
        this.periodStart = periodStart;
    }
}
