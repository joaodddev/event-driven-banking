package br.com.joaodddev.transfer_service.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.UUID;

public record TransferRequest(

        @NotNull(message = "Idempotency key is required")
        UUID idempotencyKey,

        @NotNull(message = "Source account is required")
        UUID sourceAccountId,

        @NotNull(message = "Target account is required")
        UUID targetAccountId,

        @NotNull(message = "Amount is required")
        @Positive(message = "Amount must be positive")
        BigDecimal amount
) {}