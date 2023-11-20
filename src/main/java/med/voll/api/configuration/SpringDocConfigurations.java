package med.voll.api.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfigurations {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                    .addSecuritySchemes("bearer-key",
                        new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")))
                .info(new Info()
                    .title("Api Voll.med")
                    .description("Api desenvolvida no curso SpringBoot na Alura")
                    .contact(new Contact()
                        .name("Anderson Martins")
                        .email("anderson@betamartins.com")
                    )
                    .version("1.0")
                    .license(new License()
                        .name("Betamartins")
                        .url("https://www.betamartins.com")
                    )
                );
    }

}
