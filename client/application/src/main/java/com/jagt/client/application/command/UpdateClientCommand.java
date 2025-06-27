package com.jagt.client.application.command;

public record UpdateClientCommand(
    Long id,
    String firstName,
    String secondName,
    String firstLastName,
    String secondLastName,
    String email
) {
}
