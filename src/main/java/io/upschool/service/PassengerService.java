package io.upschool.service;

import io.upschool.dto.passengerDto.PassengerRequest;
import io.upschool.dto.passengerDto.PassengerResponse;
import io.upschool.exceptions.PassengerException;
import io.upschool.entity.Passenger;
import io.upschool.repository.PassengerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PassengerService {
    private final PassengerRepository passengerRepository;

    @Transactional
    public Passenger createPassenger(PassengerRequest passengerRequest) {
        checkIfContainCharacter(passengerRequest);
        if (checkIfExist(passengerRequest))
            return passengerRepository.findByIdentityNumber(passengerRequest.getIdentityNumber());

        return passengerRepository.save(getPassenger(passengerRequest));
    }

    private boolean checkIfExist(PassengerRequest passengerRequest) {
        String identityNumber = passengerRequest.getIdentityNumber();
        String emailAddress = passengerRequest.getEmailAddress();
        return passengerRepository.existsByIdentityNumberAndEmailAddress(identityNumber, emailAddress);
    }

    private static void checkIfContainCharacter(PassengerRequest passengerRequest) {
        if (!passengerRequest.getIdentityNumber().matches("\\d+")) {
            throw new PassengerException(PassengerException.IDENTITY_NUMBER_CANNOT_CONTAIN_CHARACTER);
        }
    }

    public PassengerResponse getPassengerResponse(Passenger passenger) {
        return PassengerResponse.builder()
                .id(passenger.getId())
                .nameSurname(passenger.getName() + " " + passenger.getSurname())
                .phoneNumber(passenger.getPhoneNumber())
                .emailAddress(passenger.getEmailAddress())
                .build();
    }

    private Passenger getPassenger(PassengerRequest passengerRequest) {
        return Passenger.builder()
                .name(passengerRequest.getName())
                .surname(passengerRequest.getSurname())
                .emailAddress(passengerRequest.getEmailAddress())
                .identityNumber(passengerRequest.getIdentityNumber())
                .phoneNumber(passengerRequest.getPhoneNumber())
                .build();
    }
}
