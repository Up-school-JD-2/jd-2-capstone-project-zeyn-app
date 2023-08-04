package io.upschool.dto.airlineCompanyDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AirlineCompanyResponse {
    private Long id;
    private String name;
    private String emailAddress;
    private String phoneNumber;
}
