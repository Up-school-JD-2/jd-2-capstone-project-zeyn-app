package io.upschool.service;

import io.upschool.dto.airportDto.AirportRequest;
import io.upschool.dto.airportDto.AirportResponse;
import io.upschool.exceptions.AirportException;
import io.upschool.model.Airport;
import io.upschool.repository.AirportRepository;
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
                .map(airport -> AirportResponse.builder()
                        .id(airport.getId())
                        .name(airport.getName())
                        .location(airport.getLocation())
                        .build()).toList();
    }

    public AirportResponse createAirport(AirportRequest airportRequest) throws AirportException {
        List<Airport> airportList = airportRepository.findByNameAndLocationContainingIgnoreCase
                (airportRequest.getName(), airportRequest.getLocation());

        if (airportList.size() != 0) throw new AirportException(AirportException.AIRPORT_EXIST);

        Airport airport = airportRepository.save(
                Airport.builder()
                        .name(airportRequest.getName())
                        .location(airportRequest.getLocation())
                        .isActive(true)
                        .build());

        return AirportResponse
                .builder()
                .id(airport.getId())
                .name(airport.getName())
                .location(airport.getLocation()).build();
    }

    public List<AirportResponse> findAirportByName(String name) {
        List<Airport> airportList = airportRepository.findAirportByNameContainingIgnoreCase(name);

        return airportList.stream().map(airport ->
                AirportResponse.builder()
                        .id(airport.getId())
                        .name(airport.getName())
                        .location(airport.getLocation())
                        .build()).toList();
    }

    public Airport getAirport(Long airportId) throws AirportException {
        return airportRepository.findById(airportId).orElseThrow(() -> new AirportException(AirportException.DATA_NOT_FOUND));
    }
}
