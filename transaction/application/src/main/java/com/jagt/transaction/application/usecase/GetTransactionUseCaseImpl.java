package com.jagt.transaction.application.usecase;

import com.jagt.common.application.query.GetCommand;
import com.jagt.common.domain.model.Pagination;
import com.jagt.transaction.application.ports.input.GetTransactionUseCase;
import com.jagt.transaction.domain.exception.TransactionNotFoundException;
import com.jagt.transaction.domain.model.Transaction;
import com.jagt.transaction.domain.ports.output.TransactionPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetTransactionUseCaseImpl implements GetTransactionUseCase {
    private final TransactionPersistencePort persistencePort;

    @Override
    public Pagination<Transaction> execute(GetCommand command) {
        List<Transaction> transaction = persistencePort.findAll(command.offset(), command.limit());
        return Pagination.<Transaction>builder()
                .data(transaction)
                .offset(command.offset())
                .limit(command.limit())
                .total(persistencePort.count())
                .build();
    }

    @Override
    public Transaction execute(Long id) {
        return persistencePort.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException(null));
    }
}
