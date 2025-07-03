package com.jagt.client.domain.exception;

import com.jagt.common.domain.exceptions.EntityNotFoundException;

public class ClientNotFoundException extends EntityNotFoundException {
    public ClientNotFoundException(String message) {
        super(message);
    }

    public ClientNotFoundException() {
        super("Client not found");
    }
}
