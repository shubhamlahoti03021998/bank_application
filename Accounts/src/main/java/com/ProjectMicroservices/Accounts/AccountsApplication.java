package com.ProjectMicroservices.Accounts;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Account Microservice REST API's Documentation",
				description = "Bank Application Accounts Microservice REST API's Documentation",
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
public class AccountsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountsApplication.class, args);
	}

}
