package com.knsr.wmgmt.controller;/*
package com.knsr.ingestion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.wmgmt.dto.request.BillingRequest;
import com.example.wmgmt.service.BillingService;

@RestController
@RequestMapping("/v1/api/wmgmt/billing")
public class BillingController {

    @Autowired
    private BillingService billingService;

    @PostMapping
    public ResponseEntity<String> generateBill(@RequestBody BillingRequest request) {
        billingService.generateBill(request);
        return ResponseEntity.ok("Billing record created.");
    }

    @GetMapping("/summary")
    public ResponseEntity<?> getBillingSummary() {
        return ResponseEntity.ok(billingService.getBillingSummary());
    }
}

*/
/*@RestController
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
}*/

