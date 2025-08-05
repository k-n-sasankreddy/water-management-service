package com.knsr.wmgmt.dto.response;


import lombok.Data;

@Data
public class MonthlyUsageResponseDTO {
    private String username;
   // private OffsetDateTime month;
    private String month;
    private double totalConsumption;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public double getTotalConsumption() {
        return totalConsumption;
    }

    public void setTotalConsumption(double totalConsumption) {
        this.totalConsumption = totalConsumption;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}
