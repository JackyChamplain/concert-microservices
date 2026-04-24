package com.champsoft.concertBooking.shared.config;

import io.swagger.v3.oas.models.OpenAPI
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


public class OpenApiConfig {

    public OpenAPI showtimesOpenAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("Showtimes service API")
                        .version("1.0.0")
                        .description("Showtime system -  REST API")
                        );
    }


}
