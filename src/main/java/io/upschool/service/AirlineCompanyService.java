package io.upschool.service;

import io.upschool.dto.airlineCompanyDto.AirlineCompanyRequest;
import io.upschool.dto.airlineCompanyDto.AirlineCompanyResponse;
import io.upschool.dto.flightDto.CompanyFlightRequest;
import io.upschool.dto.flightDto.FlightResponse;
import io.upschool.dto.routeDto.RouteResponse;
import io.upschool.exceptions.AirlineCompanyException;
import io.upschool.model.AirlineCompany;
import io.upschool.model.Flight;
import io.upschool.model.Route;
import io.upschool.repository.AirlineCompanyRepository;
import io.upschool.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AirlineCompanyService {
    private final AirlineCompanyRepository airlineCompanyRepository;
    private final FlightRepository flightRepository;
    private final RouteService routeService;

    public List<AirlineCompanyResponse> getAllAirlineCompanies() {
        return airlineCompanyRepository.findAll()
                .stream().map(airlineCompany -> AirlineCompanyResponse.builder()
                        .name(airlineCompany.getName())
                        .emailAddress(airlineCompany.getEmailAddress())
                        .phoneNumber(airlineCompany.getPhoneNumber())
                        .id(airlineCompany.getId())
                        .build()).toList();
    }

    public List<AirlineCompanyResponse> getAirlineCompaniesByName(String name) {
        List<AirlineCompany> airlineCompanies = airlineCompanyRepository.findAirlineCompaniesByNameContainingIgnoreCase(name);
        return airlineCompanies
                .stream().map(
                        airlineCompany -> AirlineCompanyResponse.builder()
                                .name(airlineCompany.getName())
                                .id(airlineCompany.getId())
                                .phoneNumber(airlineCompany.getPhoneNumber())
                                .emailAddress(airlineCompany.getEmailAddress())
                                .build()).toList();
    }

    public List<FlightResponse> getAllFlightsByAirlineCompanyId(Long id) throws AirlineCompanyException {
        AirlineCompany airlineCompany = airlineCompanyRepository.findById(id).orElseThrow(() -> new AirlineCompanyException(AirlineCompanyException.DATA_NOT_FOUND));//company not found exception

        //return flightService.getAllFlightsByAirlineCompanyId(airlineCompany.getId());

        List<Flight> flights = flightRepository.findAll().stream()
                .filter(flight -> flight.getAirlineCompany().getId().equals(airlineCompany.getId())).toList();


        return flights.stream().map(flight -> FlightResponse.builder()
                .id(flight.getId())
                .airlineCompanyId(flight.getAirlineCompany().getId())
                .arrivalAirportName(flight.getRoute().getArrivalAirport().getName())
                .departureAirportName(flight.getRoute().getDepartureAirport().getName())
                .departureDateTime(flight.getDepartureDateTime())
                .capacity(flight.getCapacity())
                .build()).toList();
    }

    public AirlineCompany getAirlineCompany(Long airlineCompanyId) {
        return airlineCompanyRepository.findById(airlineCompanyId).orElseThrow();
    }

    public AirlineCompanyResponse createAirlineCompany(AirlineCompanyRequest airlineCompanyRequest) {
        AirlineCompany airlineCompany = airlineCompanyRepository.save(AirlineCompany.builder()
                .name(airlineCompanyRequest.getName())
                .emailAddress(airlineCompanyRequest.getEmailAddress())
                .phoneNumber(airlineCompanyRequest.getPhoneNumber())
                .build());

        return AirlineCompanyResponse.builder()
                .id(airlineCompany.getId())
                .name(airlineCompany.getName())
                .emailAddress(airlineCompanyRequest.getEmailAddress())
                .phoneNumber(airlineCompanyRequest.getPhoneNumber())
                .build();
    }

    public FlightResponse createFlightOnAirlineCompany(Long id, CompanyFlightRequest companyFlightRequest) throws AirlineCompanyException {
        AirlineCompany airlineCompany = airlineCompanyRepository.findById(id)
                .orElseThrow(() -> new AirlineCompanyException(AirlineCompanyException.DATA_NOT_FOUND));

        Route route = routeService.getRoute(companyFlightRequest.getRouteId());

        Flight flight = flightRepository.save(
                Flight.builder()
                        .airlineCompany(airlineCompany)
                        .route(route)
                        .departureDateTime(companyFlightRequest.getDepartureDateTime())
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

    public List<FlightResponse> getAllFlightsByRoutes(Optional<String> departureCity, Optional<String> arrivalCity) {
        if (departureCity.isPresent() && arrivalCity.isPresent()) {
            List<RouteResponse> routeResponseList = routeService.getAllRoutes().stream().filter(routeResponse ->
                    arrivalCity.get().equalsIgnoreCase(routeResponse.getArrivalAirportName())
                            && departureCity.get().equalsIgnoreCase(routeResponse.getDepartureAirportName())).toList();
            return flightResponses(routeResponseList);
        } else if (departureCity.isPresent()) {
            List<RouteResponse> routeResponseList = routeService.getAllRoutes().stream().filter(routeResponse -> departureCity.get().equalsIgnoreCase(routeResponse.getDepartureAirportName())).toList();
            return flightResponses(routeResponseList);
        } else if (arrivalCity.isPresent()) {
            List<RouteResponse> routeResponseList = routeService.getAllRoutes().stream().filter(routeResponse -> arrivalCity.get().equalsIgnoreCase(routeResponse.getArrivalAirportName())).toList();
            return flightResponses(routeResponseList);
        }
        return null;
    }

    private List<FlightResponse> flightResponses(List<RouteResponse> routeResponseList) {
        Long id = routeResponseList.get(0).getId();
        List<Flight> flights = flightRepository.findAll().stream().filter(flight -> id.equals(flight.getRoute().getId())).toList();

        return flights.stream().map(flight -> FlightResponse.builder()
                .id(flight.getId())
                .capacity(flight.getCapacity())
                .departureDateTime(flight.getDepartureDateTime())
                .arrivalAirportName(flight.getRoute().getArrivalAirport().getName())
                .departureAirportName(flight.getRoute().getDepartureAirport().getName())
                .airlineCompanyId(flight.getAirlineCompany().getId())
                .build()).toList();
    }
}
