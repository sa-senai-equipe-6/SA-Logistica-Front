package br.senai.logistica.frontend.route;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.senai.logistica.frontend.entity.Motorista;
import br.senai.logistica.frontend.entity.Usuario;

@Component
public class Rota {
	
	private final String URL_BASE = "http://localhost:8080/api/";
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private ObjectMapper mapper;
	
    public String listar(Entity entity) {
    	var url = montarUrlPara(entity);
    	ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, HttpEntity.EMPTY, String.class);
    	return response.getBody();
    }
    
    public String listar(Entity entity, String filtro) {
		var url = montarUrlPara(entity) + "/filtro/" + filtro;
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, HttpEntity.EMPTY, String.class);
    	return response.getBody();
	}

	private String montarUrlPara(Entity entity) {
		String url = URL_BASE;
		
		switch (entity) {
			case MOTORISTA: url += "motorista"; break;
			case MEIO_TRANSPORTE: url += "transporte"; break;
			case USUARIO: url += "usuario"; break;
		}
		return url;
	}
	
	public Usuario loginCom(String usuario, String senha) throws JsonMappingException, JsonProcessingException {
		var url = URL_BASE + String.format("usuario/%s/senha/%s", usuario, senha);
		ResponseEntity<String> response = null;
		try {
			response = restTemplate.exchange(url, HttpMethod.GET, HttpEntity.EMPTY, String.class);			
			return mapper.readValue(response.getBody(), Usuario.class);
		} catch (Exception e) {
			tratarException(e);
		}
		return null;
	}

	public void montarRequisicao(Motorista novoMotorista, HttpMethod method) throws JsonMappingException, JsonProcessingException {
		var id = novoMotorista.getId();
		var url = montarUrlPara(Entity.MOTORISTA) + "/id/" + id;
		try {			
			restTemplate.exchange(url, method, HttpEntity.EMPTY, String.class);
		} catch (Exception e) {
			tratarException(e);
		}
	}

	@SuppressWarnings("unchecked")
	private void tratarException(Exception e) throws JsonMappingException, JsonProcessingException {
		e.printStackTrace();
		Map<String, Object> map = new JSONObject(tratarMensagem(e.getMessage())).toMap();
		var teste = (List<HashMap<String, Object>>) map.get("erros");
		for (HashMap<String, Object> map2 : teste) {
			String msg = (String) map2.get("mensagem");
			System.out.println(msg);
			throw new RuntimeException(msg);
		}
	}
	
	private String tratarMensagem(String msg) {
		return msg.replaceAll("^(.{6})\"|\"$", "");
	}
	
	public Motorista buscarMotoristaPor(Integer id) throws JsonMappingException, JsonProcessingException {
		var url = montarUrlPara(Entity.MOTORISTA) + "/id/" + id;
		try {
			ResponseEntity<Motorista> resposta = restTemplate.exchange(url, HttpMethod.GET, HttpEntity.EMPTY, Motorista.class);
			return resposta.getBody();
		} catch (Exception e) {
			tratarException(e);
		}
		return null;
	}

}
