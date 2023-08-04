package io.upschool.dto.airlineCompanyDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AirlineCompanyRequest {
    private String name;
    private String emailAddress;
    private String phoneNumber;
}
