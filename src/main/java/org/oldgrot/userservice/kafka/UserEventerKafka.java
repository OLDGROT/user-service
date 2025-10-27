package org.oldgrot.userservice.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserEventerKafka implements UserEventProducer {

    private static final String USER_CREATE_TOPIC = "user-create";
    private static final String USER_DELETE_TOPIC = "user-delete";
    private final KafkaTemplate<String, String> kafkaTemplate;

    public UserEventerKafka(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void sendUserCreate(Long userId) {
        kafkaTemplate.send(USER_CREATE_TOPIC, userId.toString(), "Пользователь создан с ID: " + userId);
    }

    @Override
    public void sendUserDelete(Long userId) {
        kafkaTemplate.send(USER_DELETE_TOPIC, userId.toString(), "Пользователь удалён с ID: " + userId);
    }
}
