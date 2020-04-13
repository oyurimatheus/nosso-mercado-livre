package me.oyurimatheus.nossomercadolivre.configuration;

import com.google.common.base.Predicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.RequestHandlerSelectors.basePackage;
import static springfox.documentation.spi.DocumentationType.SWAGGER_2;

@Configuration
@EnableSwagger2
class SwaggerConfiguration {

    @Bean
    public Docket api() {
        Predicate<RequestHandler> basePackage = basePackage("me.oyurimatheus.nossomercadolivre");
        Predicate<String> apiUrls = PathSelectors.ant("/api/**");

        return new Docket(SWAGGER_2)
                    .select()
                    .apis(basePackage)
                    .paths(apiUrls)
                    .build();

    }
}
