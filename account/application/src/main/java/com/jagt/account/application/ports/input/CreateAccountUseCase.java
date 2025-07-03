package com.jagt.account.application.ports.input;

import com.jagt.account.application.command.CreateAccountCommand;
import com.jagt.account.domain.model.Account;

public interface CreateAccountUseCase {
    Account execute(CreateAccountCommand command);
}
