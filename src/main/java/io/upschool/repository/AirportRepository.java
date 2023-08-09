package io.upschool.repository;

import io.upschool.model.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AirportRepository extends JpaRepository<Airport, Long> {
    List<Airport> findAirportByNameContainingIgnoreCase(String name);
    List<Airport> findByNameAndLocationContainingIgnoreCase(String name, String location);
}
