
package com.champsoft.concertbooking.reservation.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiGroupsConfig {

    @Bean
    GroupedOpenApi reservationApi(){
        return GroupedOpenApi.builder()
                .group("reservation")
                .pathsToMatch("/api/reservation/**")
                .build();
    }


}
