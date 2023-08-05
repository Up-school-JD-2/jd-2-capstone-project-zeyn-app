package io.upschool.dto.routeDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RouteRequest {
    private Long departureAirportId;
    private Long arrivalAirportId;
}
