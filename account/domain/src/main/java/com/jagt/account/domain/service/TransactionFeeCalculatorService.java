package com.jagt.account.domain.service;

import java.math.BigDecimal;

public interface TransactionFeeCalculatorService {
    BigDecimal calculateGmfFee(BigDecimal amount);
    BigDecimal calculateTotalAmountWithFees(BigDecimal amount, boolean isGmfExempt);
}
