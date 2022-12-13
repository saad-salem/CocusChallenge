package com.cocus.challenge.exception;

public class RateLimitExceedException extends RuntimeException {
    public RateLimitExceedException() {
        super("Rate Limit Exceeded");
    }
}