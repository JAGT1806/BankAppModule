package com.jagt.client.application.usecase;

import com.jagt.account.domain.model.Account;
import com.jagt.account.domain.ports.output.AccountPersistencePort;
import com.jagt.client.application.ports.input.DeleteClientUseCase;
import com.jagt.client.application.ports.input.GetClientUseCase;
import com.jagt.client.domain.exception.ClientHasAccountException;
import com.jagt.client.domain.ports.output.ClientPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeleteClientUseCaseImpl implements DeleteClientUseCase {
    private final ClientPersistencePort clientPersistencePort;
    private final AccountPersistencePort accountPersistencePort;
    private final GetClientUseCase getClientUseCase;

    @Override
    public void execute(Long id) {
        getClientUseCase.execute(id);

        List<Account> accounts = accountPersistencePort.findByClientId(id, 0, 12);

        if (!accounts.isEmpty()) throw new ClientHasAccountException(null);

        clientPersistencePort.deleteById(id);
    }
}
