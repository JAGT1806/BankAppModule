package com.jagt.transaction.infrastructure.output.persistence.adapter;

import com.jagt.transaction.domain.model.Transaction;
import com.jagt.transaction.domain.ports.output.TransactionPersistencePort;
import com.jagt.transaction.infrastructure.output.persistence.mapper.TransactionPersistenceMapper;
import com.jagt.transaction.infrastructure.output.persistence.repository.TransactionJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TransactionPersistenceAdapter implements TransactionPersistencePort {
    private final TransactionJpaRepository transactionJpaRepository;
    private final TransactionPersistenceMapper mapper;

    @Override
    public List<Transaction> findAll(int offset, int limit) {
        Pageable pageable = PageRequest.of(offset, limit);
        return mapper.toDomain(transactionJpaRepository.findAll(pageable).getContent());
    }

    @Override
    public Optional<Transaction> findById(Long id) {
        return transactionJpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Transaction save(Transaction transaction) {
        return mapper.toDomain(transactionJpaRepository.save(mapper.toEntity(transaction)));
    }

    @Override
    public long count() {
        return transactionJpaRepository.count();
    }
}
