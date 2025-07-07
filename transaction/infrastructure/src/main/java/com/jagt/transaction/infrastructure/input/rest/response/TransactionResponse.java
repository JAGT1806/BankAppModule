package com.jagt.transaction.infrastructure.input.rest.response;


import com.jagt.transaction.domain.model.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionResponse(
        Long id,
        TransactionType type,
        BigDecimal amount,
        Long sourceAccount,
        Long destinationAccount,
        LocalDateTime transactionDate
) {
}
