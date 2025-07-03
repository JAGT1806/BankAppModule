package com.jagt.account.application.command;

import java.math.BigDecimal;

public record DepositCommand(
        Long accountId,
        BigDecimal amount
) {
}
