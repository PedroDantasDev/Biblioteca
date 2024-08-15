package com.exemplo.biblioteca.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class GoogleBooksConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    private static final String API_KEY = "AIzaSyAzNOI43JqvbjuThKVbBlmuzgYGHp0RF1g";

    public String getApiKey() {
        return API_KEY;
    }
}
