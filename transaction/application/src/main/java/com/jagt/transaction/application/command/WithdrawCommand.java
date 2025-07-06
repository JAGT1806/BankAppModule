package com.jagt.transaction.application.command;

import java.math.BigDecimal;

public record WithdrawCommand(
        Long accountId,
        BigDecimal amount
) {
}
