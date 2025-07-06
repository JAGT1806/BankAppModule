package com.jagt.transaction.application.command;

import java.math.BigDecimal;

public record TransferCommand(
        Long sourceAccountId,
        Long destinationAccountId,
        BigDecimal amount
) {
}
