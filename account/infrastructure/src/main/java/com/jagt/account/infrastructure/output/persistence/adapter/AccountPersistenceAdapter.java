package com.jagt.account.infrastructure.output.persistence.adapter;

import com.jagt.account.domain.model.Account;
import com.jagt.account.domain.ports.output.AccountPersistencePort;
import com.jagt.account.infrastructure.output.persistence.mapper.AccountPersistenceMapper;
import com.jagt.account.infrastructure.output.persistence.repository.AccountJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AccountPersistenceAdapter implements AccountPersistencePort {
    private final AccountJpaRepository repository;
    private final AccountPersistenceMapper mapper;

    @Override
    public Optional<Account> findById(Long id) {
        return repository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public List<Account> findAll(int offset, int limit) {
        Pageable pageable = PageRequest.of(offset, limit);
        return mapper.toDomainList(repository.findAll(pageable).getContent());
    }

    @Override
    public Account save(Account account) {
        return mapper.toDomain(repository.save(mapper.toEntity(account)));
    }

    @Override
    public Optional<Account> findByAccountNumber(String accountNumber) {
        return repository.findByAccountNumber(accountNumber)
                .map(mapper::toDomain);
    }

    @Override
    public List<Account> findByClientId(Long clientId, int offset, int limit) {
        Pageable pageable = PageRequest.of(offset, limit);
        return mapper.toDomainList(repository.findByClient_Id(clientId, pageable));
    }

    @Override
    public long count() {
        return repository.count();
    }

    @Override
    public long countByClientId(Long clientId) {
        return repository.countByClient_Id(clientId);
    }
}
