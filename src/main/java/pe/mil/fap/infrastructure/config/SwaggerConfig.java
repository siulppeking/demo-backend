package pe.mil.fap.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI demoOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Categorías - FAP")
                        .version("v1.0.0")
                        .description("API REST para gestión de categorías del sistema")
                        .contact(new Contact()
                                .name("Ministerio de Defensa - Fuerza Aérea del Perú")
                                .email("sistemas@fap.mil.pe")));
    }
}

