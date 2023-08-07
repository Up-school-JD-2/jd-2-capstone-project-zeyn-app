package io.upschool.dto.passengerDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PassengerRequest {
    private String name;
    private String surname;
    private String identityNumber;
    private String emailAddress;
    private String phoneNumber;
}
