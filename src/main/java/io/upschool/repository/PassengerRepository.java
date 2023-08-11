package io.upschool.repository;

import io.upschool.model.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long> {
    boolean existsByIdentityNumber(String identityNumber);
    boolean existsByEmailAddress(String identityNumber);
}
