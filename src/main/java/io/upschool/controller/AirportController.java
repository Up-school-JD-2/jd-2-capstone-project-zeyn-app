package io.upschool.controller;

import io.upschool.dto.BaseResponse;
import io.upschool.dto.airportDto.AirportRequest;
import io.upschool.dto.airportDto.AirportResponse;
import io.upschool.service.AirportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/airports")
@RequiredArgsConstructor
public class AirportController {
    private final AirportService airportService;

    @GetMapping
    public ResponseEntity<BaseResponse<List<AirportResponse>>> getAllAirports() {
        List<AirportResponse> allAirports = airportService.getAllAirports();
        BaseResponse<List<AirportResponse>> response = BaseResponse.<List<AirportResponse>>builder()
                .status(HttpStatus.OK.value())
                .data(allAirports)
                .isSuccess(true)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{name}")
    public ResponseEntity<BaseResponse<List<AirportResponse>>> getAirportsByName(@RequestParam String name) {
        List<AirportResponse> airport = airportService.findAirportByName(name);
        BaseResponse<List<AirportResponse>> response = BaseResponse.<List<AirportResponse>>builder()
                .status(HttpStatus.OK.value())
                .data(airport)
                .isSuccess(true)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<BaseResponse<AirportResponse>> createAirport(@Valid @RequestBody AirportRequest airportRequest){
        AirportResponse airport = airportService.createAirport(airportRequest);
        BaseResponse<AirportResponse> response = BaseResponse.<AirportResponse>builder()
                .status(HttpStatus.CREATED.value())
                .data(airport)
                .isSuccess(true)
                .build();
        return ResponseEntity.ok(response);
    }
}
