package io.upschool.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cards")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String cardNumber;
    private String expirationDate;
    private String cvv;
    @ManyToOne(fetch = FetchType.LAZY)
    private Passenger passenger;
}
