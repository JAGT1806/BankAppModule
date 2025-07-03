package com.jagt.client.application.query;

public record GetClientsQuery(
        int offset,
        int limit
) {
}
