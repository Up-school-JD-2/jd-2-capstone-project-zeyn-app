package io.upschool.service;

import io.upschool.dto.airlineCompanyDto.AirlineCompanyRequest;
import io.upschool.dto.airlineCompanyDto.AirlineCompanyResponse;
import io.upschool.model.AirlineCompany;
import io.upschool.repository.AirlineCompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AirlineCompanyService {
    private final AirlineCompanyRepository airlineCompanyRepository;

    public List<AirlineCompanyResponse> getAllAirlineCompanies() {
        return airlineCompanyRepository.findAll()
                .stream().map(airlineCompany -> AirlineCompanyResponse.builder()
                        .name(airlineCompany.getName())
                        .emailAddress(airlineCompany.getEmailAddress())
                        .phoneNumber(airlineCompany.getPhoneNumber())
                        .id(airlineCompany.getId())
                        .build()).toList();
    }

    public AirlineCompanyResponse createAirlineCompany(AirlineCompanyRequest airlineCompanyRequest) {
        AirlineCompany airlineCompany = airlineCompanyRepository.save(AirlineCompany.builder()
                .name(airlineCompanyRequest.getName())
                .emailAddress(airlineCompanyRequest.getEmailAddress())
                .phoneNumber(airlineCompanyRequest.getPhoneNumber())
                .build());

        return AirlineCompanyResponse.builder()
                .id(airlineCompany.getId())
                .name(airlineCompany.getName())
                .emailAddress(airlineCompanyRequest.getEmailAddress())
                .phoneNumber(airlineCompanyRequest.getPhoneNumber())
                .build();
    }
}
