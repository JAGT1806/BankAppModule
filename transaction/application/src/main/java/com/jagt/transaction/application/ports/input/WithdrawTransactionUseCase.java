package com.jagt.transaction.application.ports.input;

import com.jagt.transaction.application.command.WithdrawCommand;
import com.jagt.transaction.domain.model.Transaction;

public interface WithdrawTransactionUseCase {
    Transaction execute(WithdrawCommand command);
}
