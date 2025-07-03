package com.jagt.client.application.ports.input;

import com.jagt.client.application.command.CreateClientCommand;
import com.jagt.client.domain.model.Client;

public interface CreateClientUseCase {
    Client execute(CreateClientCommand command);
}
