package com.jagt.account.infrastructure.output.persistence.mapper;

import com.jagt.account.domain.model.Account;
import com.jagt.account.infrastructure.output.persistence.entity.AccountEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountPersistenceMapper {
    AccountEntity toEntity(Account account);
    Account toDomain(AccountEntity accountEntity);
    List<Account> toDomainList(List<AccountEntity> accountEntities);
}
