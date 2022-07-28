package br.senai.logistica.frontend.route;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class Rota {
	
	@Autowired
	private RestTemplate restTemplate;
	
    public String listar(Entity entity) {
    	var url = montarUrlParaLista(entity);
    	ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, HttpEntity.EMPTY, String.class);
    	return response.getBody();
    }

	private String montarUrlParaLista(Entity entity) {
		String url = "http://localhost:8080/api/";
		
		switch (entity) {
			case MOTORISTA: url += "motorista"; break;
			case MEIO_TRANSPORTE: url += ""; break;
			case USUARIO: url += ""; break;
		}
		return url;
	}
	
}
