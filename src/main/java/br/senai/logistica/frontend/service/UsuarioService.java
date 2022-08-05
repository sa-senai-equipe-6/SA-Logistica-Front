package br.senai.logistica.frontend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import br.senai.logistica.frontend.entity.Usuario;
import br.senai.logistica.frontend.route.Rota;

@Service
public class UsuarioService {

	@Autowired
	private Rota rota;
	
	public Usuario loginCom(String usuario, String senha) throws JsonMappingException, JsonProcessingException {
		return rota.loginCom(usuario, senha);
	}
	
}
