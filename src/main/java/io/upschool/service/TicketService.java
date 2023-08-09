package io.upschool.service;

import io.upschool.dto.cardDto.CardResponse;
import io.upschool.dto.flightDto.TicketFlightResponse;
import io.upschool.dto.passengerDto.PassengerResponse;
import io.upschool.dto.ticketDto.TicketRequest;
import io.upschool.dto.ticketDto.TicketResponse;
import io.upschool.exceptions.CardNumberException;
import io.upschool.exceptions.FlightException;
import io.upschool.model.Card;
import io.upschool.model.Flight;
import io.upschool.model.Passenger;
import io.upschool.model.Ticket;
import io.upschool.repository.TicketRepository;
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
        return ticketRepository.findAll().stream().map(this::entityToResponse).toList();
    }

    public TicketResponse getTicketByTicketNumber(String ticketNumber) {
        return entityToResponse(ticketRepository.findByTicketNumber(ticketNumber));
    }

    public TicketResponse createTicket(TicketRequest ticketRequest) throws FlightException, CardNumberException {
        Ticket ticket = requestToEntity(ticketRequest);
        int updatedOccupancy = ticket.getFlight().getOccupancy() + 1;
        Flight flight = flightService.updateFlightOccupancy(ticket.getFlight(), updatedOccupancy);
        ticket.setFlight(flight);
        ticketRepository.save(ticket);
        return entityToResponse(ticket);
    }

    public void cancelTicket(String ticketNumber) throws FlightException {
        Ticket ticket = ticketRepository.findByTicketNumber(ticketNumber);
        int updatedOccupancy = ticket.getFlight().getOccupancy() - 1;
        Flight flight = flightService.updateFlightOccupancy(ticket.getFlight(), updatedOccupancy);
        ticket.setFlight(flight);
        ticket.setIsActive(false);
        ticketRepository.save(ticket);
    }

    private Ticket requestToEntity(TicketRequest ticketRequest) throws FlightException, CardNumberException {
        Passenger passenger = passengerService.createPassenger(ticketRequest.getPassengerRequest());
        Card card = cardService.createCard(ticketRequest.getCardRequest());
        Flight flight = flightService.getFlightById(ticketRequest.getFlightId());

//        flight.setCapacity(flight.getCapacity()-1);
//        flightService.createFlight(flight);

        return ticketRepository.save(Ticket.builder()
                .price(ticketRequest.getTicketPrice())
                .flight(flight)
                .card(card)
                .passenger(passenger)
                .isActive(true)
                .build());
    }

    private TicketResponse entityToResponse(Ticket ticket) {
        Card card = ticket.getCard();
        Passenger passenger = ticket.getPassenger();
        Flight flight = ticket.getFlight();

        CardResponse cardResponse = CardResponse.builder().cardNumber(card.getCardNumber()).build();
        PassengerResponse passengerResponse = PassengerResponse.builder()
                .id(passenger.getId())
                .nameSurname(passenger.getName() + " " + passenger.getSurname())
                .phoneNumber(passenger.getPhoneNumber())
                .emailAddress(passenger.getEmailAddress())
                .build();
        TicketFlightResponse ticketFlightResponse = TicketFlightResponse.builder()
                .departureDateTime(flight.getDepartureDateTime())
                .departureAirportName(flight.getRoute().getDepartureAirport().getName())
                .arrivalAirportName(flight.getRoute().getArrivalAirport().getName())
                .occupancy(flight.getOccupancy())
                .capacity(flight.getCapacity())
                .build();

        return TicketResponse.builder()
                .ticketNumber(ticket.getTicketNumber())
                .ticketPrice(ticket.getPrice())
                .ticketFlightResponse(ticketFlightResponse)
                .cardResponse(cardResponse)
                .passengerResponse(passengerResponse)
                .build();
    }
}
