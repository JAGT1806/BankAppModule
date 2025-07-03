package com.jagt.client.domain.exception;

public class AgeRestrictionException extends ClientException {
    public AgeRestrictionException(String message) {
        super(message);
    }

    public AgeRestrictionException() {
        super("");
    }
}
