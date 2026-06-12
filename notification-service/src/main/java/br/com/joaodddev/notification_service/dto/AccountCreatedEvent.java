package br.com.joaodddev.notification_service.dto;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.List;


public record AccountCreatedEvent(
        UUID accountId,
        String ownerName,
        String cpf,
        BigDecimal balance,
        Object createdAt
) {}