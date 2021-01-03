package getir.bookretailfirm.configuration;

import com.google.common.base.Predicates;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.StringVendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Value("${spring.application.name:Api Documentation}")
    private String appName;

    private static final ApiKey API_KEY = new ApiKey(org.springframework.http.HttpHeaders.AUTHORIZATION, org.springframework.http.HttpHeaders.AUTHORIZATION, "header");

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(Predicates.not(RequestHandlerSelectors.basePackage("org.springframework")))
                .paths(PathSelectors.any())
                .build()
                .ignoredParameterTypes(AuthenticationPrincipal.class)
                .apiInfo(apiInfo())
                .securitySchemes(Collections.singletonList(API_KEY))
                .securityContexts(Collections.singletonList(SecurityContext.builder().securityReferences(defaultAuth()).build()));
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                appName,
                "Getir Sample Api",
                "1.0.0",
                "",
                new Contact("Sample", "url", "email"),
                ".",
                ".",
                Collections.singletonList(new StringVendorExtension("vendor", "GetirSample"))
        );
    }

    private static List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope
                = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Collections.singletonList(new SecurityReference(org.springframework.http.HttpHeaders.AUTHORIZATION, authorizationScopes));
    }
}
