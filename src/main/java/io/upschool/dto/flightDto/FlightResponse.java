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
public class FlightResponse {
    private Long id;
    private Integer capacity;
    private LocalDateTime departureDateTime;
    private String departureAirportName;
    private String arrivalAirportName;
    private Long airlineCompanyId;
}
