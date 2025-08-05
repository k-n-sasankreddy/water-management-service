package com.knsr.wmgmt.dto.response;


import lombok.Data;

@Data
public class EfficiencyScoreResponseDTO {
    private String username;
    private double efficiencyScore;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public double getEfficiencyScore() {
        return efficiencyScore;
    }

    public void setEfficiencyScore(double efficiencyScore) {
        this.efficiencyScore = efficiencyScore;
    }
}
