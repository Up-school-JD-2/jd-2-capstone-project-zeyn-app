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
public class TicketFlightResponse {
    private LocalDateTime departureDateTime;
    private String departureAirportName;
    private String arrivalAirportName;
    private Integer capacity;
    private Double price;
}
