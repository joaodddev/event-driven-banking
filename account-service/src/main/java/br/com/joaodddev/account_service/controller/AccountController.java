package br.com.joaodddev.account_service.controller;

import br.com.joaodddev.account_service.dto.AccountRequest;
import br.com.joaodddev.account_service.dto.AccountResponse;
import br.com.joaodddev.account_service.dto.BalanceUpdateRequest;
import br.com.joaodddev.account_service.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountResponse> create(@RequestBody @Valid AccountRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(accountService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<AccountResponse>> findAll() {
        return ResponseEntity.ok(accountService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(accountService.findById(id));
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<AccountResponse> findByCpf(@PathVariable String cpf) {
        return ResponseEntity.ok(accountService.findByCpf(cpf));
    }

    @PatchMapping("/{id}/balance")
    public ResponseEntity<AccountResponse> updateBalance(
            @PathVariable UUID id,
            @RequestBody @Valid BalanceUpdateRequest request) {
        return ResponseEntity.ok(accountService.updateBalance(id, request));
    }
}
