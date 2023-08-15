package io.upschool.service;

import io.upschool.dto.routeDto.RouteRequest;
import io.upschool.dto.routeDto.RouteResponse;
import io.upschool.entity.Airport;
import io.upschool.entity.Route;
import io.upschool.exceptions.RouteException;
import io.upschool.repository.RouteRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RouteService {
    private final RouteRepository routeRepository;
    private final AirportService airportService;

    public List<RouteResponse> getAllRoutes() {
        List<Route> routeList = routeRepository.findAll();
        return routeList.stream().map(this::getRouteResponse).toList();
    }

    public List<RouteResponse> getRouteByLocations(String departureCity, String arrivalCity) {
        List<Route> routes = routeRepository.findAllByDepartureAirport_LocationAndArrivalAirport_Location(departureCity, arrivalCity);
        return routes.stream().map(this::getResponse).toList();
    }

    @Transactional
    public RouteResponse createRoute(RouteRequest routeRequest) {

        checkIfAirportsSame(routeRequest.getArrivalAirportId(), routeRequest.getDepartureAirportId());

        Airport arrivalAirport = airportService.getAirport(routeRequest.getArrivalAirportId());
        Airport departureAirport = airportService.getAirport(routeRequest.getDepartureAirportId());

        checkIfExist(arrivalAirport, departureAirport);

        Route route = routeRepository.save(getRoute(arrivalAirport, departureAirport));
        return getResponse(route);
    }

    public Route getRoute(Long routeId){
        return routeRepository.findById(routeId).orElseThrow(() -> new RouteException(RouteException.DATA_NOT_FOUND));
    }

    private RouteResponse getResponse(Route route) {
        return RouteResponse.builder()
                .id(route.getId())
                .departureAirportName(route.getDepartureAirport().getName())
                .arrivalAirportName(route.getArrivalAirport().getName())
                .build();
    }

    private Route getRoute(Airport arrivalAirport, Airport departureAirport) {
        return Route.builder()
                .arrivalAirport(arrivalAirport)
                .departureAirport(departureAirport)
                .isActive(true)
                .build();
    }

    private RouteResponse getRouteResponse(Route route) {
        return RouteResponse.builder()
                .id(route.getId())
                .arrivalAirportName(route.getArrivalAirport().getName())
                .departureAirportName(route.getDepartureAirport().getName())
                .build();
    }

    private void checkIfExist(Airport arrivalAirport, Airport departureAirport) {
        if (routeRepository.existsAllByArrivalAirport_NameAndDepartureAirport_Name(arrivalAirport.getName(), departureAirport.getName()))
            throw new RouteException(RouteException.ROUTE_DUPLICATED_EXCEPTION);
    }

    private void checkIfAirportsSame(Long arrivalAirportId, Long departureAirportId) {
        if (Objects.equals(arrivalAirportId, departureAirportId))
            throw new RouteException(RouteException.DEPARTURE_AND_ARRIVAL_AIRPORT_CANNOT_BE_THE_SAME);
    }
}
