package com.restaurant.restaurant_management.service;

import com.restaurant.restaurant_management.dto.BillingResponseDTO;

import java.util.List;

/**
 * Service interface for managing billing operations.
 * Provides methods to create billing records, retrieve billing details,
 * and filter billing records by user and date range.
 */
public interface BillingService {

    /**
     * Creates a billing record for a given order applying tax and discount percentages.
     *
     * @param orderId         the ID of the order to bill
     * @param taxPercent      the tax percentage to apply
     * @param discountPercent the discount percentage to apply
     * @return BillingResponseDTO containing the created billing details
     */
    BillingResponseDTO createBilling(Long orderId, double taxPercent, double discountPercent);

    /**
     * Retrieves billing information for a given order ID.
     *
     * @param orderId the ID of the order
     * @return BillingResponseDTO containing billing details for the order
     */
    BillingResponseDTO getBillingByOrderId(Long orderId);

    /**
     * Retrieves a list of billing records filtered by user and optional date range.
     *
     * @param user      username to filter bills by (nullable)
     * @param startDate start date for filtering (nullable)
     * @param endDate   end date for filtering (nullable)
     * @return List of BillingResponseDTO matching the filters
     */
    List<BillingResponseDTO> getFilteredBills(String user, String startDate, String endDate);
}
