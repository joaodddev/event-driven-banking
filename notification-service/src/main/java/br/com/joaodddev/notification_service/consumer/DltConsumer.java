package br.com.joaodddev.notification_service.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DltConsumer {

    @KafkaListener(topics = "account-events.DLT", groupId = "notification-service-dlt")
    public void consumeAccountDlt(String payload) {
        log.error("Account event failed after all retries. Payload: {}", payload);
    }

    @KafkaListener(topics = "transfer-events.DLT", groupId = "notification-service-dlt")
    public void consumeTransferDlt(String payload) {
        log.error("Transfer event failed after all retries. Payload: {}", payload);
    }
}
