package com.jpumpkin.signupapi.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket

@Configuration
class SwaggerConfig {

    @Bean
    fun docket(): Docket =
        Docket(DocumentationType.SWAGGER_2)
            .useDefaultResponseMessages(false)
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.jpumpkin.signupapi.controller"))
            .paths(PathSelectors.any())
            .build()
            .apiInfo(apiInfo())


    private fun apiInfo() =
        ApiInfoBuilder()
            .title("jpumpkin-signup")
            .description("jpumpkin-signup")
            .version("1.0")
            .build()
}