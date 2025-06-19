package com.knsr.ingestion.dto.request;

import lombok.Data;

@Data
public class MeterStatusUpdateRequestDTO {
    private Long userId;
    private String newStatus;
}

