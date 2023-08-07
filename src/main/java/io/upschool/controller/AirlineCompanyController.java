package io.upschool.controller;

import io.upschool.dto.airlineCompanyDto.AirlineCompanyRequest;
import io.upschool.dto.airlineCompanyDto.AirlineCompanyResponse;
import io.upschool.dto.flightDto.CompanyFlightRequest;
import io.upschool.dto.flightDto.FlightResponse;
import io.upschool.exceptions.AirlineCompanyException;
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
    @GetMapping("/companyName")
    public ResponseEntity<List<AirlineCompanyResponse>> getAirlineCompaniesByName(@RequestParam("name") String name){
        return ResponseEntity.ok(airlineCompanyService.getAirlineCompaniesByName(name));
    }
    @GetMapping("/flights")
    public ResponseEntity<List<FlightResponse>> getAllFlightsByAirlineCompanyId(@RequestParam("companyId") Long id) throws AirlineCompanyException {
        return ResponseEntity.ok(airlineCompanyService.getAllFlightsByAirlineCompanyId(id));
    }

    // localhost:8080/api/airlineCompanies/flights?from=departureCity&to=arrivalCity
    @GetMapping("/flightRoute")
    public ResponseEntity<List<FlightResponse>> getAllFlightsByRoutes(@RequestParam("from") String departureCity,
                                                                      @RequestParam("to") String arrivalCity){
        return ResponseEntity.ok(airlineCompanyService.getAllFlightsByRoutes(departureCity, arrivalCity));
    }

    @GetMapping("/flightRoute/{companyId}")
    public ResponseEntity<List<FlightResponse>> getAllFlightsByRoutesAndByAirlineId
            (@RequestParam("companyId") Long companyId, @RequestParam("from") String departureCity,
                                                                      @RequestParam("to") String arrivalCity){
        return ResponseEntity.ok(airlineCompanyService.
                getAllFlightsByRoutesAndByAirlineId(companyId, departureCity, arrivalCity));
    }


    @PostMapping
    public ResponseEntity<AirlineCompanyResponse> createAirlineCompany(@RequestBody AirlineCompanyRequest airlineCompanyRequest){
        return ResponseEntity.ok(airlineCompanyService.createAirlineCompany(airlineCompanyRequest));
    }
    @PostMapping("/createFligth/{id}")
    public ResponseEntity<FlightResponse> createFlightOnAirlineCompany(@PathVariable("id") Long id, @RequestBody CompanyFlightRequest companyFlightRequest) throws AirlineCompanyException {
        return ResponseEntity.ok(airlineCompanyService.createFlightOnAirlineCompany(id, companyFlightRequest));
    }
}