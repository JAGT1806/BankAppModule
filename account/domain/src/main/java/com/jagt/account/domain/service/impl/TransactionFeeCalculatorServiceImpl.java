package com.jagt.account.domain.service.impl;

import com.jagt.account.domain.service.TransactionFeeCalculatorService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class TransactionFeeCalculatorServiceImpl implements TransactionFeeCalculatorService {
    private static final BigDecimal GMF_RATE = new BigDecimal("0.094");

    @Override
    public BigDecimal calculateGmfFee(BigDecimal amount) {
        return amount.multiply(GMF_RATE);
    }

    @Override
    public BigDecimal calculateTotalAmountWithFees(BigDecimal amount, boolean isGmfExempt) {
        if (isGmfExempt) return amount;
        return amount.add(calculateGmfFee(amount));
    }
}
