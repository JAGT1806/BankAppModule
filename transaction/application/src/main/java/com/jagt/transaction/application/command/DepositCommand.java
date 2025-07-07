package com.jagt.transaction.application.command;

import java.math.BigDecimal;

public record DepositCommand(
        Long accountId,
        BigDecimal amount
) {
}
