package com.cocus.challenge.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException() {
        super("Account Not Found");
    }
}
