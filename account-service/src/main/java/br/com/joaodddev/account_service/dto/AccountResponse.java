package br.com.joaodddev.account_service.dto;

import br.com.joaodddev.account_service.domain.Account;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record AccountResponse(
        UUID id,
        String ownerName,
        String cpf,
        BigDecimal balance,
        Boolean active,
        LocalDateTime createdAt
) {
    public static AccountResponse from(Account account) {
        return new AccountResponse(
                account.getId(),
                account.getOwnerName(),
                account.getCpf(),
                account.getBalance(),
                account.getActive(),
                account.getCreatedAt()
        );
    }
}
