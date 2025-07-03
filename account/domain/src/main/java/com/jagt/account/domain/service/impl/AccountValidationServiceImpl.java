package com.jagt.account.domain.service.impl;

import com.jagt.account.domain.exception.AccountNotActivatedException;
import com.jagt.account.domain.model.Account;
import com.jagt.account.domain.model.enums.AccountStatus;
import com.jagt.account.domain.model.enums.AccountType;
import com.jagt.account.domain.service.AccountValidationService;

import java.math.BigDecimal;

public class AccountValidationServiceImpl implements AccountValidationService {
    @Override
    public void validateAccount(Account account) {
        if (account.getBalance() == null) {
            throw new IllegalArgumentException("The account has no balance");
        }

        if (account.getBalance().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("The account has negative balance");
        }

        if (account.getAccountType() == AccountType.SAVING && account.getBalance().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("The saving account has negative balance");
        }

        if (account.getAccountNumber() == null || !account.getAccountNumber().matches("^(53|33)\\d{8}$")) {
            throw new IllegalArgumentException("The account has invalid format");
        }
    }

    @Override
    public void validateActiveAccount(Account account) {
        if(account.getStatus() != AccountStatus.ACTIVE) {
            throw new AccountNotActivatedException(null);
        }
    }

    @Override
    public void validatePositiveAmount(BigDecimal amount, String operation) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto a " + operation + " debe ser mayor a 0.");
        }
    }

    @Override
    public void validateSufficientBalance(Account account, BigDecimal amountToDeduct) {
        if (account.getBalance().compareTo(amountToDeduct) < 0) {
            throw new IllegalArgumentException("Saldo insuficiente para realizar la operación.");
        }

    }

    @Override
    public void validateCancellation(Account account) {
        if (account.getBalance().compareTo(BigDecimal.ZERO) != 0) {
            throw new IllegalStateException("Solo se puede cancelar una cuenta con saldo $0.");
        }
    }

    @Override
    public void validateTransferAccounts(Long fromAccountId, Long toAccountId) {
        if (fromAccountId.equals(toAccountId)) {
            throw new IllegalArgumentException("No se puede transferir a la misma cuenta");
        }
    }
}
