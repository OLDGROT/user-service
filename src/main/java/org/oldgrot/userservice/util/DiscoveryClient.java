package org.oldgrot.userservice.util;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

import org.oldgrot.userservice.dto.service.ServiceInstance;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class DiscoveryClient {
    private final WebClient webClient = WebClient.create();
    @Value("${server.port}") private int port;
    @Value("${spring.application.name}") private String serviceName;

    @PostConstruct
    public void register() {
        ServiceInstance instance = new ServiceInstance(serviceName, "user-service", String.valueOf(port));
        webClient.post()
                .uri("http://discovery-service:8080/register")
                .bodyValue(instance)
                .retrieve()
                .toBodilessEntity()
                .subscribe();
    }

    @Scheduled(fixedRate = 10_000)
    public void heartbeat() {
        ServiceInstance instance = new ServiceInstance(serviceName, "user-service", String.valueOf(port));
        webClient.post()
                .uri("http://discovery-service:8080/heartbeat")
                .bodyValue(instance)
                .retrieve()
                .toBodilessEntity()
                .subscribe();
    }
}
