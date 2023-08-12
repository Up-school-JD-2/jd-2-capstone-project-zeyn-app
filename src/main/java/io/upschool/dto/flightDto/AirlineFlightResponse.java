package io.upschool.dto.flightDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AirlineFlightResponse {
    @JsonProperty(value = "Airline")
    private String companyName;
    @JsonProperty(value = "Date")
    private LocalDateTime departureDateTime;
    @JsonProperty(value = "departure")
    private String departureAirportName;
    @JsonProperty(value = "arrival")
    private String arrivalAirportName;
}
