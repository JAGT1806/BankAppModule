package com.jagt.account.application.usecase;

import com.jagt.account.application.command.TransferCommand;
import com.jagt.account.application.ports.input.GetAccountUseCase;
import com.jagt.account.application.ports.input.TransferUseCase;
import com.jagt.account.domain.model.Account;
import com.jagt.account.domain.ports.output.AccountPersistencePort;
import com.jagt.account.domain.service.AccountValidationService;
import com.jagt.account.domain.service.TransactionFeeCalculatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransferUseCaseImpl implements TransferUseCase {
    private final AccountPersistencePort accountPersistencePort;
    private final GetAccountUseCase getAccountUseCase;
    private final AccountValidationService accountValidationService;
    private final TransactionFeeCalculatorService feeCalculatorService;

    @Override
    public void execute(TransferCommand command) {
        accountValidationService.validateTransferAccounts(command.fromAccountId(), command.toAccountId());
        accountValidationService.validatePositiveAmount(command.amount(), "transferir");

        Account fromAccount = getAccountUseCase.execute(command.fromAccountId());
        Account toAccount = getAccountUseCase.execute(command.toAccountId());

        accountValidationService.validateActiveAccount(fromAccount);
        accountValidationService.validateActiveAccount(toAccount);

        BigDecimal amountToTransfer = feeCalculatorService.calculateTotalAmountWithFees(command.amount(), fromAccount.isGmfExempt());

        accountValidationService.validateSufficientBalance(fromAccount, amountToTransfer);

        fromAccount.setBalance(fromAccount.getBalance().subtract(amountToTransfer));
        toAccount.setBalance(toAccount.getBalance().add(command.amount()));

        fromAccount.setUpdatedAt(LocalDateTime.now());
        toAccount.setUpdatedAt(LocalDateTime.now());

        accountPersistencePort.save(fromAccount);
        accountPersistencePort.save(toAccount);
    }
}
