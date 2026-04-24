package com.champsoft.concertBooking.shared.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiGroupsConfig {

    @Bean
    GroupedOpenApi customerApi(){
        return GroupedOpenApi.builder()
                .group("customers")
                .pathsToMatch("/api/customers/**")
                .build();
    }


}
