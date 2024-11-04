package com.spring.security.configuration;

import com.spring.security.service.model.validation.UserValidation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidationConfig {
    
    @Bean
    public UserValidation userValidation() {
        return new UserValidation();
    }
}
