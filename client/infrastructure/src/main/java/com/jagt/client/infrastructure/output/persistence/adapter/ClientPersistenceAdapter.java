package com.jagt.client.infrastructure.output.persistence.adapter;

import com.jagt.client.domain.model.Client;
import com.jagt.client.domain.ports.output.ClientPersistencePort;
import com.jagt.client.infrastructure.output.persistence.mapper.ClientPersistenceMapper;
import com.jagt.client.infrastructure.output.persistence.repository.ClientJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ClientPersistenceAdapter implements ClientPersistencePort {
    private final ClientJpaRepository repository;
    private final ClientPersistenceMapper mapper;

    @Override
    public Optional<Client> findById(Long id) {
        return repository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public List<Client> findAll(int offset, int limit) {
        Pageable pageable = PageRequest.of(offset, limit);
        return mapper.toDomainList(repository.findAll(pageable).getContent());
    }

    @Override
    public Client save(Client client) {
        return mapper.toDomain(repository.save(mapper.toEntity(client)));
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public long count() {
        return repository.count();
    }
}
