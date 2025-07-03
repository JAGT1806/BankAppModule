package com.jagt.client.infrastructure.output.persistence.mapper;

import com.jagt.client.domain.model.Client;
import com.jagt.client.infrastructure.output.persistence.entity.ClientEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClientPersistenceMapper {
    @Mapping(target = "firstName", source = "name.firstName")
    @Mapping(target = "secondName", source = "name.secondName")
    @Mapping(target = "firstLastName", source = "name.firstLastName")
    @Mapping(target = "secondLastName", source = "name.secondLastName")
    ClientEntity toEntity(Client model);

    @Mapping(target = "name.firstName", source = "firstName")
    @Mapping(target = "name.secondName", source = "secondName")
    @Mapping(target = "name.firstLastName", source = "firstLastName")
    @Mapping(target = "name.secondLastName", source = "secondLastName")
    Client toDomain(ClientEntity entity);

    List<Client> toDomainList(List<ClientEntity> models);
}
