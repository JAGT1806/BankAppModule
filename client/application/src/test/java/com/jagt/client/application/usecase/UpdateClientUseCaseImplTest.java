package com.jagt.client.application.usecase;

import com.jagt.client.application.command.UpdateClientCommand;
import com.jagt.client.domain.model.Client;
import com.jagt.client.domain.model.value.UserNameValue;
import com.jagt.client.domain.ports.output.ClientPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateClientUseCaseImplTest {

    @Mock
    private ClientPersistencePort clientPersistencePort;

    @InjectMocks
    private UpdateClientUseCaseImpl updateClientUseCase;

    private Client savedClient;
    private static final Long CLIENT_ID = 1L;

    @BeforeEach
    void setUp() {
        savedClient = Client.builder()
                .id(CLIENT_ID)
                .name(UserNameValue.builder()
                        .firstName("Old")
                        .secondName("Name")
                        .firstLastName("Lastname1")
                        .secondLastName("Lastname2")
                        .build())
                .email("old@example.com")
                .birthDate(LocalDate.now().minusYears(25))
                .build();
    }

    @Test
    void execute_shouldUpdateFieldsWhenPresent() {
        // Arrange
        UpdateClientCommand command = new UpdateClientCommand(
                CLIENT_ID, "New", null, null, null, "new@example.com"
        );

        when(clientPersistencePort.findById(CLIENT_ID)).thenReturn(Optional.of(savedClient));
        when(clientPersistencePort.save(any(Client.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        Client updated = updateClientUseCase.execute(command);

        // Assert
        assertEquals("New", updated.getName().getFirstName());
        assertEquals("new@example.com", updated.getEmail());
        verify(clientPersistencePort).save(savedClient);
    }

    @Test
    void execute_shouldThrowExceptionWhenClientNotFound() {
        // Arrange
        when(clientPersistencePort.findById(CLIENT_ID)).thenReturn(Optional.empty());

        UpdateClientCommand command = new UpdateClientCommand(CLIENT_ID, "New", null, null, null, "new@example.com");

        // Act & Assert
        assertThrows(NoSuchElementException.class, () -> {
            updateClientUseCase.execute(command);
        });

        verify(clientPersistencePort, never()).save(any());
    }
}
