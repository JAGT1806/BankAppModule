package com.jagt.account.application.ports.input;

public interface AccountStatusUseCase {
    void activate(Long accountId);
    void deactivate(Long accountId);
    void cancel(Long accountId);
}
