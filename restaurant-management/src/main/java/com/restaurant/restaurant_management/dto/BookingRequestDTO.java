package com.restaurant.restaurant_management.dto;



import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingRequestDTO {
    private Long userId;
    private Long tableId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
