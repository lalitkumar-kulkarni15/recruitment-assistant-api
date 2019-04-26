package com.recruitment.assistant.appconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationConfig {

    @Bean(name="restTemplate")
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
