package io.upschool.controller;

import io.upschool.dto.airlineCompanyDto.AirlineCompanyRequest;
import io.upschool.dto.airlineCompanyDto.AirlineCompanyResponse;
import io.upschool.service.AirlineCompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/airlineCompanies")
@RequiredArgsConstructor
public class AirlineCompanyController {
    private final AirlineCompanyService airlineCompanyService;
    @GetMapping
    public ResponseEntity<List<AirlineCompanyResponse>> getAllAirlineCompanies(){
        return ResponseEntity.ok(airlineCompanyService.getAllAirlineCompanies());
    }

    @PostMapping
    public ResponseEntity<AirlineCompanyResponse> createAirlineCompany(@RequestBody AirlineCompanyRequest airlineCompanyRequest){
        return ResponseEntity.ok(airlineCompanyService.createAirlineCompany(airlineCompanyRequest));
    }
}
