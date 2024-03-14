package com.ProjectMicroservices.Cards;

import com.ProjectMicroservices.Cards.dto.CardsContactInfoDTO;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(value = {CardsContactInfoDTO.class})
@OpenAPIDefinition(
		info = @Info(
				title = "Cards Microservice REST API's Documentation",
				description = "Bank Application Cards Microservice REST API's Documentation",
				version = "v1",
				contact = @Contact(
						name = "Shubham Lahoti",
						email = "lahotishubham4@gmail.com"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "",
				url ="localhost:8081/swagger-ui.html"
		)

)
public class CardsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CardsApplication.class, args);
	}

}
