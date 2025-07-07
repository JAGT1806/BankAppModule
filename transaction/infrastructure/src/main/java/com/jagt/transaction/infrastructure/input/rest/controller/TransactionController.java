package com.jagt.transaction.infrastructure.input.rest.controller;

import com.jagt.common.domain.model.Pagination;
import com.jagt.transaction.application.ports.input.DepositTransactionUseCase;
import com.jagt.transaction.application.ports.input.GetTransactionUseCase;
import com.jagt.transaction.application.ports.input.TransferTransactionUseCase;
import com.jagt.transaction.application.ports.input.WithdrawTransactionUseCase;
import com.jagt.transaction.domain.model.Transaction;
import com.jagt.transaction.infrastructure.input.rest.mapper.TransactionRestMapper;
import com.jagt.transaction.infrastructure.input.rest.response.TransactionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final GetTransactionUseCase getTransactionUseCase;
    private final DepositTransactionUseCase depositTransactionUseCase;
    private final WithdrawTransactionUseCase withdrawTransactionUseCase;
    private final TransferTransactionUseCase transferTransactionUseCase;
    private final TransactionRestMapper mapper;

    @GetMapping
    public Pagination<TransactionResponse> getTransactions(
            @RequestParam(required = false, defaultValue = "0") int offset, @RequestParam(required = false, defaultValue = "12") int limit
    ) {
        return mapper.toResponse(getTransactionUseCase.execute(mapper.toCommand(offset, limit)));
    }

    @GetMapping("/{id}")
    public TransactionResponse getTransaction(@PathVariable Long id) {
        return mapper.toResponse(getTransactionUseCase.execute(id));
    }

    @PostMapping("/deposit/{accountId}")
    public Transaction deposit(@PathVariable Long accountId, @RequestBody BigDecimal amount) {
        return depositTransactionUseCase.execute(mapper.toCommand(accountId, amount));
    }

    @PostMapping("/withdraw/{accountId}")
    public Transaction withdraw(@PathVariable Long accountId, @RequestBody BigDecimal amount) {
        return withdrawTransactionUseCase.execute(mapper.toCommandW(accountId, amount));
    }

    @PostMapping("/transfer/{sourceAccountId}/{destinationAccountId}")
    public Transaction transfer(@PathVariable Long sourceAccountId, @PathVariable Long destinationAccountId, @RequestBody BigDecimal amount) {
        return transferTransactionUseCase.execute(mapper.toCommand(sourceAccountId, destinationAccountId, amount));
    }
}
