package com.jagt.account.domain.service;

import com.jagt.account.domain.model.Account;

import java.math.BigDecimal;

public interface AccountValidationService {
    void validateAccount(Account account);
    void validateActiveAccount(Account account);
    void validatePositiveAmount(BigDecimal amount, String operation);
    void validateSufficientBalance(Account account, BigDecimal amountToDeduct);
    void validateCancellation(Account account);
    void validateTransferAccounts(Long fromAccountId, Long toAccountId);
}
