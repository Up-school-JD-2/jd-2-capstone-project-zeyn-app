package io.upschool.service;

import io.upschool.dto.routeDto.RouteRequest;
import io.upschool.dto.routeDto.RouteResponse;
import io.upschool.exceptions.AirportException;
import io.upschool.exceptions.RouteException;
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

    public RouteResponse createRoute(RouteRequest routeRequest) throws AirportException, RouteException {
        Airport arrivalAirport = airportService.getAirport(routeRequest.getArrivalAirportId());
        Airport departureAirport = airportService.getAirport(routeRequest.getDepartureAirportId());

        if (arrivalAirport.getName().equalsIgnoreCase(departureAirport.getName()))
            throw new RouteException(RouteException.DEPARTURE_AND_ARRIVAL_AIRPORT_CANNOT_BE_THE_SAME);

        List<Route> routeList = routeRepository.findAll().stream()
                .filter(route ->
                route.getArrivalAirport().getName().equalsIgnoreCase(arrivalAirport.getName()) &&
                        route.getDepartureAirport().getName().equalsIgnoreCase(departureAirport.getName()))
                .toList();

        if(routeList.size()!=0) throw new RouteException(RouteException.ROUTE_DUPLICATED_EXCEPTION);

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

    public Route getRoute(Long routeId) {
        return routeRepository.findById(routeId).orElseThrow();
    }
}
