package com.swedbank.parking.common.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration
@EnableSwagger2
class SwaggerConfig {
    @Bean
    fun api(
        @Value("\${swagger.api.title}") title: String,
        @Value("\${swagger.api.description}") description: String,
        @Value("\${swagger.api.version}") version: String,
        @Value("\${swagger.api.base-package}") basePackage: String,
    ): Docket {
        return Docket(DocumentationType.SWAGGER_2)
            .useDefaultResponseMessages(false)
            .select()
            .apis(RequestHandlerSelectors.basePackage(basePackage))
            .paths(PathSelectors.any())
            .build()
            .forCodeGeneration(true)
            .apiInfo(
                ApiInfoBuilder()
                    .title(title)
                    .description(description)
                    .version(version)
                    .build()
            )
    }
}
