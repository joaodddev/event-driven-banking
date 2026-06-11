package br.com.joaodddev.account_service.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record AccountCreatedEvent(
        UUID accountId,
        String ownerName,
        String cpf,
        BigDecimal balance,
        LocalDateTime createdAt
) {}