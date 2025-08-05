package com.knsr.wmgmt.dto.request;

import lombok.Data;

@Data
public class MeterStatusUpdateRequestDTO {
    private Long userId;
    private String newStatus;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(String newStatus) {
        this.newStatus = newStatus;
    }
}

