package com.jagt.account.application.ports.input;

import com.jagt.account.application.query.GetAccountsQuery;
import com.jagt.account.domain.model.Account;
import com.jagt.common.domain.model.Pagination;

import java.util.List;

public interface GetAccountUseCase {
    Account execute(Long id);

    Pagination<Account> execute(GetAccountsQuery query);

    Pagination<Account> executeByClientId(Long clientId, GetAccountsQuery query);

}
