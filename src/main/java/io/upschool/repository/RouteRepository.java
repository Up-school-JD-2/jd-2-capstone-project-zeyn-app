package io.upschool.repository;

import io.upschool.model.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {
    //List<Route> findAllByArrivalAirport_NameAndDepartureAirport_NameContainingIgnoreCase(String arrivalAirportName, String departureAirportName);
    boolean existsAllByArrivalAirport_NameAndDepartureAirport_Name(String arrivalAirportName, String departureAirportName);

    // List<Route> findByArrivalAirportEqualsIgnoreCaseAndDepartureAirport_Name(String arrivalAirportName, String departureAirportName);
//   boolean existsByArrivalAirportEqualsAndDepartureAirport_Name(Airport arrivalAirport, String departureAirportName);
}
