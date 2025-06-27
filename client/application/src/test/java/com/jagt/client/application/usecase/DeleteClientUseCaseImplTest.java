package com.jagt.client.application.usecase;

import com.jagt.client.domain.ports.output.ClientPersistencePort;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
class DeleteClientUseCaseImplTest {

    @Mock
    private ClientPersistencePort clientPersistencePort;

    @InjectMocks
    private DeleteClientUseCaseImpl deleteClientUseCase;

    @Test
    void execute_shouldCallDeleteById() {
        Long clientId = 1L;

        // Act
        deleteClientUseCase.execute(clientId);

        // Assert
        verify(clientPersistencePort, times(1)).deleteById(clientId);
    }
}
