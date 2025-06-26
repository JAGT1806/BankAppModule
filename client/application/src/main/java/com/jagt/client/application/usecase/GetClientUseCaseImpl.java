package com.jagt.client.application.usecase;

import com.jagt.client.application.ports.input.GetClientUseCase;
import com.jagt.client.application.query.GetClientsQuery;
import com.jagt.client.domain.exception.ClientNotFoundException;
import com.jagt.client.domain.model.Client;
import com.jagt.client.domain.ports.output.ClientPersistencePort;
import com.jagt.common.domain.model.Pagination;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetClientUseCaseImpl implements GetClientUseCase {
    private final ClientPersistencePort clientPersistencePort;

    @Override
    public Pagination<Client> execute(GetClientsQuery query) {
        List<Client> clients = clientPersistencePort.findAll(query.offset(), query.limit());
        long total = clientPersistencePort.count();

        return Pagination.<Client>builder()
                .data(clients)
                .offset(query.offset())
                .limit(query.limit())
                .total(total)
                .build();
    }

    @Override
    public Client execute(Long id) {
        return clientPersistencePort.findById(id)
                .orElseThrow(ClientNotFoundException::new);
    }
}
