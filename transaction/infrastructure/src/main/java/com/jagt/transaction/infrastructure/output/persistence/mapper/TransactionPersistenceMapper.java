package com.jagt.transaction.infrastructure.output.persistence.mapper;

import com.jagt.transaction.domain.model.Transaction;
import com.jagt.transaction.infrastructure.output.persistence.entity.TransactionEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransactionPersistenceMapper {
    TransactionEntity toEntity(Transaction transaction);
    Transaction toDomain(TransactionEntity entity);
    List<Transaction> toDomain(List<TransactionEntity> entities);


}
