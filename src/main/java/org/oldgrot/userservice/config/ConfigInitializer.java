package org.oldgrot.userservice.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.oldgrot.userservice.dto.service.ServiceConfig;
import org.oldgrot.userservice.service.ConfigClientService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConfigInitializer {

    private final ConfigClientService configClientService;

    @PostConstruct
    public void init() {
        ServiceConfig config = configClientService.fetchConfig("user-service");
    }
}
