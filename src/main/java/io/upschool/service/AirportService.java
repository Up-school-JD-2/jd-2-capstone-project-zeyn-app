package io.upschool.service;

import io.upschool.dto.airportDto.AirportRequest;
import io.upschool.dto.airportDto.AirportResponse;
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
                .map(airport ->
                        AirportResponse.builder()
                                .id(airport.getId())
                                .name(airport.getName())
                                .location(airport.getLocation())
                                .build()).toList();
    }

    public AirportResponse createAirport(AirportRequest airportRequest) {
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

    public Airport findAirportByName(String name){
        return airportRepository.findAirportByName(name);
    }
}
