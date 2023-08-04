package io.upschool.dto.routeDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RouteResponse {
    private Long id;
    private String departureAirportName;
    private String arrivalAirportName;
}
