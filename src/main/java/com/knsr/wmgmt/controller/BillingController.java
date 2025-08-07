package com.knsr.wmgmt.controller;

import com.knsr.wmgmt.dto.request.BillingPaymentRequestDTO;
import com.knsr.wmgmt.dto.response.BillingResponseDTO;
import com.knsr.wmgmt.service.BillingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;

@RestController
@RequestMapping("/v1/api/wmgmt")
public class BillingController {

    @Autowired
    private BillingService billingService;

    @GetMapping("/generate/current-month")
    public ResponseEntity<List<BillingResponseDTO>> generateCurrentMonthBills() {
        OffsetDateTime now = OffsetDateTime.now();
        OffsetDateTime periodStart = now.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        OffsetDateTime periodEnd = now.withHour(23).withMinute(59).withSecond(59).withNano(999999999);
        return ResponseEntity.ok(billingService.generateBillsForAllUsers(periodStart, periodEnd));
    }

    @PutMapping("/pay")
    public ResponseEntity<BillingResponseDTO> payBill(@RequestBody BillingPaymentRequestDTO dto) {
        return ResponseEntity.ok(billingService.updateBillingStatus(dto));
    }

    @GetMapping("/bills/{userId}")
    public ResponseEntity<List<BillingResponseDTO>> getBillsForUser(@PathVariable Long userId) {
        return ResponseEntity.ok(billingService.getBillsForUser(userId));
    }
}