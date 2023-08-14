package io.upschool.repository;

import io.upschool.entity.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AirportRepository extends JpaRepository<Airport, Long> {
    List<Airport> findAirportByNameContainingIgnoreCase(String name);
    boolean existsByNameAndLocationContainingIgnoreCase(String name, String location);
}
