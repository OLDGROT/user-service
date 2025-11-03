package org.oldgrot.userservice.dto.service;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(exclude = "lastHeartbeat")
public class ServiceInstance {
    private final String serviceName;
    private final String host;
    private final String port;
    private long lastHeartbeat;
}
