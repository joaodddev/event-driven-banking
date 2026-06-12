package br.com.joaodddev.transfer_service.event;

import java.math.BigDecimal;
import java.util.UUID;
import java.time.LocalDateTime;

public record TransferCompletedEvent(
        UUID transferId,
        UUID sourceAccountId,
        UUID targetAccountId,
        BigDecimal amount,
        LocalDateTime createdAt
) {}
