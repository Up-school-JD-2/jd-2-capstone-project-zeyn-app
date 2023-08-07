package io.upschool.service;

import io.upschool.dto.flightDto.FlightRequest;
import io.upschool.dto.flightDto.FlightResponse;
import io.upschool.exceptions.AirlineCompanyException;
import io.upschool.exceptions.FlightException;
import io.upschool.model.AirlineCompany;
import io.upschool.model.Flight;
import io.upschool.model.Route;
import io.upschool.repository.AirlineCompanyRepository;
import io.upschool.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightService {
    private final FlightRepository flightRepository;
    private final AirlineCompanyRepository airlineCompanyRepository;
    private final RouteService routeService;

    public List<FlightResponse> getAllFlightsByAirlineCompanyId(Long id) throws AirlineCompanyException {
        AirlineCompany airlineCompany = airlineCompanyRepository.findById(id)
                .orElseThrow(() -> new AirlineCompanyException(AirlineCompanyException.DATA_NOT_FOUND));

        List<Flight> flights = flightRepository.findAllByAirlineCompany_Id(airlineCompany.getId());
        return flights.stream().map(this::flightEntityToFlightResponse).toList();
    }

    public FlightResponse createFlight(FlightRequest flightRequest) throws AirlineCompanyException {
        Route route = routeService.getRoute(flightRequest.getRouteId());
        AirlineCompany airlineCompany = airlineCompanyRepository.findById(flightRequest.getAirlineCompanyId())
                .orElseThrow(() -> new AirlineCompanyException(AirlineCompanyException.DATA_NOT_FOUND));

        Flight flight = flightRequestToFlight(flightRequest, airlineCompany, route);

        return flightEntityToFlightResponse(flight);
    }

    public List<FlightResponse> getAllFlights() {
        return flightRepository.findAll().stream().map(this::flightEntityToFlightResponse).toList();
    }

    public List<FlightResponse> getAllFlightsByRoute(String departureCity, String arrivalCity) {

        List<Flight> flights = flightRepository.findAllByRouteDepartureAirportLocationAndRouteArrivalAirportLocation
                (departureCity, arrivalCity);
        return flights.stream().map(this::flightEntityToFlightResponse).toList();
        //return flights.stream().map(flight -> flightEntityToFlightResponse(flight)).toList();

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

    public Flight getFlightById(Long id) throws FlightException {
        return flightRepository.findById(id).orElseThrow(()-> new FlightException(FlightException.DATA_NOT_FOUND));
    }
}
