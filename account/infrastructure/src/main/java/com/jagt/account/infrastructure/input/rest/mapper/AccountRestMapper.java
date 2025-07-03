package com.jagt.account.infrastructure.input.rest.mapper;

import com.jagt.account.application.command.CreateAccountCommand;
import com.jagt.account.application.query.GetAccountsQuery;
import com.jagt.account.infrastructure.input.rest.request.AccountCreateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccountRestMapper {
    GetAccountsQuery toQuery(int offset, int limit);
    @Mapping(target = "initialBalance", source = "balance")
    CreateAccountCommand toCommand(AccountCreateRequest request);
}
