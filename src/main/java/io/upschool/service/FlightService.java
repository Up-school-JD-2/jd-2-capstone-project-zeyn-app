package io.upschool.service;

import io.upschool.dto.flightDto.FlightRequest;
import io.upschool.dto.flightDto.FlightResponse;
import io.upschool.exceptions.FlightException;
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

    public List<FlightResponse> getAllFlights() {
        return flightRepository.findAll().stream().map(this::flightEntityToFlightResponse).toList();
    }

    public List<FlightResponse> getAllFlightsByAirlineCompanyId(Long airlineCompanyId) {
        List<Flight> flights = flightRepository.findAllByAirlineCompany_Id(airlineCompanyId);
        return flights.stream().map(this::flightEntityToFlightResponse).toList();
    }

    public List<FlightResponse> getAllFlightsByRoute(String departureCity, String arrivalCity) {

        List<Flight> flights = flightRepository.findAllByRouteDepartureAirportLocationAndRouteArrivalAirportLocation
                (departureCity, arrivalCity);
        return flights.stream()
                .map(this::flightEntityToFlightResponse).toList();
    }

    public Flight getFlightById(Long id) throws FlightException {
        return flightRepository.findById(id).orElseThrow(() -> new FlightException(FlightException.DATA_NOT_FOUND));
    }

    public FlightResponse createFlight(AirlineCompany airlineCompany, Route route, FlightRequest flightRequest) {
        Flight flight = flightRequestToFlight(flightRequest, airlineCompany, route);
        return flightEntityToFlightResponse(flight);
    }

    public Flight updateFlightOccupancy(Flight flight, int updatedOccupancy) throws FlightException {
        if(updatedOccupancy==flight.getCapacity()) throw new FlightException(FlightException.CAPACITY_IS_FULL);
        flight.setOccupancy(updatedOccupancy);
        return flightRepository.save(flight);
    }


    private FlightResponse flightEntityToFlightResponse(Flight flight) {
        return FlightResponse.builder()
                .id(flight.getId())
                .capacity(flight.getCapacity())
                .departureDateTime(flight.getDepartureDateTime())
                .arrivalAirportName(flight.getRoute().getArrivalAirport().getName())
                .departureAirportName(flight.getRoute().getDepartureAirport().getName())
                .airlineCompanyId(flight.getAirlineCompany().getId())
                .build();
    }

    private Flight flightRequestToFlight(FlightRequest flightRequest, AirlineCompany airlineCompany, Route route) {
        return flightRepository.save(Flight.builder()
                .airlineCompany(airlineCompany)
                .route(route)
                .departureDateTime(flightRequest.getDepartureDateTime())
                .build());
    }
}
