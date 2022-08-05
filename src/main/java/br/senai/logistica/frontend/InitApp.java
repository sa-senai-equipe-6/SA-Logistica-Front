package br.senai.logistica.frontend;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;


//TODO: procurar lista imutavel de motorista
@SpringBootApplication
public class InitApp {

	public static void main(String[] args) {
		
		SpringApplicationBuilder sb = new SpringApplicationBuilder(InitApp.class);
		sb.headless(false);
		sb.run(args);
		
	}

}
