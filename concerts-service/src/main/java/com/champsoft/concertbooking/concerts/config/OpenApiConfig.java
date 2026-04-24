package com.champsoft.concertBooking.shared.config;

import io.swagger.v3.oas.models.OpenAPI
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


public class OpenApiConfig {

    public OpenAPI concertsOpenAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("Concerts service API")
                        .version("1.0.0")
                        .description("Concerts system -  REST API")
                );
    }


}
