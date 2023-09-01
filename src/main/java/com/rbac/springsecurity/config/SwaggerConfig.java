package com.rbac.springsecurity.config;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components().addSecuritySchemes("BearerAuth",
                        new SecurityScheme().name("token")
                                .type(SecurityScheme.Type.APIKEY).in(SecurityScheme.In.HEADER).scheme("bearer").bearerFormat("JWT")))
                .addSecurityItem(new SecurityRequirement().addList("BearerAuth"))
                .info(new Info().title("spring security rbac")
                        .description("a rbac system implement by shiro")
                        .version("V1.0")
                        .license(new License().name("Apache 2.0").url("http://www.apache.org/licenses/LICENSE-2.0"))
                        .contact(new Contact().name("Shaoshuai-Yan").email("ahutyss@gmail.com").url("https://yanshaoshuai.github.io/")))
                .externalDocs(new ExternalDocumentation()
                        .description("ExternalDocumentation")
                        .url("https://github.com/Yanshaoshuai/JAVA_DEMO"));
    }
}
