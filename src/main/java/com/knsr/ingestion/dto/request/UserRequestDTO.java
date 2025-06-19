package com.knsr.ingestion.dto.request;

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
}

