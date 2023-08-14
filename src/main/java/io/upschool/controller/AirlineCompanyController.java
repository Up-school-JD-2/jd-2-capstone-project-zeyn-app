package io.upschool.controller;

import io.upschool.dto.BaseResponse;
import io.upschool.dto.airlineCompanyDto.AirlineCompanyRequest;
import io.upschool.dto.airlineCompanyDto.AirlineCompanyResponse;
import io.upschool.dto.flightDto.FlightRequest;
import io.upschool.dto.flightDto.AirlineFlightResponse;
import io.upschool.exceptions.AirlineCompanyException;
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
    public ResponseEntity<BaseResponse<List<AirlineCompanyResponse>>> getAllAirlineCompanies() {
        List<AirlineCompanyResponse> airlineCompanyResponses = airlineCompanyService.getAllAirlineCompanies();
        BaseResponse<List<AirlineCompanyResponse>> response = BaseResponse.<List<AirlineCompanyResponse>>builder()
                .status(HttpStatus.OK.value())
                .isSuccess(true)
                .data(airlineCompanyResponses)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/searchByName")
    public ResponseEntity<BaseResponse<List<AirlineCompanyResponse>>> getAirlineCompaniesByName(@RequestParam("name") String name) {
        List<AirlineCompanyResponse> airlineCompanyResponses = airlineCompanyService.getAirlineCompaniesByName(name);
        BaseResponse<List<AirlineCompanyResponse>> baseResponse = BaseResponse.<List<AirlineCompanyResponse>>builder()
                .status(HttpStatus.OK.value())
                .isSuccess(true)
                .data(airlineCompanyResponses)
                .build();
        return ResponseEntity.ok(baseResponse);
    }

    @GetMapping("/flights")
    public ResponseEntity<BaseResponse<List<AirlineFlightResponse>>> getAllFlightsByAirlineCompanyId(@RequestParam("companyId") Long id) throws AirlineCompanyException {
        List<AirlineFlightResponse> flights = airlineCompanyService.getAllFlightsByAirlineCompanyId(id);

        BaseResponse<List<AirlineFlightResponse>> baseResponse = BaseResponse.<List<AirlineFlightResponse>>builder()
                .status(HttpStatus.OK.value())
                .isSuccess(true)
                .data(flights)
                .build();
        return ResponseEntity.ok(baseResponse);
    }
    @GetMapping("/route")
    public ResponseEntity<BaseResponse<List<AirlineFlightResponse>>> getAllFlightsByRoutes(@RequestParam("from") String departureCity,
                                                                             @RequestParam("to") String arrivalCity) {

        List<AirlineFlightResponse> allFlightsByRoutes = airlineCompanyService.getAllFlightsByRoutes(departureCity, arrivalCity);
        BaseResponse<List<AirlineFlightResponse>> baseResponse = BaseResponse.<List<AirlineFlightResponse>>builder()
                .status(HttpStatus.OK.value())
                .isSuccess(true)
                .data(allFlightsByRoutes)
                .build();
        return ResponseEntity.ok(baseResponse);
    }

    @GetMapping("/routeAndCompanyId")
    public ResponseEntity<BaseResponse<List<AirlineFlightResponse>> >getAllFlightsByRoutesAndByAirlineId
            (@RequestParam("id") Long companyId,
             @RequestParam("from") String departureCity,
             @RequestParam("to") String arrivalCity) throws AirlineCompanyException {

        List<AirlineFlightResponse> flightsByRoutesAndByAirlineId = airlineCompanyService.getAllFlightsByRoutesAndByAirlineId(companyId, departureCity, arrivalCity);
        BaseResponse<List<AirlineFlightResponse>> baseResponse = BaseResponse.<List<AirlineFlightResponse>>builder()
                .status(HttpStatus.OK.value())
                .isSuccess(true)
                .data(flightsByRoutesAndByAirlineId)
                .build();
        return ResponseEntity.ok(baseResponse);

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
    public ResponseEntity<BaseResponse<AirlineFlightResponse>> createFlightOnAirlineCompany(@Valid @PathVariable("id") Long id,
                                                                              @RequestBody FlightRequest airlineFlightRequest) {
        AirlineFlightResponse flightResponse = airlineCompanyService.createFlightOnAirline(id, airlineFlightRequest);
        BaseResponse<AirlineFlightResponse> response = BaseResponse.<AirlineFlightResponse>builder()
                .status(HttpStatus.CREATED.value())
                .isSuccess(true)
                .data(flightResponse)
                .build();
        return ResponseEntity.ok(response);
    }
}