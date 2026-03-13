package br.com.theroguedev.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI getOpenApi() {

        Contact contact = new Contact();
        contact.name("The Rogue Dev");
        contact.email("theroguedev@gmail.com");

        Info info = new Info();
        info.title("The Rogue Dev API");
        info.version("v1");
        info.description("API da plataforma de estudo colaborativo The Rogue Dev");
        info.contact(contact);

        Server server = new Server();
        server.setUrl("http://localhost:8080");


        return new OpenAPI().info(info).servers(List.of(server));
    }

    @Bean
    public GroupedOpenApi userApi() {
        return GroupedOpenApi.builder()
                .group("User")
                .pathsToMatch("/api/v1/user/**")
                .build();
    }

    @Bean
    public GroupedOpenApi authApi() {
        return GroupedOpenApi.builder()
                .group("Auth")
                .pathsToMatch("/api/v1/auth/**")
                .build();
    }

    @Bean
    public GroupedOpenApi publicationApi() {
        return GroupedOpenApi.builder()
                .group("Publication")
                .pathsToMatch("/api/v1/publication/**")
                .build();
    }

    @Bean
    public GroupedOpenApi forumApi() {
        return GroupedOpenApi.builder()
                .group("Forum")
                .pathsToMatch("/api/v1/forum/**")
                .build();
    }

    @Bean
    public GroupedOpenApi currencyApi() {
        return GroupedOpenApi.builder()
                .group("Currency Virtual")
                .pathsToMatch("/api/v1/currency/virtual/**")
                .build();
    }
}
