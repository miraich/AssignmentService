package com.andrey.assignmentservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.tags.Tag;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("PR Reviewer Assignment Service")
                        .version("1.0.0")
                        .description("Сервис для управления назначением ревьюверов на Pull Request'ы"))
                .tags(List.of(
                        new Tag().name("Teams").description("Операции с командами"),
                        new Tag().name("Users").description("Операции с пользователями"),
                        new Tag().name("PullRequests").description("Операции с Pull Request'ами"),
                        new Tag().name("Statistics").description("Статистика")
                ));
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public")
                .pathsToMatch("/**")
                .build();
    }
}

