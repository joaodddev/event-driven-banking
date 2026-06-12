package br.com.joaodddev.notification_service.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record AccountCreatedEvent(
        UUID accountId,
        String ownerName,
        String cpf,
        BigDecimal balance,
        Object createdAt
) {}