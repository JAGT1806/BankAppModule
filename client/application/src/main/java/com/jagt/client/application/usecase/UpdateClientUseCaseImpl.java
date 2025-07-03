package com.jagt.client.application.usecase;

import com.jagt.client.application.command.UpdateClientCommand;
import com.jagt.client.application.ports.input.UpdateClientUseCase;
import com.jagt.client.domain.model.Client;
import com.jagt.client.domain.ports.output.ClientPersistencePort;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UpdateClientUseCaseImpl implements UpdateClientUseCase {
    private final ClientPersistencePort clientPersistencePort;

    @Override
    public Client execute(UpdateClientCommand command) {
        return clientPersistencePort.findById(command.id())
                .map(savedClient -> {
                    if(StringUtils.isNotBlank(command.firstName()))
                        savedClient.getName().setFirstName(command.firstName());

                    if(StringUtils.isNotBlank(command.secondName()))
                        savedClient.getName().setSecondName(command.secondName());

                    if(StringUtils.isNotBlank(command.firstLastName()))
                        savedClient.getName().setFirstLastName(command.firstLastName());

                    if(StringUtils.isNotBlank(command.secondLastName()))
                        savedClient.getName().setSecondLastName(command.secondLastName());

                    if(StringUtils.isNotBlank(command.email()))
                        savedClient.setEmail(command.email());

                    savedClient.setUpdatedAt(LocalDateTime.now());

                    return clientPersistencePort.save(savedClient);
                })
                .orElseThrow();
    }
}
