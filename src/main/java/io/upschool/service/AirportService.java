package io.upschool.service;

import io.upschool.dto.airportDto.AirportRequest;
import io.upschool.dto.airportDto.AirportResponse;
import io.upschool.exceptions.AirportException;
import io.upschool.entity.Airport;
import io.upschool.repository.AirportRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AirportService {
    private final AirportRepository airportRepository;

    public List<AirportResponse> getAllAirports() {
        return airportRepository.findAll()
                .stream()
                .map(AirportService::getAirportResponse).toList();
    }

    @Transactional
    public AirportResponse createAirport(AirportRequest airportRequest) {
        checkIfExist(airportRequest);
        Airport airport = airportRepository.save(getAirport(airportRequest));
        return getAirportResponse(airport);
    }

    public List<AirportResponse> findAirportByName(String name) {
        List<Airport> airportList = airportRepository.findAirportByNameContainingIgnoreCase(name);
        return airportList.stream().map(AirportService::getAirportResponse).toList();
    }

    public Airport getAirport(Long airportId) throws AirportException {
        return airportRepository.findById(airportId).orElseThrow(() -> new AirportException(AirportException.DATA_NOT_FOUND));
    }

    private static Airport getAirport(AirportRequest airportRequest) {
        return Airport.builder()
                .name(airportRequest.getName())
                .location(airportRequest.getLocation())
                .isActive(true)
                .build();
    }

    private static AirportResponse getAirportResponse(Airport airport) {
        return AirportResponse.builder()
                .id(airport.getId())
                .name(airport.getName())
                .location(airport.getLocation())
                .build();
    }

    private void checkIfExist(AirportRequest airportRequest) {
        boolean exist = airportRepository.existsByNameAndLocationContainingIgnoreCase(airportRequest.getName(), airportRequest.getLocation());
        if (exist) throw new AirportException(AirportException.AIRPORT_EXIST);
    }
}
