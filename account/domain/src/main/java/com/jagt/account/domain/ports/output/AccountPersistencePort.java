package com.jagt.account.domain.ports.output;

import com.jagt.account.domain.model.Account;

import java.util.List;
import java.util.Optional;

public interface AccountPersistencePort {
    Optional<Account> findById(Long id);
    List<Account> findAll(int offset, int limit);
    Account save(Account account);
    Optional<Account> findByAccountNumber(String accountNumber);
    List<Account> findByClientId(Long clientId, int offset, int limit);
    long count();
    long countByClientId(Long clientId);
}
