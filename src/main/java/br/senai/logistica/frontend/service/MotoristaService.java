package br.senai.logistica.frontend.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.senai.logistica.frontend.entity.Motorista;
import br.senai.logistica.frontend.route.Entity;
import br.senai.logistica.frontend.route.Rota;

@Service
public class MotoristaService {
	
	@Autowired
	private ObjectMapper mapper;
	
	@Autowired
	private Rota rota;
	
	public List<Motorista> listarTodosMotoristas() throws JsonMappingException, JsonProcessingException {
		var motoristasArray = rota.listarTodosMotoristas();
		Motorista[] readValue = null;
		readValue = mapper.readValue(motoristasArray, Motorista[].class);
		return Arrays.asList(readValue);
	}
	
	public void excluir(Motorista motoristaSelecionado) throws JsonMappingException, JsonProcessingException {
		rota.montarRequisicao(motoristaSelecionado, HttpMethod.DELETE);
	}

	public Motorista cadastrar(Motorista novoMotorista) throws JsonMappingException, JsonProcessingException {
		return rota.cadastrar(novoMotorista);
	}
	
	public void editar(Motorista novoMotorista) throws JsonMappingException, JsonProcessingException {
		rota.montarRequisicao(novoMotorista, HttpMethod.PUT);
	}

	public List<Motorista> listarPorFiltro(String filtro) throws JsonMappingException, JsonProcessingException {
		var arrayMotoristas = rota.listar(Entity.MOTORISTA, filtro);
		var jsonMotoristas = mapper.readValue(arrayMotoristas, JsonNode.class);
		if (jsonMotoristas.isArray()) {
			return Arrays.asList(mapper.treeToValue(jsonMotoristas, Motorista[].class));
		}
		return Arrays.asList(mapper.treeToValue(jsonMotoristas, Motorista.class));
	}

	public Motorista buscarPor(Integer id) throws JsonMappingException, JsonProcessingException {
		return rota.buscarMotoristaPor(id);
	}
	
}
