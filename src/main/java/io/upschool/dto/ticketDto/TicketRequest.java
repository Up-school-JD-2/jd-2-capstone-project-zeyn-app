package io.upschool.dto.ticketDto;

import io.upschool.dto.cardDto.CardRequest;
import io.upschool.dto.passengerDto.PassengerRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketRequest {
    private Double ticketPrice;
    private Long flightId;
    private CardRequest cardRequest;
    private PassengerRequest passengerRequest;
}
