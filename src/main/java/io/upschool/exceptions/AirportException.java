package io.upschool.exceptions;

public final class AirportException extends RuntimeException {
    public final static String DATA_NOT_FOUND = "Airport not found";
    public final static String AIRPORT_EXIST = "Airport exist";
    public AirportException(String message) {
        super(message);
    }
}
