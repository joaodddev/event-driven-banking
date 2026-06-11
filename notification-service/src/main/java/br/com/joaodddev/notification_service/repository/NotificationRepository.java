package br.com.joaodddev.notification_service.repository;

import br.com.joaodddev.notification_service.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, UUID> {

    List<Notification> findByEventTypeOrderByCreatedAtDesc(String eventType);
}
