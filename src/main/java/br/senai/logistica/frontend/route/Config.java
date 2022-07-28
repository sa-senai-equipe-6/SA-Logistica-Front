package br.senai.logistica.frontend.route;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class Config {

	@Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }	
	
}
