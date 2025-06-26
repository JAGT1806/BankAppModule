package com.jagt.client.domain.ports.output;

import com.jagt.client.domain.model.Client;

import java.util.List;
import java.util.Optional;

public interface ClientPersistencePort {
    Optional<Client> findById(Long id);
    List<Client> findAll(int offset, int limit);
    Client save(Client client);
    void deleteById(Long id);
    long count();
}
