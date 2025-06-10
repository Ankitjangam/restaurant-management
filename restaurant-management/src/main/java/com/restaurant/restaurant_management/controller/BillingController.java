package com.restaurant.restaurant_management.controller;

import com.restaurant.restaurant_management.dto.BillingResponseDTO;
import com.restaurant.restaurant_management.service.BillingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for billing-related endpoints.
 */
@RestController
@RequestMapping("/billing")
@RequiredArgsConstructor
public class BillingController {

    private final BillingService billingService;

    /**
     * Create a billing record for a given order with specified tax and discount percentages.
     *
     * @param orderId         the ID of the order to bill
     * @param taxPercent      tax percentage to apply
     * @param discountPercent discount percentage to apply
     * @return created BillingResponseDTO with billing details
     */
    @PostMapping("/create/{orderId}")
    public ResponseEntity<BillingResponseDTO> createBilling(
            @PathVariable Long orderId,
            @RequestParam double taxPercent,
            @RequestParam double discountPercent) {

        BillingResponseDTO billingDTO = billingService.createBilling(orderId, taxPercent, discountPercent);
        return ResponseEntity.ok(billingDTO);
    }

    /**
     * Retrieve billing details for a specific order.
     *
     * @param orderId the order ID
     * @return BillingResponseDTO if found, 404 otherwise
     */
    @GetMapping("/{orderId}")
    public ResponseEntity<BillingResponseDTO> getBilling(@PathVariable Long orderId) {
        BillingResponseDTO billingDTO = billingService.getBillingByOrderId(orderId);
        if (billingDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(billingDTO);
    }

    /**
     * Admin endpoint to retrieve filtered billing records.
     * Filters can be applied based on username and date range.
     *
     * @param username  optional username filter
     * @param startDate optional start date filter (format should be validated in service)
     * @param endDate   optional end date filter (format should be validated in service)
     * @return list of filtered billing records
     */
    @GetMapping("/admin/bills")
    public ResponseEntity<List<BillingResponseDTO>> getBillsForAdmin(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {

        List<BillingResponseDTO> bills = billingService.getFilteredBills(username, startDate, endDate);
        return ResponseEntity.ok(bills);
    }
}
