package com.jagt.transaction.infrastructure.input.rest.mapper;

import com.jagt.common.application.query.GetCommand;
import com.jagt.common.domain.model.Pagination;
import com.jagt.transaction.application.command.DepositCommand;
import com.jagt.transaction.application.command.TransferCommand;
import com.jagt.transaction.application.command.WithdrawCommand;
import com.jagt.transaction.domain.model.Transaction;
import com.jagt.transaction.infrastructure.input.rest.response.TransactionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.math.BigDecimal;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TransactionRestMapper {
    @Mapping(target = "sourceAccount", source = "sourceAccount.id")
    @Mapping(target = "destinationAccount", source = "destinationAccount.id")
    TransactionResponse toResponse(Transaction transaction);
    List<TransactionResponse> toResponse(List<Transaction> transactions);

    default Pagination<TransactionResponse> toResponse(Pagination<Transaction> transactionPagination) {
        return Pagination.<TransactionResponse>builder()
                .data(toResponse(transactionPagination.getData()))
                .offset(transactionPagination.getOffset())
                .limit(transactionPagination.getLimit())
                .total(transactionPagination.getTotal())
                .build();
    }

    GetCommand toCommand(int offset, int limit);
    DepositCommand toCommand(Long accountId, BigDecimal amount);
    WithdrawCommand toCommandW(Long accountId, BigDecimal amount);
    TransferCommand toCommand(Long sourceAccountId, Long destinationAccountId, BigDecimal amount);
}
