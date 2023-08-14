package io.upschool.service;

import io.upschool.dto.airlineCompanyDto.AirlineCompanyRequest;
import io.upschool.dto.airlineCompanyDto.AirlineCompanyResponse;
import io.upschool.dto.flightDto.FlightRequest;
import io.upschool.dto.flightDto.AirlineFlightResponse;
import io.upschool.exceptions.AirlineCompanyException;
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
                .map(AirlineCompanyService::getAirlineCompanyResponse)
                .toList();
    }

    public List<AirlineCompanyResponse> getAirlineCompaniesByName(String name) {
        List<AirlineCompany> airlineCompanies = airlineCompanyRepository.findAirlineCompaniesByNameContainingIgnoreCase(name);
        return airlineCompanies
                .stream()
                .map(AirlineCompanyService::getAirlineCompanyResponse)
                .toList();
    }

    // findById kullanılmadan yapılabilir mi? Örneğin existById ile kontrol edip, getReferenceById kullansam??
    public List<AirlineFlightResponse> getAllFlightsByAirlineCompanyId(Long id) {
        AirlineCompany airlineCompany = airlineCompanyRepository.findById(id)
                .orElseThrow(() -> new AirlineCompanyException(AirlineCompanyException.DATA_NOT_FOUND));
        return flightService.getAllFlightsByAirlineCompanyId(airlineCompany.getId());
    }

    public List<AirlineFlightResponse> getAllFlightsByRoutes(String departureCity, String arrivalCity) {
        return flightService.getAllFlightsByRoute(departureCity, arrivalCity);
    }

    public List<AirlineFlightResponse> getAllFlightsByRoutesAndByAirlineId(Long companyId, String departureCity, String arrivalCity) {
        AirlineCompany airlineCompany = airlineCompanyRepository.findById(companyId)
                .orElseThrow(() -> new AirlineCompanyException(AirlineCompanyException.DATA_NOT_FOUND));

        return flightService.getAllFlightsByRouteAndAirlineId(departureCity, arrivalCity, airlineCompany.getId());
    }

    public AirlineCompanyResponse createAirlineCompany(AirlineCompanyRequest airlineCompanyRequest) {
        checkIfExist(airlineCompanyRequest);
        AirlineCompany airlineCompany = getAirlineCompany(airlineCompanyRequest);
        return getAirlineCompanyResponse(airlineCompany);
    }

    public AirlineFlightResponse createFlightOnAirline(Long id, FlightRequest flightRequest) {
//        AirlineCompany airlineCompany = airlineCompanyRepository.findById(id)
//                .orElseThrow(() -> new AirlineCompanyException(AirlineCompanyException.DATA_NOT_FOUND));
        checkIfExist(id);
        AirlineCompany airline = airlineCompanyRepository.getReferenceById(id);
        Route route = routeService.getRoute(flightRequest.getRouteId());
        return getAirlineFlightResponse(flightRequest, airline, route);
    }

    private AirlineFlightResponse getAirlineFlightResponse(FlightRequest flightRequest, AirlineCompany airlineCompany, Route route) {
        return flightService.createFlight(airlineCompany, route,
                FlightRequest.builder()
                        .routeId(route.getId())
                        .departureDateTime(flightRequest.getDepartureDateTime())
                        .price(flightRequest.getPrice())
                        .build());
    }

    private static AirlineCompanyResponse getAirlineCompanyResponse(AirlineCompany airlineCompany) {
        return AirlineCompanyResponse.builder()
                .name(airlineCompany.getName())
                .emailAddress(airlineCompany.getEmailAddress())
                .phoneNumber(airlineCompany.getPhoneNumber())
                .id(airlineCompany.getId())
                .build();
    }

    @Transactional
    private AirlineCompany getAirlineCompany(AirlineCompanyRequest airlineCompanyRequest) {
        return airlineCompanyRepository.save(AirlineCompany.builder()
                .name(airlineCompanyRequest.getName())
                .emailAddress(airlineCompanyRequest.getEmailAddress())
                .phoneNumber(airlineCompanyRequest.getPhoneNumber())
                .build());
    }

    private void checkIfExist(AirlineCompanyRequest airlineCompanyRequest) {
        if (airlineCompanyRepository.existsByName(airlineCompanyRequest.getName()))
            throw new AirlineCompanyException(AirlineCompanyException.AIRLINE_COMPANY_EXIST);
    }
    private void checkIfExist(Long id){
        if (!airlineCompanyRepository.existsById(id))
            throw new AirlineCompanyException(AirlineCompanyException.DATA_NOT_FOUND);
    }
}
