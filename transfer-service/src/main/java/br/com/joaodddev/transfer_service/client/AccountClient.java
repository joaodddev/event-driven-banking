package br.com.joaodddev.transfer_service.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@Component
public class AccountClient {

    private final WebClient webClient;

    public AccountClient(@Value("${account-service.url}") String accountServiceUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(accountServiceUrl)
                .build();
    }

    public AccountResponse findById(UUID accountId) {
        return webClient.get()
                .uri("/accounts/{id}", accountId)
                .retrieve()
                .bodyToMono(AccountResponse.class)
                .block();
    }

    public void updateBalance(UUID accountId, BigDecimal newBalance) {
        webClient.patch()
                .uri("/accounts/{id}/balance", accountId)
                .bodyValue(Map.of("balance", newBalance))
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}
