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
    private LocalDateTime departureDateTime;
    private Long routeId;
    private Double price;
}
