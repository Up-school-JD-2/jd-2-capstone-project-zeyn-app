package io.upschool.service;

import io.upschool.dto.cardDto.CardResponse;
import io.upschool.dto.flightDto.TicketFlightResponse;
import io.upschool.dto.passengerDto.PassengerResponse;
import io.upschool.dto.ticketDto.TicketRequest;
import io.upschool.dto.ticketDto.TicketResponse;
import io.upschool.model.Card;
import io.upschool.model.Flight;
import io.upschool.model.Passenger;
import io.upschool.model.Ticket;
import io.upschool.repository.TicketRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final FlightService flightService;
    private final CardService cardService;
    private final PassengerService passengerService;
    private final TicketRepository ticketRepository;

    public List<TicketResponse> getAllTickets() {
        return ticketRepository.findAll().stream().map(this::getTicketResponse).toList();
    }

    public TicketResponse getTicketByTicketNumber(String ticketNumber) {
        Ticket ticket = ticketRepository.findByTicketNumber(ticketNumber);
        return getTicketResponse(ticket);
    }

    public TicketResponse getTicketByIdentityNumber(String identityNumber) {
        Ticket ticket = ticketRepository.findByPassengerIdentityNumber(identityNumber);
        return getTicketResponse(ticket);
    }

    @Transactional
    public TicketResponse createTicket(TicketRequest ticketRequest) {
        Ticket ticket = getTicket(ticketRequest);
        Flight flight = flightService.decreaseFlightCapacity(ticket.getFlight());

        ticket.setFlight(flight);
        ticketRepository.save(ticket);
        return getTicketResponse(ticket);
    }

    @Transactional
    public void cancelTicket(String ticketNumber) {
        Ticket ticket = ticketRepository.findByTicketNumber(ticketNumber);
        Flight flight = flightService.increaseFlightCapacity(ticket.getFlight());

        ticket.setFlight(flight);
        ticket.setIsActive(false);
        ticketRepository.save(ticket);
    }

    @Transactional
    private Ticket getTicket(TicketRequest ticketRequest) {
        Passenger passenger = passengerService.createPassenger(ticketRequest.getPassengerRequest());
        Card card = cardService.createCard(ticketRequest.getCardRequest());
        Flight flight = flightService.getFlightById(ticketRequest.getFlightId());

        return ticketRepository.save(Ticket.builder()
                .flight(flight)
                .card(card)
                .passenger(passenger)
                .isActive(true)
                .build());
    }

    @Transactional
    private TicketResponse getTicketResponse(Ticket ticket) {
        Card card = ticket.getCard();
        Passenger passenger = ticket.getPassenger();
        Flight flight = ticket.getFlight();

        CardResponse cardResponse = cardService.getCardResponse(card);
        PassengerResponse passengerResponse = passengerService.getPassengerResponse(passenger);
        TicketFlightResponse ticketFlightResponse = getTicketFlightResponse(flight);

        return TicketResponse.builder()
                .ticketNumber(ticket.getTicketNumber())
                .ticketPrice(flight.getPrice())
                .ticketFlightResponse(ticketFlightResponse)
                .cardResponse(cardResponse)
                .passengerResponse(passengerResponse)
                .build();
    }

    private TicketFlightResponse getTicketFlightResponse(Flight flight) {
        return TicketFlightResponse.builder()
                .departureDateTime(flight.getDepartureDateTime())
                .departureAirportName(flight.getRoute().getDepartureAirport().getName())
                .arrivalAirportName(flight.getRoute().getArrivalAirport().getName())
                .capacity(flight.getCapacity())
                .price(flight.getPrice())
                .build();
    }
}
