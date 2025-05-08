package tech.xirius.payment.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de Swagger para la API de pagos.
 * Esta clase define la configuración de Swagger para la documentación de la
 * API.
 * Se utiliza la anotación @Configuration para indicar que es una clase de
 * configuración de Spring.
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Payment API")
                        .version("1.0"));
    }
}
