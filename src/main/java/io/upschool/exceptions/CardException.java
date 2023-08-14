package io.upschool.exceptions;

public final class CardException extends RuntimeException{
    public static String INVALID_CARD_NUMBER_EXCEPTION = "Card number must be 16-digit and contain";
    public static String INVALID_CVV = "Cvv must be 3 digits and must contain only numbers";
    public CardException(String message){
        super(message);
    }
}