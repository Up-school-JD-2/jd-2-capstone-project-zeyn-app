package io.upschool.service;

import io.upschool.dto.flightDto.AirlineFlightResponse;
import io.upschool.dto.flightDto.FlightRequest;
import io.upschool.dto.flightDto.FlightResponse;
import io.upschool.exceptions.FlightException;
import io.upschool.model.AirlineCompany;
import io.upschool.model.Flight;
import io.upschool.model.Route;
import io.upschool.repository.FlightRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightService {
    private final FlightRepository flightRepository;

    public List<FlightResponse> getAllFlights() {
        return flightRepository.findAll().stream().map(flight -> FlightResponse.builder()
                .id(flight.getId())
                .companyName(flight.getAirlineCompany().getName())
                .departureDateTime(flight.getDepartureDateTime())
                .arrivalAirportName(flight.getRoute().getArrivalAirport().getName())
                .departureAirportName(flight.getRoute().getDepartureAirport().getName())
                .capacity(flight.getCapacity())
                .price(flight.getPrice())
                .build()).toList();
    }

    public List<AirlineFlightResponse> getAllFlightsByAirlineCompanyId(Long airlineCompanyId) {
        List<Flight> flights = flightRepository.findAllByAirlineCompany_Id(airlineCompanyId);
        return flights.stream().map(this::flightEntityToFlightResponse).toList();
    }

    public List<AirlineFlightResponse> getAllFlightsByRoute(String departureCity, String arrivalCity) {
        List<Flight> flights = flightRepository.findAllByRouteDepartureAirportLocationAndRouteArrivalAirportLocation(departureCity, arrivalCity);
        return flights.stream().map(this::flightEntityToFlightResponse).toList();
    }

    public List<AirlineFlightResponse> getAllFlightsByRouteAndAirlineId(String departureCity, String arrivalCity, Long companyId) {
        List<Flight> flights = flightRepository.findAllByRouteDepartureAirportLocationAndRouteArrivalAirportLocationAndAirlineCompanyId(departureCity, arrivalCity, companyId);
        return flights
                .stream()
                .map(this::flightEntityToFlightResponse)
                .toList();
    }

    public Flight getFlightById(Long id) throws FlightException {
        return flightRepository.findById(id).orElseThrow(() -> new FlightException(FlightException.DATA_NOT_FOUND));
    }

    public AirlineFlightResponse createFlight(AirlineCompany airlineCompany, Route route, FlightRequest flightRequest) {
        Flight flight = flightRequestToFlight(flightRequest, airlineCompany, route);
        return flightEntityToFlightResponse(flight);
    }

    @Transactional
    public Flight updateFlightCapacity(Flight flight, int updatedCapacity) {
        flight.setCapacity(updatedCapacity);
        return flightRepository.save(flight);
    }


    private AirlineFlightResponse flightEntityToFlightResponse(Flight flight) {
        return AirlineFlightResponse.builder()
                .companyName(flight.getAirlineCompany().getName())
                .departureDateTime(flight.getDepartureDateTime())
                .arrivalAirportName(flight.getRoute().getArrivalAirport().getName())
                .departureAirportName(flight.getRoute().getDepartureAirport().getName())
                .price(flight.getPrice())
                .build();
    }

    @Transactional
    private Flight flightRequestToFlight(FlightRequest flightRequest, AirlineCompany airlineCompany, Route route) {
        return flightRepository.save(Flight.builder()
                .airlineCompany(airlineCompany)
                .route(route)
                .departureDateTime(flightRequest.getDepartureDateTime())
                .price(flightRequest.getPrice())
                .build());
    }
}