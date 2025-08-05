package com.knsr.wmgmt.dto.response;


import lombok.Data;
import java.time.OffsetDateTime;

@Data
public class AlertResponseDTO {
    private Long meterId;
    private String type;
    private String message;
    private OffsetDateTime createdAt;

    public Long getMeterId() {
        return meterId;
    }

    public void setMeterId(Long meterId) {
        this.meterId = meterId;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

