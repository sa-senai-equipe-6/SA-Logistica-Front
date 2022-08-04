package br.senai.logistica.frontend.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Usuario {
	Integer id;

	@NonNull
	String nomeCompleto, login, senha;
	
	@NonNull
	Perfil perfil;
}
