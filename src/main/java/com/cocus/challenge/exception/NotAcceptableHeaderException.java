package com.cocus.challenge.exception;

public class NotAcceptableHeaderException extends RuntimeException {
    public NotAcceptableHeaderException() {
        super("Not Acceptable Header");
    }
}