package com.knsr.wmgmt.dto.response;


import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class RecentAlertResponseDTO {
    private OffsetDateTime date;
    private long meterId;
    private String zone;
    private String type;
    private String message;

    public OffsetDateTime getDate() {
        return date;
    }

    public void setDate(OffsetDateTime date) {
        this.date = date;
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

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public long getMeterId() {
        return meterId;
    }

    public void setMeterId(long meterId) {
        this.meterId = meterId;
    }
}
