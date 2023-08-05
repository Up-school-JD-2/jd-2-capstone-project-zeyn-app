package io.upschool.service;

import io.upschool.dto.flightDto.FlightRequest;
import io.upschool.dto.flightDto.FlightResponse;
import io.upschool.model.AirlineCompany;
import io.upschool.model.Flight;
import io.upschool.model.Route;
import io.upschool.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightService {
    private final FlightRepository flightRepository;
    private final RouteService routeService;
    private final AirlineCompanyService airlineCompanyService;

    public List<FlightResponse> getAllFlightsByAirlineCompanyId(Long id) {
        List<Flight> flights = flightRepository.findAll().stream()
                .filter(flight -> flight.getAirlineCompany().getId().equals(id)).toList();


        return flights.stream().map(flight -> FlightResponse.builder()
                .id(flight.getId())
                .airlineCompanyId(flight.getAirlineCompany().getId())
                .arrivalAirportName(flight.getRoute().getArrivalAirport().getName())
                .departureAirportName(flight.getRoute().getDepartureAirport().getName())
                .departureDateTime(flight.getDepartureDateTime())
                .capacity(flight.getCapacity())
                .build()).toList();
    }

    public FlightResponse createFlight(FlightRequest flightRequest) {
        Route route = routeService.getRoute(flightRequest.getRouteId());
        AirlineCompany airlineCompany = airlineCompanyService.getAirlineCompany(flightRequest.getAirlineCompanyId());

        Flight flight = flightRepository.save(Flight.builder()
                .airlineCompany(airlineCompany)
                .route(route)
                .departureDateTime(flightRequest.getDepartureDateTime())
                .build());

        return FlightResponse.builder()
                .id(flight.getId())
                .capacity(flight.getCapacity())
                .airlineCompanyId(flight.getAirlineCompany().getId())
                .arrivalAirportName(flight.getRoute().getArrivalAirport().getName())
                .departureAirportName(flight.getRoute().getDepartureAirport().getName())
                .departureDateTime(flight.getDepartureDateTime())
                .build();
    }
}
