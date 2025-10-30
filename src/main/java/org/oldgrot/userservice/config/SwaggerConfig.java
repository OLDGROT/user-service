package org.oldgrot.userservice.config;

import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenApiCustomizer removeHateoasModels() {
        return openApi -> openApi.getComponents()
                .getSchemas()
                .entrySet()
                .removeIf(e -> e.getKey().contains("EntityModel")
                        || e.getKey().contains("CollectionModel")
                        || e.getKey().contains("Links")
                        || e.getKey().contains("Link"));
    }
}
