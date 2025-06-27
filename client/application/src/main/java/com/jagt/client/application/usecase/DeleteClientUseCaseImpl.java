package com.jagt.client.application.usecase;

import com.jagt.client.application.ports.input.DeleteClientUseCase;
import com.jagt.client.domain.ports.output.ClientPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteClientUseCaseImpl implements DeleteClientUseCase {
    private final ClientPersistencePort clientPersistencePort;

    @Override
    public void execute(Long id) {
        clientPersistencePort.deleteById(id);
    }
}
