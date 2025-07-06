package com.jagt.transaction.application.ports.input;

import com.jagt.common.application.query.GetCommand;
import com.jagt.common.domain.model.Pagination;
import com.jagt.transaction.domain.model.Transaction;

public interface GetTransactionUseCase {
    Pagination<Transaction> execute(GetCommand command);

    Transaction execute(Long id);
}
