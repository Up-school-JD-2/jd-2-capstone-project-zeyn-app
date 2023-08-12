package io.upschool.dto.passengerDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
    @Size(min = 11, max = 11, message = "Identity number must be 11 characters long")
    private String identityNumber;
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
            flags = Pattern.Flag.CASE_INSENSITIVE, message = "Email must be a well-formed email address")
    private String emailAddress;
    private String phoneNumber;
}
