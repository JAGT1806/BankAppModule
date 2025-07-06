package com.jagt.transaction.domain.model;

import com.jagt.account.domain.model.Account;
import com.jagt.transaction.domain.model.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    private Long id;
    private TransactionType type;
    private BigDecimal amount;
    private Account sourceAccount;
    private Account destinationAccount;
    private LocalDateTime transactionDate;
}
