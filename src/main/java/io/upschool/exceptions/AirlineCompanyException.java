package io.upschool.exceptions;

public final class AirlineCompanyException extends RuntimeException{
    public static final String DATA_NOT_FOUND = "Airline company not found";
    public static final String AIRLINE_COMPANY_EXIST = "Airline company exist";
    public AirlineCompanyException(String message){
        super(message);
    }
}
