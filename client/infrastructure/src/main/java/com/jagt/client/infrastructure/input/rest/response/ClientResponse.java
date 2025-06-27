package com.jagt.client.infrastructure.input.rest.response;

import com.jagt.client.domain.model.enums.IdentificationType;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ClientResponse(
        Long id,
        IdentificationType identificationType,
        String fullName,
        String email,
        LocalDate birthDate,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
