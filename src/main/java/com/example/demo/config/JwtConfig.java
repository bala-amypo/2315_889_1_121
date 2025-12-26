package com.example.demo.config;

import com.example.demo.security.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {
    
    @Bean
    public JwtTokenProvider jwtTokenProvider() {
        return new JwtTokenProvider("this_is_a_test_secret_key_must_be_long_enough_for_hmac_sha_which_is_long", 3600000);
    }
}   