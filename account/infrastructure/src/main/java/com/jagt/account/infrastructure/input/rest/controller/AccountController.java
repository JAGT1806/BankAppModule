package com.jagt.account.infrastructure.input.rest.controller;

import com.jagt.account.application.ports.input.AccountStatusUseCase;
import com.jagt.account.application.ports.input.CreateAccountUseCase;
import com.jagt.account.application.ports.input.GetAccountUseCase;
import com.jagt.account.domain.model.Account;
import com.jagt.account.infrastructure.input.rest.mapper.AccountRestMapper;
import com.jagt.account.infrastructure.input.rest.request.AccountCreateRequest;
import com.jagt.common.domain.model.Pagination;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/accounts")
public class AccountController {
    private final AccountRestMapper mapper;
    private final GetAccountUseCase getAccountUseCase;
    private final CreateAccountUseCase createAccountUseCase;
    private final AccountStatusUseCase accountStatusUseCase;

    @GetMapping
    public Pagination<Account> findAll(@RequestParam(required = false, defaultValue = "0") int offset, @RequestParam(required = false, defaultValue = "12") int limit) {
        return getAccountUseCase.execute(mapper.toQuery(offset, limit));
    }

    @GetMapping("/{id}")
    public Account findById(@PathVariable Long id) {
        return getAccountUseCase.execute(id);
    }

    @GetMapping("/client/{client-id}")
    public Pagination<Account> findByClientId(@PathVariable("client-id") Long clientId, @RequestParam(required = false, defaultValue = "0") int offset, @RequestParam(required = false, defaultValue = "12") int limit) {
        return getAccountUseCase.executeByClientId(clientId, mapper.toQuery(offset, limit));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Account create(@Valid @RequestBody AccountCreateRequest request) {
        return createAccountUseCase.execute(mapper.toCommand(request));
    }

    @PutMapping("/{id}/activate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void activate(@PathVariable Long id) {
        accountStatusUseCase.activate(id);
    }

    @PutMapping("/{id}/deactivate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deactivate(@PathVariable Long id) {
        accountStatusUseCase.deactivate(id);
    }

    @PutMapping("/{id}/cancel")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancel(@PathVariable Long id) {
        accountStatusUseCase.cancel(id);
    }
}
