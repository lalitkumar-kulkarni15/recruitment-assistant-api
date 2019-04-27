package com.recruitment.assistant.appconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * This class houses all the application configurations
 * if needed any.
 *
 * @since 26-04-2019
 * @version 1.0
 * @author lalit
 */
@Configuration
public class ApplicationConfig {

    /** This is used to invoke the rest endpoints from the
    application. */
    @Bean(name="restTemplate")
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
