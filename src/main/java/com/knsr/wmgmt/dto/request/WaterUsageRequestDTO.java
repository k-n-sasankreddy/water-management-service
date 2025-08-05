package com.knsr.wmgmt.dto.request;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class WaterUsageRequestDTO {
    private Long userId;
    private double consumptionLiters;
    private OffsetDateTime timestamp; // Optional; if null, use current time

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public double getConsumptionLiters() {
        return consumptionLiters;
    }

    public void setConsumptionLiters(double consumptionLiters) {
        this.consumptionLiters = consumptionLiters;
    }

    public OffsetDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(OffsetDateTime timestamp) {
        this.timestamp = timestamp;
    }
}

