package br.senai.logistica.frontend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.senai.logistica.frontend.entity.Usuario;
import br.senai.logistica.frontend.route.Entity;
import br.senai.logistica.frontend.route.Rota;

@Service
public class UsuarioService {

	@Autowired
	private Rota rota;
	
	@Autowired
	private ObjectMapper mapper;
	
	public List<Usuario> listarTodosUsuarios() throws JsonMappingException, JsonProcessingException {
		return List.of(mapper.readValue(rota.listar(Entity.USUARIO), Usuario[].class));
	}
	
	public Usuario loginCom(String usuario, String senha) throws JsonMappingException, JsonProcessingException {
		return rota.loginCom(usuario, senha);
	}
	
}
