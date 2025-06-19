package com.knsr.ingestion.controller;

import com.knsr.ingestion.dto.request.BillingPaymentRequestDTO;
import com.knsr.ingestion.dto.request.BillingRequestDTO;
import com.knsr.ingestion.dto.response.BillingResponseDTO;
import com.knsr.ingestion.service.BillingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/wmgmt/billing")
public class BillingController {

    @Autowired
    private BillingService billingService;

    @PostMapping("/generate")
    public ResponseEntity<BillingResponseDTO> generateBill(@RequestBody BillingRequestDTO dto) {
        return ResponseEntity.ok(billingService.generateBill(dto));
    }

    @PutMapping("/pay")
    public ResponseEntity<BillingResponseDTO> payBill(@RequestBody BillingPaymentRequestDTO dto) {
        return ResponseEntity.ok(billingService.updateBillingStatus(dto));
    }
}

