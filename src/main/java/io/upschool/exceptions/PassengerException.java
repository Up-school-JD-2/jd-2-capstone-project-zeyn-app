package io.upschool.exceptions;

public class PassengerException extends Exception{
    public static final String IDENTITY_NUMBER_CANNOT_CONTAIN_CHARACTER = "Identity number cannot contain any character";
    public static final String IDENTITY_NUMBER_EXIST = "Identity number exist";
    public static final String EMAIL_ADDRESS_EXIST = "Email address exist";
    public PassengerException(String message){
        super(message);
    }
}
