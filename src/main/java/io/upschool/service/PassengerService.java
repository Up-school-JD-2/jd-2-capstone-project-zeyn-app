package io.upschool.service;

import io.upschool.dto.passengerDto.PassengerRequest;
import io.upschool.exceptions.PassengerException;
import io.upschool.model.Passenger;
import io.upschool.repository.PassengerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PassengerService {
    private final PassengerRepository passengerRepository;

    @Transactional
    public Passenger createPassenger(PassengerRequest passengerRequest) throws PassengerException {
        if (!passengerRequest.getIdentityNumber().matches("\\d+")) {
            throw new PassengerException(PassengerException.IDENTITY_NUMBER_CANNOT_CONTAIN_CHARACTER);
        }
        if(passengerRepository.existsByIdentityNumber(passengerRequest.getIdentityNumber())){
            return passengerRepository.findByIdentityNumber(passengerRequest.getIdentityNumber());
        }
            //throw new PassengerException(PassengerException.IDENTITY_NUMBER_EXIST);

//        if(passengerRepository.existsByEmailAddress(passengerRequest.getEmailAddress()))
//            throw new PassengerException(PassengerException.EMAIL_ADDRESS_EXIST);

        return passengerRepository.save(Passenger.builder()
                .name(passengerRequest.getName())
                .surname(passengerRequest.getSurname())
                .emailAddress(passengerRequest.getEmailAddress())
                .identityNumber(passengerRequest.getIdentityNumber())
                .phoneNumber(passengerRequest.getPhoneNumber())
                .build());
    }
}
