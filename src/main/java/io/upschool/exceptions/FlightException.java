package io.upschool.exceptions;

public class FlightException extends Exception{
    public static String DATA_NOT_FOUND = "Flight not found";
    public static String CAPACITY_IS_FULL = "The capacity of flight is full";
    public FlightException(String message){
        super(message);
    }
}
