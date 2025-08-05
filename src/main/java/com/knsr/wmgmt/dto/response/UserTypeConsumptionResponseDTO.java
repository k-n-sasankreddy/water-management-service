package com.knsr.wmgmt.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
public class UserTypeConsumptionResponseDTO {
    private String userType;
    private double totalConsumptionLiters;

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public double getTotalConsumptionLiters() {
        return totalConsumptionLiters;
    }

    public void setTotalConsumptionLiters(double totalConsumptionLiters) {
        this.totalConsumptionLiters = totalConsumptionLiters;
    }
}

