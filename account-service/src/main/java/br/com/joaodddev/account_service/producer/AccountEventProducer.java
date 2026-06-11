package br.com.joaodddev.account_service.producer;

import br.com.joaodddev.account_service.event.AccountCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AccountEventProducer {

    private static final String TOPIC = "account-events";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishAccountCreated(AccountCreatedEvent event) {
        log.info("Publishing account created event for accountId: {}", event.accountId());
        kafkaTemplate.send(TOPIC, event.accountId().toString(), event);
    }
}
