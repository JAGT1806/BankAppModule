package com.jagt.client.application.command;

import com.jagt.client.domain.model.enums.IdentificationType;

import java.time.LocalDate;

public record CreateClientCommand(
        IdentificationType identificationType,
        String identificationNumber,
        String firstName,
        String secondName,
        String firstLastName,
        String secondLastName,
        String email,
        LocalDate birthDate
) {
}
