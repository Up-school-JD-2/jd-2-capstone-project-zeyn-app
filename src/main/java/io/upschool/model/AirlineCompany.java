package io.upschool.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "airline_companies")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AirlineCompany {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    private String emailAddress;
    private String phoneNumber;
}