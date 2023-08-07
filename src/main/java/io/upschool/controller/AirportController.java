package io.upschool.controller;

import io.upschool.dto.airportDto.AirportRequest;
import io.upschool.dto.airportDto.AirportResponse;
import io.upschool.exceptions.AirportException;
import io.upschool.service.AirportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/airports")
@RequiredArgsConstructor
public class AirportController {
    private final AirportService airportService;

    @GetMapping
    public ResponseEntity<List<AirportResponse>> getAllAirports() {
        return ResponseEntity.ok(airportService.getAllAirports());
    }
    @GetMapping("/{name}")
    public ResponseEntity<List<AirportResponse>> getAirportsByName(@RequestParam String name){
        return ResponseEntity.ok(airportService.findAirportByName(name));
    }
    @PostMapping
    public ResponseEntity<AirportResponse> createAirport(@RequestBody AirportRequest airportRequest) throws AirportException {
        return ResponseEntity.ok(airportService.createAirport(airportRequest));
    }
}
