package org.oldgrot.userservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;

public class KafkaTopicConfig {
    @Bean
    public NewTopic userTopic(String topicName) {
        return TopicBuilder.name(topicName)
                .partitions(1)
                .replicas(1)
                .build();
    }

}
