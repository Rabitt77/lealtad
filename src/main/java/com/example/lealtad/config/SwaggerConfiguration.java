package com.example.lealtad.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;


@Configuration
public class SwaggerConfiguration {


        @Value("${info.microservice.version}")
        private String version;

        @Value("${spring.application.name}")
        private String name;

        @Bean
        OpenAPI myOpenAPI() {
            Server devServer = new Server();
            devServer.setUrl("http://localhost:8080");

            Contact contact = new Contact();
            contact.setEmail("ing.oscarblancas@gmail.com");

            License mitLicense = new License()
                    .name("Apache 2.0")
                    .url("http://www.apache.org/licenses/LICENSE-2.0.html");

            Info info = new Info()
                    .title(name)
                    .version(version)
                    .contact(contact)
                    .description("Desafio programa de lealtad")
                    .termsOfService("http://swagger.io/terms/")
                    .license(mitLicense);

            return new OpenAPI().info(info).servers(List.of(devServer));
        }
    }