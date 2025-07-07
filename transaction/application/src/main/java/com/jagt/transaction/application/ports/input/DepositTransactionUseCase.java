package com.jagt.transaction.application.ports.input;

import com.jagt.transaction.application.command.DepositCommand;
import com.jagt.transaction.domain.model.Transaction;

public interface DepositTransactionUseCase {
    Transaction execute(DepositCommand command);
}
