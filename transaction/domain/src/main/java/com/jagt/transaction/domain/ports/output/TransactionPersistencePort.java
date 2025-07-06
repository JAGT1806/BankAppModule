package com.jagt.transaction.domain.ports.output;

import com.jagt.transaction.domain.model.Transaction;

import java.util.List;
import java.util.Optional;

public interface TransactionPersistencePort {
    List<Transaction> findAll(int offset, int limit);
    Optional<Transaction> findById(Long id);
    Transaction save(Transaction transaction);
    long count();
}
