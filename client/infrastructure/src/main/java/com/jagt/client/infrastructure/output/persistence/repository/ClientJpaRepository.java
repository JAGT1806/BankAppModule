package com.jagt.client.infrastructure.output.persistence.repository;

import com.jagt.client.infrastructure.output.persistence.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientJpaRepository extends JpaRepository<ClientEntity, Long> {
}
