package io.upschool.exceptions;

public class AirportException extends Exception {
    public final static String DATA_NOT_FOUND = "Airport not found";
    public final static String AIRPORT_EXIST = "Airport exist";

    //    public static Exception dataNotFound(String message){
//        return new Exception(message);
//    }
    public AirportException(String message) {
        super(message);
    }
}
