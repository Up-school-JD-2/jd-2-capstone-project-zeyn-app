package io.upschool.repository;

import io.upschool.entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {
    boolean existsAllByArrivalAirport_NameAndDepartureAirport_Name(String arrivalAirportName, String departureAirportName);
    List<Route> findAllByDepartureAirport_LocationAndArrivalAirport_Location(String departureAirportLocation, String arrivalAirportLocation);
}
