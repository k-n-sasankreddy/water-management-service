package com.knsr.wmgmt.dto.response;

public class RoleMonthlyConsumptionResponseDTO {
    private String role;
    private String month;
    private Double totalConsumptionLiters;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Double getTotalConsumptionLiters() {
        return totalConsumptionLiters;
    }

    public void setTotalConsumptionLiters(Double totalConsumptionLiters) {
        this.totalConsumptionLiters = totalConsumptionLiters;
    }
}