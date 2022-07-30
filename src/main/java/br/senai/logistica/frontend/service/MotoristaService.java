package br.senai.logistica.frontend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
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
		var motoristasArray = rota.listar(Entity.MOTORISTA);
		Motorista[] readValue = null;
		readValue = mapper.readValue(motoristasArray, Motorista[].class);
		return List.of(readValue);
	}

	public void excluir(Motorista motoristaSelecionado) throws JsonMappingException, JsonProcessingException {
		rota.montarRequisicao(motoristaSelecionado, HttpMethod.DELETE);
	}

	public void cadastrar(Motorista novoMotorista) throws JsonMappingException, JsonProcessingException {
		rota.montarRequisicao(novoMotorista, HttpMethod.POST);
	}
	
	public void editar(Motorista novoMotorista) throws JsonMappingException, JsonProcessingException {
		rota.montarRequisicao(novoMotorista, HttpMethod.PUT);
	}
	
}
