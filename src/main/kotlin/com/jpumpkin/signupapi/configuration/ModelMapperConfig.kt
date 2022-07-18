package com.jpumpkin.signupapi.configuration

import org.modelmapper.ModelMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class ModelMapperConfig {

    @Bean
    fun modelMapper(): ModelMapper =
        ModelMapper().apply {
            configuration.isFieldMatchingEnabled = true
            configuration.fieldAccessLevel = org.modelmapper.config.Configuration.AccessLevel.PRIVATE
        }
}