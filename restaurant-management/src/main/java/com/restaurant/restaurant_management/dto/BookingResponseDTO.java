package com.restaurant.restaurant_management.dto;


import com.restaurant.restaurant_management.model.BookingStatus;
import lombok.*;

import java.time.LocalDateTime;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponseDTO {
    private Long id;
    private Long userId;
    private Long tableId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private BookingStatus status;
}
