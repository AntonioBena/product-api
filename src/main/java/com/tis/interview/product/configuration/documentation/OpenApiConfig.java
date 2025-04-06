package com.tis.interview.product.configuration.documentation;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Antonio",
                        email = "antoniotumbic@gmail.com"
                ),
                description = "OpenApi documentation for Product app",
                title = "OpenApi specification - Product CRUD Demo Application",
                version = "1.0"
        ),
        servers = {
                @Server(
                        description = "Local ENV",
                        url = "http://localhost:8080/api/v1"
                )
        },
        security = {
                @SecurityRequirement(
                        name = "basicAuth"
                )
        }

)
@SecurityScheme(
        name = "basicAuth",
        description = "Basic authentication",
        scheme = "basic",
        type = SecuritySchemeType.HTTP
)
public class OpenApiConfig {
}