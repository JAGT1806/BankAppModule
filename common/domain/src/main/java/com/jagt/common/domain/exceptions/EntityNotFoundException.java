package com.jagt.common.domain.exceptions;

public class EntityNotFoundException extends BankException {
    public EntityNotFoundException(String message) {
        super(message);
    }
    public EntityNotFoundException() {
        super("");
    }
}
