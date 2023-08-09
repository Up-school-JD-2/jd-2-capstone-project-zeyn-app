package io.upschool.service;

import io.upschool.dto.airlineCompanyDto.AirlineCompanyRequest;
import io.upschool.dto.airlineCompanyDto.AirlineCompanyResponse;
import io.upschool.dto.flightDto.CompanyFlightRequest;
import io.upschool.dto.flightDto.FlightRequest;
import io.upschool.dto.flightDto.FlightResponse;
import io.upschool.exceptions.AirlineCompanyException;
import io.upschool.exceptions.RouteException;
import io.upschool.model.AirlineCompany;
import io.upschool.model.Route;
import io.upschool.repository.AirlineCompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AirlineCompanyService {
    private final AirlineCompanyRepository airlineCompanyRepository;
    private final FlightService flightService;
    private final RouteService routeService;

    public List<AirlineCompanyResponse> getAllAirlineCompanies() {
        return airlineCompanyRepository.findAll()
                .stream()
                .map(this::entityToResponse)
                .toList();
    }

    public List<AirlineCompanyResponse> getAirlineCompaniesByName(String name) {
        List<AirlineCompany> airlineCompanies = airlineCompanyRepository.findAirlineCompaniesByNameContainingIgnoreCase(name);
        return airlineCompanies
                .stream()
                .map(this::entityToResponse)
                .toList();
    }

    public List<FlightResponse> getAllFlightsByAirlineCompanyId(Long id) throws AirlineCompanyException {
        AirlineCompany airlineCompany = airlineCompanyRepository.findById(id)
                .orElseThrow(() -> new AirlineCompanyException(AirlineCompanyException.DATA_NOT_FOUND));
        return flightService.getAllFlightsByAirlineCompanyId(airlineCompany.getId());
    }

    public List<FlightResponse> getAllFlightsByRoutes(String departureCity, String arrivalCity) {
        return flightService.getAllFlightsByRoute(departureCity, arrivalCity);
    }

    public List<FlightResponse> getAllFlightsByRoutesAndByAirlineId(Long id, String departureCity, String arrivalCity) throws AirlineCompanyException {
        AirlineCompany airlineCompany = airlineCompanyRepository.findById(id)
                .orElseThrow(() -> new AirlineCompanyException(AirlineCompanyException.DATA_NOT_FOUND));

        return flightService.getAllFlightsByRoute(departureCity, arrivalCity).stream()
                .filter(flightResponse ->
                        airlineCompany.getId().equals(flightResponse.getAirlineCompanyId())).toList();
    }

    public AirlineCompanyResponse createAirlineCompany(AirlineCompanyRequest airlineCompanyRequest) {
        AirlineCompany airlineCompany = requestToEntity(airlineCompanyRequest);
        return entityToResponse(airlineCompany);
    }

    public FlightResponse createFlightOnAirlineCompany(Long id, CompanyFlightRequest companyFlightRequest) throws AirlineCompanyException, RouteException {
        AirlineCompany airlineCompany = airlineCompanyRepository.findById(id)
                .orElseThrow(() -> new AirlineCompanyException(AirlineCompanyException.DATA_NOT_FOUND));

        Route route = routeService.getRoute(companyFlightRequest.getRouteId());
        return flightService.createFlight(airlineCompany, route,
                FlightRequest.builder()
                        .routeId(route.getId())
                        .airlineCompanyId(airlineCompany.getId())
                        .departureDateTime(companyFlightRequest.getDepartureDateTime())
                        .build());
    }

    private AirlineCompanyResponse entityToResponse(AirlineCompany airlineCompany) {
        return AirlineCompanyResponse.builder()
                .name(airlineCompany.getName())
                .emailAddress(airlineCompany.getEmailAddress())
                .phoneNumber(airlineCompany.getPhoneNumber())
                .id(airlineCompany.getId())
                .build();
    }

    private AirlineCompany requestToEntity(AirlineCompanyRequest airlineCompanyRequest) {
        return airlineCompanyRepository.save(AirlineCompany.builder()
                .name(airlineCompanyRequest.getName())
                .emailAddress(airlineCompanyRequest.getEmailAddress())
                .phoneNumber(airlineCompanyRequest.getPhoneNumber())
                .build());
    }
}
