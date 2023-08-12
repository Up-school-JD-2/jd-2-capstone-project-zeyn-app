package io.upschool.service;

import io.upschool.dto.airlineCompanyDto.AirlineCompanyRequest;
import io.upschool.dto.airlineCompanyDto.AirlineCompanyResponse;
import io.upschool.dto.flightDto.AirlineFlightRequest;
import io.upschool.dto.flightDto.AirlineFlightResponse;
import io.upschool.dto.flightDto.FlightRequest;
import io.upschool.exceptions.AirlineCompanyException;
import io.upschool.exceptions.RouteException;
import io.upschool.model.AirlineCompany;
import io.upschool.model.Route;
import io.upschool.repository.AirlineCompanyRepository;
import jakarta.transaction.Transactional;
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

    public List<AirlineFlightResponse> getAllFlightsByAirlineCompanyId(Long id) throws AirlineCompanyException {
        AirlineCompany airlineCompany = airlineCompanyRepository.findById(id)
                .orElseThrow(() -> new AirlineCompanyException(AirlineCompanyException.DATA_NOT_FOUND));
        return flightService.getAllFlightsByAirlineCompanyId(airlineCompany.getId());
    }

    public List<AirlineFlightResponse> getAllFlightsByRoutes(String departureCity, String arrivalCity) {
        return flightService.getAllFlightsByRoute(departureCity, arrivalCity);
    }

    public List<AirlineFlightResponse> getAllFlightsByRoutesAndByAirlineId(Long companyId, String departureCity, String arrivalCity) throws AirlineCompanyException {
        AirlineCompany airlineCompany = airlineCompanyRepository.findById(companyId)
                .orElseThrow(() -> new AirlineCompanyException(AirlineCompanyException.DATA_NOT_FOUND));

//        return flightService.getAllFlightsByRoute(departureCity, arrivalCity)
//                .stream()
//                .filter(flightResponse -> flightService.getAllFlightsByAirlineCompanyId(airlineCompany.getId())).toList();
        return flightService.getAllFlightsByRouteAndAirlineId(departureCity, arrivalCity, airlineCompany.getId());
    }

    public AirlineCompanyResponse createAirlineCompany(AirlineCompanyRequest airlineCompanyRequest){
        AirlineCompany airlineCompany = requestToEntity(airlineCompanyRequest);
        return entityToResponse(airlineCompany);
    }

    public AirlineFlightResponse createFlightOnAirlineCompany(Long id, AirlineFlightRequest airlineFlightRequest) throws AirlineCompanyException, RouteException {
        AirlineCompany airlineCompany = airlineCompanyRepository.findById(id)
                .orElseThrow(() -> new AirlineCompanyException(AirlineCompanyException.DATA_NOT_FOUND));

        Route route = routeService.getRoute(airlineFlightRequest.getRouteId());
        return flightService.createFlight(airlineCompany, route,
                FlightRequest.builder()
                        .routeId(route.getId())
                        .airlineCompanyId(airlineCompany.getId())
                        .departureDateTime(airlineFlightRequest.getDepartureDateTime())
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

    @Transactional
    private AirlineCompany requestToEntity(AirlineCompanyRequest airlineCompanyRequest) {
//        if(airlineCompanyRepository.existsByEmailAddress(airlineCompanyRequest.getEmailAddress()))
//            throw new AirlineCompanyException(AirlineCompanyException.EMAIL_ADDRESS_EXIST);
        return airlineCompanyRepository.save(AirlineCompany.builder()
                .name(airlineCompanyRequest.getName())
                .emailAddress(airlineCompanyRequest.getEmailAddress())
                .phoneNumber(airlineCompanyRequest.getPhoneNumber())
                .build());
    }
}
