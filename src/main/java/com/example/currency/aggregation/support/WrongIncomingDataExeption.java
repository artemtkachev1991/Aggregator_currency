package com.example.currency.aggregation.support;

public class WrongIncomingDataExeption extends Exception {
    public WrongIncomingDataExeption() {
    }

    public WrongIncomingDataExeption(String message) {
        super(message);
    }

    public WrongIncomingDataExeption(String message, Throwable cause) {
        super(message, cause);
    }
}