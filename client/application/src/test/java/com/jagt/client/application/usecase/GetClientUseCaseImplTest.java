package com.jagt.client.application.usecase;

import com.jagt.client.application.ports.input.GetClientUseCase;
import com.jagt.client.application.query.GetClientsQuery;
import com.jagt.client.domain.exception.ClientNotFoundException;
import com.jagt.client.domain.model.Client;
import com.jagt.client.domain.model.value.UserNameValue;
import com.jagt.client.domain.ports.output.ClientPersistencePort;
import com.jagt.common.domain.model.Pagination;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetClientUseCaseImplTest {
    @Mock
    private ClientPersistencePort clientPersistencePort;

    @InjectMocks
    private GetClientUseCaseImpl getClientUseCase;

    private Client client;
    private final Long CLIENT_ID = 1L;
    private final int OFFSET = 0;
    private final int LIMIT = 10;

    @BeforeEach
    void setup() {
        client = Client.builder()
                .id(CLIENT_ID)
                .name(UserNameValue.builder()
                        .firstName("John")
                        .secondName("Smith")
                        .firstLastName("Doe")
                        .secondLastName("Si")
                        .build())
                .build();
    }

    @Test
    void execute_withId_shouldReturnClientWhenExists() {
        // Arrange
        when(clientPersistencePort.findById(CLIENT_ID))
                .thenReturn(Optional.of(client));

        // Act
        Client response = getClientUseCase.execute(CLIENT_ID);

        // Assert
        assertNotNull(response);
        assertEquals(CLIENT_ID, response.getId());
        verify(clientPersistencePort, times(1)).findById(CLIENT_ID);
    }

    @Test
    void execute_withId_shouldThrowExceptionWhenClientNotFound() {
        // Arrange
        when(clientPersistencePort.findById(CLIENT_ID))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ClientNotFoundException.class, () -> {
            getClientUseCase.execute(CLIENT_ID);
        });
        verify(clientPersistencePort, times(1)).findById(CLIENT_ID);
    }

    @Test
    void execute_withQuery_shouldReturnPaginationWithClients() {
        // Arrange
        GetClientsQuery query = new GetClientsQuery(OFFSET, LIMIT);
        List<Client> clients = List.of(client);
        long totalCount = 1L;

        when(clientPersistencePort.findAll(OFFSET, LIMIT))
                .thenReturn(clients);
        when(clientPersistencePort.count())
                .thenReturn(totalCount);

        // Act
        Pagination<Client> result = getClientUseCase.execute(query);

        // Assert
        assertNotNull(result);
        assertEquals(OFFSET, result.getOffset());
        assertEquals(LIMIT, result.getLimit());
        assertEquals(totalCount, result.getTotal());
        assertEquals(1, result.getData().size());
        assertEquals(CLIENT_ID, result.getData().get(0).getId());

        verify(clientPersistencePort, times(1)).findAll(OFFSET, LIMIT);
        verify(clientPersistencePort, times(1)).count();
    }

    @Test
    void execute_withQuery_shouldReturnEmptyPaginationWhenNoClients() {
        // Arrange
        GetClientsQuery query = new GetClientsQuery(OFFSET, LIMIT);
        List<Client> emptyList = List.of();
        long totalCount = 0L;

        when(clientPersistencePort.findAll(OFFSET, LIMIT))
                .thenReturn(emptyList);
        when(clientPersistencePort.count())
                .thenReturn(totalCount);

        // Act
        Pagination<Client> result = getClientUseCase.execute(query);

        // Assert
        assertNotNull(result);
        assertEquals(OFFSET, result.getOffset());
        assertEquals(LIMIT, result.getLimit());
        assertEquals(totalCount, result.getTotal());
        assertTrue(result.getData().isEmpty());

        verify(clientPersistencePort, times(1)).findAll(OFFSET, LIMIT);
        verify(clientPersistencePort, times(1)).count();
    }
}