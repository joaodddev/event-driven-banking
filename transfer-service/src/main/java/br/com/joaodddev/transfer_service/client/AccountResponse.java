package br.com.joaodddev.transfer_service.client;

import java.math.BigDecimal;
import java.util.UUID;

public record AccountResponse(
        UUID id,
        String ownerName,
        String cpf,
        BigDecimal balance,
        Boolean active
) {}