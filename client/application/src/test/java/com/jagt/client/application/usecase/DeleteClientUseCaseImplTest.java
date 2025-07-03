package com.jagt.client.application.usecase;

import com.jagt.account.domain.model.Account;
import com.jagt.account.domain.ports.output.AccountPersistencePort;
import com.jagt.client.application.ports.input.GetClientUseCase;
import com.jagt.client.domain.exception.ClientHasAccountException;
import com.jagt.client.domain.ports.output.ClientPersistencePort;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
class DeleteClientUseCaseImplTest {

    @Mock
    private ClientPersistencePort clientPersistencePort;

    @Mock
    private GetClientUseCase getClientUseCase;

    @Mock
    private AccountPersistencePort accountPersistencePort;

    @InjectMocks
    private DeleteClientUseCaseImpl deleteClientUseCase;

    @Test
    void execute_shouldCallDeleteById_whenClientHasNoAccounts() {
        Long clientId = 1L;

        // Arrange
        when(accountPersistencePort.findByClientId(clientId, 0, 12))
                .thenReturn(Collections.emptyList());

        // Act
        deleteClientUseCase.execute(clientId);

        // Assert
        verify(getClientUseCase, times(1)).execute(clientId);
        verify(accountPersistencePort, times(1)).findByClientId(clientId, 0, 12);
        verify(clientPersistencePort, times(1)).deleteById(clientId);
    }

    @Test
    void execute_shouldThrowException_whenClientHasAccounts() {
        Long clientId = 2L;

        // Arrange
        when(accountPersistencePort.findByClientId(clientId, 0, 12))
                .thenReturn(List.of(new Account()));

        // Assert + Act
        assertThrows(
                ClientHasAccountException.class,
                () -> deleteClientUseCase.execute(clientId)
        );

        verify(getClientUseCase, times(1)).execute(clientId);
        verify(accountPersistencePort, times(1)).findByClientId(clientId, 0, 12);
        verifyNoInteractions(clientPersistencePort);
    }
}
