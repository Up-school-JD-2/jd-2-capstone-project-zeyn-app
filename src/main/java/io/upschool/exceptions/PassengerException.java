package io.upschool.exceptions;

public class PassengerException extends RuntimeException{
    public static final String IDENTITY_NUMBER_CANNOT_CONTAIN_CHARACTER = "Identity number cannot contain any character";
    public PassengerException(String message){
        super(message);
    }
}
