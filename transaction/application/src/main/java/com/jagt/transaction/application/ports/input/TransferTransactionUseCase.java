package com.jagt.transaction.application.ports.input;

import com.jagt.transaction.application.command.TransferCommand;
import com.jagt.transaction.domain.model.Transaction;

public interface TransferTransactionUseCase {
    Transaction execute(TransferCommand command);
}
