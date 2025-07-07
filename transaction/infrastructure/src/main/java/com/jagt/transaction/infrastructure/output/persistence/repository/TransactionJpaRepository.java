package com.jagt.transaction.infrastructure.output.persistence.repository;


import com.jagt.transaction.infrastructure.output.persistence.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionJpaRepository extends JpaRepository<TransactionEntity, Long> {
}
