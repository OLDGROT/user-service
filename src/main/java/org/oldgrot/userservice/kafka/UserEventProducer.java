package org.oldgrot.userservice.kafka;

public interface UserEventProducer {
    void sendUserCreate(String email);
    void sendUserDelete(String email);
}
