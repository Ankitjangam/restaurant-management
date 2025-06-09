package com.restaurant.restaurant_management.service;

import com.restaurant.restaurant_management.dto.BillingResponseDTO;
import com.restaurant.restaurant_management.exception.ResourceNotFoundException;
import com.restaurant.restaurant_management.model.Billing;
import com.restaurant.restaurant_management.model.Order;
import com.restaurant.restaurant_management.repository.BillingRepository;
import com.restaurant.restaurant_management.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BillingServiceImpl implements BillingService {

    private final BillingRepository billingRepository;
    private final OrderRepository orderRepository;

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

    @Override
    @Transactional
    public BillingResponseDTO createBilling(Long orderId, double taxPercent, double discountPercent) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));

        double price = order.getTotalAmount();
        double tax = price * taxPercent / 100;
        double discount = price * discountPercent / 100;
        double totalAmount = price + tax - discount;

        Billing billing = new Billing();
        billing.setOrder(order);
        billing.setPrice(price);
        billing.setTax(tax);
        billing.setDiscount(discount);
        billing.setTotalAmount(totalAmount);

        billing = billingRepository.save(billing);

        return mapToDTO(billing);
    }

    @Override
    public BillingResponseDTO getBillingByOrderId(Long orderId) {
        Billing billing = billingRepository.findByOrderId(orderId);
        if (billing == null) return null;
        return mapToDTO(billing);
    }

    @Override
    public List<BillingResponseDTO> getFilteredBills(String user, String startDate, String endDate) {
        LocalDateTime start = null;
        LocalDateTime end = null;

        if (startDate != null && !startDate.isEmpty()) {
            LocalDate startLocalDate = LocalDate.parse(startDate);
            start = startLocalDate.atStartOfDay();  // start of day
        }

        if (endDate != null && !endDate.isEmpty()) {
            LocalDate endLocalDate = LocalDate.parse(endDate);
            end = endLocalDate.atTime(23, 59, 59, 999_999_999);  // end of day
        }

        List<Billing> bills = billingRepository.findByUserAndDateRange(user, start, end);

        return bills.stream()
            .map(this::mapToDTO)
            .collect(Collectors.toList());
    }

}
