package com.jagt.account.application.command;

import java.math.BigDecimal;

public record TransferCommand(
        Long fromAccountId,
        Long toAccountId,
        BigDecimal amount
) {
}
