package br.com.joaodddev.transfer_service.producer;

import br.com.joaodddev.transfer_service.event.TransferCompletedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TransferEventProducer {

    private static final String TOPIC = "transfer-events";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishTransferCompleted(TransferCompletedEvent event) {
        log.info("Publishing transfer completed event for transferId: {}", event.transferId());
        kafkaTemplate.send(TOPIC, event.transferId().toString(), event);
    }
}
