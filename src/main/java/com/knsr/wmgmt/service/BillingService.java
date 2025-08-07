package com.knsr.wmgmt.service;

import com.knsr.wmgmt.dto.request.BillingPaymentRequestDTO;
import com.knsr.wmgmt.dto.response.BillingResponseDTO;

import java.time.OffsetDateTime;
import java.util.List;

public interface BillingService {

    List<BillingResponseDTO> generateBillsForAllUsers(OffsetDateTime periodStart, OffsetDateTime periodEnd);

    BillingResponseDTO generateBillForUser(Long userId, OffsetDateTime periodStart, OffsetDateTime periodEnd);

    BillingResponseDTO updateBillingStatus(BillingPaymentRequestDTO dto);

    List<BillingResponseDTO> getBillsForUser(Long userId);
}