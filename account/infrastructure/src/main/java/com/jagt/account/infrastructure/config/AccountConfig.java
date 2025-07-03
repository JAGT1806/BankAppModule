package com.jagt.account.infrastructure.config;

import com.jagt.account.domain.service.AccountNumberGeneratorService;
import com.jagt.account.domain.service.AccountValidationService;
import com.jagt.account.domain.service.TransactionFeeCalculatorService;
import com.jagt.account.domain.service.impl.AccountNumberGeneratorServiceImpl;
import com.jagt.account.domain.service.impl.AccountValidationServiceImpl;
import com.jagt.account.domain.service.impl.TransactionFeeCalculatorServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AccountConfig {
    @Bean
    public AccountNumberGeneratorService accountNumberGeneratorService() {
        return new AccountNumberGeneratorServiceImpl();
    }

    @Bean
    public AccountValidationService accountValidationService() {
        return new AccountValidationServiceImpl();
    }

    @Bean
    public TransactionFeeCalculatorService transactionFeeCalculatorService() {
        return new TransactionFeeCalculatorServiceImpl();
    }
}
