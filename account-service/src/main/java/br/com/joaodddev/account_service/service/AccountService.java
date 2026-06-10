package br.com.joaodddev.account_service.service;

import br.com.joaodddev.account_service.domain.Account;
import br.com.joaodddev.account_service.dto.AccountRequest;
import br.com.joaodddev.account_service.dto.AccountResponse;
import br.com.joaodddev.account_service.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    @Transactional
    public AccountResponse create(AccountRequest request) {
        if (accountRepository.existsByCpf(request.cpf())) {
            throw new IllegalArgumentException("CPF already registered");
        }

        Account account = Account.builder()
                .ownerName(request.ownerName())
                .cpf(request.cpf())
                .build();

        return AccountResponse.from(accountRepository.save(account));
    }

    @Transactional(readOnly = true)
    public AccountResponse findById(UUID id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        return AccountResponse.from(account);
    }

    @Transactional(readOnly = true)
    public AccountResponse findByCpf(String cpf) {
        Account account = accountRepository.findByCpf(cpf)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        return AccountResponse.from(account);
    }

    @Transactional(readOnly = true)
    public List<AccountResponse> findAll() {
        return accountRepository.findAll()
                .stream()
                .map(AccountResponse::from)
                .toList();
    }
}
