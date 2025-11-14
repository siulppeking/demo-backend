package pe.mil.fap.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Categorías")
                        .version("1.0.0")
                        .description("API REST para gestión de categorías")
                        .contact(new Contact()
                                .name("FAP - Sistema de Gestión")
                                .email("soporte@fap.mil.pe")
                                .url("https://fap.mil.pe")));
    }
}
