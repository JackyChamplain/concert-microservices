package com.champsoft.concertBooking.shared.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiGroupsConfig {

    @Bean
    GroupedOpenApi concertsApi(){
        return GroupedOpenApi.builder()
                .group("concerts")
                .pathsToMatch("/api/concerts/**")
                .build();
    }


}
