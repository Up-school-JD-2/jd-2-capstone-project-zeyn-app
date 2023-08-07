package io.upschool.dto.ticketDto;

import io.upschool.dto.cardDto.CardResponse;
import io.upschool.dto.flightDto.TicketFlightResponse;
import io.upschool.dto.passengerDto.PassengerResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketResponse {
    private String ticketNumber;
    private Double ticketPrice;
    private TicketFlightResponse ticketFlightResponse;
    private CardResponse cardResponse;
    private PassengerResponse passengerResponse;
}
