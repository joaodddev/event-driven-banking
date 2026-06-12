package br.com.joaodddev.notification_service.dto;

import java.util.UUID;
import java.math.BigDecimal;


public record TransferCompletedEvent(
        UUID transferId,
        UUID sourceAccountId,
        UUID targetAccountId,
        BigDecimal amount,
        Object createdAt
) {}