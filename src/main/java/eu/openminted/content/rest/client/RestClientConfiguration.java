package eu.openminted.content.rest.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@ComponentScan("eu.openminted.content")
public class RestClientConfiguration {

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
