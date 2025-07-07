package com.jagt.client.infrastructure.input.rest.controller.docs;

import com.jagt.client.domain.model.Client;
import com.jagt.client.infrastructure.input.rest.request.ClientCreateRequest;
import com.jagt.client.infrastructure.input.rest.request.ClientUpdateRequest;
import com.jagt.client.infrastructure.input.rest.response.ClientResponse;
import com.jagt.common.domain.model.Pagination;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Client Controller", description = "Operaciones relacionadas con los clientes")
public interface ClientControllerApi {
    @Operation(summary = "Obtener todos los clientes con paginación")
    @GetMapping
    Pagination<Client> getClients(
            @RequestParam(defaultValue = "0") @Parameter(description = "Número de página") int offset,
            @RequestParam(defaultValue = "12") @Parameter(description = "Tamaño de página") int limit
    );

    @Operation(summary = "Obtener un cliente por ID")
    @GetMapping("/{id}")
    ClientResponse getClient(@PathVariable Long id);

    @Operation(summary = "Crear un nuevo cliente")
    @PostMapping
    ResponseEntity<ClientResponse> createClient(@Valid @RequestBody ClientCreateRequest request);

    @Operation(summary = "Actualizar un cliente existente")
    @PutMapping("/{id}")
    ClientResponse updateClient(@PathVariable Long id, @Valid @RequestBody ClientUpdateRequest request);

    @Operation(summary = "Eliminar un cliente por ID")
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteClient(@PathVariable Long id);
}
