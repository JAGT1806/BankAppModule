package com.jagt.account.application.usecase;

import com.jagt.account.application.command.DepositCommand;
import com.jagt.account.application.ports.input.DepositUseCase;
import com.jagt.account.application.ports.input.GetAccountUseCase;
import com.jagt.account.domain.model.Account;
import com.jagt.account.domain.ports.output.AccountPersistencePort;
import com.jagt.account.domain.service.AccountValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DepositUseCaseImpl implements DepositUseCase {
    private final AccountPersistencePort accountPersistencePort;
    private final GetAccountUseCase getAccountUseCase;
    private final AccountValidationService accountValidationService;

    @Override
    public void execute(DepositCommand command) {
        accountValidationService.validatePositiveAmount(command.amount(), "depositar");

        Account account = getAccountUseCase.execute(command.accountId());
        accountValidationService.validateAccount(account);

        account.setBalance(account.getBalance().add(command.amount()));
        account.setUpdatedAt(LocalDateTime.now());

        accountPersistencePort.save(account);
    }
}
