package br.com.joaodddev.notification_service.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record TransferCompletedEvent(
        UUID transferId,
        UUID sourceAccountId,
        UUID targetAccountId,
        BigDecimal amount,
        Object createdAt
) {}