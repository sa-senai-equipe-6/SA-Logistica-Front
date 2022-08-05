package br.senai.logistica.frontend.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.senai.logistica.frontend.entity.MeioTransporte;
import br.senai.logistica.frontend.route.Entity;
import br.senai.logistica.frontend.route.Rota;

@Service
public class MeioTransporteService {

	@Autowired
	private ObjectMapper mapper;
	
	@Autowired
	private Rota rota;
	
	public void excluir(MeioTransporte transporteSelecionado) throws JsonMappingException, JsonProcessingException {
		rota.montarRequisicao(transporteSelecionado, HttpMethod.DELETE);
	}
	
	public MeioTransporte cadastrar(MeioTransporte transporte) throws JsonProcessingException {
		return (MeioTransporte) rota.cadastrar(transporte);
	}
	
	public MeioTransporte buscarPor(Integer id) throws JsonMappingException, JsonProcessingException {
		return rota.buscarMeioTransportePor(id);
	}
	
	public void editar(MeioTransporte transporte) throws JsonMappingException, JsonProcessingException {
		rota.montarRequisicao(transporte, HttpMethod.PUT);
	}
	
	public List<MeioTransporte> listarPor(String descricao) throws JsonMappingException, JsonProcessingException {
		var motoristasArray = rota.listar(Entity.MEIO_TRANSPORTE, descricao);
		MeioTransporte[] readValue = null;
		readValue = mapper.readValue(motoristasArray, MeioTransporte[].class);
		return Arrays.asList(readValue);
	}
	
}
