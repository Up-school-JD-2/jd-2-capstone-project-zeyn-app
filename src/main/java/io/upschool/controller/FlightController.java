package io.upschool.controller;

import io.upschool.dto.flightDto.FlightResponse;
import io.upschool.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/flights")
@RequiredArgsConstructor
public class FlightController {
    private final FlightService flightService;

    @GetMapping
    public ResponseEntity<List<FlightResponse>> getAllFlights(){
        return ResponseEntity.ok(flightService.getAllFlights());
    }
}
