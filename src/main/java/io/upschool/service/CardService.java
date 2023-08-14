package io.upschool.service;

import io.upschool.dto.cardDto.CardRequest;
import io.upschool.dto.cardDto.CardResponse;
import io.upschool.exceptions.CardException;
import io.upschool.model.Card;
import io.upschool.repository.CardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CardService {
    private final CardRepository cardRepository;

    @Transactional
    public Card createCard(CardRequest cardRequest) {
        return cardRepository.save(getCard(cardRequest));
    }
    public CardResponse getCardResponse(Card card){
        return CardResponse.builder()
                .cardNumber(card.getCardNumber())
                .build();
    }

    private String maskedCard(String card) {
        String cleanedCardNumber = convertToDigit(card);
        checkDigit(cleanedCardNumber);

        String maskedPart = cleanedCardNumber.substring(6, cleanedCardNumber.length() - 4).replaceAll(".", "*");
        return cleanedCardNumber.substring(0, 6) + maskedPart + cleanedCardNumber.substring(cleanedCardNumber.length() - 4);
    }

    private static void checkDigit(String card) {
        if (card.length() != 16)
            throw new CardException(CardException.INVALID_CARD_NUMBER_EXCEPTION);
    }

    private String convertToDigit(String card){
        return card.replaceAll("[^0-9]", "");
    }

    private Card getCard(CardRequest cardRequest) {
        String cardNumber = maskedCard(cardRequest.getCardNumber());
        return Card.builder()
                .cardNumber(cardNumber)
                .cvv(cardRequest.getCvv())
                .expirationDate(cardRequest.getExpirationDate())
                .build();
    }
    
    /*******************************************************/


    public void checkValidExpirationDate(String expirationDate){

    }

    private void checkValidCvv(String cvv) {
        if (cvv.length() != 3||isContainChar(cvv))
            throw new CardException(CardException.INVALID_CVV);
    }

    private boolean isContainChar(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) return true;
        }
        return false;
    }
    
    
}
