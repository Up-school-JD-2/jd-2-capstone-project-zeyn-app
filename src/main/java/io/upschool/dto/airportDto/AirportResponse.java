package io.upschool.dto.airportDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AirportResponse {
    private Long id;
    private String name;
    private String location;
}
