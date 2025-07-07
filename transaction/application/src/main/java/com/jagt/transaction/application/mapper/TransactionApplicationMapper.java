package com.jagt.transaction.application.mapper;

import com.jagt.account.application.command.DepositCommand;
import com.jagt.account.application.command.TransferCommand;
import com.jagt.account.application.command.WithdrawCommand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransactionApplicationMapper {
    DepositCommand toAccountCommand(com.jagt.transaction.application.command.DepositCommand command);

    WithdrawCommand toAccountCommand(com.jagt.transaction.application.command.WithdrawCommand command);

    @Mapping(target = "fromAccountId", source = "sourceAccountId")
    @Mapping(target = "toAccountId", source = "destinationAccountId")
    TransferCommand toAccountCommand(com.jagt.transaction.application.command.TransferCommand command);
}
