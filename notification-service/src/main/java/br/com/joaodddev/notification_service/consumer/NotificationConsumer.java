package br.com.joaodddev.notification_service.consumer;

import br.com.joaodddev.notification_service.domain.Notification;
import br.com.joaodddev.notification_service.dto.AccountCreatedEvent;
import br.com.joaodddev.notification_service.dto.TransferCompletedEvent;
import br.com.joaodddev.notification_service.repository.NotificationRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationConsumer {

    private final NotificationRepository notificationRepository;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "account-events", groupId = "notification-service")
    public void consumeAccountEvent(AccountCreatedEvent event) {
        log.info("Received account created event for accountId: {}", event.accountId());
        try {
            Notification notification = Notification.builder()
                    .eventType("ACCOUNT_CREATED")
                    .payload(objectMapper.writeValueAsString(event))
                    .build();
            notificationRepository.save(notification);
            log.info("Notification saved for accountId: {}", event.accountId());
        } catch (JsonProcessingException e) {
            log.error("Error processing account event: {}", e.getMessage());
        }
    }

    @KafkaListener(topics = "transfer-events", groupId = "notification-service")
    public void consumeTransferEvent(TransferCompletedEvent event) {
        log.info("Received transfer completed event for transferId: {}", event.transferId());
        try {
            Notification notification = Notification.builder()
                    .eventType("TRANSFER_COMPLETED")
                    .payload(objectMapper.writeValueAsString(event))
                    .build();
            notificationRepository.save(notification);
            log.info("Notification saved for transferId: {}", event.transferId());
        } catch (JsonProcessingException e) {
            log.error("Error processing transfer event: {}", e.getMessage());
        }
    }
}
