package com.champsoft.concertBooking.shared.config;

import io.swagger.v3.oas.models.OpenAPI
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


public class OpenApiConfig {

    public OpenAPI customersOpenAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("Customers service API")
                        .version("1.0.0")
                        .description("Customers system -  REST API")
                );
    }


}
