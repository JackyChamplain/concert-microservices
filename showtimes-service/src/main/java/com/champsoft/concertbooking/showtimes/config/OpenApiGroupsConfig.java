package com.champsoft.concertbooking.showtimes.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiGroupsConfig {

    @Bean
    GroupedOpenApi showtimeApi(){
        return GroupedOpenApi.builder()
                .group("showtimes")
                .pathsToMatch("/api/showtimes/**")
                .build();
    }


}
