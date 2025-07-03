package com.jagt.account.application.command;

import com.jagt.account.domain.model.enums.AccountType;

import java.math.BigDecimal;

public record CreateAccountCommand(
        Long clientId,
        AccountType accountType,
        BigDecimal initialBalance,
        boolean gmfExempt
) {
}
