package com.jagt.client.application.ports.input;

import com.jagt.client.application.query.GetClientsQuery;
import com.jagt.client.domain.model.Client;
import com.jagt.common.domain.model.Pagination;

public interface GetClientUseCase {
    Pagination<Client> execute(GetClientsQuery query);

    Client execute(Long id);
}
