package io.upschool.service;

import io.upschool.dto.cardDto.CardRequest;
import io.upschool.exceptions.CardNumberException;
import io.upschool.model.Card;
import io.upschool.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CardService {
    private final CardRepository cardRepository;

    public Card createCard(CardRequest cardRequest) throws CardNumberException {
        return cardRepository.save(Card.builder()
                .cardNumber(maskedCard(cardRequest.getCardNumber()))
                .cvv(cardRequest.getCvv())
                .expirationDate(cardRequest.getExpirationDate())
                .build());
    }

    private String maskedCard(String card) throws CardNumberException {
        String cleanedCardNumber = card.replaceAll("[^0-9]", "");

        int length = cleanedCardNumber.length();

        if (length != 16)
            throw new CardNumberException(CardNumberException.INVALID_CARD_NUMBER_EXCEPTION);

        String maskedPart = cleanedCardNumber.substring(6, length - 4).replaceAll(".", "*");
        return cleanedCardNumber.substring(0, 6) + maskedPart + cleanedCardNumber.substring(length - 4);
    }
}
