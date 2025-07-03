package com.jagt.client.infrastructure.input.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jagt.client.application.command.CreateClientCommand;
import com.jagt.client.application.command.UpdateClientCommand;
import com.jagt.client.application.ports.input.CreateClientUseCase;
import com.jagt.client.application.ports.input.DeleteClientUseCase;
import com.jagt.client.application.ports.input.GetClientUseCase;
import com.jagt.client.application.ports.input.UpdateClientUseCase;
import com.jagt.client.application.query.GetClientsQuery;
import com.jagt.client.domain.model.Client;
import com.jagt.client.domain.model.enums.IdentificationType;
import com.jagt.client.domain.model.value.UserNameValue;
import com.jagt.client.infrastructure.input.rest.mapper.ClientRestMapper;
import com.jagt.client.infrastructure.input.rest.request.ClientCreateRequest;
import com.jagt.client.infrastructure.input.rest.request.ClientUpdateRequest;
import com.jagt.client.infrastructure.input.rest.response.ClientResponse;
import com.jagt.common.domain.model.Pagination;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClientController.class)
@ContextConfiguration(classes = {ClientController.class})
class ClientControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private GetClientUseCase getClientUseCase;

    @MockitoBean
    private CreateClientUseCase createClientUseCase;

    @MockitoBean
    private UpdateClientUseCase updateClientUseCase;

    @MockitoBean
    private DeleteClientUseCase deleteClientUseCase;

    @MockitoBean
    private ClientRestMapper mapper;

    private Client client;
    private ClientResponse clientResponse;
    private ClientCreateRequest createRequest;
    private ClientUpdateRequest updateRequest;
    private Pagination<Client> pagination;

    @BeforeEach
    void setUp() {
        client = Client.builder()
                .id(1L)
                .name(UserNameValue.builder()
                        .firstName("Jhon")
                        .secondName("Alejando")
                        .firstLastName("Jimenez")
                        .secondLastName("Doe")
                        .build())
                .email("john.doe@example.com")
                .build();

        clientResponse = new ClientResponse(
                1L,
                IdentificationType.CC,
                "Jhon Alejandro Jimenez Doe",
                "john.doe@example.com",
                null,
                null,
                null
        );

        createRequest = new ClientCreateRequest(
                IdentificationType.CC,
                "1234567890",
                "Jhon",
                "Alejandro",
                "Jimenez",
                "Doe",
                "john.doe@example.com",
                LocalDate.of(1990, 1, 1)
        );

        updateRequest = new ClientUpdateRequest("Juan", "Doe", "Actualizado", null, null);

        pagination = Pagination.<Client>builder()
                .data(List.of(client))
                .total(1L)
                .offset(0)
                .limit(12)
                .build();
    }

    @Test
    void getClients_ShouldReturnPaginatedClients_WhenValidRequest() throws Exception {
        // Given
        GetClientsQuery query = new GetClientsQuery(0, 12);
        when(mapper.toQuery(0, 12)).thenReturn(query);
        when(getClientUseCase.execute(query)).thenReturn(pagination);

        // When & Then
        mockMvc.perform(get("/api/clients")
                        .param("offset", "0")
                        .param("limit", "12"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.total").value(1L))
                .andExpect(jsonPath("$.offset").value(0))
                .andExpect(jsonPath("$.limit").value(12));

        verify(mapper).toQuery(0, 12);
        verify(getClientUseCase).execute((GetClientsQuery) any());
    }

    @Test
    void getClients_ShouldUseDefaultPagination_WhenNoParametersProvided() throws Exception {
        // Given
        GetClientsQuery query = new GetClientsQuery(0, 12);
        when(mapper.toQuery(0, 12)).thenReturn(query);
        when(getClientUseCase.execute(query)).thenReturn(pagination);

        // When & Then
        mockMvc.perform(get("/api/clients"))
                .andExpect(status().isOk());

        verify(mapper).toQuery(0, 12);
        verify(getClientUseCase).execute((GetClientsQuery) any());
    }

    @Test
    void getClient_ShouldReturnClient_WhenValidId() throws Exception {
        // Given
        Long clientId = 1L;
        when(getClientUseCase.execute(clientId)).thenReturn(client);
        when(mapper.toResponse(client)).thenReturn(clientResponse);

        // When & Then
        mockMvc.perform(get("/api/clients/{id}", clientId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));

        verify(getClientUseCase).execute(clientId);
        verify(mapper).toResponse(client);
    }

    @Test
    void createClient_ShouldReturnCreatedClient_WhenValidRequest() throws Exception {
        // Given
        when(mapper.toCommand(createRequest)).thenReturn(mock(CreateClientCommand.class));
        when(createClientUseCase.execute(any())).thenReturn(client);
        when(mapper.toResponse(client)).thenReturn(clientResponse);

        // When & Then
        mockMvc.perform(post("/api/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));

        verify(mapper).toCommand(createRequest);
        verify(createClientUseCase).execute(any());
        verify(mapper).toResponse(client);
    }

    @Test
    void createClient_ShouldReturnBadRequest_WhenInvalidRequest() throws Exception {
        // Given
        ClientCreateRequest invalidRequest = new ClientCreateRequest(
                IdentificationType.CC,
                "1234567890",
                "Jhon",
                "Alejandro",
                "Jimenez",
                "Doe",
                "invalid-email",
                null
        );

        // When & Then
        mockMvc.perform(post("/api/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(mapper, createClientUseCase);
    }

    @Test
    void updateClient_ShouldReturnUpdatedClient_WhenValidRequest() throws Exception {
        // Given
        Long clientId = 1L;

        when(mapper.toCommand(eq(clientId), any(ClientUpdateRequest.class))).thenReturn(mock(UpdateClientCommand.class));
        when(updateClientUseCase.execute(any())).thenReturn(client);
        when(mapper.toResponse(client)).thenReturn(clientResponse);

        // When & Then
        mockMvc.perform(put("/api/clients/{id}", clientId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));

        verify(mapper).toCommand(eq(clientId), any(ClientUpdateRequest.class));
        verify(updateClientUseCase).execute(any());
        verify(mapper).toResponse(client);
    }

    @Test
    void updateClient_ShouldReturnBadRequest_WhenInvalidRequest() throws Exception {
        // Given
        Long clientId = 1L;
        ClientUpdateRequest invalidRequest = new ClientUpdateRequest("Juan", "Doe", "Actualizado", null, "null");

        // When & Then
        mockMvc.perform(put("/api/clients/{id}", clientId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(mapper, updateClientUseCase);
    }

    @Test
    void deleteClient_ShouldReturnNoContent_WhenValidId() throws Exception {
        // Given
        Long clientId = 1L;
        doNothing().when(deleteClientUseCase).execute(clientId);

        // When & Then
        mockMvc.perform(delete("/api/clients/{id}", clientId))
                .andExpect(status().isNoContent());

        verify(deleteClientUseCase).execute(clientId);
    }

}