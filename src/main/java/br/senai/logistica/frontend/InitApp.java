package br.senai.logistica.frontend;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;


@SpringBootApplication
public class InitApp {

	public static void main(String[] args) {
		
		SpringApplicationBuilder sb = new SpringApplicationBuilder(InitApp.class);
		sb.headless(false);
		sb.run(args);
		
	}

}
