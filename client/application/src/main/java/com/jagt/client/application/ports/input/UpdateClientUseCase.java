package com.jagt.client.application.ports.input;

import com.jagt.client.application.command.UpdateClientCommand;
import com.jagt.client.domain.model.Client;

public interface UpdateClientUseCase {
    Client execute(UpdateClientCommand command);
}
