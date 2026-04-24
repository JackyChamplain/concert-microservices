package com.champsoft.concertbooking.reservation.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


public class OpenApiConfig {

    public OpenAPI reservationOpenAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("Reservation service API")
                        .version("1.0.0")
                        .description("Reservation system -  REST API")
                );
    }


}
