package com.restaurant.restaurant_management.serviceImp;

import com.restaurant.restaurant_management.dto.BillingResponseDTO;
import com.restaurant.restaurant_management.exception.ResourceNotFoundException;
import com.restaurant.restaurant_management.model.Billing;
import com.restaurant.restaurant_management.model.Order;
import com.restaurant.restaurant_management.repository.BillingRepository;
import com.restaurant.restaurant_management.repository.OrderRepository;
import com.restaurant.restaurant_management.service.BillingService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of BillingService interface for managing billing-related operations.
 */
@Service
@RequiredArgsConstructor
public class BillingServiceImpl implements BillingService {

    private final BillingRepository billingRepository;
    private final OrderRepository orderRepository;

    /**
     * Helper method to map Billing entity to BillingResponseDTO.
     *
     * @param billing the Billing entity to map
     * @return BillingResponseDTO with necessary fields populated
     */
    private BillingResponseDTO mapToDTO(Billing billing) {
        BillingResponseDTO dto = new BillingResponseDTO();
        dto.setId(billing.getId());
        dto.setOrderId(billing.getOrder().getId());
        dto.setPrice(billing.getPrice());
        dto.setTax(billing.getTax());
        dto.setDiscount(billing.getDiscount());
        dto.setTotalAmount(billing.getTotalAmount());
        return dto;
    }

    /**
     * Creates a billing record for a given order with calculated tax and discount.
     * This method is transactional to ensure data integrity.
     *
     * @param orderId         the ID of the order to bill
     * @param taxPercent      the tax percentage to apply
     * @param discountPercent the discount percentage to apply
     * @return the created BillingResponseDTO
     * @throws ResourceNotFoundException if order with given ID does not exist
     */
    @Override
    @Transactional
    public BillingResponseDTO createBilling(Long orderId, double taxPercent, double discountPercent) {
        // Retrieve order by ID or throw exception if not found
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));

        // Calculate price components based on order's total amount
        double price = order.getTotalAmount();
        double tax = price * taxPercent / 100;
        double discount = price * discountPercent / 100;
        double totalAmount = price + tax - discount;

        // Create new Billing entity and set fields
        Billing billing = new Billing();
        billing.setOrder(order);
        billing.setPrice(price);
        billing.setTax(tax);
        billing.setDiscount(discount);
        billing.setTotalAmount(totalAmount);

        // Persist billing entity
        billing = billingRepository.save(billing);

        // Return DTO mapped from saved billing
        return mapToDTO(billing);
    }

    /**
     * Retrieves the billing details for a specific order.
     *
     * @param orderId the ID of the order
     * @return BillingResponseDTO or null if billing not found
     */
    @Override
    public BillingResponseDTO getBillingByOrderId(Long orderId) {
        Billing billing = billingRepository.findByOrderId(orderId);
        if (billing == null) {
            throw new ResourceNotFoundException("Billing not found for order ID: " + orderId);
        }

        return mapToDTO(billing);
    }

    /**
     * Retrieves a list of billing records filtered by username and date range.
     * If the date parameters are null or empty, they are ignored.
     *
     * @param user      the username to filter by
     * @param startDate the start date filter in ISO format (yyyy-MM-dd)
     * @param endDate   the end date filter in ISO format (yyyy-MM-dd)
     * @return list of BillingResponseDTO matching the filter criteria
     */
    @Override
    public List<BillingResponseDTO> getFilteredBills(String user, String startDate, String endDate) {
        LocalDateTime start = null;
        LocalDateTime end = null;

        // Parse and convert startDate to start of the day if provided
        if (startDate != null && !startDate.isEmpty()) {
            LocalDate startLocalDate = LocalDate.parse(startDate);
            start = startLocalDate.atStartOfDay();
        }

        // Parse and convert endDate to end of the day if provided
        if (endDate != null && !endDate.isEmpty()) {
            LocalDate endLocalDate = LocalDate.parse(endDate);
            end = endLocalDate.atTime(23, 59, 59, 999_999_999);
        }

        // Fetch filtered billing records from repository
        List<Billing> bills = billingRepository.findByUserAndDateRange(user, start, end);

        // Map Billing entities to DTOs and return as list
        return bills.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
}
