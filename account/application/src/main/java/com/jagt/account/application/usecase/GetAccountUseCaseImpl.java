package com.jagt.account.application.usecase;

import com.jagt.account.application.ports.input.GetAccountUseCase;
import com.jagt.account.application.query.GetAccountsQuery;
import com.jagt.account.domain.model.Account;
import com.jagt.account.domain.ports.output.AccountPersistencePort;
import com.jagt.common.domain.model.Pagination;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAccountUseCaseImpl implements GetAccountUseCase {
    private final AccountPersistencePort accountPersistencePort;

    @Override
    public Account execute(Long id) {
        return accountPersistencePort.findById(id)
                .orElseThrow();
    }

    @Override
    public Pagination<Account> execute(GetAccountsQuery query) {
        List<Account> data =  accountPersistencePort.findAll(query.offset(), query.limit());

        return Pagination.<Account>builder()
                .data(data)
                .offset(query.offset())
                .limit(query.limit())
                .total(accountPersistencePort.count())
                .build();
    }

    @Override
    public Pagination<Account> executeByClientId(Long clientId, GetAccountsQuery query) {
        List<Account> data =  accountPersistencePort.findByClientId(clientId, query.offset(), query.limit());

        return Pagination.<Account>builder()
                .data(data)
                .offset(query.offset())
                .limit(query.limit())
                .total(accountPersistencePort.countByClientId(clientId))
                .build();
    }
}
