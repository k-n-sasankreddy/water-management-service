package com.knsr.ingestion.dto.response;


import lombok.Data;
import java.time.OffsetDateTime;

@Data
public class AlertResponseDTO {
    private Long meterId;
    private String type;
    private String message;
    private OffsetDateTime createdAt;
}

