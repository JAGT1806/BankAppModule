package com.jagt.client.domain.exception;

import com.jagt.common.domain.exceptions.BankException;

public class ClientException extends BankException {
    public ClientException(String message) {
        super(message);
    }
}
