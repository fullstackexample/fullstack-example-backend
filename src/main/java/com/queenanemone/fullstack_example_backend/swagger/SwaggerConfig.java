package com.queenanemone.fullstack_example_backend.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

  @Bean
  public OpenAPI openAPI() {
    return new OpenAPI()
        .info(new Info()
            .title("Fullstack Example Backend")
            .description("Fullstack 역량을 기르기 위한 프로젝트")
            .version("v1"));
  }
}