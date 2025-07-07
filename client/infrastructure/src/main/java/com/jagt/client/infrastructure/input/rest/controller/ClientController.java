package com.jagt.client.infrastructure.input.rest.controller;

import com.jagt.client.application.ports.input.CreateClientUseCase;
import com.jagt.client.application.ports.input.DeleteClientUseCase;
import com.jagt.client.application.ports.input.GetClientUseCase;
import com.jagt.client.application.ports.input.UpdateClientUseCase;
import com.jagt.client.domain.model.Client;
import com.jagt.client.infrastructure.input.rest.controller.docs.ClientControllerApi;
import com.jagt.client.infrastructure.input.rest.mapper.ClientRestMapper;
import com.jagt.client.infrastructure.input.rest.request.ClientCreateRequest;
import com.jagt.client.infrastructure.input.rest.request.ClientUpdateRequest;
import com.jagt.client.infrastructure.input.rest.response.ClientResponse;
import com.jagt.common.domain.model.Pagination;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController implements ClientControllerApi {
    private final GetClientUseCase getClientUseCase;
    private final CreateClientUseCase createClientUseCase;
    private final UpdateClientUseCase updateClientUseCase;
    private final DeleteClientUseCase deleteClientUseCase;
    private final ClientRestMapper mapper;

    @Override
    public Pagination<Client> getClients(int offset, int limit) {
        return getClientUseCase.execute(mapper.toQuery(offset, limit));
    }

    @Override
    public ClientResponse getClient(Long id) {
        return mapper.toResponse(getClientUseCase.execute(id));
    }

    @Override
    public ResponseEntity<ClientResponse> createClient(ClientCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mapper.toResponse(createClientUseCase.execute(mapper.toCommand(request))));
    }

    @Override
    public ClientResponse updateClient(Long id, ClientUpdateRequest request) {
        return mapper.toResponse(updateClientUseCase.execute(mapper.toCommand(id, request)));
    }

    @Override
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        deleteClientUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
