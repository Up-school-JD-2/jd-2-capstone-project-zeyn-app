package io.upschool.dto.cardDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardRequest {
    private String cardNumber;
    //@DateTimeFormat(pattern = "MM/yyyy") // Define the desired date pattern
    private String expirationDate;
    private String cvv;
}

