package io.upschool.exceptions;

public class CardNumberException extends Exception{
    public static String INVALID_CARD_NUMBER_EXCEPTION = "Card number must be 16-digit and contain";
    public CardNumberException(String message){
        super(message);
    }
}