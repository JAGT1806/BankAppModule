package com.jagt.transaction.domain.exception;

import com.jagt.common.domain.exceptions.EntityNotFoundException;

public class TransactionNotFoundException extends EntityNotFoundException {
    public TransactionNotFoundException(String message) {
        super(message);
    }
}
