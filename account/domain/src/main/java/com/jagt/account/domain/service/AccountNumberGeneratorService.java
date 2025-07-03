package com.jagt.account.domain.service;

import com.jagt.account.domain.model.enums.AccountType;

public interface AccountNumberGeneratorService {
    String generate(AccountType accountType);
}
