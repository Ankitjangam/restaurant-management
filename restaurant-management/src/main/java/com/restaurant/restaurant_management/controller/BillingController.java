package com.restaurant.restaurant_management.controller;

import com.restaurant.restaurant_management.dto.BillingResponseDTO;
import com.restaurant.restaurant_management.service.BillingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/billing")
@RequiredArgsConstructor
public class BillingController {

    private final BillingService billingService;

    @PostMapping("/create/{orderId}")
    public ResponseEntity<BillingResponseDTO> createBilling(
            @PathVariable Long orderId,
            @RequestParam double taxPercent,
            @RequestParam double discountPercent) {

        BillingResponseDTO billingDTO = billingService.createBilling(orderId, taxPercent, discountPercent);
        return ResponseEntity.ok(billingDTO);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<BillingResponseDTO> getBilling(@PathVariable Long orderId) {
        BillingResponseDTO billingDTO = billingService.getBillingByOrderId(orderId);
        if (billingDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(billingDTO);
    }

    @GetMapping("/admin/bills")
    public ResponseEntity<List<BillingResponseDTO>> getBillsForAdmin(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        List<BillingResponseDTO> bills = billingService.getFilteredBills(username, startDate, endDate);
        return ResponseEntity.ok(bills);
    }
}
