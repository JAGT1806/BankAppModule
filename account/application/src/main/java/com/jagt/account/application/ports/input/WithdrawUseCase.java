package com.jagt.account.application.ports.input;

import com.jagt.account.application.command.WithdrawCommand;

public interface WithdrawUseCase {
    void execute(WithdrawCommand command);
}
