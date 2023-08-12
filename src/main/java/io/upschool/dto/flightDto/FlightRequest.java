package io.upschool.dto.flightDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightRequest {
    private LocalDateTime departureDateTime; // 2023-08-05T10:15:30
    private Long routeId;
    private Long airlineCompanyId;
    private Double price;
}
