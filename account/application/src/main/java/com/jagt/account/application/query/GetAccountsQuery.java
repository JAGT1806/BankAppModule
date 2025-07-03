package com.jagt.account.application.query;

import com.jagt.account.domain.model.enums.AccountType;

public record GetAccountsQuery(
        int offset, int limit
) {
}
