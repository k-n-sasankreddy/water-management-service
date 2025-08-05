package com.knsr.wmgmt.dto.response;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class AnomalyUsageResponseDTO {
    private String username;
    private OffsetDateTime timestamp;
    private double consumptionLiters;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
