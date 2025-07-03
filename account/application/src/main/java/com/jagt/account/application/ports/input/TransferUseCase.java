package com.jagt.account.application.ports.input;

import com.jagt.account.application.command.TransferCommand;

public interface TransferUseCase {
    void execute(TransferCommand command);
}
