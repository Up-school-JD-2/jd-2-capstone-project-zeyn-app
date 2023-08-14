package io.upschool.exceptions;

public class RouteException extends RuntimeException{
    public final static String DATA_NOT_FOUND = "Route not found";
    public final static String ROUTE_DUPLICATED_EXCEPTION = "Route duplicated";
    public final static String DEPARTURE_AND_ARRIVAL_AIRPORT_CANNOT_BE_THE_SAME = "Departure and arrival airports can not be the same";
    public RouteException(String message){
        super(message);
    }
}
