package com.jagt.client.application.usecase;

import com.jagt.client.application.command.CreateClientCommand;
import com.jagt.client.application.ports.input.CreateClientUseCase;
import com.jagt.client.domain.exception.AgeRestrictionException;
import com.jagt.client.domain.exception.InvalidBirthDateException;
import com.jagt.client.domain.model.Client;
import com.jagt.client.domain.model.value.UserNameValue;
import com.jagt.client.domain.ports.output.ClientPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

@Service
@RequiredArgsConstructor
public class CreateClientUseCaseImpl implements CreateClientUseCase {
    private final ClientPersistencePort clientPersistencePort;

    @Override
    public Client execute(CreateClientCommand command) {
        LocalDate today = LocalDate.now();
        Period period = Period.between(command.birthDate(), today);

        if(command.birthDate().isAfter(today))
            throw new InvalidBirthDateException();

        if(period.getYears() < 18)
            throw new AgeRestrictionException(null);

        Client client = Client.builder()
                .identificationType(command.identificationType())
                .name(UserNameValue.builder()
                        .firstName(command.firstName())
                        .secondName(command.secondName())
                        .firstLastName(command.firstLastName())
                        .secondLastName(command.secondLastName())
                        .build())
                .email(command.email())
                .birthDate(command.birthDate())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        return clientPersistencePort.save(client);
    }
}
