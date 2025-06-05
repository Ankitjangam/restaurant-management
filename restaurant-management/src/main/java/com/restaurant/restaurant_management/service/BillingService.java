package com.restaurant.restaurant_management.service;


import com.restaurant.restaurant_management.dto.BillingResponseDTO;

import java.util.List;

public interface BillingService {

    BillingResponseDTO createBilling(Long orderId, double taxPercent, double discountPercent);

    BillingResponseDTO getBillingByOrderId(Long orderId);
    List<BillingResponseDTO> getFilteredBills(String user, String startDate, String endDate);
}
