package io.upschool.service;

import io.upschool.dto.passengerDto.PassengerRequest;
import io.upschool.model.Passenger;
import io.upschool.repository.PassengerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PassengerService {
    private final PassengerRepository passengerRepository;

    public Passenger createPassenger(PassengerRequest passengerRequest) {
        return passengerRepository.save(Passenger.builder()
                .name(passengerRequest.getName())
                .surname(passengerRequest.getSurname())
                .emailAddress(passengerRequest.getEmailAddress())
                .identityNumber(passengerRequest.getIdentityNumber())
                .phoneNumber(passengerRequest.getPhoneNumber())
                .build());
    }
}
