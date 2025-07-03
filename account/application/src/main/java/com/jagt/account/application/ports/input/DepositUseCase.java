package com.jagt.account.application.ports.input;

import com.jagt.account.application.command.DepositCommand;

public interface DepositUseCase {
    void execute(DepositCommand command);
}
