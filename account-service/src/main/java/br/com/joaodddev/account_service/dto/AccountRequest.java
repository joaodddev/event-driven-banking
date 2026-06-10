package br.com.joaodddev.account_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AccountRequest(

        @NotBlank(message = "Owner name is required")
        String ownerName,

        @NotBlank(message = "CPF is required")
        @Size(min = 11, max = 11, message = "CPF must have 11 digits")
        String cpf
) {}
