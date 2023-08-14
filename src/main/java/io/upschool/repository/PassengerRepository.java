package io.upschool.repository;

import io.upschool.entity.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long> {
    boolean existsByIdentityNumberAndEmailAddress(String identityNumber, String emailAddress);
    Passenger findByIdentityNumber(String s);
}
