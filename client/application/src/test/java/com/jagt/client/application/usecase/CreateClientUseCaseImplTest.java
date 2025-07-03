package com.jagt.client.application.usecase;

import com.jagt.client.application.command.CreateClientCommand;
import com.jagt.client.domain.exception.AgeRestrictionException;
import com.jagt.client.domain.exception.InvalidBirthDateException;
import com.jagt.client.domain.model.Client;
import com.jagt.client.domain.model.enums.IdentificationType;
import com.jagt.client.domain.ports.output.ClientPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateClientUseCaseImplTest {

    @Mock
    private ClientPersistencePort clientPersistencePort;

    @InjectMocks
    private CreateClientUseCaseImpl createClientUseCase;

    private CreateClientCommand command;

    @BeforeEach
    void setUp() {
        command = new CreateClientCommand(
                IdentificationType.CC,
                "123456789",
                "John",
                "Michael",
                "Doe",
                "Smith",
                "john@example.com",
                LocalDate.now().minusYears(20) // mayor de edad
        );
    }

    @Test
    void execute_shouldCreateClientSuccessfully() {
        // Arrange
        when(clientPersistencePort.save(any(Client.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Client client = createClientUseCase.execute(command);

        // Assert
        assertNotNull(client);
        assertEquals(command.firstName(), client.getName().getFirstName());
        verify(clientPersistencePort).save(any(Client.class));
    }

    @Test
    void execute_shouldThrowInvalidBirthDateException_whenBirthDateIsInFuture() {
        // Arrange
        CreateClientCommand futureDateCommand = new CreateClientCommand(
                command.identificationType(), command.identificationNumber(),
                command.firstName(), command.secondName(),
                command.firstLastName(), command.secondLastName(),
                command.email(), LocalDate.now().plusDays(1)
        );

        // Act & Assert
        assertThrows(InvalidBirthDateException.class, () -> {
            createClientUseCase.execute(futureDateCommand);
        });

        verifyNoInteractions(clientPersistencePort);
    }

    @Test
    void execute_shouldThrowAgeRestrictionException_whenUnderage() {
        // Arrange
        CreateClientCommand underageCommand = new CreateClientCommand(
                command.identificationType(), command.identificationNumber(),
                command.firstName(), command.secondName(),
                command.firstLastName(), command.secondLastName(),
                command.email(), LocalDate.now().minusYears(17)
        );

        // Act & Assert
        assertThrows(AgeRestrictionException.class, () -> {
            createClientUseCase.execute(underageCommand);
        });

        verifyNoInteractions(clientPersistencePort);
    }
}
