package io.upschool.controller;

import io.upschool.dto.BaseResponse;
import io.upschool.dto.airlineCompanyDto.AirlineCompanyRequest;
import io.upschool.dto.airlineCompanyDto.AirlineCompanyResponse;
import io.upschool.dto.flightDto.AirlineFlightRequest;
import io.upschool.dto.flightDto.AirlineFlightResponse;
import io.upschool.exceptions.AirlineCompanyException;
import io.upschool.exceptions.RouteException;
import io.upschool.service.AirlineCompanyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/airlineCompanies")
@RequiredArgsConstructor
public class AirlineCompanyController {
    private final AirlineCompanyService airlineCompanyService;

    @GetMapping
    public ResponseEntity<List<AirlineCompanyResponse>> getAllAirlineCompanies() {
        return ResponseEntity.ok(airlineCompanyService.getAllAirlineCompanies());
    }

    @GetMapping("/searchByName")
    public ResponseEntity<List<AirlineCompanyResponse>> getAirlineCompaniesByName(@RequestParam("name") String name) {
        return ResponseEntity.ok(airlineCompanyService.getAirlineCompaniesByName(name));
    }

    @GetMapping("/flights")
    public ResponseEntity<List<AirlineFlightResponse>> getAllFlightsByAirlineCompanyId(@RequestParam("companyId") Long id) throws AirlineCompanyException {
        return ResponseEntity.ok(airlineCompanyService.getAllFlightsByAirlineCompanyId(id));
    }

    // localhost:8080/api/airlineCompanies/flights?from=departureCity&to=arrivalCity
    @GetMapping("/route")
    public ResponseEntity<List<AirlineFlightResponse>> getAllFlightsByRoutes(@RequestParam("from") String departureCity,
                                                                             @RequestParam("to") String arrivalCity) {
        return ResponseEntity.ok(airlineCompanyService.getAllFlightsByRoutes(departureCity, arrivalCity));
    }

    @GetMapping("/routeAndCompanyId")
    public ResponseEntity<List<AirlineFlightResponse>> getAllFlightsByRoutesAndByAirlineId
            (@RequestParam("id") Long companyId,
             @RequestParam("from") String departureCity,
             @RequestParam("to") String arrivalCity) throws AirlineCompanyException {

        return ResponseEntity.ok(airlineCompanyService.getAllFlightsByRoutesAndByAirlineId(companyId, departureCity, arrivalCity));
    }

    @PostMapping
    public ResponseEntity<Object> createAirlineCompany(@Valid @RequestBody AirlineCompanyRequest airlineCompanyRequest) throws AirlineCompanyException {
        AirlineCompanyResponse airlineCompany = airlineCompanyService.createAirlineCompany(airlineCompanyRequest);
        var response = BaseResponse.<AirlineCompanyResponse>builder()
                .status(HttpStatus.CREATED.value())
                .isSuccess(true)
                .data(airlineCompany)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/createFligth/{id}")
    public ResponseEntity<AirlineFlightResponse> createFlightOnAirlineCompany(@Valid @PathVariable("id") Long id,
                                                                              @RequestBody AirlineFlightRequest airlineFlightRequest) throws AirlineCompanyException, RouteException {
        return ResponseEntity.ok(airlineCompanyService.createFlightOnAirlineCompany(id, airlineFlightRequest));
    }
}