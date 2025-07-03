package com.jagt.account.domain.service.impl;

import com.jagt.account.domain.model.enums.AccountType;
import com.jagt.account.domain.service.AccountNumberGeneratorService;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class AccountNumberGeneratorServiceImpl implements AccountNumberGeneratorService {
    private final Random random = new Random();

    @Override
    public String generate(AccountType accountType) {
        int num = 10000000 + random.nextInt(90000000);
        return (accountType == AccountType.SAVING ? "53" : "33") + num;
    }
}
