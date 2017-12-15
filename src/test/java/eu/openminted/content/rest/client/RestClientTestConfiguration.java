package eu.openminted.content.rest.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Configuration
public class RestClientTestConfiguration {
    @Bean
    public static PropertyPlaceholderConfigurer propertyPlaceholderConfigurer() {
        final PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
        ppc.setLocation(new ClassPathResource("application.properties"));
        return ppc;
    }

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Value("${content.connector.rest.host}")
    private String restHost;

    @Bean
    public ContentConnectorRestClient contentConnectorRestClient() {
        return new ContentConnectorRestClient(restTemplate(), restHost);
    }
}
