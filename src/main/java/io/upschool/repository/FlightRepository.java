package io.upschool.repository;

import io.upschool.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {
    List<Flight> findAllByAirlineCompany_Id(Long id);
    List<Flight> findAllByRouteDepartureAirportLocationAndRouteArrivalAirportLocation(String departureAirportLocation, String arrivalAirportLocation);
}
