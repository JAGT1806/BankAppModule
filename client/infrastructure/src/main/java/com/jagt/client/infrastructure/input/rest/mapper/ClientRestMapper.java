package com.jagt.client.infrastructure.input.rest.mapper;

import com.jagt.client.application.command.CreateClientCommand;
import com.jagt.client.application.command.UpdateClientCommand;
import com.jagt.client.application.query.GetClientsQuery;
import com.jagt.client.domain.model.Client;
import com.jagt.client.domain.model.value.UserNameValue;
import com.jagt.client.infrastructure.input.rest.request.ClientCreateRequest;
import com.jagt.client.infrastructure.input.rest.request.ClientUpdateRequest;
import com.jagt.client.infrastructure.input.rest.response.ClientResponse;
import io.micrometer.common.util.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ClientRestMapper {
    GetClientsQuery toQuery(int offset, int limit);

    @Mapping(target = "fullName", source = "name", qualifiedByName = "buildFullName")
    ClientResponse toResponse(Client client);

    @Named("buildFullName")
    default String buildFullName(UserNameValue userNameValue) {
        StringBuilder fullName = new StringBuilder();

        fullName.append(userNameValue.getFirstName());

        if (StringUtils.isNotBlank(userNameValue.getSecondName())) {
            fullName.append(" ").append(userNameValue.getSecondName());
        }

        fullName.append(" ")
                .append(userNameValue.getFirstLastName());

        if (StringUtils.isNotBlank(userNameValue.getSecondLastName())) {
            fullName.append(" ").append(userNameValue.getSecondLastName());
        }

        return fullName.toString();
    }

    CreateClientCommand toCommand(ClientCreateRequest request);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "firstName", source = "request.firstName")
    @Mapping(target = "secondName", source = "request.secondName")
    @Mapping(target = "firstLastName", source = "request.firstLastName")
    @Mapping(target = "secondLastName", source = "request.secondLastName")
    @Mapping(target = "email", source = "request.email")
    UpdateClientCommand toCommand(Long id, ClientUpdateRequest request);
}
