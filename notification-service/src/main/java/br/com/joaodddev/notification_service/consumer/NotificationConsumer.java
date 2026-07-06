package br.com.joaodddev.notification_service.consumer;

import br.com.joaodddev.notification_service.domain.Notification;
import br.com.joaodddev.notification_service.repository.NotificationRepository;
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
    public void consumeAccountEvent(String payload) {
        log.info("Received account created event: {}", payload);
        try {
            Notification notification = Notification.builder()
                    .eventType("ACCOUNT_CREATED")
                    .payload(payload)
                    .build();
            notificationRepository.save(notification);
            log.info("Notification saved successfully");
        } catch (Exception e) {
            log.error("Error processing account event: {}", e.getMessage());
        }
    }

    @KafkaListener(topics = "transfer-events", groupId = "notification-service")
    public void consumeTransferEvent(String payload) {
        log.info("Received transfer completed event: {}", payload);
        try {
            Notification notification = Notification.builder()
                    .eventType("TRANSFER_COMPLETED")
                    .payload(payload)
                    .build();
            notificationRepository.save(notification);
            log.info("Notification saved successfully");
        } catch (Exception e) {
            log.error("Error processing transfer event: {}", e.getMessage());
        }
    }
}