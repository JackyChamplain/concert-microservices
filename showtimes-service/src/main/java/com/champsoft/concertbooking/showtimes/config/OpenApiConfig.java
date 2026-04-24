package com.champsoft.concertbooking.showtimes.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
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
