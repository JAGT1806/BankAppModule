package com.jagt.account.application.usecase;

import com.jagt.account.application.ports.input.AccountStatusUseCase;
import com.jagt.account.application.ports.input.GetAccountUseCase;
import com.jagt.account.domain.model.Account;
import com.jagt.account.domain.model.enums.AccountStatus;
import com.jagt.account.domain.ports.output.AccountPersistencePort;
import com.jagt.account.domain.service.AccountValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AccountStatusUseCaseImpl implements AccountStatusUseCase {
    private final AccountPersistencePort accountPersistencePort;
    private final GetAccountUseCase getAccountUseCase;
    private final AccountValidationService accountValidationService;

    @Override
    public void activate(Long accountId) {
        Account account = getAccountUseCase.execute(accountId);
        account.setStatus(AccountStatus.ACTIVE);
        account.setUpdatedAt(LocalDateTime.now());
        accountPersistencePort.save(account);
    }

    @Override
    public void deactivate(Long accountId) {
        Account account = getAccountUseCase.execute(accountId);
        account.setStatus(AccountStatus.INACTIVE);
        account.setUpdatedAt(LocalDateTime.now());
        accountPersistencePort.save(account);
    }

    @Override
    public void cancel(Long accountId) {
        Account account = getAccountUseCase.execute(accountId);
        accountValidationService.validateCancellation(account);
        account.setStatus(AccountStatus.CANCELED);
        account.setUpdatedAt(LocalDateTime.now());
        accountPersistencePort.save(account);
    }
}
