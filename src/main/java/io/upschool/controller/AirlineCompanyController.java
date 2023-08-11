package io.upschool.controller;

import io.upschool.dto.airlineCompanyDto.AirlineCompanyRequest;
import io.upschool.dto.airlineCompanyDto.AirlineCompanyResponse;
import io.upschool.dto.flightDto.CompanyFlightRequest;
import io.upschool.dto.flightDto.FlightResponse;
import io.upschool.exceptions.AirlineCompanyException;
import io.upschool.exceptions.RouteException;
import io.upschool.service.AirlineCompanyService;
import jakarta.validation.Valid;
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
    @GetMapping("/searchByName")
    public ResponseEntity<List<AirlineCompanyResponse>> getAirlineCompaniesByName(@RequestParam("name") String name){
        return ResponseEntity.ok(airlineCompanyService.getAirlineCompaniesByName(name));
    }
    @GetMapping("/flights")
    public ResponseEntity<List<FlightResponse>> getAllFlightsByAirlineCompanyId(@RequestParam("companyId") Long id) throws AirlineCompanyException {
        return ResponseEntity.ok(airlineCompanyService.getAllFlightsByAirlineCompanyId(id));
    }

    // localhost:8080/api/airlineCompanies/flights?from=departureCity&to=arrivalCity
    @GetMapping("/route")
    public ResponseEntity<List<FlightResponse>> getAllFlightsByRoutes(@RequestParam("from") String departureCity,
                                                                      @RequestParam("to") String arrivalCity){
        return ResponseEntity.ok(airlineCompanyService.getAllFlightsByRoutes(departureCity, arrivalCity));
    }

    @GetMapping("/routeAndCompanyId")
    public ResponseEntity<List<FlightResponse>> getAllFlightsByRoutesAndByAirlineId
            (@RequestParam("id") Long companyId, @RequestParam("from") String departureCity,
                                                                      @RequestParam("to") String arrivalCity) throws AirlineCompanyException {
        return ResponseEntity.ok(airlineCompanyService.
                getAllFlightsByRoutesAndByAirlineId(companyId, departureCity, arrivalCity));
    }
    @PostMapping
    public ResponseEntity<AirlineCompanyResponse> createAirlineCompany(@Valid @RequestBody AirlineCompanyRequest airlineCompanyRequest) throws AirlineCompanyException {
        return ResponseEntity.ok(airlineCompanyService.createAirlineCompany(airlineCompanyRequest));
    }
    @PostMapping("/createFligth/{id}")
    public ResponseEntity<FlightResponse> createFlightOnAirlineCompany(@Valid @PathVariable("id") Long id, @RequestBody CompanyFlightRequest companyFlightRequest) throws AirlineCompanyException, RouteException {
        return ResponseEntity.ok(airlineCompanyService.createFlightOnAirlineCompany(id, companyFlightRequest));
    }
}