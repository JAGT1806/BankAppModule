package com.jagt.account.application.usecase;

import com.jagt.account.application.command.WithdrawCommand;
import com.jagt.account.application.ports.input.GetAccountUseCase;
import com.jagt.account.application.ports.input.WithdrawUseCase;
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
public class WithdrawUseCaseImpl implements WithdrawUseCase {
    private final AccountPersistencePort accountPersistencePort;
    private final GetAccountUseCase getAccountUseCase;
    private final AccountValidationService accountValidationService;
    private final TransactionFeeCalculatorService feeCalculatorService;

    @Override
    public void execute(WithdrawCommand command) {
        accountValidationService.validatePositiveAmount(command.amount(), "retirar");

        Account account = getAccountUseCase.execute(command.accountId());
        accountValidationService.validateActiveAccount(account);

        BigDecimal amountToWithdraw = feeCalculatorService.calculateTotalAmountWithFees(command.amount(), account.isGmfExempt());

        accountValidationService.validateSufficientBalance(account, amountToWithdraw);

        account.setBalance(account.getBalance().subtract(amountToWithdraw));
        account.setUpdatedAt(LocalDateTime.now());

        accountPersistencePort.save(account);
    }
}
