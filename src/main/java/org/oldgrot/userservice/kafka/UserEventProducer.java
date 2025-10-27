package org.oldgrot.userservice.kafka;

public interface UserEventProducer {
    void sendUserCreate(Long userId);
    void sendUserDelete(Long userId);
}
