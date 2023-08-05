package io.upschool.exceptions;

public class AirlineCompanyException extends Exception{
    public static final String DATA_NOT_FOUND = "Airline company not found";
    public AirlineCompanyException(String message){
        super(message);
    }
}
