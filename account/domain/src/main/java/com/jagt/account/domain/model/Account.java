package com.jagt.account.domain.model;

import com.jagt.account.domain.model.enums.AccountStatus;
import com.jagt.account.domain.model.enums.AccountType;
import com.jagt.client.domain.model.Client;
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
public class Account {
    private Long id;
    private AccountType accountType;
    private String accountNumber;
    private AccountStatus status;
    private BigDecimal balance;
    private boolean gmfExempt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Client client;
}
