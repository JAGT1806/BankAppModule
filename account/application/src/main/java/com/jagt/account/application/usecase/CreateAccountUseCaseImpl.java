package com.jagt.account.application.usecase;

import com.jagt.account.application.command.CreateAccountCommand;
import com.jagt.account.application.ports.input.CreateAccountUseCase;
import com.jagt.account.domain.model.Account;
import com.jagt.account.domain.model.enums.AccountStatus;
import com.jagt.account.domain.model.enums.AccountType;
import com.jagt.account.domain.ports.output.AccountPersistencePort;
import com.jagt.account.domain.service.AccountNumberGeneratorService;
import com.jagt.account.domain.service.AccountValidationService;
import com.jagt.client.application.ports.input.GetClientUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CreateAccountUseCaseImpl implements CreateAccountUseCase {
    private final AccountPersistencePort accountPersistencePort;
    private final AccountNumberGeneratorService accountNumberGeneratorService;
    private final AccountValidationService accountValidationService;
    private final GetClientUseCase getClientUseCase;

    @Override
    public Account execute(CreateAccountCommand command) {
        getClientUseCase.execute(command.clientId());

        Account account = Account.builder()
                .client(getClientUseCase.execute(command.clientId()))
                .accountType(command.accountType())
                .balance(command.initialBalance() != null ? command.initialBalance() : BigDecimal.ZERO)
                .gmfExempt(command.gmfExempt())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .status(determineInitialStatus(command.accountType()))
                .build();

        accountValidationService.validateAccount(account);

        return accountPersistencePort.save(account);
    }

    private AccountStatus determineInitialStatus(AccountType accountType) {
        return accountType == AccountType.SAVING ? AccountStatus.ACTIVE : AccountStatus.INACTIVE;
    }
}
