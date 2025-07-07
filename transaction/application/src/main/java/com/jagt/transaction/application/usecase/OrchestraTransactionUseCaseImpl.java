package com.jagt.transaction.application.usecase;

import com.jagt.account.application.ports.input.DepositUseCase;
import com.jagt.account.application.ports.input.GetAccountUseCase;
import com.jagt.account.application.ports.input.TransferUseCase;
import com.jagt.account.application.ports.input.WithdrawUseCase;
import com.jagt.transaction.application.command.DepositCommand;
import com.jagt.transaction.application.command.TransferCommand;
import com.jagt.transaction.application.command.WithdrawCommand;
import com.jagt.transaction.application.mapper.TransactionApplicationMapper;
import com.jagt.transaction.application.ports.input.DepositTransactionUseCase;
import com.jagt.transaction.application.ports.input.TransferTransactionUseCase;
import com.jagt.transaction.application.ports.input.WithdrawTransactionUseCase;
import com.jagt.transaction.domain.model.Transaction;
import com.jagt.transaction.domain.model.enums.TransactionType;
import com.jagt.transaction.domain.ports.output.TransactionPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OrchestraTransactionUseCaseImpl implements DepositTransactionUseCase, WithdrawTransactionUseCase, TransferTransactionUseCase {
    private final TransactionPersistencePort persistencePort;
    private final TransactionApplicationMapper mapper;
    private final GetAccountUseCase getAccountUseCase;
    private final DepositUseCase depositUseCase;
    private final WithdrawUseCase withdrawUseCase;
    private final TransferUseCase transferUseCase;

    @Override
    public Transaction execute(DepositCommand command) {
        depositUseCase.execute(mapper.toAccountCommand(command));

        Transaction transaction = Transaction.builder()
                .amount(command.amount())
                .type(TransactionType.DEPOSIT)
                .transactionDate(LocalDateTime.now())
                .sourceAccount(getAccountUseCase.execute(command.accountId()))
                .build();

        return persistencePort.save(transaction);
    }

    @Override
    public Transaction execute(TransferCommand command) {
        transferUseCase.execute(mapper.toAccountCommand(command));

        Transaction transaction = Transaction.builder()
                .amount(command.amount())
                .type(TransactionType.TRANSFER)
                .transactionDate(LocalDateTime.now())
                .sourceAccount(getAccountUseCase.execute(command.sourceAccountId()))
                .destinationAccount(getAccountUseCase.execute(command.destinationAccountId()))
                .build();

        return persistencePort.save(transaction);
    }

    @Override
    public Transaction execute(WithdrawCommand command) {
        withdrawUseCase.execute(mapper.toAccountCommand(command));

        Transaction transaction = Transaction.builder()
                .amount(command.amount())
                .type(TransactionType.WITHDRAWAL)
                .transactionDate(LocalDateTime.now())
                .sourceAccount(getAccountUseCase.execute(command.accountId()))
                .build();

        return persistencePort.save(transaction);
    }
}
