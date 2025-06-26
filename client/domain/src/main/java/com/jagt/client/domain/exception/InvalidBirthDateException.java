package com.jagt.client.domain.exception;

public class InvalidBirthDateException extends ClientException {
    public InvalidBirthDateException(String message) {
        super(message);
    }

    public InvalidBirthDateException() {
        super("");
    }
}
