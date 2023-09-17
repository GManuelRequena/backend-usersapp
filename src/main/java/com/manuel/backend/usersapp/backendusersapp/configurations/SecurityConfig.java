package com.manuel.backend.usersapp.backendusersapp.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.SecureRandom;

@Configuration
public class SecurityConfig {

    @Bean
    public SecureRandom secureRandom(){
        return new SecureRandom();
    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){

        SecureRandom secureRandom = new SecureRandom();
        return new BCryptPasswordEncoder(10, secureRandom());
    }
}
