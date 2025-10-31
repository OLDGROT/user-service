package org.oldgrot.userservice;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
class TestListner {
    private final List<String> messages = new ArrayList<>();

    @KafkaListener(topics = "user-events", groupId = "test-group")
    public void listen(String message) {
        messages.add(message);
    }

    public String getMessage(int index) {
        return messages.get(index);
    }

    public List<String> getMessages(){
        return messages;
    }

    public void clean(){
        messages.clear();
    }
}
