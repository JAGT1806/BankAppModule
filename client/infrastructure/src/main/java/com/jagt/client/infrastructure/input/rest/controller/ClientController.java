package com.jagt.client.infrastructure.input.rest.controller;

import com.jagt.client.application.ports.input.CreateClientUseCase;
import com.jagt.client.application.ports.input.DeleteClientUseCase;
import com.jagt.client.application.ports.input.GetClientUseCase;
import com.jagt.client.application.ports.input.UpdateClientUseCase;
import com.jagt.client.domain.model.Client;
import com.jagt.client.infrastructure.input.rest.mapper.ClientRestMapper;
import com.jagt.client.infrastructure.input.rest.request.ClientCreateRequest;
import com.jagt.client.infrastructure.input.rest.request.ClientUpdateRequest;
import com.jagt.client.infrastructure.input.rest.response.ClientResponse;
import com.jagt.common.domain.model.Pagination;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {
    private final GetClientUseCase getClientUseCase;
    private final CreateClientUseCase createClientUseCase;
    private final UpdateClientUseCase updateClientUseCase;
    private final DeleteClientUseCase deleteClientUseCase;
    private final ClientRestMapper mapper;

    @GetMapping
    public Pagination<Client> getClients(@RequestParam(required = false, defaultValue = "0") int offset, @RequestParam(required = false, defaultValue = "12") int limit) {
        return getClientUseCase.execute(mapper.toQuery(offset, limit));
    }

    @GetMapping("/{id}")
    public ClientResponse getClient(@PathVariable Long id) {
        return mapper.toResponse(getClientUseCase.execute(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClientResponse createClient(@Valid @RequestBody ClientCreateRequest request) {
        return mapper.toResponse(createClientUseCase.execute(mapper.toCommand(request)));
    }

    @PutMapping("/{id}")
    public ClientResponse updateClient(@PathVariable Long id, @Valid @RequestBody ClientUpdateRequest request) {
        return mapper.toResponse(updateClientUseCase.execute(mapper.toCommand(id, request)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteClient(@PathVariable Long id) {
        deleteClientUseCase.execute(id);
    }
}
