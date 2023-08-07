package io.upschool.repository;

import io.upschool.model.AirlineCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AirlineCompanyRepository extends JpaRepository<AirlineCompany, Long> {
    // List<AirlineCompany> findAirlineCompaniesByName(String name);
    List<AirlineCompany> findAirlineCompaniesByNameContainingIgnoreCase(String name);

}
