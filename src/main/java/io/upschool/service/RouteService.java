package io.upschool.service;

import io.upschool.dto.routeDto.RouteRequest;
import io.upschool.dto.routeDto.RouteResponse;
import io.upschool.model.Airport;
import io.upschool.model.Route;
import io.upschool.repository.RouteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RouteService {
    private final RouteRepository routeRepository;
    private final AirportService airportService;

    public List<RouteResponse> getAllRoutes() {
        List<Route> routeList = routeRepository.findAll();
        return routeList.stream().map(route ->
                RouteResponse.builder()
                        .id(route.getId())
                        .arrivalAirportName(route.getArrivalAirport().getName())
                        .departureAirportName(route.getDepartureAirport().getName())
                        .build()).toList();
    }

    public RouteResponse createRoute(RouteRequest routeRequest) {
        Airport arrivalAirport = airportService.findAirportByName(routeRequest.getArrivalAirportName());
        Airport departureAirport = airportService.findAirportByName(routeRequest.getDepartureAirportName());

        Route route = routeRepository.save(Route.builder()
                .arrivalAirport(arrivalAirport)
                .departureAirport(departureAirport)
                .isActive(true)
                .build());

        return RouteResponse.builder()
                .id(route.getId())
                .departureAirportName(route.getDepartureAirport().getName())
                .arrivalAirportName(route.getArrivalAirport().getName())
                .build();
    }
}
