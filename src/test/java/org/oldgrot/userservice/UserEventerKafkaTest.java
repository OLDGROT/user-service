package org.oldgrot.userservice;

import lombok.Getter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.oldgrot.userservice.kafka.UserEventerKafka;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.testcontainers.shaded.org.awaitility.Awaitility;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;



@EmbeddedKafka(partitions = 1, topics = { "user-events" })
@SpringBootTest(properties = {
        "spring.kafka.consumer.value-deserializer=org.apache.kafka.common.serialization.StringDeserializer",
        "spring.kafka.consumer.key-deserializer=org.apache.kafka.common.serialization.StringDeserializer"
})
public class UserEventerKafkaTest {

    @Autowired
    UserEventerKafka userEventerKafka;

    @Autowired
    TestListner listener;

    @BeforeEach
    public void setUp() {
        listener.clean();
    }

    @Test
    public void sendUserCreatedEventOk() {
        userEventerKafka.sendUserCreate("test@email.com");

        Awaitility.await().atMost(Duration.ofSeconds(5))
                .until(() -> listener.getMessages().size() > 0);
        assertEquals("CREATE", listener.getMessage(0));
    }

    @Test
    public void sendUserDeleteEventOk() {
        userEventerKafka.sendUserDelete("test@email.com");

        Awaitility.await().atMost(Duration.ofSeconds(5))
                .until(() -> listener.getMessages().size() > 0);
        assertEquals("DELETE", listener.getMessage(0));
    }

}
