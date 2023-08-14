package io.upschool.controller;

import io.upschool.dto.BaseResponse;
import io.upschool.dto.flightDto.FlightResponse;
import io.upschool.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<BaseResponse<List<FlightResponse>>> getAllFlights(){
        List<FlightResponse> flights = flightService.getAllFlights();
        BaseResponse<List<FlightResponse>> response = BaseResponse.<List<FlightResponse>>builder()
                .status(HttpStatus.OK.value())
                .data(flights)
                .isSuccess(true)
                .build();
        return ResponseEntity.ok(response);
    }
}
