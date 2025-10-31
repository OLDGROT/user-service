package org.oldgrot.userservice.kafka;

import org.oldgrot.userservice.NotificationMessage;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserEventerKafka implements UserEventProducer {

    private static final String USER_TOPIC = "user-events";
    private final KafkaTemplate<String, String> kafkaTemplate;

    public UserEventerKafka(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void sendUserCreate(String email) {
        kafkaTemplate.send(USER_TOPIC, email, NotificationMessage.CREATE.toString());
    }

    @Override
    public void sendUserDelete(String email) {
        kafkaTemplate.send(USER_TOPIC, email, NotificationMessage.DELETE.toString());
    }
}
