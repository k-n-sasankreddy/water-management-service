package com.knsr.wmgmt.dto.request;

import lombok.Data;

@Data
public class UserRequestDTO {
    // User fields
    private String username;
    private String email;
    private String role;

    // WaterMeter fields
    private String meterLocation;
    private String meterStatus;

    public String getMeterStatus() {
        return meterStatus;
    }

    public void setMeterStatus(String meterStatus) {
        this.meterStatus = meterStatus;
    }

    public String getMeterLocation() {
        return meterLocation;
    }

    public void setMeterLocation(String meterLocation) {
        this.meterLocation = meterLocation;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

